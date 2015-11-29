package edu.up.cs301.spadestest;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Class that allows the playCard action to be recognized
 */
public class SpadesPlayCardAction extends GameAction {


    int index;
    /**
     * SpadesPlayCardAction(): calls parent player object making the action
     * @param player
     */
    public SpadesPlayCardAction(GamePlayer player, int index){
        super(player);
        this.index = index;
    }

    public int getCardIndex() { return index; }
}
