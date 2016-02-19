package com.example.nicholasarduini.multiplechoicetest;

public class Question {

    private String mQuestionString; //id of string resource representing the question
    private String aAnswer;
    private String bAnswer;
    private String cAnswer;
    private String dAnswer;
    private String eAnswer;
    private String guessedAnswer = "";

    public Question(String aQuestion, String a, String b, String c, String d, String e){
        mQuestionString = aQuestion;
        aAnswer = a;
        bAnswer = b;
        cAnswer = c;
        dAnswer = d;
        eAnswer = e;
    }

    public String getQuestionString(){return mQuestionString;}
    public String getaAnswer(){return aAnswer;}
    public String getbAnswer(){return bAnswer;}
    public String getcAnswer(){return cAnswer;}
    public String getdAnswer(){return dAnswer;}
    public String geteAnswer(){return eAnswer;}
    public String getGuessedAnswer(){return guessedAnswer;}

    public void setQuessedAnswer(String answer){
        guessedAnswer = answer;
    }

    public String answersToString(){
        if(guessedAnswer.equals(States.STATE_A_ANSWER)){
            return "(A) " + aAnswer;
        } else if(guessedAnswer.equals(States.STATE_B_ANSWER)){
            return "(B) " + bAnswer;
        } else if(guessedAnswer.equals(States.STATE_C_ANSWER)){
            return "(C) " + cAnswer;
        } else if(guessedAnswer.equals(States.STATE_D_ANSWER)){
            return "(D) " + dAnswer;
        } else if(guessedAnswer.equals(States.STATE_E_ANSWER)){
            return "(E) " + eAnswer;
        }
        return "";
    }
}
