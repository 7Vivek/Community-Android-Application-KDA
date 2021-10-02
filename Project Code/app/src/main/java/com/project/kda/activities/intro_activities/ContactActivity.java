package com.project.kda.activities.intro_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.kda.R;

public class ContactActivity extends AppCompatActivity {
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;
    private Button mButtonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);
        mButtonSend = findViewById(R.id.button_send);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }
    private void sendMail() {
        String recipientList =  "kda1959@gmail.com";
        String[] recipients = recipientList.split(",");
        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    private boolean ValidateData() {
        int ErrCount = 0;
        if (mEditTextSubject.getText().toString().trim().equals("")) {
            mEditTextSubject.setError("Enter Subject!");
            mEditTextSubject.requestFocus();
            ErrCount++;
            return false;
        }

        if (mEditTextMessage.getText().toString().trim().equals("")) {
            mEditTextMessage.setError("Enter Description!");
            mEditTextMessage.requestFocus();
            ErrCount++;
            return false;
        }

        if (ErrCount > 0)
            return false;
        else
            return true;
    }
}
