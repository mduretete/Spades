package edu.up.cs301.spadestest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

/**
 * @author Ryan Morrison, Jin Mok, Nick Wagner, Maddy Duretete
 * @version Nov. 2015
 *
 * Main Activity, primarily to handle onClicks and background process's
 */

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    ImageButton c0;
    ImageButton c1;
    ImageButton c2;
    ImageButton c3;
    ImageButton c4;
    ImageButton c5;
    ImageButton c6;
    ImageButton c7;
    ImageButton c8;
    ImageButton c9;
    ImageButton c10;
    ImageButton c11;
    ImageButton c12;

    /**
     * onCreate(): creates the layout and holds button listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c0 = (ImageButton) findViewById(R.id.c0);
        c1 = (ImageButton) findViewById(R.id.c1);
        c2 = (ImageButton) findViewById(R.id.c2);
        c3 = (ImageButton) findViewById(R.id.c3);
        c4 = (ImageButton) findViewById(R.id.c4);
        c5 = (ImageButton) findViewById(R.id.c5);
        c6 = (ImageButton) findViewById(R.id.c6);
        c7 = (ImageButton) findViewById(R.id.c7);
        c8 = (ImageButton) findViewById(R.id.c8);
        c9 = (ImageButton) findViewById(R.id.c9);
        c10= (ImageButton) findViewById(R.id.c10);
        c11= (ImageButton) findViewById(R.id.c11);
        c12= (ImageButton) findViewById(R.id.c12);

    }

    /**
     * onCreateOptionsMenu(): handles the default menu options given by android
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * onOptionsItemSelected(): handles the action bar item in the options menu
     *                          being clicked
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onClick(): handles buttons being clicked
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v==c0){
            c0.setAlpha(0);
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
