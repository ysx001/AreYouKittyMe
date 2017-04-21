package com.example.android.areyoukittyme.Quiz_Utilities;

import com.example.android.areyoukittyme.Vocabs_Utilities.VocabList;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_DatabaseManager;
import com.example.android.areyoukittyme.Vocabs_Utilities.Vocab_Repo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by haoyuxiong on 4/18/17.
 */

public class Quiz {

    private Random random = new Random();

    protected int record = 0;

    protected ArrayList<Question> questions;

    protected ArrayList<Question> answeredQuestions;

    protected Question currentQuestion;

    protected boolean exited = false;

    public Quiz(boolean mode) throws ParseException {

        questions = Vocab_Repo.convertVocabListsToQuestions(Vocab_Repo.getVocabsToStudy());
        if (mode){
        questions.addAll(Vocab_Repo.convertVocabListsToQuestions(Vocab_Repo.getVocabsReviewableVocab()));}
        this.answeredQuestions = new ArrayList<Question>();
        currentQuestion = this.questions.get(0);

    }

    public boolean getIfThereIsAnyVocab(){
        return !questions.isEmpty();
    }

    public boolean checkAnswer(Question question, int chosenIndex) {

        boolean correct = question.checkAnswer(chosenIndex);

        if(!answeredQuestions.contains(question)){
            answeredQuestions.add(question);
            questions.remove(question);
        }

        return correct;

    }

    public boolean checkCurrentAnswer(int chosenIndex) {

        boolean correct = currentQuestion.checkAnswer(chosenIndex);

        if(!answeredQuestions.contains(currentQuestion)){
            answeredQuestions.add(currentQuestion);
            questions.remove(currentQuestion);
        }

        return correct;

    }



    public void processCorrectAnswer(Question question) throws ParseException {
        if(question.getVocab().getProgress() == 0){
            Vocab_Repo.updateAWordOnItsProgress(1,question.getVocab().getVocab_Id(),0);
        }else if(question.getVocab().getProgress()==1){
            Vocab_Repo.updateAWordOnItsReviewPeriod(question.getVocab().getVocab_Id());
        }

    }

    public void toNextQuestion(){
        this.currentQuestion = getUnansweredQuestion();
    }

    public void reset() {
        this.questions = null;
        this.answeredQuestions = null;
    }

    public Question getUnansweredQuestion(){
        if (this.questions.size()>0){
            return this.questions.get(0);
        }else{
            return null;
        }
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public boolean isExited() {
        return exited;
    }

    public void setExited(boolean exited) {
        this.exited = exited;
    }


}
