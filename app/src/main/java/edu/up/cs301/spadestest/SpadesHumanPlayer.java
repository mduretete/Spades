package edu.up.cs301.spadestest;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Class that extends GameHumanPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds
 */
public class SpadesHumanPlayer extends GameHumanPlayer{

    // Widgets to be used and modified during play
    private TextView playerScoreTextView;
    private TextView partnerScoreTextView;
    private TextView LScoreTextView;
    private TextView RScoreTextView;
    private TextView playerTrickTextView;
    private TextView partnerTrickTextView;
    private TextView LTrickTextView;
    private TextView RTrickTextView;
    private Button card0;
    private Button card1;
    private Button card2;
    private Button card3;
    private Button card4;
    private Button card5;
    private Button card6;
    private Button card7;
    private Button card8;
    private Button card9;
    private Button card10;
    private Button card11;
    private Button card12;
    private Space dropSpace;

    private GameMainActivity myActivity;



    /**
     * SpadesHumanPlayer():ctor for the human player
     * @param name
     */
    public SpadesHumanPlayer(String name) {
        super(name);
    }

    /**
     * getTopView(): returns the GUI's top object
     * @return
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * recieveInfo(): Callback method, called when player gets a message
     * @param info
     */
    public void receiveInfo(GameInfo info) {
        if (info instanceof SpadesState) {
            SpadesState myGameState = (SpadesState) info;
            playerScoreTextView.setText("" + myGameState.getPlayerScore(0) );
        }
    }

    /**
     * setAsGui(): Sets this player as the one attached to the GUI, saves the
     *              activity, then invokes subclass-specific method
     * @param activity
     */
    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}
