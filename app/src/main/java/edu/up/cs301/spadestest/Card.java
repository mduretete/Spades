package edu.up.cs301.spadestest;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 ALPHA
 *
 * Class defines a Card object
 */

public class Card {
    //constants that have no reason to ever be changed, corresponding with suit
    public static final String SPADES = "S";
    public static final String CLUBS = "C";
    public static final String HEARTS = "H";
    public static final String DIAMONDS = "D";
    public static final String DEFAULT = "A"; //defaults to unusable

    public int imageId;

    //rank and suit variables
    private int rank;
    private String suit;

    /**
     * Card(): initialize new Card object
     * @param r int that holds the rank of a Card object, 1-13
     * @param s string that holds the suit of a Card object
     * @param im image id
     */
    public Card(int r, String s, int im) {
        if(rankCheck(r)){
            rank = r;
        }
        if(suitCheck(s)){
            suit = s;
        }
        imageId = im;

        //based on rank and suit, a card image is chosen
    }//ctor
    /**
     * Constructor that doesn't take an image
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

    /**
     * setSuit(): setter for the rank of a Card object, checks for validity
     * @param s string that sets the suit for a Card object
     */
    public void setSuit(String s){
        if(suitCheck(s)) {
            this.suit = s;
        }
    }

    /**
     * rankCheck(): checks to see if the rank proposed is valid
     * @param r int that is proposed to be the rank of a Card object
     * @return boolean, false if the rank is not valid (2-14 inclusive is valid)
     */
    public boolean rankCheck(int r){
        return !(r < 2 || r > 14);
    }

    /**
     * suitCheck(): checks to see if the suit proposed is valid
     * @param s string that is proposed to be the suit of a Card object
     * @return boolean, false if the suit is not valid (equal to one of our
     *          string constants is valid)
     */
    public boolean suitCheck(String s){
        return !(!s.equals(DIAMONDS) &&
                !s.equals(HEARTS) &&
                !s.equals(CLUBS) &&
                !s.equals(SPADES) &&
                !s.equals(DEFAULT));
    }
}
