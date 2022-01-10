package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btncal, btnreset;
    TextView tvDate, tvBMI, tvOpt, tvOutPut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //==================Init==================
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btncal = findViewById(R.id.cal);
        btnreset = findViewById(R.id.reset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOpt = findViewById(R.id.textViewOption);
        tvOutPut = findViewById(R.id.textViewStatus);
        //==================Init==================


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI: 0.0");
                tvOpt.setText("-");
                tvOutPut.setText("-");
            }
        });


        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean a = checkEntry();

                if (a == false) {
                    Toast.makeText(MainActivity.this, "Invalid Input. Please key in value 0 or more.", Toast.LENGTH_SHORT).show();
                }

                else {
                    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                    String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                            (now.get(Calendar.MONTH) + 1) + "/" +
                            now.get(Calendar.YEAR) + " " +
                            now.get(Calendar.HOUR_OF_DAY) + ":" +
                            now.get(Calendar.MINUTE);


                    float W = Float.parseFloat(etWeight.getText().toString());
                    float H = Float.parseFloat(etHeight.getText().toString());

                    float Step1 = H/100;
                    float total = W / (Step1 * Step1);


                    tvDate.setText("Last Calculated Date: " + datetime);
                    tvBMI.setText(String.format("Last Calculated BMI: %.3f", total));
                    etHeight.setText("");
                    etWeight.setText("");

                    if (total < 18.5) {
                        tvOutPut.setText("You are considered underweight and possibly malnourished");
                        tvOpt.setText("UnderWeight");
                        tvOpt.setTextColor(Color.parseColor("#d9d9d9"));

                    } else if (total >= 18.5 && total <= 24.9) {
                        tvOutPut.setText("You are within a healthy weight range for young and middle-aged adults");
                        tvOpt.setText("Normal");
                        tvOpt.setTextColor(Color.parseColor("#00ab4c"));
                    } else if (total >= 25 && total <= 29.9) {
                        tvOutPut.setText("You are at slight risk of health issues");
                        tvOpt.setText("Overweight");
                        tvOpt.setTextColor(Color.parseColor("#fb9800"));

                    } else if (total > 30) {
                        tvOutPut.setText("You are at a high risk of health issues");
                        tvOpt.setText("obese");
                        tvOpt.setTextColor(Color.parseColor("#ff0000"));

                    } else {
                        tvOpt.setText("Invalid BMI");
                    }
                }
            }
        });
    }


    //==================================FuncZone=======================================
    @Override
    protected void onPause() {
        super.onPause();

        String strDate = tvDate.getText().toString();
        String strBMI = tvBMI.getText().toString();
        String strOPT = tvOpt.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date", strDate);
        prefEdit.putString("bmi", strBMI);
        prefEdit.putString("option", strOPT);
        prefEdit.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String strDate = prefs.getString("date","Last Calculated Date:");
        String strBMI = prefs.getString("bmi", "Last Calculated BMI:");
        String strOPT = prefs.getString("option", "");

        tvDate.setText(strDate);
        tvOpt.setText(strOPT);
        tvBMI.setText(strBMI);
        tvOutPut.setText("-");
        tvOpt.setText("-");
        tvOpt.setTextColor(Color.parseColor("#000000"));
    }

    private boolean checkEntry(){
        String He = etHeight.getText().toString();
        String We = etWeight.getText().toString();

        if(He.isEmpty() || We.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }
    //==================================FuncZone=======================================
}
