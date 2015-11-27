package edu.up.cs301.spadestest;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
public class SpadesHumanPlayer extends GameHumanPlayer {

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

    SpadesState myGameState;
    private GameMainActivity myActivity;

    private String[] cardNames;
    private ArrayList<Bitmap> deck;


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
            c0.setImageResource(myGameState.getPlayer1Hand().get(0).imageId);
            c1.setImageResource(myGameState.getPlayer1Hand().get(1).imageId);
            c2.setImageResource(myGameState.getPlayer1Hand().get(2).imageId);
            c3.setImageResource(myGameState.getPlayer1Hand().get(3).imageId);
            c4.setImageResource(myGameState.getPlayer1Hand().get(4).imageId);
            c5.setImageResource(myGameState.getPlayer1Hand().get(5).imageId);
            c6.setImageResource(myGameState.getPlayer1Hand().get(6).imageId);
            c7.setImageResource(myGameState.getPlayer1Hand().get(7).imageId);
            c8.setImageResource(myGameState.getPlayer1Hand().get(8).imageId);
            c9.setImageResource(myGameState.getPlayer1Hand().get(9).imageId);
            c10.setImageResource(myGameState.getPlayer1Hand().get(10).imageId);
            c11.setImageResource(myGameState.getPlayer1Hand().get(11).imageId);
            c12.setImageResource(myGameState.getPlayer1Hand().get(12).imageId);
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
        partnerTrickTextView = (TextView) activity.findViewById(R.id.p0trick);
        LTrickTextView = (TextView) activity.findViewById(R.id.p0trick);
        RTrickTextView = (TextView) activity.findViewById(R.id.p0trick);

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

        // create arrayList to hold the deck
        cardNames = activity.getResources().getStringArray(R.array.card_names);
        deck = new ArrayList<Bitmap>();
        TypedArray cardIds = activity.getResources().obtainTypedArray(R.array.cardIdArray); //don't use "activity?"
        for (int i = 0; i < cardNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = cardIds.getResourceId(i, 0);
            if (id == 0) id = cardIds.getResourceId(0, 0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(activity.getResources(), id);
            deck.add(img);
        }

        c0.setOnTouchListener(new ChoiceTouchListener());
        c1.setOnTouchListener(new ChoiceTouchListener());
        c2.setOnTouchListener(new ChoiceTouchListener());
        c3.setOnTouchListener(new ChoiceTouchListener());
        c4.setOnTouchListener(new ChoiceTouchListener());
        c5.setOnTouchListener(new ChoiceTouchListener());
        c6.setOnTouchListener(new ChoiceTouchListener());
        c7.setOnTouchListener(new ChoiceTouchListener());
        c8.setOnTouchListener(new ChoiceTouchListener());
        c9.setOnTouchListener(new ChoiceTouchListener());
        c10.setOnTouchListener(new ChoiceTouchListener());
        c11.setOnTouchListener(new ChoiceTouchListener());
        c12.setOnTouchListener(new ChoiceTouchListener());

        p0card.setOnDragListener(new ChoiceDragListener());
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else {
                return false;
            }

        }


    }
    @TargetApi(11)
    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
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
                    //currently does not add card back to deck
                    //note: playCard currently updates trickCards by adding new cards to array; think they get removed after a trick but not sure
                    if (dropped == c0) {
                        myGameState.playCard(0);
                    } else if (dropped == c1) {
                        myGameState.playCard(1);
                    } else if (dropped == c2) {
                        myGameState.playCard(2);
                    } else if (dropped == c3) {
                        myGameState.playCard(3);
                    } else if (dropped == c4) {
                        myGameState.playCard(4);
                    } else if (dropped == c5) {
                        myGameState.playCard(5);
                    } else if (dropped == c6) {
                        myGameState.playCard(6);
                    } else if (dropped == c7) {
                        myGameState.playCard(7);
                    } else if (dropped == c8) {
                        myGameState.playCard(8);
                    } else if (dropped == c9) {
                        myGameState.playCard(9);
                    } else if (dropped == c10) {
                        myGameState.playCard(10);
                    } else if (dropped == c11) {
                        myGameState.playCard(11);
                    } else if (dropped == c12) {
                        myGameState.playCard(12);
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
    }
}