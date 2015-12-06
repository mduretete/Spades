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

            else if (currentState.getCurrentPlayer() == playerNum) { //play a card
                if (currentState.getCurrentPlayerHand(playerNum) != null) {
                    myHand = currentState.getCurrentPlayerHand(playerNum);
                    int cardToPlay;

                    do {
                        cardToPlay = rand.nextInt(13); //play a random card TODO: implement rules on which card can be played
                    } while (myHand.get(cardToPlay) == null);

                    this.sleep(500);
                    game.sendAction(new SpadesPlayCardAction(this, cardToPlay));
                }
            }
        }

    }

    public int getPlayerNo() {
        return this.playerNum;
    }
}
