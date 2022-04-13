package com.example.androiddicegame;

public class Die {

        private int noOfSides;
        private int currentSideUp;
        private int newCurrentSideup;

        public void SetNoOfSides(int sides){
            noOfSides = sides;
        }


        public int GetNoOfSides(){
            return noOfSides;
        }

        public int GetCurrentSide(){
            return currentSideUp;
        }

        public int GetNewCurrentSide(){
            return newCurrentSideup;
        }




        public int roll(){
            //setting the maximum number of sides as per the dice's max number of sides
            int max = GetNoOfSides();
            this.currentSideUp =  ((int) ((Math.random() * (max)) + 1));
          // System.out.println(currentSideUp);
            return currentSideUp;
        }

        public int rollTwice(){
            int max = GetNoOfSides();
            this.newCurrentSideup =  ((int) ((Math.random() * (max)) + 1));
            // System.out.println(currentSideUp);
            return newCurrentSideup;
        }
}
