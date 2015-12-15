package edu.up.cs301.Spades;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 RELEASE
 *
 * Class that extends GameComputerPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds, PLAYS BETTER
 */
public class SpadesComputerPlayerAdv extends GameComputerPlayer {

    /**
     * SpadesComputerPlayer():ctor for the computer player
     * @param name
     */
    public SpadesComputerPlayerAdv(String name) {
        super(name);
    }

    /**
     * recieveInfo(): Callback method, called as a formality (it's a computer -_-)
     * @param info
     */

    protected void receiveInfo(GameInfo info) {

        SpadesState currentState;
        ArrayList<Card> myHand;

        if(info instanceof SpadesState) {

            currentState = (SpadesState) info;

            if (currentState.getPlayerBids(playerNum) == -1) { //if a bid has not been made yet
                int randBid = partnerBid(currentState); //bid based on partners bid
                game.sendAction(new SpadesBidAction(this, randBid));
            }
            else if (currentState.getCurrentPlayer() == playerNum) { //play a card
                if (currentState.getCurrentPlayerHand(playerNum) != null) { //if we haven't dealt yet you can't play, go away

                    myHand = currentState.getCurrentPlayerHand(playerNum);
                    int card = -1; //what I'm going to play

                    Card leadCard; // if card has been led with, this is it
                    int leadPlayer = currentState.getLeadTrick(); // who led
                    String leadSuit; // if card has been led with, this is its suit
                    int highCard;
                    int partner = playerNum + 2;
                    if (partner > 3) partner = partner - 4;
                    boolean wantTricks;
                    if ((currentState.getPlayerTricks(playerNum) + currentState.getPlayerTricks(partner)) <
                            (currentState.getPlayerBids(playerNum) + currentState.getPlayerBids(partner)) &&
                                    ((currentState.getPlayerBids(playerNum) != 0) && (currentState.getPlayerBids(partner) != 0))) {
                        wantTricks = true;
                    }
                    else if (currentState.getPlayerBids(partner) == 0) { //help partner
                        wantTricks = true;
                    }
                    else {
                        wantTricks = false;
                    }

                    ArrayList<Card> playerHand = currentState.getCurrentPlayerHand(); // player's current hand


                    //TODO Bid logic should definitely take into consideration which cards he has (eg ace of spades)
                    if ((leadPlayer != -1)) { //make the player follow the rules if he can't play first

                        leadCard = currentState.getTrickCards().get(leadPlayer); //store leading card info
                        leadSuit = leadCard.getSuit();
                        highCard = currentState.highCard;

                        if (wantTricks) {
                            if (highCard > 14) { //have to beat a spade
                                highCard = highCard - 13; //bring it down to real value
                                for (int i = 0; i < playerHand.size(); i++) { //see if I can beat
                                    if (playerHand.get(i) != null) { //existent card
                                        //find lowest winning card
                                        if (Card.SPADES.equals(playerHand.get(i).getSuit()) && playerHand.get(i).getRank() > highCard) {
                                            card = i;
                                            break; //go use this card
                                        }
                                    }
                                }
                            }//want tricks
                            else { //have to beat same suit
                                for (int i = 0; i < playerHand.size(); i++) { //see if I can beat
                                    if (playerHand.get(i) != null) { //existent card
                                        //find lowest winning card
                                        if (leadSuit.equals(playerHand.get(i).getSuit()) && playerHand.get(i).getRank() > highCard) {
                                            card = i;
                                            break; //go use this card
                                        }
                                    }
                                }
                            }//have to beat same suit
                        }//if want tricks
                        if (!wantTricks || card == -1) { //if we don't want tricks or still haven't played
                            int lowest = 100; //find low
                            for (int i = 0; i < playerHand.size(); i++) { //try to follow suit with lowest possible. If not, play lowest other card
                                if (playerHand.get(i) != null) {
                                    if (leadSuit.equals(playerHand.get(i).getSuit())) {
                                        card = i;
                                        break; //go use this card
                                    }
                                    else if (playerHand.get(i).getRank() < lowest) {
                                        card = i;
                                    }
                                }
                            }
                        }
                    }//not lead player
                    else { //lead
                        if (wantTricks) { //want to win
                            int i;
                            if (currentState.spadesBroken) { //want to play high spade (greater than 10)
                                for (i = playerHand.size() - 1; i >= 0; i--) {
                                    if (playerHand.get(i) != null) { //existent card
                                        //find high spade
                                        if (Card.SPADES.equals(playerHand.get(i).getSuit())) {
                                            if (playerHand.get(i).getRank() > 9) {
                                                card = i;
                                                break; //go use this card
                                            }
                                            else { //ran over
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (card == -1) { //no spades or want a better card
                                for (int j = 14; j > 1; j--) { //find next highest card
                                    for (int k = 0; k < playerHand.size(); k++) {
                                        if (playerHand.get(k) != null && playerHand.get(k).getRank() == j) {
                                            card = k;
                                            break;
                                        }
                                    }
                                }
                            }
                        }//if want to win
                        else { //if not want to win
                            //find low card (would be nice if we could exclude spades, but if spades are all that's left we won't play
                            for (int j =2; j < 15; j++) {
                                for (int k = 0; k < playerHand.size(); k++) {
                                    if (playerHand.get(k) != null && playerHand.get(k).getRank() == j) {
                                        card = k;
                                        break;
                                    }
                                }
                            }
                        }//if not want to win
                    }//not lead




                    if (currentState.cardsInTrick == 4) {
                        this.sleep(500); // let human see end of trick
                        game.sendAction(new EndTrickAction(this));
                        this.sleep(1000);
                    }
                    this.sleep(200);
                    game.sendAction(new SpadesPlayCardAction(this, card));
                }
            }
        }
    }

    //old getter
    public int getPlayerNo() {
        return this.playerNum;
    }

    /**
     * partnerBid(): used to determine a logical bid based on your partners bid, so that each "team"
     *                  has one low bid and one high
     * @param state: take in a SpadesState
     * @return int, which is inputted to the computers bid
     */
    public int partnerBid(SpadesState state){
        int toBid = -1; //default uninitted bid
        Random rand2 = new Random(); //random init
        int idx = 0;

        //set partner index
        if(state.currentPlayer == 1){
            idx = 3;
        }else if(state.currentPlayer == 2){
            idx = 0;
        }else if(state.currentPlayer == 3){
            idx = 1;
        }

        //bid based on partner bid with a chance for a null bid in most cases
        if(state.playerBids[idx] == -1){ //if partner hasn't bid yet
            int myRand = rand2.nextInt(10);
            if(myRand != 0) {
                toBid = rand2.nextInt(5)+1; //if myRand != 0 make a bid 1-5
            }else {toBid = 0;} //else bid nil
        }else if(state.playerBids[idx] == 0){ //if partner bid nil
            toBid = rand2.nextInt(4)+1;
        }else if(state.playerBids[idx] >= 3){ //if partner bid >= 3
            int myRand = rand2.nextInt(10);
            if(myRand != 0) {
                toBid = rand2.nextInt(3)+1; //if myRand != 0 make a bid 1-3
            }else {toBid = 0;} //else bid nil
        }else if(state.playerBids[idx] < 3){ //if partner bid < 3 & not nil (above case for nil)
                toBid = rand2.nextInt(3)+2; //bid 2-5, no chance for nil in this case
        }
        if(state.currentPlayerHand.get(12).getRank() == 12 &&
                state.currentPlayerHand.get(12).getSuit().equals('S') &&
                toBid == 0){ //if computer has the Ace of Spades
            toBid = rand2.nextInt(3)+1; //make a bid 1-3
        }
        return toBid;
    }
}