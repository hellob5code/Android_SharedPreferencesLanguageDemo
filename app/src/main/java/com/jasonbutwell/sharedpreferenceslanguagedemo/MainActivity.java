package com.jasonbutwell.sharedpreferenceslanguagedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String [] languages = { "English", "Spanish" };
    private String languageToUse = "";
    private int languageID = -1;

    private TextView languageTextView;

    private void updateLanguageView () {
        TextView languageTextView = (TextView) findViewById( R.id.languageTextView );
        languageTextView.setText( "Language is set to : " + languages[languageID] ); // show what language is set to.
    }
    // sets the language id and then saves the language based on ID
    private void setLanguageAndSave( int id ) {
        languageID = id;
        saveLanguagePrefence( languages[ languageID ] );
        updateLanguageView ();
    }

    // saves the preference
    private void saveLanguagePrefence( String preference ) {
        sharedPreferences.edit().putString( "language", preference ).apply();
    }

    // attempts to get the preference
    private String getLanguagePreference() {
        return sharedPreferences.getString( "language", "" );
    }

    // get array index value of the string from the array
    private int getLanguageID() {
        int id = -1;

        for (int i = 0; i < languages.length; i ++) {
            if ( languages[i].equals(languageToUse) ) {
                id = i;
                break;  // exit the loop if found
            }
        }

        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences( "com.jasonbutwell.sharedpreferencesdemo", Context.MODE_PRIVATE );

        // if no preferences were saved previously then do the alert and ask the user

        // get the language string
        languageToUse = getLanguagePreference();
//
//        // check to see if it was set and request a language if not

        if ( languageToUse.isEmpty() )
             doAlert();                                              // calls the alert

        Log.i("language", languageToUse);

        languageID = getLanguageID();

        if (languageID != -1 )
            updateLanguageView();

        // Clear the value for testing purposes
        //sharedPreferences.edit().remove("language").commit();
    }

    private void doAlert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // stop user cancelling the dialog
        alertDialog.setCanceledOnTouchOutside( false );
        alertDialog.setCancelable( false );

        // set up the other fields
        alertDialog.setTitle("User Prompt");
        alertDialog.setMessage("Which language would you prefer to use?");

        // button and listener
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, languages[0],
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setLanguageAndSave( 0 );
                        dialog.dismiss();
                    }
                });

        // button and listener
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, languages[1],
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setLanguageAndSave( 1 );
                        dialog.dismiss();
                    }
                });

        // show the dialog
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // Check to see if the first Options menu item was selected.

        if (id == R.id.action_settings1) {
            Log.i("select","Item 1 selected");
            setLanguageAndSave( 0 );
            updateLanguageView();
            return true;
        }

        // CHeck to see if the second Options menu item was selected.

        if (id == R.id.action_settings2) {
            Log.i("select","Item 2 selected");
            setLanguageAndSave( 1 );
            updateLanguageView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
