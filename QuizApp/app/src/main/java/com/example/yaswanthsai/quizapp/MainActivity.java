package com.example.yaswanthsai.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText answer5;
    EditText answer6;
    RadioButton[] optionRadioButtons;
    CheckBox[] optionCheckBoxes;
    int[] optionRadioButtonsIds = {R.id.option11, R.id.option12, R.id.option13, R.id.option21, R.id.option22, R.id.option23};
    int[] optionCheckBoxIds = {R.id.option31, R.id.option32, R.id.option33, R.id.option34, R.id.option41, R.id.option42, R.id.option43, R.id.option44};
    Button submitButton;
    Button resetButton;
    int correct = 0;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answer5 = findViewById(R.id.answer5);
        answer6 = findViewById(R.id.answer6);
        submitButton = findViewById(R.id.submit);
        resetButton = findViewById(R.id.reset);
        optionRadioButtons = new RadioButton[6];
        optionCheckBoxes = new CheckBox[8];
        for (int i = 0; i < 6; i++)
            optionRadioButtons[i] = findViewById(optionRadioButtonsIds[i]);
        for (int i = 0; i < 8; i++)
            optionCheckBoxes[i] = findViewById(optionCheckBoxIds[i]);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswers();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAnswers();
            }
        });
    }

    public void submitAnswers() {
        correct = 0;
        score = 0;
        /* RadioButton
         * Question1
         * 1. What is fragment in android ?
         * Answer : A Piece of Activity (option2)
         */
        if (optionRadioButtons[1].isChecked())
            correct += 1;

        /* RadioButton
         * Question2
         * 2. What is ANR in android ?
         * Answer : When the Application is not Responding ANR will Occur (option 1)
         */
        if (optionRadioButtons[3].isChecked())
            correct += 1;

        /* CheckBoxes
         * Question3
         * 3. Which of the following are Views in Android ?
         * Answer : textView,EditText,Button (options 1,2,4)
         */
        if (optionCheckBoxes[0].isChecked() && optionCheckBoxes[1].isChecked() && !optionCheckBoxes[2].isChecked() && optionCheckBoxes[3].isChecked())
            correct += 1;
        /* CheckBoxes
         * Question4
         *  4. Choose Valid Statements about ViewGroups ?
         *  Answer :(2)There Can Be One Root View
         *          (3)A ViewGroup that Contains other view is called the Parent
         *          (4)A View which is inside the RootView is called Children
         *          (options 2,3,4)
         */
        if (!optionCheckBoxes[4].isChecked() && optionCheckBoxes[5].isChecked() && optionCheckBoxes[6].isChecked() && optionCheckBoxes[7].isChecked())
            correct += 1;

        /* TextFormat
         * Question5
         * 5. A Method Can Be Initiated By?
         * Answer : object
         */

        if (answer5.getText().toString().equalsIgnoreCase("object"))
            correct += 1;
        /* TextFormat
         * Question6
         * 6. Which folder of resources contains styles.xml ?
         * Answer : values
         */
        if (answer6.getText().toString().equalsIgnoreCase("values"))
            correct += 1;

        /*Each question 5 Points*/
        score = correct * 5;

        if (correct == 6)
            Toast.makeText(this, "THE BEST, Your Score :" + score + "/30", Toast.LENGTH_LONG).show();
        else if (correct < 6 && correct > 2)
            Toast.makeText(this, "Good,keep Going Your Score :" + score + "/30 ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "You Need Practise Your Score :" + score + "/30", Toast.LENGTH_LONG).show();

    }

    public void resetAnswers() {
        correct = 0;
        score = 0;
        for (int i = 0; i < 6; i++)
            optionRadioButtons[i].setChecked(false);
        for (int i = 0; i < 8; i++)
            optionCheckBoxes[i].setChecked(false);
        answer5.setText("");
        answer6.setText("");
    }

}
