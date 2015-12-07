package edu.up.cs301.spadestest;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 ALPHA
 *
 * Class that extends GameComputerPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds
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
            Random rand = new Random(); //random init
            int randBid = partnerBid(currentState); //bid based on partners bid

            if (currentState.getPlayerBids(playerNum) == -1) { //if a bid has not been made yet
                game.sendAction(new SpadesBidAction(this, randBid));
            }
            else if (currentState.getCurrentPlayer() == playerNum) { //play a card
                if (currentState.getCurrentPlayerHand(playerNum) != null) { //if we haven't dealt yet you can't play, go away
                    myHand = currentState.getCurrentPlayerHand(playerNum);

                    int card;

                    Card leadCard; // if card has been led with, this is it
                    int leadPlayer = currentState.getLeadTrick(); // who led
                    String leadSuit; // if card has been led with, this is its suit
                    ArrayList<Card> playerHand = currentState.getCurrentPlayerHand(); // player's current hand

                    do {
                        card = rand.nextInt(13); //choose a random ish card
                    } while (playerHand.get(card) == null);

                    //TODO: bid logic is updated, playing card logic needs to be updated
                    if ((leadPlayer != -1)) { //make the player follow the rules if he can't play first

                        leadCard = currentState.getTrickCards().get(leadPlayer); //store leading card info
                        leadSuit = leadCard.getSuit();

                        if (!leadSuit.equals(myHand.get(card).getSuit())) { //if can't play the chosen card

                            for (int i = 0; i < playerHand.size(); i++) { //try to find a card that works
                                if (playerHand.get(i) != null) { //existent card
                                    if (leadSuit.equals(playerHand.get(i).getSuit())) { //see if we can use this card
                                        card = i;
                                        break; //go use this card
                                    }
                                }
                            }
                        }
                    }
                    else if ((playerHand.get(card).getSuit().equals("S")) && ((!currentState.spadesBroken))) {
                        for (int i = 0; i < playerHand.size(); i++) { //try to find a card that works
                            if (playerHand.get(i) != null) { //existent card
                                if (!playerHand.get(i).getSuit().equals("S")) { //see if we can use this card
                                    card = i;
                                    break; //go use this card
                                }
                            }
                        }
                    }

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
        int toBid = -1; //default uninited bid
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
                toBid = rand2.nextInt(6)+1; //if myRand != 0 make a bid 1-6
            }else { toBid = 0;} //else bid nil
        }else if(state.playerBids[idx] >= 4){ //if partner bid >= 4
            int myRand = rand2.nextInt(10);
            if(myRand != 0) {
                toBid = rand2.nextInt(3)+1; //if myRand != 0 make a bid 1-3
            }else { toBid = 0;} //else bid nil
        }else if(state.playerBids[idx] < 4){ //if partner bid < 4
            int myRand = rand2.nextInt(10);
                toBid = rand2.nextInt(3)+3; //bid 3-6, no chance for null in this case
        }
        return toBid;
    }

}