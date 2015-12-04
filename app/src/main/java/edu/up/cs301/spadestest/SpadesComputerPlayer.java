package edu.up.cs301.spadestest;

import android.widget.ImageView;

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

    static ImageView p1card;
    static ImageView p2card;
    static ImageView p3card;

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
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof SpadesState) {

            SpadesState state = (SpadesState) info;
            Random rand = new Random();
            int ctrlRand = rand.nextInt(10);
            int randBid;
            if(ctrlRand != 0) { //just basic AI work, TODO: will be changed eventually for something more logical
                randBid = rand.nextInt(7)+1; //if ctrlRand != 0 make a bid 1-7 **RESTRICTED BIDS**
            }else { randBid = 0;} //else bid nil
            int cardToPlay = rand.nextInt(13); //play a random card TODO: implement rules on which card can be played

            if (state.getPlayerBids(playerNum) == -1) { //if a bid has not been made yet
                game.sendAction(new SpadesBidAction(this, randBid));
            }
            else {
                if (state.getCurrentPlayer() != 0) { //play a card
                    //this.sleep(200); //buggy, sometimes it's slower or faster, I (Nick) think it depends on the complexity of the
                                        //compTrickCards logic, which varies depending on the cards
                    game.sendAction(new SpadesPlayCardAction(this, cardToPlay));
                }
            }
        }

    }
}
