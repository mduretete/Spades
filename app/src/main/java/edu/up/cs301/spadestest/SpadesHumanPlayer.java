package edu.up.cs301.spadestest;

import android.os.Handler;
import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Class that extends GameHumanPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds
 */
public class SpadesHumanPlayer extends GameHumanPlayer{

    /**
     * SpadesHumanPlayer():ctor for the human player
     * @param name
     */
    public SpadesHumanPlayer(String name) {
        super(name);
    }

    /**
     * getTopView(): returns the GUI's top object
     * @return
     */
    @Override
    public View getTopView() {
        return null;
    }

    /**
     * recieveInfo(): Callback method, called when player gets a message
     * @param info
     */
    public void receiveInfo(GameInfo info) {

    }

    /**
     * setAsGui(): Sets this player as the one attached to the GUI, saves the
     *              activity, then invokes subclass-specific method
     * @param activity
     */
    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}
