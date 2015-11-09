package edu.up.cs301.spadestest;
import java.util.Random;

/**
 * Created by morrisor17 on 11/8/2015.
 */
public class SpadesState {

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

    public SpadesState() {
        currentPlayer = 0;

        cardsPlayed = 0;

        playerScores = new int[]{0, 0, 0, 0};
        playerTricks = new int[]{0, 0, 0, 0};

        team1Score = 0;
        team2Score = 0;

        trickCards = new Card[]{};

        player1Hand = new Card[]{};
        player2Hand = new Card[]{};
        player3Hand = new Card[]{};
        player4Hand = new Card[]{};

        playerBags = new int[]{0, 0, 0, 0};
        playerBids = new int[]{0, 0, 0, 0};

        Random rand = new Random();
        userTeammate = rand.nextInt(3) + 1;
    }

    public SpadesState(SpadesState copy){
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

    }

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
        return playerBags[player];
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
        if(currentPlayer == 0)
            trickCards[cardsPlayed] = player1Hand[index];

        else if(currentPlayer == 1)
            trickCards[cardsPlayed] = player2Hand[index];

        else if(currentPlayer == 2)
            trickCards[cardsPlayed] = player3Hand[index];

        else if(currentPlayer == 3)
            trickCards[cardsPlayed] = player4Hand[index];

        if(cardsPlayed <3)
            cardsPlayed++;
        else cardsPlayed = 0;

        if(currentPlayer<3)
            currentPlayer++;
        else currentPlayer = 0;
    }
}
