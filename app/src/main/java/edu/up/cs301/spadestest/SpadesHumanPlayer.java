package edu.up.cs301.spadestest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.game.util.MessageBox;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Class that extends GameHumanPlayer and specifies the actions
 *      the player can make and values that the player object
 *      holds
 */
public class SpadesHumanPlayer extends GameHumanPlayer implements View.OnDragListener, View.OnTouchListener, View.OnClickListener {

    // Widgets to be used and modified during play
    private TextView playerBidTextView;
    private TextView partnerBidTextView;
    private TextView LBidTextView;
    private TextView RBidTextView;
    private TextView playerTrickTextView;
    private TextView partnerTrickTextView;
    private TextView LTrickTextView;
    private TextView RTrickTextView;
    private ImageView p0card;
    private ImageView p1card;
    private ImageView p2card;
    private ImageView p3card;
    private ImageView c0;
    private ImageView c1;
    private ImageView c2;
    private ImageView c3;
    private ImageView c4;
    private ImageView c5;
    private ImageView c6;
    private ImageView c7;
    private ImageView c8;
    private ImageView c9;
    private ImageView c10;
    private ImageView c11;
    private ImageView c12;

    private EditText bidView;
    private Button bidConfirm;

    private TextView t1Score;
    private TextView t2Score;

    SpadesState myGameState;
    private GameMainActivity myActivity;

    private String[] cardNames;

    /**
     * SpadesHumanPlayer():ctor for the human player
     *
     * @param name
     */
    public SpadesHumanPlayer(String name) {
        super(name);
    }

    /**
     * getTopView(): returns the GUI's top object
     *
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * recieveInfo(): Callback method, called when player gets a message
     *
     * @param info
     */
    public void receiveInfo(GameInfo info) {
        if (info instanceof SpadesState) {
            myGameState = (SpadesState) info;
            playerBidTextView.setText("" + myGameState.getPlayerBids(0));
            LBidTextView.setText("" + myGameState.getPlayerBids(1));
            partnerBidTextView.setText("" + myGameState.getPlayerBids(2));
            RBidTextView.setText("" + myGameState.getPlayerBids(3));

            playerTrickTextView.setText("" + myGameState.getPlayerTricks(0));
            LTrickTextView.setText("" + myGameState.getPlayerTricks(1));
            partnerTrickTextView.setText("" + myGameState.getPlayerTricks(2));
            RTrickTextView.setText("" + myGameState.getPlayerTricks(3));
            if (myGameState.getPlayer1Hand().get(0) != null) {
                c0.setImageResource(myGameState.getPlayer1Hand().get(0).imageId);
            }
            if (myGameState.getPlayer1Hand().get(1) != null) {
                c1.setImageResource(myGameState.getPlayer1Hand().get(1).imageId);
            }
            if (myGameState.getPlayer1Hand().get(2) != null) {
                c2.setImageResource(myGameState.getPlayer1Hand().get(2).imageId);
            }
            if (myGameState.getPlayer1Hand().get(3) != null) {
                c3.setImageResource(myGameState.getPlayer1Hand().get(3).imageId);
            }
            if (myGameState.getPlayer1Hand().get(4) != null) {
                c4.setImageResource(myGameState.getPlayer1Hand().get(4).imageId);
            }
            if (myGameState.getPlayer1Hand().get(5) != null) {
                c5.setImageResource(myGameState.getPlayer1Hand().get(5).imageId);
            }
            if (myGameState.getPlayer1Hand().get(6) != null) {
                c6.setImageResource(myGameState.getPlayer1Hand().get(6).imageId);
            }
            if (myGameState.getPlayer1Hand().get(7) != null) {
                c7.setImageResource(myGameState.getPlayer1Hand().get(7).imageId);
            }
            if (myGameState.getPlayer1Hand().get(8) != null) {
                c8.setImageResource(myGameState.getPlayer1Hand().get(8).imageId);
            }
            if (myGameState.getPlayer1Hand().get(9) != null) {
                c9.setImageResource(myGameState.getPlayer1Hand().get(9).imageId);
            }
            if (myGameState.getPlayer1Hand().get(10) != null) {
                c10.setImageResource(myGameState.getPlayer1Hand().get(10).imageId);
            }
            if (myGameState.getPlayer1Hand().get(11) != null) {
                c11.setImageResource(myGameState.getPlayer1Hand().get(11).imageId);
            }
            if (myGameState.getPlayer1Hand().get(12) != null) {
                c12.setImageResource(myGameState.getPlayer1Hand().get(12).imageId);
            }


            if (myGameState.getTrickCards().size() > 0) {
                if (myGameState.getTrickCards().get(1) != null) {
                    p1card.setImageResource(myGameState.getTrickCards().get(1).imageId);
                }
                if (myGameState.getTrickCards().get(2) != null) {
                    p2card.setImageResource(myGameState.getTrickCards().get(2).imageId);
                }
                if (myGameState.getTrickCards().get(3) != null) {
                    p3card.setImageResource(myGameState.getTrickCards().get(3).imageId);
                }
            }

            t1Score.setText("T1 Score:       " + myGameState.team1Score + " ");
            t2Score.setText("T2 Score:       " + myGameState.team2Score + " ");

        }
    }

