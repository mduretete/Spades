package edu.up.cs301.spadestest;

import android.util.Log;

/**
 * Created by duretete16 on 11/8/2015.
 */
public class Card {
    //constants that have no reason to ever be changed, corresponding with suit
    private final String SPADES = "S";
    private final String CLUBS = "C";
    private final String HEARTS = "H";
    private final String DIAMONDS = "D";
    private final String DEFAULT = "S"; //defaults to spades upon programmer error

    //rank and suit variables for the Card ctor
    private int rank;
    private String suit;

    /**
     * Card(): ctor for Card object
     * @param r int that holds the rank of a Card object, 2-13
     * @param s string that holds the suit of a Card object
     */
    public Card(int r, String s) {
        if(rankCheck(r)){
            rank = r;
        }
        if(suitCheck(s)){
            suit = s;
        }
    }

    /**
     * getRank(): getter for the rank of a Card object
     * @return int, equivalent to the rank of the Card object
     */
    public int getRank(){
        return this.rank;
    }

    /**
     * getSuit(): getter for the suit of a Card object
     * @return string, equivalent to the suit of the Card object
     */
    public String getSuit(){
        return this.suit;
    }

    /**
     * setRank(): setter for the rank of a Card object, checks for validity
     * @param r int that sets the rank for a Card object
     */
    public void setRank(int r){
        if(rankCheck(r)){
            this.rank = r;
        }
    }

    public void setSuit(String s){
        if(suitCheck(s)) {
            this.suit = s;
        }
    }

    public boolean rankCheck(int r){
        if(r < 2 || r > 13){
            Log.d("RANK ERROR", "Rank out of range: "+ r);
            return false;
        }
        return true;
    }

    public boolean suitCheck(String s){
        if(!s.equals(DIAMONDS) ||
                !s.equals(HEARTS) ||
                !s.equals(CLUBS) ||
                !s.equals(SPADES)){
            Log.d("SUIT ERROR", "Suit invalid: " + s);
            return false;
        }
        return true;
    }
}
