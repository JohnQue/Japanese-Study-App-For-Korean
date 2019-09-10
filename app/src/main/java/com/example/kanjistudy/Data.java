package com.example.kanjistudy;

public class Data {
    private int index;
    private String kanji;
    private String meaning;
    //private String pronunce;

    Data(int index, String kanji, String meaning){
        this.index = index;
        this.kanji = kanji;
        this.meaning = meaning;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
