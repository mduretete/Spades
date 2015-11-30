package edu.up.cs301.spadestest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Holds all the values contingent with the current gameState
 */
public class SpadesState extends GameState{

    int currentPlayer; //player who's current turn it is
    String leadTrick; //player who led the trick

    int[] playerScores; //1-d array for each player's score
    int[] playerTricks; //1-d array for tricks won by each player

    int cardsPlayed; //number of cards played in current trick

    int team1Score; //team scores
    int team2Score;

    ArrayList<Card> trickCards; //arrayList for cards played in current trick

    ArrayList<Card> player1Hand; //arrayList for cards left in each player's hand
    ArrayList<Card> player2Hand;
    ArrayList<Card> player3Hand;
    ArrayList<Card> player4Hand;

    int[] playerBags; //1-d array for number of bags for each player
    int[] playerBids; //1-d array for player bids for the round

    int userTeammate; //player number of user's teammate

    ArrayList<Card> deck = new ArrayList<>();

    public SpadesState() {
        currentPlayer = 0;

        cardsPlayed = 0;

        playerScores = new int[]{0, 0, 0, 0};
        playerTricks = new int[]{0, 0, 0, 0};

        team1Score = 0;
        team2Score = 0;

        trickCards = new ArrayList<>(4);
        //dummy up array if prev line of code not initializing causes problems
        for (int i = 0; i < 4; i++) {
            trickCards.add(new Card(i, Card.DEFAULT));
        }

        //fill deck
        initDeck();

        Random rand = new Random();

        //give players hands of cards
        player1Hand = new ArrayList<>(13);
        player2Hand = new ArrayList<>(13);
        player3Hand = new ArrayList<>(13);
        player4Hand = new ArrayList<>(13);

        //deal the cards
        deal();

        //new arrays to hold player Bags and Bids
        playerBags = new int[]{0, 0, 0, 0};
        playerBids = new int[]{-1, -1, -1, -1};

        userTeammate = 2; //player across from user will always be their teammate
    }

    public SpadesState(SpadesState copy){
        //declare all the empty array definitions so there is something to copy to
        trickCards = new ArrayList<>(copy.getTrickCards());

        playerScores = copy.playerScores;
        playerTricks = copy.playerTricks;

        player1Hand = new ArrayList<>(copy.getPlayer1Hand());
        player2Hand = new ArrayList<>(copy.getPlayer2Hand());
        player3Hand = new ArrayList<>(copy.getPlayer3Hand());
        player4Hand = new ArrayList<>(copy.getPlayer4Hand());

        playerBags = copy.playerBags;
        playerBids = copy.playerBids;

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

        Collections.copy(this.trickCards, copy.getTrickCards());

        Collections.copy(this.player1Hand, copy.getPlayer1Hand());
        Collections.copy(this.player2Hand, copy.getPlayer2Hand());
        Collections.copy(this.player3Hand, copy.getPlayer3Hand());
        Collections.copy(this.player4Hand, copy.getPlayer4Hand());

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

        /**
         * BUG DESCRIPTION: after a trick is completed, the trick cards are returned to the deck ArrayList,
         *          leaving it with a size of 4, then we get a size error 0 < 4, because this.deck
         *          has a size of 0
         */
        Collections.copy(this.deck, copy.deck); //<----BROKEN---->

    }

