package edu.csumb.tohernandez.averagecalculator;

import java.text.DecimalFormat;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class mainCalulatorActivity extends AppCompatActivity implements OnClickListener{

    private Button calculateButton;
    private TextView gradeTextView, averageTextView;
    private EditText scoreOneText, scoreTwoText;
    private String scoreOneString = "", scoreTwoString = "", gradeLetter = "", averageScoreText = "";
    private SharedPreferences savedValues;
    private double averageScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calulator);

        calculateButton = (Button) findViewById(R.id.calculateButton);
        gradeTextView = (TextView) findViewById(R.id.letterLabel);
        averageTextView = (TextView) findViewById(R.id.scoreLabel);
        scoreOneText = (EditText) findViewById(R.id.scoreOneText);
        scoreTwoText = (EditText) findViewById(R.id.scoreTwoText);

        calculateButton.setOnClickListener(this);
        savedValues = getPreferences(MODE_PRIVATE);

    }

    public void calculateAverage(){
        double scoreOne, scoreTwo;
        scoreOneString = scoreOneText.getText().toString();
        scoreTwoString = scoreTwoText.getText().toString();

        if(!scoreOneString.equals("") && !scoreTwoString.equals("")){
            scoreOne = Double.parseDouble(scoreOneString);
            scoreTwo = Double.parseDouble(scoreTwoString);
            if(scoreOne > 100 || scoreTwo > 100){
                Toast t = Toast.makeText(this, "Enter values less than 100", Toast.LENGTH_SHORT);
                t.show();
            }else{
                averageScore = (scoreOne + scoreTwo)/2.0;
                Log.d("AverageCalculator", Double.toString(averageScore));
                setLetterAndScoreText(averageScore);
            }
        }else{
            Toast t = Toast.makeText(this, "Missing values in field.", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void setLetterAndScoreText(double averageScore){

        String pattern = "###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        averageScoreText = decimalFormat.format(averageScore);

        averageTextView.setText(averageScoreText);

        if(averageScore <= 100 && averageScore >= 90){
            gradeLetter = "A";
            gradeTextView.setTextColor(Color.GREEN);
        } else if(averageScore < 90 && averageScore >= 87){
            gradeLetter = "B+";
            gradeTextView.setTextColor(Color.GREEN);
        }else if(averageScore < 87 && averageScore >= 83){
            gradeLetter = "B";
            gradeTextView.setTextColor(Color.BLUE);
        } else if(averageScore < 83 && averageScore >= 80){
            gradeLetter = "B-";
            gradeTextView.setTextColor(Color.BLUE);
        }else if(averageScore <= 80 && averageScore >= 70){
            gradeLetter = "C";
            gradeTextView.setTextColor(Color.rgb(255,165,0));
        }else if(averageScore <= 70 && averageScore >= 60){
            gradeLetter = "D";
            gradeTextView.setTextColor(Color.RED);
        }else {
            gradeLetter = "F";
            gradeTextView.setTextColor(Color.RED);
        }
        gradeTextView.setText(gradeLetter);
        Toast t = Toast.makeText(this, "Grade: " + gradeLetter , Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void onClick(View v){
        calculateAverage();
    }

    @Override
    protected void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("scoreOneString", scoreOneString);
        editor.putString("scoreTwoString", scoreTwoString);
        editor.putString("gradeLetter", gradeLetter);
        editor.putString("averageScore", averageScoreText);
        editor.apply();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scoreOneText.setText(savedValues.getString("scoreOneString", ""));
        scoreTwoText.setText(savedValues.getString("scoreTwoString", ""));
        gradeTextView.setText(savedValues.getString("gradeLetter", "-"));
        averageTextView.setText(savedValues.getString("averageScore", "-"));
    }
}
