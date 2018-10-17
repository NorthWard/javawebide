package com.north.lat.autocomplete.model;

/**
 * @author laihaohua
 */
public class AutoCompleteCondition {
    private final TipsType tipsType;
    private String word;
    private AutoCompleteCondition(AutoCompleteConditionBuilder builder){
          this.tipsType = builder.tipsType;
          this.word = builder.word;
    }

    public TipsType getTipsType() {
        return tipsType;
    }

    public String getWord() {
        return word;
    }

    private static class AutoCompleteConditionBuilder{
        private final TipsType tipsType;
        private String word;
        AutoCompleteConditionBuilder(TipsType tipsType){
                 this.tipsType = tipsType;
        }
        public void setWord(String word){
            this.word = word;
        }
        public AutoCompleteCondition build(){
            return new AutoCompleteCondition(this);
        }

    }
}
