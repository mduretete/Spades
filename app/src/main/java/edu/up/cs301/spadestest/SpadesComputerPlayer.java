package edu.up.cs301.spadestest;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 ALPHA
 *
 * Class that extends GameComputerPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds
 */
public class SpadesComputerPlayer extends GameComputerPlayer {

    /**
     * SpadesComputerPlayer():ctor for the computer player
     * @param name
     */
    public SpadesComputerPlayer(String name) {
        super(name);
    }

    /**
     * recieveInfo(): Callback method, called as a formality (it's a computer -_-)
     * @param info
     */

    protected void receiveInfo(GameInfo info) {

        SpadesState currentState;
        ArrayList<Card> myHand;

        if(info instanceof SpadesState) {

            currentState = (SpadesState) info;

            Random rand = new Random();
            int ctrlRand = rand.nextInt(10);
            int randBid;
            if(ctrlRand != 0) { //just basic AI work, TODO: will be changed eventually for something more logical
                randBid = rand.nextInt(7)+1; //if ctrlRand != 0 make a bid 1-7 **RESTRICTED BIDS**
            }else { randBid = 0;} //else bid nil

            if (currentState.getPlayerBids(playerNum) == -1) { //if a bid has not been made yet
                game.sendAction(new SpadesBidAction(this, randBid));
            }

            // TODO prohibit spades if spades haven't been broken
            else if (currentState.getCurrentPlayer() == playerNum) { //play a card

                if (currentState.getCurrentPlayerHand(playerNum) != null) { //if we haven't dealt yet you can't play, go away
                    myHand = currentState.getCurrentPlayerHand(playerNum);

                    int card;

                    Card leadCard; // if card has been led with, this is it
                    int leadPlayer = currentState.getLeadTrick(); // who led
                    String leadSuit; // if card has been led with, this is its suit
                    ArrayList<Card> playerHand = currentState.getCurrentPlayerHand(); // player's current hand

                    do {
                        card = rand.nextInt(13); //choose a random ish card
                    } while (playerHand.get(card) == null);

                    if (leadPlayer != -1) { //make the player follow the rules if he can't play first

                        leadCard = currentState.getTrickCards().get(leadPlayer); //store leading card info
                        leadSuit = leadCard.getSuit();

                        if (!leadSuit.equals(myHand.get(card).getSuit())) { //if can't play the chosen card

                            for (int i = 0; i < playerHand.size(); i++) { //try to find a card that works
                                if (playerHand.get(i) != null) { //existent card
                                    if (leadSuit.equals(playerHand.get(i).getSuit())) { //see if we can use this card
                                        card = i;
                                        break; //go use this card
                                    }
                                }
                            }
                        }
                    }

                    if (currentState.cardsInTrick == 4) {
                        this.sleep(500);
                        game.sendAction(new EndTrickAction(this));
                        this.sleep(1000);
                    }
                    this.sleep(500);
                    game.sendAction(new SpadesPlayCardAction(this, card));

                }
            }
        }

    }

    public int getPlayerNo() {
        return this.playerNum;
    }
}
