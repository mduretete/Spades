package edu.up.cs301.Spades;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Dec. 2015 RELEASE
 *
 * Holds all the values contingent with the current gameState as well as scoring
 *      logic
 */
public class SpadesState extends GameState{

    int currentPlayer; //player who's current turn it is
    int leadTrick; //player who led the trick

    //update values in state so that each player doesn't have to calculate them independently
    int highCard; //to be updated to store the highest card currently in the trick

    boolean showPlayer0;
    boolean showPlayer1;
    boolean showPlayer2;
    boolean showPlayer3;

    int[] playerScores; //1-d array for each player's score
    int[] playerTricks; //1-d array for tricks won by each player

    int cardsInTrick; //number of cards played in current trick
    int cardsPlayed; //"total" cards played

    int team1Score; //team scores
    int team2Score;

    ArrayList<Card> trickCards; //arrayList for cards played in current trick
    String firstCardSuit;

    ArrayList<Card> player1Hand; //arrayList for cards left in each player's hand
    ArrayList<Card> player2Hand;
    ArrayList<Card> player3Hand;
    ArrayList<Card> player4Hand;
    ArrayList<Card> currentPlayerHand;

    int team1Bags;
    int team2Bags;
    int[] playerBids; //1-d array for player bids for the round

    int userTeammate; //player number of user's teammate

    int winningTeam; //keeps track of which team is currently winning

    boolean spadesBroken;
    boolean endOfRound;

    ArrayList<Card> deck = new ArrayList<>(52); //inits arrayList of size 52, a deck of cards

    public SpadesState() {
        currentPlayer = 0;
        leadTrick = -1;
        firstCardSuit = "";
        highCard = 0;

        //booleans whether or not to show a players cards (in trick)
        showPlayer0 = false;
        showPlayer1 = false;
        showPlayer2 = false;
        showPlayer3 = false;

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
        currentPlayerHand = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            currentPlayerHand.add(i, null);
        }

        //deal the cards
        deal();

        //to hold player Bags and Bids
        team1Bags = 0;
        team2Bags = 0;
        playerBids = new int[]{-1, -1, -1, -1};

        userTeammate = 2; //player across from user will always be their teammate
        winningTeam = -1; //inited to -1 b/c 0 means team 1, 1 means team 2, 2 means draw

        spadesBroken = false;
        endOfRound = false;
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
        currentPlayerHand = new ArrayList<>(copy.getCurrentPlayerHand(currentPlayer));

        team1Bags = copy.team1Bags;
        team2Bags = copy.team2Bags;
        playerBids = copy.playerBids;

        //begin the copy process
        currentPlayer = copy.getCurrentPlayer();
        leadTrick = copy.getLeadTrick();
        firstCardSuit = copy.getFirstCardSuit();
        cardsPlayed = copy.getCardsPlayed();
        cardsInTrick = copy.getCardsInTrick();
        showPlayer0 = copy.showPlayer0;
        showPlayer1 = copy.showPlayer1;
        showPlayer2 = copy.showPlayer2;
        showPlayer3 = copy.showPlayer3;

        int i = 0;
        do {
            playerScores[i] = copy.getPlayerScore(i);
            i++;
        }while(i<4);

        for(i=0;i<4;i++)
            playerTricks[i] = copy.getPlayerTricks(i);


        this.team1Score = copy.getTeam1Score();
        this.team2Score = copy.getTeam2Score();

        Collections.copy(trickCards, copy.getTrickCards());

        Collections.copy(player1Hand, copy.getPlayer1Hand());
        Collections.copy(player2Hand, copy.getPlayer2Hand());
        Collections.copy(player3Hand, copy.getPlayer3Hand());
        Collections.copy(player4Hand, copy.getPlayer4Hand());
        Collections.copy(currentPlayerHand, copy.getCurrentPlayerHand(currentPlayer));

        i = 0;
        while(i<4) {
            playerBids[i] = copy.getPlayerBids(i);
            i++;
        }

        userTeammate = copy.getUserTeammate();

        Collections.copy(deck, copy.deck);

        winningTeam = copy.winningTeam;

