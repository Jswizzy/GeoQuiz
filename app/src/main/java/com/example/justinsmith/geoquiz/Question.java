package com.example.justinsmith.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mUserAnswer;
    private boolean answered;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mUserAnswer = false;
        answered = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isCorrect() {
        return mUserAnswer == mAnswerTrue;
    }

    public void setUserAnswer(boolean userAnswer) {
        mUserAnswer = userAnswer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered() {
        this.answered = true;
    }
}
