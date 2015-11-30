package edu.up.cs301.spadestest;

import android.os.Message;

import java.util.ArrayList;
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

    private SpadesState spadesGameState; //gameState used in the local game

    /**
     * SpadesLocalGame(): ctor for the new game state
     */
    protected SpadesLocalGame() {
        spadesGameState = new SpadesState();
    }//ctor

    /**
     * sendUpdatedStateTo(): send updated state to a player
     * @param p gamePlayer to send the copied game state to
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        SpadesState copy = new SpadesState(spadesGameState);
        p.sendInfo(copy);
    }//sendUpdatedStateTo()

    /**
     * canMove(): can a player make a move/action?
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return boolean
     */
    @Override
    public boolean canMove(int playerIdx) {
        return playerIdx == spadesGameState.getCurrentPlayer();
    }//canMove()

    /**
     * checkIfGameOver(): has the win condition been met?
     * @return String
     */
    @Override
    public String checkIfGameOver() {
        if(spadesGameState.getTeam1Score() >= 100){
            return "TEAM 1 WINS";
        } else if (spadesGameState.getTeam2Score() >= 100) {
            return "TEAM 2 WINS";
        }
        return null;
    }//checkIfGameOver()

    /**
     * makeMove(): is called when a new action arrives from a player
     * @param action the move that the player has sent to the game
     * @return boolean
     */
    @Override
    public boolean makeMove(GameAction action) {
        if(!canMove(getPlayerIdx(action.getPlayer()))){
            return false;
        }
        if(action instanceof SpadesBidAction){
            spadesGameState.placeBid(((SpadesBidAction) action).getBid());
        }
        else if(action instanceof SpadesPlayCardAction){
            spadesGameState.playCard(((SpadesPlayCardAction) action).getCardIndex());
        }
        //if hands are empty, round is over
        if(spadesGameState.player1Hand.isEmpty() && spadesGameState.player2Hand.isEmpty()
                && spadesGameState.player3Hand.isEmpty() && spadesGameState.player4Hand.isEmpty()) {
            SpadesState temp = spadesGameState; //store the current SpadesState in a temp
            spadesGameState = new SpadesState(); //overwrite current SpadesState with a new one, newly inited and dealt deck
            spadesGameState.set(temp); //restore the permanent values (such as scores and bags) to the current SpadesState
        }
        return true;
    }//makeMove()

    /**
     * compTrickCards(): compare cards in a trick array and declare a winner
     * @param trickList array of cards in the trick
     * @return player who won the trick (0-3), -1 is failure case
     */
    public int compTrickCards(ArrayList<Card> trickList) {

        //if the arrayList has less or more than 4 elements, return -1
        if(trickList.size() != 4){
            return -1; //ERROR
        }

        //convert the arrayList to an array so it can be more easily referenced
        Card[] trick = trickList.toArray(new Card[trickList.size()]);

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
        int result = -1;
        if(largest == card1){
            result = 0; //player 0 (human player) took the trick
        } else if(largest == card2){
            result = 1; //player 1 took the trick
        } else if(largest == card3){
            result = 2; //player 2 took the trick
        } else if(largest == card4){
            result = 3; //player 3 took the trick
        }
        return result; //returns "fail" = -1 if no cards largest
    }//compTrickCards()

    /**
     * compCards(): compare 2 cards to determine which has more trick value, helper
     *              method for the compTrickCards() method
     * @param c1 card 1
     * @param c2 card 2
     * @return returns the card that has more trick value
     */
    public Card compCards(Card c1, Card c2){
        //if c1 or c2 == null, return the other
        if(c1==null){
            return c2;
        } else if(c2==null){
            return c1;
        }
        //if both cards are spades
        if(c1.getSuit().equals(c1.SPADES) && c2.getSuit().equals(c2.SPADES)){
            //compare ranks, same suit so cannot have same rank
            if(c1.getRank() > c2.getRank()){
                return c1;
            } else if(c1.getRank() < c2.getRank()){
                return c2;
            }
            //if c1 is spades it's a higher value
        } else if (c1.getSuit().equals(c1.SPADES) && !c2.getSuit().equals(c2.SPADES)){
            return c1;
            //if c2 is spades it's a higher value
        } else if (!c1.getSuit().equals(c1.SPADES) && c2.getSuit().equals(c2.SPADES)){
            return c2;
            //if both cards are not spades
        } else if (!c1.getSuit().equals(c1.SPADES) && !c2.getSuit().equals(c2.SPADES)){
            //whichever is the leading suit would win, if both leading suit
            if(c1.getSuit().equals(spadesGameState.leadTrick) && c2.getSuit().equals(spadesGameState.leadTrick)){
                //compare ranks, same suit so cannot have same rank
                if(c1.getRank() > c2.getRank()){
                    return c1;
                } else if(c1.getRank() < c2.getRank()){
                    return c2;
                }
                //if c1 is leading suit it's a higher value
            } else if (c1.getSuit().equals(spadesGameState.leadTrick) && !c2.getSuit().equals(spadesGameState.leadTrick)){
                return c1;
                //if c2 is leading suit it's a higher value
            } else if (!c1.getSuit().equals(spadesGameState.leadTrick) && c2.getSuit().equals(spadesGameState.leadTrick)){
                return c2;
            }
        }
        //no case for if neither is leading suit because then it won't matter which
        //gets returned, because neither will end up being the highest value card
        return c1;
    }//compCards()


}//SpadesLocalGame.java