package edu.up.cs301.spadestest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Main Activity, primarily to handle onClicks and background process's
 */

public class MainActivity extends GameMainActivity {

    private static final int PORT_NUMBER = 5555;

    /**
     * Create the default configuration for this game:
     * - one human player vs. thtee computer players
     *
     * @return the new configuration object, representing the default configuration
     */
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // Spades has two player types:  human and computer
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) { return new SpadesHumanPlayer(name);}});

        playerTypes.add(new GamePlayerType("Local Computer Player") {
            public GamePlayer createPlayer(String name) { return new SpadesComputerPlayer(name,1);}});

        playerTypes.add(new GamePlayerType("Local Computer Player") {
            public GamePlayer createPlayer(String name) { return new SpadesComputerPlayer(name,2);}});

        playerTypes.add(new GamePlayerType("Local Computer Player") {
            public GamePlayer createPlayer(String name) { return new SpadesComputerPlayer(name,3);}});

        GameConfig defaultConfig = new GameConfig(playerTypes,4,4,"Spades",PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player
        defaultConfig.addPlayer("Computer", 2); // player 2: a computer player
        defaultConfig.addPlayer("Computer", 3); // player 2: a computer player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        return defaultConfig;
    }

    public LocalGame createLocalGame() {
        return new SpadesLocalGame();
    }

}
