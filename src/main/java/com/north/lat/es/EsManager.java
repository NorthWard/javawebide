package com.north.lat.es;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author laihaohua
 */
public class EsManager {
    private static volatile EsManager INSTANCE = null;
    private RestHighLevelClient esClient;
    private static final String INDEX_NAME = "north_class_cache_es";
    private EsManager(){
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        builder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(HttpHost host) {

            }
        });
        RestClient restClient = builder.build();
        esClient = new RestHighLevelClient(restClient);
    }
    private static EsManager getInstance(){
        if(INSTANCE == null){
              synchronized (EsManager.class){
                  if(INSTANCE == null){
                       INSTANCE = new EsManager();
                  }
              }
        }
        return INSTANCE;
    }
    public static <T extends Serializable> void index(String type, T source){
        if(StringUtils.isBlank(type) || source == null){
            return;
        }
        String sourceJson = JSON.toJSONString(source);
        IndexRequest request = new IndexRequest(INDEX_NAME,type);
        request.source(sourceJson, XContentType.JSON);
        try {
            IndexResponse indexResponse = getInstance().esClient.index(request, blankHeader());
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("shardInfo.getTotal() != shardInfo.getSuccessful(): sourceJson = " + sourceJson);
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    System.out.println("failure:" + JSON.toJSONString(failure)+" ,sourceJson = " + sourceJson);
                }
            }
            handleResponse(indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T extends Serializable> void put(String type, String id, T source){
        if(StringUtils.isBlank(type) || source == null || StringUtils.isBlank(id)){
            return;
        }
        String sourceJson = JSON.toJSONString(source);
        IndexRequest request = new IndexRequest(INDEX_NAME,type, id);
        request.source(sourceJson, XContentType.JSON);
        try {
            IndexResponse indexResponse = getInstance().esClient.index(request, blankHeader());
            handleResponse(indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void handleResponse(IndexResponse indexResponse){
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("shardInfo.getTotal() != shardInfo.getSuccessful(): ");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                System.out.println("failure:" + JSON.toJSONString(failure));
            }
        }
    }
        public static List<Map<String, Object>> search(String type, String keyword, String ... fields){
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.types(type);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, fields)
                    .fuzziness(Fuzziness.AUTO)
                    .prefixLength(3)
                    .maxExpansions(10);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = getInstance().esClient.search(searchRequest, blankHeader());
            SearchHits hits = searchResponse.getHits();
            List<Map<String, Object>> list = new ArrayList((int)hits.totalHits());
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                list.add(hit.getSource());
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean exists(String type, String id){
        GetRequest getRequest = new GetRequest(INDEX_NAME);
        getRequest.type(type).id(id);
        try {
           return getInstance().esClient.exists(getRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static Header blankHeader(){
        return  new Header() {
            @Override
            public String getName() {
                return "no";
            }

            @Override
            public String getValue() {
                return "no";
            }

            @Override
            public HeaderElement[] getElements() throws ParseException {
                return new HeaderElement[0];
            }
        };
    }

}
