package com.example.android.areyoukittyme.Quiz_Utilities;

/**
 * Created by haoyuxiong on 4/18/17.
 */

public class Answer {

    private final String definition;
    private final boolean isCorrect;

    public Answer(final String definition, boolean isCorrect) {
        this.definition = definition;
        this.isCorrect = isCorrect;
    }

    public String getDefinition() {
        return definition;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
