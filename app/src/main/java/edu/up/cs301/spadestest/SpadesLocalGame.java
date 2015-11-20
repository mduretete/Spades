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
        if(spadesGameState.getTeam1Score() >= 100){
            return "TEAM 1 WINS";
        } else if (spadesGameState.getTeam2Score() >= 100) {
            return "TEAM 2 WINS";
        }
        return null;
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
        //stores the trick cards in temps
        Card card1 = trick[0];
        Card card2 = trick[1];
        Card card3 = trick[2];
        Card card4 = trick[3];
        Card largest; //holds the largest to be returned

        //compares card2 and card2, updating the largest card
        if(compCards(card1, card2) == card1){
            largest = card1;
        } else {
            largest = card2;
        }

        //compares card3 and the current largest, updates largest if card3 is larger
        if(compCards(largest, card3) == largest){
        } else {
            largest = card3;
        }

        //compares card4 and the current largest, updates largest if card4 is larger
        if(compCards(largest, card4) == largest){
        } else {
            largest = card4;
        }

        //returns an int corresponding with the user that was in possession of the "largest" card
        if(largest == card1){
            return 0; //player 0 (human player) took the trick
        } else if(largest == card2){
            return 1; //player 1 took the trick
        } else if(largest == card3){
            return 2; //player 2 took the trick
        } else if(largest == card4){
            return 3; //player 3 took the trick
        }
        return -1; //fails and none of them are the "largest"
    }

    /**
     * compCards(): compare 2 cards to determine which has more trick value, helper
     *              method for the compTrickCards() method
     * @param c1 card 1
     * @param c2 card 2
     * @return returns the card that has more trick value
     */
    public Card compCards(Card c1, Card c2){
        if(c1.getSuit().equals(c1.SPADES) && c2.getSuit().equals(c2.SPADES)){
            if(c1.getRank() > c2.getRank()){
                return c1;
            } else if(c1.getRank() < c2.getRank()){
                return c2;
            }
        } else if (c1.getSuit().equals(c1.SPADES) && !c2.getSuit().equals(c2.SPADES)){
            return c1;
        } else if (!c1.getSuit().equals(c1.SPADES) && c2.getSuit().equals(c2.SPADES)){
            return c2;
        } else if (!c1.getSuit().equals(c1.SPADES) && !c2.getSuit().equals(c2.SPADES)){
            //leading suit wins
            if(c1.getSuit().equals(spadesGameState.leadTrick) && c2.getSuit().equals(spadesGameState.leadTrick)){
                if(c1.getRank() > c2.getRank()){
                    return c1;
                } else if(c1.getRank() < c2.getRank()){
                    return c2;
                }
            } else if (c1.getSuit().equals(spadesGameState.leadTrick) && !c2.getSuit().equals(spadesGameState.leadTrick)){
                return c1;
            } else if (!c1.getSuit().equals(spadesGameState.leadTrick) && c2.getSuit().equals(spadesGameState.leadTrick)){
                return c2;
            }
        }
        return c1;
    }


}