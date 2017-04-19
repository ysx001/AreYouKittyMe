package com.example.android.areyoukittyme.Quiz_Utilities;

import com.example.android.areyoukittyme.Vocabs_Utilities.VocabList;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Repo;

import java.util.Random;

/**
 * Created by haoyuxiong on 4/18/17.
 */

public class Quiz {

    private Random random = new Random();

    public Quiz(Random random) {

    }


    public boolean checkAnswer(Question question, int chosenIndex) {

        boolean correct = question.getIndexOfRightAnswer() == (chosenIndex-1);
        return correct;

    }

    public void processCorrectAnswer(Question question){
        if(question.getVocab().getProgress() == 0){
            Vocab_Repo.updateAWordOnItsProgress(1,question.getVocab().getVocab_Id(),0);
        }
    }

}
