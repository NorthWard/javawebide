package com.north.lat.autocomplete.model;


/**
 * @author laihaohua
 */
public class AutoCompleteTipItem {
    private final String value;
    private final String displayText;
    private final String extend;

    private AutoCompleteTipItem(AutoCompleteTipItemBuilder builder){
          this.displayText = builder.displayText;
          this.value = builder.value;
          this.extend = builder.extend;
    }
    public String getValue() {
        return value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getExtend() {
        return extend;
    }

    public static class AutoCompleteTipItemBuilder {
        private final String value;
        private final String displayText;
        private String extend;
        public AutoCompleteTipItemBuilder(String value, String displayText){
            this.displayText = displayText;
            this.value = value;
        }

        public AutoCompleteTipItemBuilder setExtend(String extend) {
            this.extend = extend;
            return this;
        }
        public  AutoCompleteTipItem build(){
            return new AutoCompleteTipItem(this);
        }
    }
}
