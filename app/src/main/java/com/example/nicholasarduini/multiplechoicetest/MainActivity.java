package com.example.nicholasarduini.multiplechoicetest;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button aButton;
    private Button bButton;
    private Button cButton;
    private Button dButton;
    private Button eButton;
    private Button backButton;
    private Button nextButton;

    private TextView mQuestionTextView;
    private ArrayList<Question> questions;

    FloatingActionButton fab;

    private int questionIndex = 0;

    private String name;
    private String studentNumber;

    //string to be sent in the email
    public String emailAnswersToString(){
        String output = name + "\n" + studentNumber + "\n" + "\nYou answered \n";
        int questionNum;
        for(int i = 0; i < questions.size(); i++){
            questionNum = i + 1;
            output += "Question " + questionNum + ": " + questions.get(i).answersToString() + "\n";
        }
        return output;
    }

    //press the appropriate button when a question is answered
    public void buttonPress(String answer){
        aButton.setPressed(false);
        bButton.setPressed(false);
        cButton.setPressed(false);
        dButton.setPressed(false);
        eButton.setPressed(false);
        if (answer.equals(States.STATE_A_ANSWER)) {
            aButton.setPressed(true);
        } else if (answer.equals(States.STATE_B_ANSWER)) {
            bButton.setPressed(true);
        } else if (answer.equals(States.STATE_C_ANSWER)) {
            cButton.setPressed(true);
        } else if (answer.equals(States.STATE_D_ANSWER)) {
            dButton.setPressed(true);
        } else if (answer.equals(States.STATE_E_ANSWER)) {
            eButton.setPressed(true);
        }
        questions.get(questionIndex).setQuessedAnswer(answer);
    }

    //fill buttons with their answers
    public void populateButton(){
        aButton.setText("a. " + questions.get(questionIndex).getaAnswer());
        bButton.setText("b. " + questions.get(questionIndex).getbAnswer());
        cButton.setText("c. " + questions.get(questionIndex).getcAnswer());
        dButton.setText("d. " + questions.get(questionIndex).getdAnswer());
        eButton.setText("e. " + questions.get(questionIndex).geteAnswer());
    }

    //check if all the questions have been answered, if so show the submit button
    public boolean checkSelectedAnswers(){
        for(int i = 0; i < questions.size(); i++){
            if(questions.get(i).getGuessedAnswer().equals("")){
                fab.hide();
                return false;
            }
        }
        fab.show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //stay on the proper questions index on rotate
        if(savedInstanceState != null && savedInstanceState.containsKey(States.STATE_ROTATE)){
            questionIndex = savedInstanceState.getInt(States.STATE_ROTATE);
        }

        //get the users name and student id
        Intent userIntent = getIntent();
        Bundle userBundle = userIntent.getBundleExtra(States.STATE_BUNDLE);
        name = userBundle.getString(States.STATE_NAME);
        studentNumber = userBundle.getString(States.STATE_STUDENT_NUMBER);

        //email button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Exam.getEmail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Marvel Test Results");
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailAnswersToString());
                startActivity(Intent.createChooser(emailIntent, "Email Client ..."));
            }
        });

        aButton = (Button) findViewById(R.id.aButton);
        bButton = (Button) findViewById(R.id.bButton);
        cButton = (Button) findViewById(R.id.cButton);
        dButton = (Button) findViewById(R.id.dButton);
        eButton = (Button) findViewById(R.id.eButton);
        backButton = (Button) findViewById(R.id.backButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setTextColor(Color.BLUE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionIndex > 0) {
                    questionIndex--;

                    checkSelectedAnswers();
                    populateButton();

                    mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                            questions.get(questionIndex).getQuestionString());

                    buttonPress(questions.get(questionIndex).getGuessedAnswer());
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionIndex < questions.size() - 1) {
                    questionIndex++;

                    checkSelectedAnswers();
                    populateButton();

                    mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                            questions.get(questionIndex).getQuestionString());

                    buttonPress(questions.get(questionIndex).getGuessedAnswer());

                } else {
                    mQuestionTextView.setText("Test is over, please email your results");
                }
            }
        });

        aButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(States.STATE_A_ANSWER);
                checkSelectedAnswers();
                return true;
            }
        });

        bButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(States.STATE_B_ANSWER);
                checkSelectedAnswers();
                return true;
            }
        });

        cButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(States.STATE_C_ANSWER);
                checkSelectedAnswers();
                return true;
            }
        });

        dButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(States.STATE_D_ANSWER);
                checkSelectedAnswers();
                return true;
            }
        });

        eButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonPress(States.STATE_E_ANSWER);
                checkSelectedAnswers();
                return true;
            }
        });

        ArrayList<Question> parsedModel = null;
        try {
            InputStream iStream = getResources().openRawResource(R.raw.sample_exam);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
            parsedModel = Exam.pullParseFrom(bReader);
            bReader.close();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }

        if(parsedModel == null || parsedModel.isEmpty())
            Log.i(TAG, "ERROR: Questions Not Parsed");
        questions = parsedModel;

        if (questions != null && questions.size() > 0)
            mQuestionTextView.setText("" + (questionIndex + 1) + ") " +
                    questions.get(questionIndex).getQuestionString());

        if(questionIndex == 0) {
            populateButton();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(States.STATE_ROTATE, questionIndex);
        for(int i = 0; i < questions.size(); i++) {
            outState.putString(String.valueOf(i), questions.get(i).getGuessedAnswer());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        populateButton();
        for(int i = 0; i < questions.size(); i++) {
            questions.get(i).setQuessedAnswer(savedInstanceState.getString(String.valueOf(i)));
            buttonPress(questions.get(i).getGuessedAnswer());
        }
        checkSelectedAnswers();
        questions.get(questionIndex).setQuessedAnswer(savedInstanceState.getString(String.valueOf(questionIndex)));
        buttonPress(questions.get(questionIndex).getGuessedAnswer());
        super.onRestoreInstanceState(savedInstanceState);
    }
}