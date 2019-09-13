package com.example.kanjistudy;

public class ItemData {
    private String kanji;
    private String meanAndPronc;

    public ItemData(String kanji, String meanAndPronc) {
        this.kanji = kanji;
        this.meanAndPronc = meanAndPronc;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getMeanAndPronc() {
        return meanAndPronc;
    }

    public void setMeanAndPronc(String meanAndPronc) {
        this.meanAndPronc = meanAndPronc;
    }
}
