package com.example.android.areyoukittyme.Quiz_Utilities;

import com.example.android.areyoukittyme.Vocabs_Utilities.VocabList;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Repo;

import java.util.ArrayList;

/**
 * Created by haoyuxiong on 4/18/17.
 */

public class Question {

    private String[] answers;
    private int indexOfRightAnswer;
    private VocabList vocab;

    public Question(VocabList vocab) {
        answers = new String[4];
        indexOfRightAnswer = Vocab_Repo.random.nextInt(4);
        answers[indexOfRightAnswer] = vocab.getDefinition();
        this.vocab = vocab;
        ArrayList<String> incorrectAnswers = Vocab_Repo.getRandomThreeDefinitions(vocab.getVocab_Id());
        int i = 0;
        for (String j: incorrectAnswers){
            if(answers[i]!= null){
                i += 1;
            }
            answers[i] = j;
            i += 1;
        }
    }

    public boolean checkAnswer(int chosenAnswer){
        return chosenAnswer == this.indexOfRightAnswer;
    }

    public int getIndexOfRightAnswer() {
        return indexOfRightAnswer;
    }

    public void setIndexOfRightAnswer(int indexOfRightAnswer) {
        this.indexOfRightAnswer = indexOfRightAnswer;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public VocabList getVocab() {
        return vocab;
    }

    public void setVocab(VocabList vocab) {
        this.vocab = vocab;
    }
}
