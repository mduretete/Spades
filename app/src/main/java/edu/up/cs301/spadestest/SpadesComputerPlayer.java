package edu.up.cs301.spadestest;

import android.widget.ImageView;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Class that extends GameComputerPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds
 */
public class SpadesComputerPlayer extends GameComputerPlayer {

    static ImageView p1card;
    static ImageView p2card;
    static ImageView p3card;
    private int playerNo;
    /**
     * SpadesComputerPlayer():ctor for the computer player
     * @param name
     */
    public SpadesComputerPlayer(String name,int playerNo) {
        super(name);
        this.playerNo = playerNo;
    }

    /**
     * recieveInfo(): Callback method, called as a formality (it's a computer -_-)
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof SpadesState) {
            Random rand = new Random();

            if(((SpadesState)info).getPlayerBids(playerNo) == -1) { //if a bid has not been made yet
                game.sendAction(new SpadesBidAction(this,rand.nextInt(13)));
            }else{
                game.sendAction(new SpadesPlayCardAction(this, rand.nextInt(13)));
            }
        }

    }
}
