package edu.up.cs301.spadestest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 ALPHA
 *
 * Holds all the values contingent with the current gameState
 */
public class SpadesState extends GameState{

    int currentPlayer; //player who's current turn it is
    String leadTrick; //player who led the trick

    int[] playerScores; //1-d array for each player's score
    int[] playerTricks; //1-d array for tricks won by each player

    int cardsInTrick; //number of cards played in current trick
    int cardsPlayed; //"total" cards played

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

    int winningTeam; //keeps track of which team is currently winning

    ArrayList<Card> deck = new ArrayList<>(52); //inits arrayList of size 52, a deck of cards

    public SpadesState() {
        currentPlayer = 0;

        cardsInTrick = 0;
        cardsPlayed = -1; //-1 for index purposes

        playerScores = new int[]{0, 0, 0, 0};
        playerTricks = new int[]{0, 0, 0, 0};

        team1Score = 0;
        team2Score = 0;

        trickCards = new ArrayList<>(4);
        //dummy up array if prev line of code not initializing causes problems
        for (int i = 0; i < 4; i++) {
            trickCards.add(i, null);
        }

        //fill deck
        initDeck();

        Random rand = new Random();

        //give players hands of cards
        player1Hand = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            player1Hand.add(i, null);
        }
        player2Hand = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            player2Hand.add(i, null);
        }
        player3Hand = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            player3Hand.add(i, null);
        }
        player4Hand = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            player4Hand.add(i, null);
        }

        //deal the cards
        deal();

        //new arrays to hold player Bags and Bids
        playerBags = new int[]{0, 0, 0, 0};
        playerBids = new int[]{0, -1, -1, -1};

        userTeammate = 2; //player across from user will always be their teammate
        winningTeam = -1;
    }

    public SpadesState(SpadesState copy){
        //declare all the empty array definitions so there is something to copy to
        trickCards = new ArrayList<>(copy.getTrickCards());
        deck = new ArrayList<>(copy.getDeck()); //need?

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
        this.cardsInTrick = copy.getCardsInTrick();

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

        Collections.copy(this.deck, copy.deck);

        this.winningTeam = copy.winningTeam;

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
        this.winningTeam = temp.winningTeam;
    }

    //getters
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public int getCardsPlayed(){
        return cardsPlayed;
    }

    public int getCardsInTrick() { return cardsInTrick; }

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

    public ArrayList<Card> getDeck() {
        return deck;
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
    public void placeBid(int newBid){
        playerBids[currentPlayer] = newBid;
        if(currentPlayer < 3) currentPlayer++; //TESTING TODO: attempting to get the computers to bid at the same time as the human
        else currentPlayer = 0;
    }


    /**
     * Select a card to be played in the current trick
     * @param index index of card in the player's hand
     */
    public void playCard(int index){

        //boolean for error detection
        boolean detectError = false;


        if(cardsInTrick == 4) {
            int i;
            for(i = 3;i >= 0; i--){
                deck.set(cardsPlayed, trickCards.get(i));
                trickCards.set(i, null);
            }
        }

        //if player1's turn
        if(currentPlayer == 0) {
            if(player1Hand.get(index)!=null) { //can only play cards from hand
                trickCards.set(cardsInTrick, player1Hand.get(index));
                player1Hand.set(index, null); //don't remove to avoid problems?
                currentPlayer++;
            } else detectError = true;
        }
        //if player2's turn
        else if(currentPlayer == 1) {
            if(player2Hand.get(index)!=null) { //can only play cards from hand
                trickCards.set(cardsInTrick, player2Hand.get(index));
                player2Hand.set(index, null);
                currentPlayer++;
            } else detectError = true;
        }
        //if player 3's turn
        else if(currentPlayer == 2) {
            if(player3Hand.get(index)!=null) { //can only play cards from hand
                trickCards.set(cardsInTrick, player3Hand.get(index));
                player3Hand.set(index, null);
                currentPlayer++;
            } else detectError = true;
        }
        //if player4's turn
        else if(currentPlayer == 3) {
            if(player4Hand.get(index)!=null) { //can only play cards from hand
                trickCards.set(cardsInTrick, player4Hand.get(index));
                player4Hand.set(index, null);
                currentPlayer = 0;
            } else detectError = true;
        }

        //if an invalid move was made, don't move on to the next turn
        if(!detectError) {
            cardsPlayed++;
            cardsInTrick++;
            //scoring();
            int trickWinner;
            if (cardsInTrick == 4) {
                trickWinner = compTrickCards(getTrickCards());
                playerTricks[trickWinner]++;
                currentPlayer = trickWinner;
                cardsInTrick = 0;
            }
        }

    }

    /**
     * helper method for the constructor. Fills "deck", an arrayList of Cards
     */
    public void initDeck() {

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
        deck.add(new Card(1 , Card.CLUBS, R.mipmap.card_ac));


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
        deck.add(new Card(1 , Card.DIAMONDS, R.mipmap.card_ad));


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
        deck.add(new Card(1 , Card.SPADES, R.mipmap.card_as));


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
        deck.add(new Card(1 , Card.HEARTS, R.mipmap.card_ah));

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
            do {
                cardNo = rand.nextInt(deck.size());
                player1Hand.set(i, deck.get(cardNo));
                deck.set(cardNo, null);
            } while (player1Hand.get(i) == null);
        }
        for(i=0;i<13;i++) { //takes the random number, places the card in player2Hand[], removes from deck
            do {
                cardNo = rand.nextInt(deck.size());
                player2Hand.set(i, deck.get(cardNo));
                deck.set(cardNo, null);
            } while (player2Hand.get(i) == null);
        }
        for(i=0;i<13;i++) { //takes the random number, places the card in player3Hand[], removes from deck
            do {
                cardNo = rand.nextInt(deck.size());
                player3Hand.set(i, deck.get(cardNo));
                deck.set(cardNo, null);
            } while (player3Hand.get(i) == null);
        }
        for(i=0;i<13;i++) { //takes the random number, places the card in player4Hand[], removes from deck
            do {
                cardNo = rand.nextInt(deck.size());
                player4Hand.set(i, deck.get(cardNo));
                deck.set(cardNo, null);
            } while (player4Hand.get(i) == null);
        }
    }

    // The fact that so much of SpadesState was being edited in SpadesLocalGame and that there was a temp version stored
    // in local game makes me think all of this belongs here

    /**
     * This is where the scoring happens
     */
    private void scoring(){
        //who won the trick?
        int trickWinner;
        if (cardsInTrick == 4) {
            trickWinner = compTrickCards(getTrickCards());
            playerTricks[trickWinner]++;
            currentPlayer = trickWinner;
            cardsInTrick = 0;
        }

        //if hands are empty, round is over
        if(cardsPlayed == 51) {

            //gets winning team
            winningTeam = roundWin();

        }
    }

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
        if(c1.getSuit().equals(Card.SPADES) && c2.getSuit().equals(Card.SPADES)){
            //compare ranks, same suit so cannot have same rank
            if(c1.getRank() > c2.getRank()){
                return c1;
            } else if(c1.getRank() < c2.getRank()){
                return c2;
            }
            //if c1 is spades it's a higher value
        } else if (c1.getSuit().equals(Card.SPADES) && !c2.getSuit().equals(Card.SPADES)){
            return c1;
            //if c2 is spades it's a higher value
        } else if (!c1.getSuit().equals(Card.SPADES) && c2.getSuit().equals(Card.SPADES)){
            return c2;
            //if both cards are not spades
        } else if (!c1.getSuit().equals(Card.SPADES) && !c2.getSuit().equals(Card.SPADES)){
            //whichever is the leading suit would win, if both leading suit
            if(c1.getSuit().equals(leadTrick) && c2.getSuit().equals(leadTrick)){
                //compare ranks, same suit so cannot have same rank
                if(c1.getRank() > c2.getRank()){
                    return c1;
                } else if(c1.getRank() < c2.getRank()){
                    return c2;
                }
                //if c1 is leading suit it's a higher value
            } else if (c1.getSuit().equals(leadTrick) && !c2.getSuit().equals(leadTrick)){
                return c1;
                //if c2 is leading suit it's a higher value
            } else if (!c1.getSuit().equals(leadTrick) && c2.getSuit().equals(leadTrick)){
                return c2;
            }
        }
        //no case for if neither is leading suit because then it won't matter which
        //gets returned, because neither will end up being the highest value card
        return c1;
    }//compCards()

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
     * roundWin(): determines which team scored the most in a round and updates the arrays
     * @return int, 0 for team 1 (human + comp) and 1 for team 2 (comp + comp), 2 for draw, -1 for ERROR
     */
    public int roundWin(){

        for(int i = 0; i < 4; i++) {
            //if nul bid is made
            if(playerTricks[i] == playerBids[i] && playerTricks[i] == 0){
                playerScores[i] = playerScores[i] + 50;
                //if bid is made and not nil
            } else if(playerTricks[i] == playerBids[i] && playerBids[i] != 0){
                int add = (playerBids[i])*10;
                playerScores[i] = playerScores[i] + add;
                //if null bid not made
            } else if(playerTricks[i] != playerBids[i] && playerBids[i] == 0){
                playerScores[i] = playerScores[i] - 50;
                //if overbid
            } else if(playerTricks[i] > playerBids[i] && playerBids[i] != 0){
                int sub = playerTricks[i] - playerBids[i];
                int add = ((playerBids[i])*10)+sub;
                playerScores[i] = playerScores[i] + add;
                //if underbid
            } else if(playerTricks[i] < playerBids[i] && playerBids[i] != 0){
                int add = (playerBids[i])*10;
                playerScores[i] = playerScores[i] - add;
            }
        }

        //updates team scores
        team1Score = playerScores[0] + playerScores[2];
        team2Score = playerScores[1] + playerScores[3];

        //sets winning team
        if(team1Score > team2Score){
            return 0;
        } else if(team2Score > team1Score){
            return 1;
        } else if(team1Score == team2Score){
            return 2; //draw
        }
        return -1;
    }

    /**
     * incrementTricks(): ups the value in a players trick if they win a trick
     * @param player: player who just won the trick
     */
    public void incrementTricks (int player) {
        playerTricks[player]++;
        currentPlayer = player;
    }
}
