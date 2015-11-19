package edu.up.cs301.spadestest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
public class SpadesHumanPlayer extends GameHumanPlayer implements View.OnClickListener {

    // Widgets to be used and modified during play
    private TextView playerScoreTextView;
    private TextView partnerScoreTextView;
    private TextView LScoreTextView;
    private TextView RScoreTextView;
    private TextView playerTrickTextView;
    private TextView partnerTrickTextView;
    private TextView LTrickTextView;
    private TextView RTrickTextView;
    private ImageButton c0;
    private ImageButton c1;
    private ImageButton c2;
    private ImageButton c3;
    private ImageButton c4;
    private ImageButton c5;
    private ImageButton c6;
    private ImageButton c7;
    private ImageButton c8;
    private ImageButton c9;
    private ImageButton c10;
    private ImageButton c11;
    private ImageButton c12;
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
        myActivity = activity;
        activity.setContentView(R.layout.activity_main);

        playerScoreTextView = (TextView) activity.findViewById(R.id.p0trickfield);

        c0 = (ImageButton) activity.findViewById(R.id.c0);
        c1 = (ImageButton) activity.findViewById(R.id.c1);
        c2 = (ImageButton) activity.findViewById(R.id.c2);
        c3 = (ImageButton) activity.findViewById(R.id.c3);
        c4 = (ImageButton) activity.findViewById(R.id.c4);
        c5 = (ImageButton) activity.findViewById(R.id.c5);
        c6 = (ImageButton) activity.findViewById(R.id.c6);
        c7 = (ImageButton) activity.findViewById(R.id.c7);
        c8 = (ImageButton) activity.findViewById(R.id.c8);
        c9 = (ImageButton) activity.findViewById(R.id.c9);
        c10= (ImageButton) activity.findViewById(R.id.c10);
        c11= (ImageButton) activity.findViewById(R.id.c11);
        c12= (ImageButton) activity.findViewById(R.id.c12);

        c0.setOnClickListener(this);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);
        c9.setOnClickListener(this);
        c10.setOnClickListener(this);
        c11.setOnClickListener(this);
        c12.setOnClickListener(this);
    }

    /**
     * onClick(): handles buttons being clicked
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v==c0){
            playerScoreTextView.setText("BLAH");
        }else if (v==c1){

        }else if (v==c2){

        }else if (v==c3){

        }else if (v==c4){

        }else if (v==c5){

        }else if (v==c6){

        }else if (v==c7){

        }else if (v==c8){

        }else if (v==c9){

        }else if (v==c10){

        }else if (v==c11){

        }else if (v==c12){

        }

    }
}