    /**
     * setAsGui(): Sets this player as the one attached to the GUI, saves the
     * activity, then invokes subclass-specific method
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setAsGui(GameMainActivity activity) {
        myActivity = activity;
        activity.setContentView(R.layout.activity_main);

        playerBidTextView = (TextView) activity.findViewById(R.id.p0bid);
        partnerBidTextView = (TextView) activity.findViewById(R.id.p2bid);
        LBidTextView = (TextView) activity.findViewById(R.id.p1bid);
        RBidTextView = (TextView) activity.findViewById(R.id.p3bid);
        playerTrickTextView = (TextView) activity.findViewById(R.id.p0trick);
        partnerTrickTextView = (TextView) activity.findViewById(R.id.p2trick);
        LTrickTextView = (TextView) activity.findViewById(R.id.p1trick);
        RTrickTextView = (TextView) activity.findViewById(R.id.p3trick);

        bidView = (EditText) activity.findViewById(R.id.bidview);
        bidConfirm = (Button) activity.findViewById(R.id.bidconfirm);

        t1Score = (TextView) activity.findViewById(R.id.team1score);
        t2Score = (TextView) activity.findViewById(R.id.team2score);

        c0 = (ImageView) activity.findViewById(R.id.c0);
        c1 = (ImageView) activity.findViewById(R.id.c1);
        c2 = (ImageView) activity.findViewById(R.id.c2);
        c3 = (ImageView) activity.findViewById(R.id.c3);
        c4 = (ImageView) activity.findViewById(R.id.c4);
        c5 = (ImageView) activity.findViewById(R.id.c5);
        c6 = (ImageView) activity.findViewById(R.id.c6);
        c7 = (ImageView) activity.findViewById(R.id.c7);
        c8 = (ImageView) activity.findViewById(R.id.c8);
        c9 = (ImageView) activity.findViewById(R.id.c9);
        c10 = (ImageView) activity.findViewById(R.id.c10);
        c11 = (ImageView) activity.findViewById(R.id.c11);
        c12 = (ImageView) activity.findViewById(R.id.c12);

        p0card = (ImageView) activity.findViewById(R.id.p0card);
        p1card = (ImageView) activity.findViewById(R.id.p1card);
        p2card = (ImageView) activity.findViewById(R.id.p2card);
        p3card = (ImageView) activity.findViewById(R.id.p3card);

        c0.setOnTouchListener(this);
        c1.setOnTouchListener(this);
        c2.setOnTouchListener(this);
        c3.setOnTouchListener(this);
        c4.setOnTouchListener(this);
        c5.setOnTouchListener(this);
        c6.setOnTouchListener(this);
        c7.setOnTouchListener(this);
        c8.setOnTouchListener(this);
        c9.setOnTouchListener(this);
        c10.setOnTouchListener(this);
        c11.setOnTouchListener(this);
        c12.setOnTouchListener(this);

        p0card.setOnDragListener(this);

        bidConfirm.setOnClickListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        if(!bidView.isEnabled()) {
            //handle drag events
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.INVISIBLE);
                    //cast passed area to drop in
                    ImageView dropSpace = (ImageView) v;
                    //cast active button being dragged
                    ImageView dropped = (ImageView) view;
                    //drop the card
                    dropped.setDrawingCacheEnabled(true);
                    dropSpace.setImageBitmap(dropped.getDrawingCache());
                    //update played card
                    //note: playCard currently updates trickCards by adding new cards to array; think they get removed after a trick but not sure
                    if (dropped == c0) {
                        game.sendAction(new SpadesPlayCardAction(this, 0));
                    } else if (dropped == c1) {
                        game.sendAction(new SpadesPlayCardAction(this, 1));
                    } else if (dropped == c2) {
                        game.sendAction(new SpadesPlayCardAction(this, 2));
                    } else if (dropped == c3) {
                        game.sendAction(new SpadesPlayCardAction(this, 3));
                    } else if (dropped == c4) {
                        game.sendAction(new SpadesPlayCardAction(this, 4));
                    } else if (dropped == c5) {
                        game.sendAction(new SpadesPlayCardAction(this, 5));
                    } else if (dropped == c6) {
                        game.sendAction(new SpadesPlayCardAction(this, 6));
                    } else if (dropped == c7) {
                        game.sendAction(new SpadesPlayCardAction(this, 7));
                    } else if (dropped == c8) {
                        game.sendAction(new SpadesPlayCardAction(this, 8));
                    } else if (dropped == c9) {
                        game.sendAction(new SpadesPlayCardAction(this, 9));
                    } else if (dropped == c10) {
                        game.sendAction(new SpadesPlayCardAction(this, 10));
                    } else if (dropped == c11) {
                        game.sendAction(new SpadesPlayCardAction(this, 11));
                    } else if (dropped == c12) {
                        game.sendAction(new SpadesPlayCardAction(this, 12));
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //only act if the bid has been confirmed already
        if(!bidView.isEnabled()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    /**
     * write a better comment here
     * @param v
     */
    @Override
    public void onClick(View v){
        if(v == bidConfirm){
            //default bid to zero if the player is a dumbass
            int bid = 0;
            try {
                bid = Integer.parseInt(bidView.getText().toString());
                if((bid>=0)&&(bid<=13)) {
                    myGameState.playerBids[0] = bid;
                    playerBidTextView.setText(bidView.getText().toString());

                    bidView.setEnabled(false);
                    bidConfirm.setEnabled(false);

                    return;
                }
            }catch(Exception e){}

            //in correspondence with default
            myGameState.playerBids[0] = 0;
            playerBidTextView.setText("0");

            bidView.setEnabled(false);
            bidConfirm.setEnabled(false);

            bidView.setText("ERROR: "+bidView.getText().toString());

        }
    }
}