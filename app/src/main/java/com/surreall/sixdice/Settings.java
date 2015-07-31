package com.surreall.sixdice;


import java.util.regex.Pattern;
import java.util.zip.Inflater;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.surreall.sixdice.R;

public class Settings extends Main {
	SharedPreferences mGameSettings;
	SharedPreferences mGameSound;
	SharedPreferences scoreLoopSettings, mGooglePlay;
	SharedPreferences mGameDoubleScore;
	SharedPreferences mTrueScore, mSeqScore;
	SharedPreferences mFourScore, mFiveScore, mSixScore;
	SharedPreferences mHint;
	SharedPreferences mHeight;
	SharedPreferences mRadioSound;
	
	Boolean isHint;
	Boolean isSound;
	Boolean isScoreLoop, isGooglePlay;
	Editor editor, editor2, editor3,  editorSeq, editorDouble, editorTrue;
	Editor editorFour, editorFive, editorSix, editorHint, eHeight, eRadioSound, eGooglePlay;
	CheckBox muteSound, scoreLoopCB, hintCB, cbGooglePlay;
	Button resetScore;
	Integer layHi = 0;
	Integer radioSound;
	OnClickListener resetListener;
	OnClickListener muteListener;
	OnClickListener loopListener;
	OnClickListener playListener;
	OnClickListener hintListener;
	OnClickListener soundListener;
	TextView resetText;
	TextView developerText;
	TextView versionText;
	RadioButton radio_ori, radio_new, radio_mute;
	String app_ver;
	public static final String tag = "versionNo";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings);	 
        getGameHelper().setMaxAutoSignInAttempts(0);
	    developerText = (TextView) findViewById(R.id.developerText);	    
	    Pattern p = Pattern.compile("surreallgames@gmail.com");
	    String Scheme = "mailto:";
	    Linkify.addLinks(developerText, p, Scheme);
	    developerText.setLinkTextColor(getResources().getColorStateList(R.color.colors));	    
	    versionText = (TextView)findViewById(R.id.versionText);
	    
	    try
	    {
	        String app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
	        versionText.setText("\n\n\nVersion - " + app_ver);
	    }	    catch (NameNotFoundException e){
	        Log.v(tag, e.getMessage());
	    }	   
	    
	    resetText = (TextView)findViewById(R.id.resetText);
	    resetScore = (Button)findViewById(R.id.resetScore2);
	    //muteSound = (CheckBox)findViewById(R.id.checkBox1); 
	   // scoreLoopCB = (CheckBox)findViewById(R.id.checkBox2);
	    //cbGooglePlay = (CheckBox)findViewById(R.id.checkBox3);
	   // hintCB = (CheckBox)findViewById(R.id.checkBox4);
	    radio_ori = (RadioButton)findViewById(R.id.radio_ori);
	    radio_new = (RadioButton)findViewById(R.id.radio_new);
	    radio_mute = (RadioButton)findViewById(R.id.radio_mute);
	    
	    mGameSound = getSharedPreferences(GAME_PREFERENCES_SOUND, Context.MODE_PRIVATE);
   	 	//scoreLoopSettings = getSharedPreferences(GAME_PREFERENCES_SCORELOOP,Context.MODE_PRIVATE);
   	 	mHint = getSharedPreferences(GAME_PREFERENCES_HINT,Context.MODE_PRIVATE);
   	 	mHeight = getSharedPreferences(GAME_PREFERENCES_HEIGHT,Context.MODE_PRIVATE);
   	 	mRadioSound = getSharedPreferences(GAME_PREFERENCES_RADIO_SOUND, Context.MODE_PRIVATE);
   	 	mGooglePlay = getSharedPreferences(GAME_PLAY_SERVICES, Context.MODE_PRIVATE);
	    
	    editor2 = mGameSound.edit();
	    editorHint = mHint.edit();
	    eHeight = mHeight.edit();
	    eRadioSound = mRadioSound.edit();
	    eGooglePlay = mGooglePlay.edit();
	    
        isGooglePlay = mGooglePlay.getBoolean(GAME_PLAY_SERVICES, true);
        isHint = mHint.getBoolean(GAME_PREFERENCES_HINT, false);
        layHi = mHeight.getInt(GAME_PREFERENCES_HEIGHT, 0);
        radioSound = mRadioSound.getInt(GAME_PREFERENCES_RADIO_SOUND, 1);
        
        if(radioSound == 0){
        	radio_ori.setChecked(true);
        	radio_new.setChecked(false);
        	radio_mute.setChecked(false);
        } else if (radioSound == 1){
        	radio_ori.setChecked(false);
        	radio_new.setChecked(true);
        	radio_mute.setChecked(false);
        } else {
        	radio_ori.setChecked(false);
        	radio_new.setChecked(false);
        	radio_mute.setChecked(true);
        }
        
        
        
        textAdjust();


        
        soundListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(radio_ori.isChecked()){
					radioSound = 0;
				} else if (radio_new.isChecked()) {
					radioSound = 1;
				} else if (radio_mute.isChecked()) {
					radioSound = 2;
				}
				eRadioSound.putInt(GAME_PREFERENCES_RADIO_SOUND, radioSound);
				eRadioSound.commit();
			}
		};
        
        
	    muteListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(isSound){
					isSound=false;
					editor2.putBoolean(GAME_PREFERENCES_SOUND, false);
		   	 		editor2.commit();
				} else {
					isSound = true;
					editor2.putBoolean(GAME_PREFERENCES_SOUND, true);
		   	 		editor2.commit();
				}
			}
	    	
	    };
	    
	    hintListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(isHint){
					isHint=false;
					editorHint.putBoolean(GAME_PREFERENCES_HINT, false);
		   	 		editorHint.commit();
				} else {
					isHint = true;
					editorHint.putBoolean(GAME_PREFERENCES_HINT, true);
		   	 		editorHint.commit();
				}
				
				
			}
	    	
	    };
	    
	 	resetListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				//Button b = (Button)v;
				mGameSettings = getSharedPreferences(GAME_PREFERENCES_HISCORE, Context.MODE_PRIVATE);
				editor = mGameSettings.edit();
				editor.putInt(GAME_PREFERENCES_HISCORE, 0);
				editor.commit();
				
				mGameDoubleScore = getSharedPreferences(GAME_PREFERENCES_DOUBLEHISCORE, Context.MODE_PRIVATE);
				editorDouble = mGameDoubleScore.edit();
				editorDouble.putInt(GAME_PREFERENCES_DOUBLEHISCORE, 0);
				editorDouble.commit();
				
				mTrueScore = getSharedPreferences(GAME_PREFERENCES_TRUE_HISCORE, Context.MODE_PRIVATE);
				editorTrue = mTrueScore.edit();
				editorTrue.putInt(GAME_PREFERENCES_TRUE_HISCORE, 0);
				editorTrue.commit();
				
				mSeqScore = getSharedPreferences(GAME_PREFERENCES_SEQ_HISCORE,  Context.MODE_PRIVATE);
				editorSeq = mSeqScore.edit();
				editorSeq.putInt(GAME_PREFERENCES_SEQ_HISCORE, 0);
				editorSeq.commit();
				
				mFourScore = getSharedPreferences(GAME_PREFERENCES_FOUR_HISCORE, Context.MODE_PRIVATE);
				editorFour = mFourScore.edit();
				editorFour.putInt(GAME_PREFERENCES_FOUR_HISCORE, 0);
				editorFour.commit();
				
				// set Result to pass back to main.java or yahtzee.java
			    // Reset Score button is pushed
			            setResult(RESULT_OK);
			    // else
			            //setResult(RESULT_CANCELED);
			}
	 	};
	 	

	 	
	 	playListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				/*
				if(isGooglePlay){
					isGooglePlay=false;
					eGooglePlay.putBoolean(GAME_PLAY_SERVICES, false);
					eGooglePlay.commit();
				} else {
					isGooglePlay = true;
					eGooglePlay.putBoolean(GAME_PLAY_SERVICES, true);
					eGooglePlay.commit();
				}
				*/
			}
	 		
	 	};
	 	
	 	
	    //addPreferencesFromResource(R.xml.preferences);
	 	resetScore.setOnClickListener(resetListener); 
	 	//cbGooglePlay.setOnClickListener(playListener);
	 	//hintCB.setOnClickListener(hintListener);
	 	radio_ori.setOnClickListener(soundListener);
	 	radio_new.setOnClickListener(soundListener);
	 	radio_mute.setOnClickListener(soundListener);
	}
	
	private void textAdjust() {
		Integer ats1 = 0;  	// Adjusted Text Size
		Integer ats2 = 0;
		Integer ms1 = 0;
		if(layHi <480){
			ats1 = 10; ats2 = 11; ms1 = 0;
		}

		
		if(layHi <500){
			ats1 = 10; ats2 = 14; ms1 = 0;
		}
		if(layHi >=500 &layHi < 800 ){
			ats1 = 10; ats2 = 15; ms1 = 0;
		}
		if(layHi >=800 & layHi < 900){
			ats1 = 12; ats2 = 17; ms1 = 1;
		}
		if(layHi >=900 & layHi < 1000){
			ats1 = 14; ats2 = 19; ms1 = 2;
		}
		if(layHi >=1000 & layHi < 1100){
			ats1 = 16; ats2 = 20; ms1 = 3;
		}
		if(layHi >=1100 & layHi < 1200){ 
			ats1 = 18; ats2 = 22; ms1 = 5;
		}
		if(layHi >=1200 & layHi < 1300){ 
			ats1 = 22; ats2 = 28; ms1 = 8;
		}
		if(layHi >=1300){
			ats1 = 24; ats2 = 30; ms1 = 10;
		}		
		
		Integer x = ats2+ms1 + ats1;
		ms1 = x;
		//LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)rules.getLayoutParams();
		
		//params.setMargins(ms1, ms1, ms1, ms1); //substitute parameters for left, top, right, bottom
		
		//rules.setLayoutParams(params);
		
		
		resetText.setTextSize(ats2);
		
        
		
	}
	
	 public void tosty(String tost){
	  		Toast.makeText(getApplicationContext(),
	  		""+tost,
	  		Toast.LENGTH_LONG).show();
	 }
}
/*
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
*/