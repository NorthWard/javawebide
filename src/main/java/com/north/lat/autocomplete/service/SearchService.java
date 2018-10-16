package com.north.lat.autocomplete.service;

import java.util.List;

public interface SearchService<QUERYTYPE,RESULTTYPE> {
    List<RESULTTYPE> search(QUERYTYPE params);
}
