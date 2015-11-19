package edu.up.cs301.spadestest;

import android.os.Message;

import java.util.logging.Handler;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.util.GameTimer;
import edu.up.cs301.game.util.Tickable;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Class that extends LocalGame and handles the in-game conditions, such as turns,
 *      winning, and valid moves.
 */
public class SpadesLocalGame extends LocalGame {

    private SpadesState spadesGameState;

    /**
     * SpadesLocalGame(): ctor for the new game state
     */
    protected SpadesLocalGame() {
        spadesGameState = new SpadesState();
    }

    /**
     * sendUpdatedStateTo(): send updated state to a player
     * @param p
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        SpadesState copy = new SpadesState(spadesGameState);
        p.sendInfo(copy);
    }

    /**
     * canMove(): can a player make a move/action?
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     */
    @Override
    public boolean canMove(int playerIdx) {
        return playerIdx == spadesGameState.getCurrentPlayer();
    }

    /**
     * checkIfGameOver(): has the win condition been met?
     * @return
     */
    @Override
    public String checkIfGameOver() {
        return "";
    }

    /**
     * makeMove(): is called when a new action arrives from a player
     * @param action
     * 			The move that the player has sent to the game
     * @return
     */
    @Override
    public boolean makeMove(GameAction action) {
        if(!canMove(getPlayerIdx(action.getPlayer()))){
            return false;
        }
        if(action instanceof SpadesBidAction){
            spadesGameState.placeBid();
        }
        else if(action instanceof SpadesPlayCardAction){
            spadesGameState.playCard();
        }

        return true;
    }

    /**
     * compTrickCards(): compare cards in a trick array and declare a winner
     * @param trick
     * @return player who won the trick (0-3)
     */
    public int compTrickCards(Card[] trick) {
        return 0;
    }


}