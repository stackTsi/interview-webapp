package com.example.interviewWebapp.Dto.ResponsesDTO;

import org.bson.types.ObjectId;

public class SubmitResponseRequestDTO {
    private ObjectId questionId;
    private String userAnswer;
    private int questionOrder;

    public ObjectId getQuestionId() {
        return questionId;
    }

    public void setQuestionId(ObjectId questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
}
