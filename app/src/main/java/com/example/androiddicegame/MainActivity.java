package com.example.androiddicegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button  spin1btn, spin2btn, addDiceBtn;
    EditText addDiceText;
    TextView historyLabel, resultLabel;
    Spinner dice_spinner;
    SharedPreferences sharedPreferences;
    Context context;


    Integer dice[] = new Integer[]{ 2, 4, 6, 8, 10, 12, 20}; //adding the default dice values

    String result[] = {};

    ArrayList diceList = new ArrayList(Arrays.asList(dice));

    ArrayList resultList = new ArrayList(Arrays.asList(result)); //result string for saving it to history


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData(); //loading the data from shared preference

        spin1btn = (Button) findViewById(R.id.spin1btn);
        spin2btn = (Button) findViewById(R.id.spin2btn);
        addDiceBtn = (Button) findViewById(R.id.addDiceBtn);
        addDiceText = (EditText) findViewById(R.id.addDiceText);
        historyLabel = (TextView) findViewById(R.id.historyLabel);
        resultLabel = (TextView) findViewById(R.id.resultLabel);
        dice_spinner = (Spinner) findViewById(R.id.dice_spinner);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, diceList); //dropdown adapter and passing the array list
        dice_spinner.setAdapter(adapter); //setting the adapter to our spinner

                /*----------------------------------------------
                            Adding new item to spinner
                 -------------------------------------------------*/
        addDiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addDiceText.getText().toString().equals("")) { //checking if the field is empty
                    Toast.makeText(getApplicationContext(),"Oops forgot to enter a number, try again",Toast.LENGTH_SHORT).show();
                } else if (addDiceText.getText().toString().equals("0") || addDiceText.getText().toString().equals("00") || addDiceText.getText().toString().equals("000")|| addDiceText.getText().toString().equals("0000")|| addDiceText.getText().toString().equals("00000") || addDiceText.getText().toString().equals("000000")) {
                    Toast.makeText(getApplicationContext(),"😱 Really!!!, try again",Toast.LENGTH_SHORT).show();
                    addDiceText.setText(""); //checking for zero's and not adding them to list
                } else {
                    int diceText = Integer.parseInt(String.valueOf(addDiceText.getText()));
                    if (!diceList.contains(diceText)){
                        diceList.add(diceText); //adding the items to list

                        /*----------------------------------------------
                            Adding to shared preference
                        -------------------------------------------------*/
                        sharedPreferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE);
                        int diceNumber = Integer.parseInt(String.valueOf(addDiceText.getText()));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("dList", diceNumber);
                        editor.apply();

                        /*----------------------------------------------

                        -------------------------------------------------*/


                        adapter.notifyDataSetChanged(); //letting the spinner adapter know about data change to let them add new item
                        dice_spinner.setAdapter(adapter); //setting spinner adapter
                        int spinnerPosition = adapter.getPosition(diceText);  //getting the spinner position/number
                        dice_spinner.setSelection(spinnerPosition); //setting the spinner position to newly added number
                    }
                    addDiceText.setText(""); //clearing the text field
                }
           }
        });


                /*----------------------------------------------
                            Rolling the dice once
                 -----------------------------------------------*/

        spin1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Die d = new Die(); //constructor for die class
               int noOfSides = (int) dice_spinner.getSelectedItem(); // getting selected value from spinner
                d.SetNoOfSides(noOfSides); //passing the number of sides to die class
               d.roll();

                 Integer currentSide = d.GetCurrentSide(); //getting current side
                resultLabel.setTextSize(18);//
                if (noOfSides == currentSide) { //checking for conditions and if the number is the highest showing a message
                    resultLabel.setText("Congratulations!!! You've got highest number " + currentSide.toString());
                }
                 else {
                    resultLabel.setText(currentSide.toString()); //no message if its not the highest number
                }
                resultList.add(currentSide.toString());
                historyLabel.setText("History: " + resultList); //adding all results to list and displaying it to history label
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

                if (noOfSides == currentSide1 && noOfSides == currentSide2) { //checking if both dice is same and of highest number
                    resultLabel.setTextSize(14); //setting the font to smaller size and displaying the message
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

    private void loadData() {
        sharedPreferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE);
        int diceNumber = sharedPreferences.getInt("dList",0);
        diceList.add(diceNumber);
        if (diceList == null){
            diceList = new ArrayList(Arrays.asList(dice));
        }

    }


}