        this.spadesBroken = copy.spadesBroken;
    }

    /**
     * sets a SpadesState to have certain values from another SpadesState
     */
    public void set(SpadesState temp){
        //values updated are the values that are needed throughout the entire game
        this.currentPlayer = 0;
        this.team1Bags = temp.team1Bags;
        this.team2Bags = temp.team2Bags;
        this.playerScores = temp.playerScores;
        this.team1Score = temp.team1Score;
        this.team2Score = temp.team2Score;
        this.winningTeam = temp.winningTeam;
    }

    //getters
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public int getLeadTrick(){
        return leadTrick;
    }

    public int getCardsPlayed(){
        return cardsPlayed;
    }

    public String getFirstCardSuit(){
        return firstCardSuit;
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

    public boolean getEndOfRound() { return endOfRound; }

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

    public ArrayList<Card> getCurrentPlayerHand(int player) {
        if (player == 1) {
            return player2Hand;
        }
        if (player == 2) {
            return player3Hand;
        }
        if (player == 3) {
            return player4Hand;
        }
        else return player1Hand;
    }

    public ArrayList<Card> getCurrentPlayerHand() {
        if (currentPlayer == 1) {
            return player2Hand;
        }
        if (currentPlayer == 2) {
            return player3Hand;
        }
        if (currentPlayer == 3) {
            return player4Hand;
        }
        else return player1Hand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
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
        if(currentPlayer < 3) currentPlayer++;
        else currentPlayer = 0;
    }

    /**
     * Select a card to be played in the current trick
     * @param index index of card in the player's hand
     */
    public void playCard(int index) {

        currentPlayerHand = getCurrentPlayerHand();
        if(currentPlayerHand.get(index) != null){
            if (cardsInTrick == 4) {
                cardsInTrick = 0;
            }

            if (cardsInTrick == 0) {
                leadTrick = currentPlayer;
                firstCardSuit = currentPlayerHand.get(index).getSuit();
            }

            //if player1's turn
            if (currentPlayer == 0) {
                if (player1Hand.get(index) != null) { //can only play cards from hand
                    trickCards.set(currentPlayer, player1Hand.get(index));
                    updateHighest(player1Hand.get(index));
                    player1Hand.set(index, null);
                    currentPlayer++;
                    showPlayer0 = true;
                } else {
                    return;
                }
            }
            //if player2's turn
            else if (currentPlayer == 1) {
                if (player2Hand.get(index) != null) { //can only play cards from hand
                    trickCards.set(currentPlayer, player2Hand.get(index));
                    updateHighest(player2Hand.get(index));
                    player2Hand.set(index, null);
                    currentPlayer++;
                    showPlayer1 = true;
                } else {
                    return;
                }
            }
            //if player 3's turn
            else if (currentPlayer == 2) {
                if (player3Hand.get(index) != null) { //can only play cards from hand
                    trickCards.set(currentPlayer, player3Hand.get(index));
                    updateHighest(player3Hand.get(index));
                    player3Hand.set(index, null);
                    currentPlayer++;
                    showPlayer2 = true;
                } else {
                    return;
                }
            }
            //if player4's turn
            else if (currentPlayer == 3) {
                if (player4Hand.get(index) != null) { //can only play cards from hand
                    trickCards.set(currentPlayer, player4Hand.get(index));
                    updateHighest(player4Hand.get(index));
                    player4Hand.set(index, null);
                    currentPlayer = 0;
                    showPlayer3 = true;
                } else {
                    return;
                }
            }

            cardsPlayed++;
            cardsInTrick++;
            scoring();
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
        deck.add(new Card(14, Card.CLUBS, R.mipmap.card_ac));


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
        deck.add(new Card(14, Card.DIAMONDS, R.mipmap.card_ad));


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
        deck.add(new Card(14, Card.SPADES, R.mipmap.card_as));


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
        deck.add(new Card(14, Card.HEARTS, R.mipmap.card_ah));

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

        orderHand(player1Hand);
        orderHand(player2Hand);
        orderHand(player3Hand);
        orderHand(player4Hand);
    }

    /**
     * This is where the scoring happens
     */
    public void scoring(){
        //who won the trick?
        int trickWinner;
        if (cardsInTrick == 4) {
            trickWinner = compTrickCards(getTrickCards());
            playerTricks[trickWinner]++;
            currentPlayer = trickWinner;
            firstCardSuit = "";
            leadTrick = -1;

            int i;
            if (!spadesBroken) {
                for (i = 3; i >= 0; i--) {
                    if (trickCards.get(i).getSuit().equals(Card.SPADES)) {
                        spadesBroken = true;
                        break;
                    }
                }
            }
        }

        //if hands are empty, round is over
        if(cardsPlayed == 51) {

            //gets winning team
            winningTeam = roundWin();
            currentPlayer = 0;
            endOfRound = true;
        }
    }

    /**
     * Reset for next round
     */
    public void newRound() {
        currentPlayer = 0; //player who's current turn it is

        showPlayer0 = false;
        showPlayer1 = false;
        showPlayer2 = false;
        showPlayer3 = false;

        for (int i = 1; i < 4; i++) {
            playerTricks[i] = 0;
        }
        for (int i = 1; i < 4; i++) {
            playerBids[i] = -1;
        }

        cardsPlayed = 0;
        spadesBroken = false;
        endOfRound = false;

        deal();
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
            if(c1.getSuit().equals(firstCardSuit) && c2.getSuit().equals(firstCardSuit)){
                //compare ranks, same suit so cannot have same rank
                if(c1.getRank() > c2.getRank()){
                    return c1;
                } else if(c1.getRank() < c2.getRank()){
                    return c2;
                }
                //if c1 is leading suit it's a higher value
            } else if (c1.getSuit().equals(firstCardSuit) && !c2.getSuit().equals(firstCardSuit)){
                return c1;
                //if c2 is leading suit it's a higher value
            } else if (!c1.getSuit().equals(firstCardSuit) && c2.getSuit().equals(firstCardSuit)){
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
        if(compCards(largest, card3) != largest){
            largest = card3;
        }

        //compares card4 and the current largest, updates largest if card4 is larger
        if(compCards(largest, card4) != largest){
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
        } else {
            result = 3; //player 3 took the trick
        }
        return result; //returns "fail" = -1 if no cards largest
    }//compTrickCards()


    /**
     * roundWin(): determines which team scored the most in a round and updates the arrays
     * @return int, 0 for team 1 (human + comp) and 1 for team 2 (comp + comp), 2 for draw, -1 for ERROR
     */
    public int roundWin(){

        boolean p0done = false;
        boolean p1done = false;
        boolean p2done = false;
        boolean p3done = false;

        for(int i = 0; i < 4; i++) {
            if (playerBids[i] == 0) { //if nil bid is made by a player

                int partner = (i + 2); //score his partner alone
                if (partner > 3) {
                    partner = partner - 4;
                }
                //ok resetting player scores between rounds because team scores are the only ones that need to carry over
                if (playerTricks[i] == playerBids[i]) { //nil bid made
                    playerScores[i] = 100;
                }
                else if (playerTricks[i] > playerBids[i]) { //nil bid not made
                    playerScores[i] = (-100) + playerTricks[i] ;
                    if (i == 0 || i == 2) {
                        team1Bags = team1Bags + playerTricks[i];
                    }
                    else {
                        team2Bags = team2Bags + playerTricks[i];
                    }
                }
                switch (i) {
                    case 0:
                        p0done = true;
                        break;
                    case 1:
                        p1done = true;
                        break;
                    case 2:
                        p2done = true;
                        break;
                    case 3:
                        p3done = true;
                }
                if (playerTricks[partner] >= playerBids[partner] && playerBids[partner] != 0) { //partner made bid
                    int bags = (playerTricks[partner] - playerBids[partner]);
                    int add = ((playerBids[partner]) * 10) + bags;
                    playerScores[i] += add;
                }
                else { //partner did not make bid
                    playerScores[partner] = ((-10) * (playerBids[partner]));
                }
                switch (partner) {
                    case 0:
                        p0done = true;
                        break;
                    case 1:
                        p1done = true;
                        break;
                    case 2:
                        p2done = true;
                        break;
                    case 3:
                        p3done = true;
                }
            }
        }

        if (!p0done && !p2done) {
            if ((playerTricks[0] + playerTricks[2]) >= (playerBids[0] + playerBids[2])) { //partnership made bid
                int bags = ((playerTricks[0] + playerTricks[2]) - (playerBids[0] + playerBids[2]));
                team1Score = team1Score + ((playerBids[0] + playerBids[2]) * 10) + bags;
                team1Bags = team1Bags + bags;
            }
            else { //partnership did not make bid
                team1Score = team1Score + ((-10) * (playerBids[0] + playerBids[2]));
            }
        }
        else {
            team1Score = team1Score + playerScores[0] + playerScores[2];
        }
        if (!p1done && !p3done) {
            if ((playerTricks[1] + playerTricks[3]) >= (playerBids[1] + playerBids[3])) {
                int bags = ((playerTricks[1] + playerTricks[3]) - (playerBids[1] + playerBids[3]));
                team2Score = team2Score + ((playerBids[1] + playerBids[3]) * 10) + bags;
                team2Bags = team2Bags + bags;
            }
            else {
                team2Score = team2Score + ((-10) * (playerBids[1] + playerBids[3]));
            }
        }
        else {
            team2Score = team2Score + playerScores[1] + playerScores[3];
        }

        //bags penalty
        if (team1Bags >= 10) {
            team1Score = team1Score - 100;
            team1Bags = team1Bags%10;
        }
        if (team2Bags >= 10) {
            team2Score = team1Score - 100;
            team2Bags = team2Bags%10;
        }

        //sets winning team
        if(team1Score > team2Score){
            return 0; //team 1 winning
        } else if(team2Score > team1Score){
            return 1; //team 2 winning
        } else if(team1Score == team2Score){
            return 2; //draw
        }
        return -1;
    }

    /**
     * orderHand(): orders player hand in suit and rank
     * @param toOrder
     */
    private void orderHand(ArrayList<Card> toOrder) {
        int[] hearts = new int[13];
        int[] clubs = new int[13];
        int[] diamonds = new int[13];
        int[] spades = new int[13];
        for (int j = 0; j < 13; j++) { //pick out cards of each suit
            if (toOrder.get(j).getSuit().equals(Card.HEARTS)) {
                hearts[toOrder.get(j).getRank() - 2] = j + 1; //store in array based on rank; cards unique...
            }
            else if (toOrder.get(j).getSuit().equals(Card.CLUBS)) {//...each suit array is updated at the entry corresponding to card rank...
                clubs[toOrder.get(j).getRank() - 2] = j + 1;
            }
            else if (toOrder.get(j).getSuit().equals(Card.DIAMONDS)) {//...and those entries contain the location of the card...
                diamonds[toOrder.get(j).getRank() - 2] = j + 1;
            }
            else if (toOrder.get(j).getSuit().equals(Card.SPADES)) {//...in player's hand
                spades[toOrder.get(j).getRank() - 2] = j + 1;
            }
        }

        ArrayList<Card> temp = new ArrayList<>(13);
        for (int j = 0; j < 13; j++) {
            temp.add(j, null);
        }

        Collections.copy(temp, toOrder); //hold the cards so we can find them

        int idx = 0;
        for (int k = 0; k < 13; k++) { //find the cards and rewrite the player's ArrayList of cards
            if (hearts[k] != 0) {
                toOrder.set(idx, temp.get(hearts[k] - 1));
                idx++;
            }
        }
        for (int k = 0; k < 13; k++) {
            if (clubs[k] != 0) {
                toOrder.set(idx, temp.get(clubs[k] - 1));
                idx++;
            }
        }
        for (int k = 0; k < 13; k++) {
            if (diamonds[k] != 0) {
                toOrder.set(idx, temp.get(diamonds[k] - 1));
                idx++;
            }
        }
        for (int k = 0; k < 13; k++) {
            if (spades[k] != 0) {
                toOrder.set(idx, temp.get(spades[k] - 1));
                idx++;
            }
        }
    }

    public void updateHighest(Card played) { // 2 spade = value 15, 3 spade val 16, etc
        int realRank = played.getRank();
        if (played.getSuit().equals(Card.SPADES)) { //if spade
            realRank = realRank + 13;
            if (realRank > highCard) {
                highCard = realRank;
            }
        }
        else if (cardsInTrick != 0) { //if not first card
            if (firstCardSuit.equals(played.getSuit())) { //if follow suit
                if (played.getRank() > highCard) { //if beats
                    highCard = played.getRank();
                }
            }
        }
        else { //else first card
            highCard = played.getRank();
        }


    }

    /**
     * noShowCard(): makes trick cards invisible after a trick has concluded
     */
    public void noShowCard() {
        if(cardsInTrick == 4) {
            showPlayer0 = false;
            showPlayer1 = false;
            showPlayer2 = false;
            showPlayer3 = false;
        }
    }
}
