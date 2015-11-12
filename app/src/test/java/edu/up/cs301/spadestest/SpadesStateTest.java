package edu.up.cs301.spadestest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Nick Wagner, Ryan Morrison, Jin Mok, Maddy Duretete
 * @version Nov. 2015
 *
 * Tests SpadesState.java using JUNIT
 */
public class SpadesStateTest {

    /**
     * testSpadesState(): tests that all objects in SpadesState are correctly
     *                      initialized
     * @throws Exception
     */
    @Test
    public void testSpadesState() throws Exception {
        SpadesState test = new SpadesState(); //creates test state

        //tests the int objects
        assertEquals(test.currentPlayer, 0);
        assertEquals(test.cardsPlayed, 0);
        assertEquals(test.team1Score, 0);
        assertEquals(test.team2Score, 0);
        assertEquals(test.userTeammate, 2);

        //tests the arrays
        assertEquals(test.playerScores[0], 0);
        assertEquals(test.playerTricks[0], 0);
        assertEquals(test.playerBags[0], 0);
        assertEquals(test.playerBids[0], 0);

        //tests the ArrayLists
        assertNotNull(test.trickCards);
        assertNotNull(test.player1Hand);
        assertNotNull(test.player2Hand);
        assertNotNull(test.player3Hand);
        assertNotNull(test.player4Hand);
    }

    /**
     * testSpadesStateCopy(): tests that objects in SpadesState are correctly copied
     *                      to the copy constructor
     * @throws Exception
     */
    @Test
    public void testSpadesStateCopy() throws Exception {
        SpadesState test = new SpadesState(); //creates test state

        test.placeBid(4); //places a bid of 4 into current player (index 0)
        test.initDeck();

        SpadesState testCopy = new SpadesState(test); //copy updated test to a copy
        assertEquals(test.getPlayerBids(0), testCopy.getPlayerBids(0)); //test the copy has the updated value
        assertEquals(test.deck.get(5), testCopy.deck.get(5)); //test the copy has it's deck init'd
    }

    /**
     * testPlaceBid(): tests that a bid is correctly placed into playerBids[]
     *                   using placeBid()
     * @throws Exception
     */
    @Test
    public void testPlaceBid() throws Exception {
        SpadesState test = new SpadesState();
        test.placeBid(4);
        assertEquals(test.getPlayerBids(0),4);
    }

    /**
     * testPlayCard(): tests that playing a card places it in trickCards[]
     *                  using playCard()
     * @throws Exception
     */
    @Test
    public void testPlayCard() throws Exception {
        SpadesState test = new SpadesState();
        test.playCard(0);
        assertNotNull(test.trickCards[0]);
    }

    /**
     * testInitDeck(): tests that the deck is not equal to an empty deck
     * @throws Exception
     */
    @Test
    public void testInitDeck() throws Exception {
        SpadesState test = new SpadesState();
        SpadesState temp = new SpadesState();
        test.initDeck();
        assertNotEquals(test.deck, temp.deck);
    }

    @Test
    public void testGetUserTeammate() throws Exception {
        SpadesState test = new SpadesState();
        assertNotNull(test.getUserTeammate());
    }
}