package com.example.interviewWebapp.Dto;

import com.example.interviewWebapp.Entity.Enum.Category;
import com.example.interviewWebapp.Entity.Enum.Level;
import com.example.interviewWebapp.Entity.Enum.Type;

import java.util.List;

public class CreateQuestionsRequestDTO {

    private String title;
    private String content;
    private Level level;
    private Category category;
    private Type type;
    private String correctAnswer;
    private List<String> options;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }



}
