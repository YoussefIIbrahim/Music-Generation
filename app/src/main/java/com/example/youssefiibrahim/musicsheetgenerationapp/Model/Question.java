package com.example.youssefiibrahim.musicsheetgenerationapp.Model;

import java.util.List;

import static com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common.categoryId;

public class Question {
    private String Question, CorrectAnswer, CategoryId, IsImageQuestion;
    private List<String> Answers;

    public Question() {
    }

    public Question(String question, String correctAnswer, String categoryId, String isImageQuestion, List<String> answers) {
        Question = question;
        CorrectAnswer = correctAnswer;
        CategoryId = categoryId;
        IsImageQuestion = isImageQuestion;
        Answers = answers;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }

    public List<String> getAnswers() {
        return Answers;
    }

    public void setAnswers(List<String> answers) {
        Answers = answers;
    }
}
