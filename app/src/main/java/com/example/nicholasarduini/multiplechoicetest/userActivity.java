package com.example.nicholasarduini.multiplechoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class userActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText studentNumberText;
    private Button submitButton;

    private static final String provideNameStudentNum = "Please provide a name and student number";
    private static final String provideName = "Please provide a name";
    private static final String provideStudentNum = "Please provide a student number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameText = (EditText) findViewById(R.id.nameText);
        studentNumberText = (EditText) findViewById(R.id.studentNumberText);

        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(userActivity.this, MainActivity.class);;
                Bundle userInfoBundle = new Bundle();
                userInfoBundle.putString(States.STATE_NAME, nameText.getText().toString());
                userInfoBundle.putString(States.STATE_STUDENT_NUMBER, studentNumberText.getText().toString());
                userIntent.putExtra(States.STATE_BUNDLE, userInfoBundle);

                Toast t;
                if (!nameText.getText().toString().isEmpty() && !studentNumberText.getText().toString().isEmpty()) {
                    startActivity(userIntent);
                } else if(!nameText.getText().toString().isEmpty() && studentNumberText.getText().toString().isEmpty()) {
                    t = Toast.makeText(userActivity.this, provideStudentNum, Toast.LENGTH_SHORT);
                    t.show();
                } else if(nameText.getText().toString().isEmpty() && !studentNumberText.getText().toString().isEmpty()) {
                    t = Toast.makeText(userActivity.this, provideName, Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    t = Toast.makeText(userActivity.this, provideNameStudentNum, Toast.LENGTH_SHORT);
                    t.show();
                }
            }

        });
    }
}
