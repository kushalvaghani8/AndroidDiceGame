package com.example.androiddicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    Button  spin1btn, spin2btn, addDiceBtn;
    EditText addDiceText;
    TextView historyLabel, resultLabel;
    Spinner dice_spinner;
    Integer dice[] = new Integer[]{ 2, 4, 6, 8, 10, 12, 20};
    String result[] = {};

    ArrayList diceList = new ArrayList(Arrays.asList(dice));

    ArrayList resultList = new ArrayList(Arrays.asList(result));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin1btn = (Button) findViewById(R.id.spin1btn);
        spin2btn = (Button) findViewById(R.id.spin2btn);
        addDiceBtn = (Button) findViewById(R.id.addDiceBtn);
        addDiceText = (EditText) findViewById(R.id.addDiceText);
        historyLabel = (TextView) findViewById(R.id.historyLabel);
        resultLabel = (TextView) findViewById(R.id.resultLabel);
        dice_spinner = (Spinner) findViewById(R.id.dice_spinner);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, diceList);
        dice_spinner.setAdapter(adapter);

                /*----------------------------------------------
                            Adding new item to spinner
                 -------------------------------------------------*/
        addDiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addDiceText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Oops forgot to enter a number, try again",Toast.LENGTH_SHORT).show();
                } else {
                    int diceText = Integer.parseInt(String.valueOf(addDiceText.getText()));

                    if (!diceList.contains(diceText)) {
                        diceList.add(diceText);
                        adapter.notifyDataSetChanged();
                        dice_spinner.setAdapter(adapter);
                        int spinnerPosition = adapter.getPosition(diceText);
                        dice_spinner.setSelection(spinnerPosition);
                    }
                    addDiceText.setText("");
                }
           }
        });



                /*----------------------------------------------
                            Rolling the dice once
                 -----------------------------------------------*/

        spin1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Die d = new Die();
               int noOfSides = (int) dice_spinner.getSelectedItem();
                d.SetNoOfSides(noOfSides);
               d.roll();

                 Integer currentSide = d.GetCurrentSide();
                 if (noOfSides == currentSide) {
                     resultLabel.setTextSize(18);
                     resultLabel.setText("Congratulations!!! You've got highest number " + currentSide.toString());
                     resultList.add(currentSide.toString());
                     historyLabel.setText("History: " + resultList);
                 }
                 else {
                     resultLabel.setTextSize(18);
                     resultLabel.setText(currentSide.toString());
                     resultList.add(currentSide.toString());
                     historyLabel.setText("History: " + resultList);
                 }
            }
        });

         /*----------------------------------------------
                            Rolling the dice twice
                 -----------------------------------------------*/
        spin2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Die d = new Die();
                int noOfSides = (int) dice_spinner.getSelectedItem();
                d.SetNoOfSides(noOfSides);
                d.roll();
                d.rollTwice();
                Integer currentSide1 = d.GetCurrentSide();
                Integer currentSide2 = d.GetNewCurrentSide();

                if (noOfSides == currentSide1 && noOfSides == currentSide2) {
                    resultLabel.setTextSize(14);
                    resultLabel.setText("Congratulations!!! You've got highest number for both " + currentSide1.toString() + ", " + currentSide2.toString());
                }
                else {
                    resultLabel.setTextSize(18);
                    resultLabel.setText(currentSide1.toString() + ", " + currentSide2.toString());
                }
                resultList.add(currentSide1.toString() + "," + currentSide2.toString());
                historyLabel.setText("History: " + resultList);


            }
        });

    }
}