    /**
     * sets a SpadesState to have certain values from another SpadesState
     */
    public void set(SpadesState temp){
        //values updated are the values that are needed throughout the entire game
        this.currentPlayer = temp.currentPlayer;
        this.playerBags = temp.playerBags;
        this.playerScores = temp.playerScores;
        this.team1Score = temp.team1Score;
        this.team2Score = temp.team2Score;
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

    public ArrayList<Card> getTrickCards() {
        return trickCards;
    }

    public ArrayList<Card> getPlayer1Hand() {
        return player1Hand;
    }

    public ArrayList<Card> getPlayer2Hand() {
        return player2Hand;
    }

    public ArrayList<Card> getPlayer3Hand() {
        return player3Hand;
    }

    public ArrayList<Card> getPlayer4Hand(){
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

    public String getLeadTrick() { return leadTrick; }

    /**
     * Place a bid at the beginning of the round
     * @param newBid the bid to be set
     */
    public void placeBid(int newBid){ playerBids[currentPlayer] = newBid; }

    /**
     * Select a card to be played in the current trick
     * @param index index of card in the player's hand
     */
    public void playCard(int index){

        //boolean for error detection
        boolean detectError = false;

        //if player1's turn
        if(currentPlayer == 0) {
            if(player1Hand.get(index)!=null) { //can only play cards from hand
                trickCards.add(cardsPlayed, player1Hand.get(index));
                player1Hand.remove(index);
            } else detectError = true;
        }
        //if player2's turn
        else if(currentPlayer == 1) {
            if(player2Hand.get(index)!=null) { //can only play cards from hand
                trickCards.add(cardsPlayed, player2Hand.get(index));
                player2Hand.remove(index);
            } else detectError = true;
        }
        //if player 3's turn
        else if(currentPlayer == 2) {
            if(player3Hand.get(index)!=null) { //can only play cards from hand
                trickCards.add(cardsPlayed, player3Hand.get(index));
                player3Hand.remove(index);
            } else detectError = true;
        }
        //if player4's turn
        else if(currentPlayer == 3) {
            if(player4Hand.get(index)!=null) { //can only play cards from hand
                trickCards.add(cardsPlayed, player4Hand.get(index));
                player4Hand.remove(index);
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
                deck.add(trickCards.get(i));
                trickCards.remove(i);
            }
        }
    }

    /**
     * helper method for the constructor. Fills "deck", an arrayList of Cards
     */
    public void initDeck() {

                deck.add(new Card(1 , Card.CLUBS, R.mipmap.card_ac));
                deck.add(new Card(2 , Card.CLUBS, R.mipmap.card_2c));
                deck.add(new Card(3 , Card.CLUBS, R.mipmap.card_3c));
                deck.add(new Card(4 , Card.CLUBS, R.mipmap.card_4c));
                deck.add(new Card(5 , Card.CLUBS, R.mipmap.card_5c));
                deck.add(new Card(6 , Card.CLUBS, R.mipmap.card_6c));
                deck.add(new Card(7 , Card.CLUBS, R.mipmap.card_7c));
                deck.add(new Card(8 , Card.CLUBS, R.mipmap.card_8c));
                deck.add(new Card(9 , Card.CLUBS, R.mipmap.card_9c));
                deck.add(new Card(10, Card.CLUBS, R.mipmap.card_tc));
                deck.add(new Card(11, Card.CLUBS, R.mipmap.card_jc));
                deck.add(new Card(12, Card.CLUBS, R.mipmap.card_qc));
                deck.add(new Card(13, Card.CLUBS, R.mipmap.card_kc));

                deck.add(new Card(1 , Card.DIAMONDS, R.mipmap.card_ad));
                deck.add(new Card(2 , Card.DIAMONDS, R.mipmap.card_2d));
                deck.add(new Card(3 , Card.DIAMONDS, R.mipmap.card_3d));
                deck.add(new Card(4 , Card.DIAMONDS, R.mipmap.card_4d));
                deck.add(new Card(5 , Card.DIAMONDS, R.mipmap.card_5d));
                deck.add(new Card(6 , Card.DIAMONDS, R.mipmap.card_6d));
                deck.add(new Card(7 , Card.DIAMONDS, R.mipmap.card_7d));
                deck.add(new Card(8 , Card.DIAMONDS, R.mipmap.card_8d));
                deck.add(new Card(9 , Card.DIAMONDS, R.mipmap.card_9d));
                deck.add(new Card(10, Card.DIAMONDS, R.mipmap.card_td));
                deck.add(new Card(11, Card.DIAMONDS, R.mipmap.card_jd));
                deck.add(new Card(12, Card.DIAMONDS, R.mipmap.card_qd));
                deck.add(new Card(13, Card.DIAMONDS, R.mipmap.card_kd));

                deck.add(new Card(1 , Card.SPADES, R.mipmap.card_as));
                deck.add(new Card(2 , Card.SPADES, R.mipmap.card_2s));
                deck.add(new Card(3 , Card.SPADES, R.mipmap.card_3s));
                deck.add(new Card(4 , Card.SPADES, R.mipmap.card_4s));
                deck.add(new Card(5 , Card.SPADES, R.mipmap.card_5s));
                deck.add(new Card(6 , Card.SPADES, R.mipmap.card_6s));
                deck.add(new Card(7 , Card.SPADES, R.mipmap.card_7s));
                deck.add(new Card(8 , Card.SPADES, R.mipmap.card_8s));
                deck.add(new Card(9 , Card.SPADES, R.mipmap.card_9s));
                deck.add(new Card(10, Card.SPADES, R.mipmap.card_ts));
                deck.add(new Card(11, Card.SPADES, R.mipmap.card_js));
                deck.add(new Card(12, Card.SPADES, R.mipmap.card_qs));
                deck.add(new Card(13, Card.SPADES, R.mipmap.card_ks));

                deck.add(new Card(1 , Card.HEARTS, R.mipmap.card_ah));
                deck.add(new Card(2 , Card.HEARTS, R.mipmap.card_2h));
                deck.add(new Card(3 , Card.HEARTS, R.mipmap.card_3h));
                deck.add(new Card(4 , Card.HEARTS, R.mipmap.card_4h));
                deck.add(new Card(5 , Card.HEARTS, R.mipmap.card_5h));
                deck.add(new Card(6 , Card.HEARTS, R.mipmap.card_6h));
                deck.add(new Card(7 , Card.HEARTS, R.mipmap.card_7h));
                deck.add(new Card(8 , Card.HEARTS, R.mipmap.card_8h));
                deck.add(new Card(9 , Card.HEARTS, R.mipmap.card_9h));
                deck.add(new Card(10, Card.HEARTS, R.mipmap.card_th));
                deck.add(new Card(11, Card.HEARTS, R.mipmap.card_jh));
                deck.add(new Card(12, Card.HEARTS, R.mipmap.card_qh));
                deck.add(new Card(13, Card.HEARTS, R.mipmap.card_kh));
    }

    /**
     * helper method for constructor that deals the cards to the players
     */
    public void deal(){
        //ints i and cardNo for the for-loops
        int i;
        int cardNo;

        Random rand = new Random();

        //deal 13 cards to each player using rand.nextInt with the deck size
        //to get a random card number 1-52
        for(i=0;i<13;i++) { //takes the random number, places the card in player1Hand[], removes from deck
            cardNo = rand.nextInt(deck.size());
            player1Hand.add(deck.get(cardNo));
            deck.remove(cardNo);
        }
        for(i=0;i<13;i++) { //takes the random number, places the card in player2Hand[], removes from deck
            cardNo = rand.nextInt(deck.size());
            player2Hand.add(deck.get(cardNo));
            deck.remove(cardNo);
        }
        for(i=0;i<13;i++) { //takes the random number, places the card in player3Hand[], removes from deck
            cardNo = rand.nextInt(deck.size());
            player3Hand.add(deck.get(cardNo));
            deck.remove(cardNo);
        }
        for(i=0;i<13;i++) { //takes the random number, places the card in player4Hand[], removes from deck
            cardNo = rand.nextInt(deck.size());
            player4Hand.add(deck.get(cardNo));
            deck.remove(cardNo);
        }
    }
}
