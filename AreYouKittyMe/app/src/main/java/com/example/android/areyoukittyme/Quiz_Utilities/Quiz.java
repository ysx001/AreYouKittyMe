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

    protected int numOfQuestions;
    protected int numOfQuestionStudiedToday;
    protected int numOfQuestionGotRight;
    protected int numOfQuestionKnwon;

    public Quiz(boolean mode) throws ParseException {
        if (!mode){
            questions = Vocab_Repo.convertVocabListsToQuestions(Vocab_Repo.getVocabsToStudy());
            questions.addAll(Vocab_Repo.convertVocabListsToQuestions(Vocab_Repo.getVocabsReviewableVocab()));
        }else{
            questions = Vocab_Repo.convertVocabListsToQuestions(Vocab_Repo.getVocabsReviewableVocab());
        }
        this.answeredQuestions = new ArrayList<Question>();
        currentQuestion = this.questions.get(0);
    }

    /**
     * Checks if there is any vocab left.
     *
     * @return true if there is, otherwise false.
     */
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

    /**
     * Checks if the current answer is correct.
     *
     * @param chosenIndex The index of the answer to be checked.
     * @return True if correct, false otherwise.
     */
    public boolean checkCurrentAnswer(int chosenIndex) {
        boolean correct = currentQuestion.checkAnswer(chosenIndex);

        if(!answeredQuestions.contains(currentQuestion)){
            answeredQuestions.add(currentQuestion);
            questions.remove(currentQuestion);
        }


        return correct;
    }

    /**
     * Processes the correct answer.
     *
     * @param question The question of the answer.
     * @throws ParseException
     */
    public void processCorrectAnswer(Question question) throws ParseException {
        if(question.getVocab().getProgress() == 0){
            Vocab_Repo.updateAWordOnItsProgress(1,question.getVocab().getVocab_Id(),0);
            //numOfQuestionStudiedToday += 1;
        }else if(question.getVocab().getProgress()==1){
            Vocab_Repo.updateAWordOnItsReviewPeriod(question.getVocab().getVocab_Id());
        }
    }

    public void processYoAnswer(Question question) throws ParseException {
        if(question.getVocab().getProgress() == 0){
            Vocab_Repo.updateAWordOnItsProgress(1,question.getVocab().getVocab_Id(),0);
            //numOfQuestionStudiedToday += 1;
        }
    }

    /**
     * Processes known vocabularies.
     *
     * @param question The question to which the vocabs belong to.
     */
    public void processKnownVocab(Question question){
        if(!answeredQuestions.contains(currentQuestion)){
            answeredQuestions.add(currentQuestion);
            questions.remove(currentQuestion);
        }

        Vocab_Repo.updateAWordOnItsProgress(2,question.getVocab().getVocab_Id(),0);
    }

    /**
     * Change the current question to the next.
     */
    public void toNextQuestion(){
        this.numOfQuestionStudiedToday += 1;
        this.currentQuestion = getUnansweredQuestion();
    }

    public int getNumOfQuestionStudiedToday() {
        return numOfQuestionStudiedToday;
    }

    /**
     * Gets the unanswer question to be displayed
     *
     * @return The question that is not answered.
     */
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
