package com.example.android.areyoukittyme.Vocabs_Utilities;

/**
 * Created by haoyuxiong on 4/15/17.
 */

public class VocabList {

    private int Vocab_Id;
    private String word;
    private String definition;
    private int progress;

    public VocabList() {
    }

    public int getVocab_Id() {
        return Vocab_Id;
    }

    public void setVocab_Id(int vocab_Id) {
        Vocab_Id = vocab_Id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
