package edu.up.cs301.spadestest;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 *
 * Temp class for testing purposes
 *
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 ALPHA
 *
 * Class that allows the playCard action to be recognized
 */
public class EndTrickAction extends GameAction {

    /**
     * SpadesPlayCardAction(): calls parent player object making the action
     *
     * @param player
     */
    public EndTrickAction(GamePlayer player) {
        super(player);
    }
}