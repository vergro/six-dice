package com.surreall.sixdice;


import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.surreall.sixdice.R;


public class Preferences extends Main {
	SharedPreferences mGameSettings;
	SharedPreferences mGameSound;
	SharedPreferences scoreLoopSettings;
	SharedPreferences mHint;
	SharedPreferences mRadioSound;
	Boolean isHint;
	Boolean isSound;
	Boolean isScoreLoop;
	ResizableButton rulesButton;
	Editor editor, editor2, editor3, editorHint, eRadioSound;
	Integer radioSound;
	CheckBox muteSound, scoreLoopCB, hintCB;
	OnClickListener muteListener;
	OnClickListener loopListener;
	OnClickListener hintListener;
	OnClickListener soundListener, rulesListener;
	TextView developerText;
	RadioButton radio_ori, radio_new, radio_mute;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.preferences); 
	    //muteSound = (CheckBox)findViewById(R.id.checkbox);
	    scoreLoopCB = (CheckBox)findViewById(R.id.checkbox2);
	    hintCB = (CheckBox)findViewById(R.id.checkboxHint);
	    radio_ori = (RadioButton)findViewById(R.id.radio_ori);
	    radio_new = (RadioButton)findViewById(R.id.radio_new);
	    radio_mute = (RadioButton)findViewById(R.id.radio_mute);
	    rulesButton = (ResizableButton)findViewById(R.id.rulesButton);
	    //mGameSound = getSharedPreferences(GAME_PREFERENCES_SOUND, Context.MODE_PRIVATE);
   	 	scoreLoopSettings = getSharedPreferences(GAME_PREFERENCES_SCORELOOP,Context.MODE_PRIVATE);
   	 	mHint = getSharedPreferences(GAME_PREFERENCES_HINT,Context.MODE_PRIVATE);
   	 	mRadioSound = getSharedPreferences(GAME_PREFERENCES_RADIO_SOUND, Context.MODE_PRIVATE);
	    
   	 	developerText = (TextView) findViewById(R.id.developerText);
	    
	    Pattern p = Pattern.compile("surreallgames@gmail.com");
	    String Scheme = "mailto:";
	    Linkify.addLinks(developerText, p, Scheme);
	    developerText.setLinkTextColor(getResources().getColorStateList(R.color.colors));
   	 	
   	 	
   	 	//editor2 = mGameSound.edit();
	    editor3 = scoreLoopSettings.edit();
	    editorHint = mHint.edit();   	 	
	    eRadioSound = mRadioSound.edit();
	    radioSound = mRadioSound.getInt(GAME_PREFERENCES_RADIO_SOUND, 1);
        //isSound = mGameSound.getBoolean(GAME_PREFERENCES_SOUND, true);
        isScoreLoop = scoreLoopSettings.getBoolean(GAME_PREFERENCES_SCORELOOP, true);
        isHint = mHint.getBoolean(GAME_PREFERENCES_HINT, false);
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
        /*
        if(isSound){
        	muteSound.setChecked(true);
        }
        */
        if(isScoreLoop){
        	scoreLoopCB.setChecked(true);
        }
        if(isHint){
        	hintCB.setChecked(true);
        }
        
        rulesListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent inten1 = new Intent(Preferences.this, Help.class);
				startActivity(inten1);
				
			}
        	
        };
        
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
				// TODO Auto-generated method stub
				if(isSound){
					isSound=false;
					editor2.putBoolean(GAME_PREFERENCES_SOUND, false);
		   	 		editor2.commit();
		   	 		
		   	 		//Toast.makeText(getApplicationContext(),"toggle on",Toast.LENGTH_LONG).show();
				} else {
					isSound = true;
					editor2.putBoolean(GAME_PREFERENCES_SOUND, true);
		   	 		editor2.commit();
				}
			}
	    	
	    };
	    loopListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(isScoreLoop){
					isScoreLoop=false;
					editor3.putBoolean(GAME_PREFERENCES_SCORELOOP, false);
		   	 		editor3.commit();
				} else {
					isScoreLoop = true;
					editor3.putBoolean(GAME_PREFERENCES_SCORELOOP, true);
		   	 		editor3.commit();
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
	   // muteSound.setOnClickListener(muteListener);
	 	scoreLoopCB.setOnClickListener(loopListener);
	 	hintCB.setOnClickListener(hintListener);
	 	radio_ori.setOnClickListener(soundListener);
	 	radio_new.setOnClickListener(soundListener);
	 	radio_mute.setOnClickListener(soundListener);
	 	rulesButton.setOnClickListener(rulesListener);
	}
}
/*
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
*/