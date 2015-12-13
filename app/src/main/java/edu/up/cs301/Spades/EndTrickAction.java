package edu.up.cs301.Spades;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Sent at the end of a trick to prepare GUI for next trick
 *
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 ALPHA
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