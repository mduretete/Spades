package edu.up.cs301.spadestest;
import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Holds all the values contingent with the current gameState
 */
public class SpadesState extends GameState{

    int currentPlayer;

    int[] playerScores; //1-d array for each player's score
    int[] playerTricks; //1-d array for tricks won by each player

    int cardsPlayed; //number of cards played in current trick

    int team1Score; //team scores
    int team2Score;

    Card[] trickCards; //1-d array for cards played in current trick

    Card[] player1Hand; //1-d arrays for cards left in each player's hand
    Card[] player2Hand;
    Card[] player3Hand;
    Card[] player4Hand;

    int[] playerBags; //1-d array for number of bags for each player
    int[] playerBids; //1-d array for player bids for the round

    int userTeammate; //player number of user's teammate

    int selectedCard;
    int selectedBid;

    ArrayList<Card> deck = new ArrayList<>();

    public SpadesState() {
        currentPlayer = 0;

        cardsPlayed = 0;

        playerScores = new int[]{0, 0, 0, 0};
        playerTricks = new int[]{0, 0, 0, 0};

        team1Score = 0;
        team2Score = 0;

        trickCards = new Card[4];

        initDeck(); //fill deck

        Random rand = new Random();

        player1Hand = new Card[13]; //give players hands of cards
        player2Hand = new Card[13];
        player3Hand = new Card[13];
        player4Hand = new Card[13];

        int i;
        int cardNo;
        // shuffle and deal
        for(i=0;i<13;i++) {
            cardNo = rand.nextInt(deck.size());
            player1Hand[i] = deck.get(cardNo);
            deck.remove(cardNo);
        }
        for(i=0;i<13;i++) {
            cardNo = rand.nextInt(deck.size());
            player2Hand[i] = deck.get(cardNo);
            deck.remove(cardNo);
        }
        for(i=0;i<13;i++) {
            cardNo = rand.nextInt(deck.size());
            player3Hand[i] = deck.get(cardNo);
            deck.remove(cardNo);
        }
        for(i=0;i<13;i++) {
            cardNo = rand.nextInt(deck.size());
            player4Hand[i] = deck.get(cardNo);
            deck.remove(cardNo);
        }



        playerBags = new int[]{0, 0, 0, 0};
        playerBids = new int[]{0, 0, 0, 0};

        userTeammate = 2; //player across from user will always be their teammate
    }

    public SpadesState(SpadesState copy){
        //declare all the empty array definitions so there is something to copy to
        trickCards = new Card[4];

        playerScores = new int[]{0, 0, 0, 0};
        playerTricks = new int[]{0, 0, 0, 0};

        player1Hand = new Card[13]; //give players hands of cards
        player2Hand = new Card[13];
        player3Hand = new Card[13];
        player4Hand = new Card[13];

        playerBags = new int[]{0, 0, 0, 0};
        playerBids = new int[]{0, 0, 0, 0};

        //begin the copy process
        this.currentPlayer = copy.getCurrentPlayer();
        this.cardsPlayed = copy.getCardsPlayed();

        int i = 0;
        do {
            this.playerScores[i] = copy.getPlayerScore(i);
            i++;
        }while(i<4);

        for(i=0;i<4;i++)
            this.playerTricks[0] = copy.getPlayerTricks(0);


        this.team1Score = copy.getTeam1Score();
        this.team2Score = copy.getTeam2Score();

        System.arraycopy(copy.getTrickCards(),0,this.trickCards,0,copy.getTrickCards().length);

        System.arraycopy(copy.getPlayer1Hand(),0,this.player1Hand,0,copy.getPlayer1Hand().length);
        System.arraycopy(copy.getPlayer2Hand(),0,this.player2Hand,0,copy.getPlayer2Hand().length);
        System.arraycopy(copy.getPlayer3Hand(),0,this.player3Hand,0,copy.getPlayer3Hand().length);
        System.arraycopy(copy.getPlayer4Hand(),0,this.player4Hand,0,copy.getPlayer4Hand().length);

        i = 0;
        while(i<4) {
            this.playerBags[i] = copy.getPlayerBags(i);
            i++;
        }

        i = 0;
        while(i<4) {
            this.playerBids[i] = copy.getPlayerBids(i);
            i++;
        }

        this.userTeammate = copy.getUserTeammate();

        this.deck = new ArrayList<Card>(copy.deck);

    }

    //getters
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public int getCardsPlayed(){
        return cardsPlayed;
    }

    public int getPlayerScore(int player) {
        return playerScores[player];
    }

    public int getPlayerTricks(int player) {
        return playerTricks[player];
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public Card[] getTrickCards() {
        return trickCards;
    }

    public Card[] getPlayer1Hand() {
        return player1Hand;
    }

    public Card[] getPlayer2Hand() {
        return player2Hand;
    }

    public Card[] getPlayer3Hand() {
        return player3Hand;
    }

    public Card[] getPlayer4Hand(){
        return player4Hand;
    }

    public int getPlayerBags(int player){
        return playerBags[player];
    }

    public int getPlayerBids(int player){
        return playerBids[player];
    }

    public int getUserTeammate(){
        return userTeammate;
    }

    /**
     * Place a bid at the beginning of the round
     * @param newBid the bid to be set
     */
    public void placeBid(int newBid){
        playerBids[currentPlayer] = newBid;
    }

    /**
     * Select a card to be played in the current trick
     * @param index index of card in the player's hand
     */
    public void playCard(int index){

        boolean detectError = false;

        if(currentPlayer == 0) {
            if(player1Hand[index]!=null) {
                trickCards[cardsPlayed] = player1Hand[index];
                player1Hand[index] = null;
            } else detectError = true;
        }

        else if(currentPlayer == 1) {
            if(player2Hand[index]!=null) {
                trickCards[cardsPlayed] = player2Hand[index];
                player2Hand[index] = null;
            } else detectError = true;
        }

        else if(currentPlayer == 2) {
            if(player3Hand[index]!=null) {
                trickCards[cardsPlayed] = player3Hand[index];
                player3Hand[index] = null;
            } else detectError = true;
        }

        else if(currentPlayer == 3) {
            if(player4Hand[index]!=null) {
                trickCards[cardsPlayed] = player4Hand[index];
                player4Hand[index] = null;
            } else detectError = true;
        }

        //if an invalid move was made, don't move on to the next turn
        if(!detectError) {
            cardsPlayed++;

            if (currentPlayer < 3)
                currentPlayer++;
            else currentPlayer = 0;
        }

        if(cardsPlayed >= 4) {
            int i;
            for(i=0;i<4;i++){
                deck.add(trickCards[i]);
                trickCards[i] = null;
            }
        }
    }

    /**
     * helper method for the constructor. Fills "deck", an arraylist of Cards
     *
     */
    public void initDeck(){
                int i;
        for(i=2;i<15;i++)
            deck.add(new Card(i,Card.CLUBS));
        for(i=2;i<15;i++)
            deck.add(new Card(i,Card.DIAMONDS));
        for(i=2;i<15;i++)
            deck.add(new Card(i,Card.SPADES));
        for(i=2;i<15;i++)
            deck.add(new Card(i,Card.HEARTS));

    }

    /**
     * call playCard with card currently selected by the player
     */
    public void playCard(){
        playCard(this.selectedCard);
    }

    /**
     * call placeBid with the bid currently entered by the player
     */
    public void placeBid(){
        placeBid(this.selectedBid);
    }
}
