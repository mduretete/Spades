package edu.up.cs301.spadestest;

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

    }
}
