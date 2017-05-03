package com.example.android.areyoukittyme.Vocabs_Utilities;

/**
 * Created by haoyuxiong on 4/13/17.
 */

public class Vocab {

    public static final String TAG = Vocab.class.getSimpleName();
    public static final String TABLE = "Vocab";
    // Labels Table Columns names
    public static final String _ID = "_id";
    public static final String KEY_WORD = "Word";
    public static final String KEY_DEFINITION = "Definition";
    public static final String KEY_DAY = "Days";
    public static final String KEY_PROGRESS = "Progress";
    public static final String KEY_DATE = "Date";


    private int Vocab_Id;
    private String word;
    private String definition;
    private String day;
    private int progress;
    private String date;


    public Vocab(){}

    public Vocab(int vocab_Id, String word, String definition,  int progress,String day, String date) {
        Vocab_Id = vocab_Id;
        this.word = word;
        this.definition = definition;
        this.day = day;
        this.progress = progress;
        this.date = date;
    }

    public Vocab(String word, String definition, String day, int progress, String date) {
        this.word = word;
        this.definition = definition;
        this.day = day;
        this.progress = progress;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
