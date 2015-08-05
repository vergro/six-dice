package com.surreall.sixdice;


//import com.google.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.BaseGameUtils;
/*
import com.scoreloop.client.android.ui.AchievementsScreenActivity;
import com.scoreloop.client.android.ui.ChallengesScreenActivity;
import com.scoreloop.client.android.ui.EntryScreenActivity;
import com.scoreloop.client.android.ui.OnScoreSubmitObserver;
import com.scoreloop.client.android.ui.OnStartGamePlayRequestObserver;
import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;
*/
import com.surreall.sixdice.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Start extends Main{
	
	public static final String LOG_TAG = "Driod";     

	
	Boolean firstTime = true;
	
	//ResizableButton rules, startgame, scoreloopbutton, achievements, setting, challenges, moregames, upgrade;
	
	ResizableButton trueStart, originalStart, seqStart, doubleStart, fourStart, fiveStart, chooseText;
	
	ResizableButton[] butN = new ResizableButton[9]; 
	
	TextView startT, startDouble;
	//Boolean isScoreLoop;
	Integer pageNum = 1;
	
	Boolean isPro;
	
	SharedPreferences mPro;	
	SharedPreferences preferences;
	SharedPreferences mGameSettings;
	SharedPreferences mGameDoubleScore; 
	SharedPreferences mGameType;
	SharedPreferences mWidth;
	SharedPreferences mHeight;
	SharedPreferences spinner_set;
	SharedPreferences scoreLoopSettings; 

	
	Editor gameEditor;
	Editor eWidth, eHeight;
	Editor eSpinner, ePro, eScoreLoop;
	Integer highScore, highScoreDouble, boxSize, spinner_position, layHi;

	OnClickListener buttonListener1, buttonListener2, buttonListener3, buttonListener4, buttonListener5,  buttonListener6, buttonListener7, buttonListener8;
	OnClickListener seqListener, oriListener, douListener, truListener, fourListener, fiveListener, moregamesListener, upgradeListener, resumeListener;
	OnClickListener achieveSL, achieveGP, leaderSL, leaderGP, multiSL, multiGP, signInGP, signOutGP;
	OnClickListener backListener;
	
	
	Integer gameNum;
	
	//Save Stuff
	SharedPreferences mContinue; 
	Editor eContinue;
	Boolean isContinue = false;
	Boolean isScoreLoop;
	OnClickListener resListener;
	

	
	private AdView adView;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	setVolumeControlStream (AudioManager.STREAM_MUSIC);
	    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
	        super.onCreate(savedInstanceState);
	        getGameHelper().setMaxAutoSignInAttempts(0);
	        //setContentView(R.layout.startpage);
	        mPro = getSharedPreferences(GAME_PREFERENCES_PRO, Context.MODE_PRIVATE);
		    ePro = mPro.edit();
		   

		  //SAVE STUFF
	        //resume = (ResizableButton)findViewById(R.id.start2);
	        mContinue = getSharedPreferences(GAME_PREFERENCES_CONTINUE, Context.MODE_PRIVATE);
	        eContinue = mContinue.edit();
	        isContinue = mContinue.getBoolean(GAME_PREFERENCES_CONTINUE, false); 
	        
	        /*
	        if(isContinue){
	        	resume.setVisibility(View.VISIBLE);
	        } else {
	        	resume.setVisibility(View.INVISIBLE);
	        }
	        */
	        
	        buttonListener2 = new OnClickListener(){	//resume
				@Override
				public void onClick(View arg0) {	
					Intent inten1 = new Intent(Start.this,  Game.class);
		   	 		startActivityForResult(inten1,0);					
				}	        	
	        };
		    
	        if(isProInstalled(getApplicationContext())){  
	        	
	        	setContentView(R.layout.startpagepro);    
	        	isPro = true;	   	        	
	        	
	        } else {
	        	setContentView(R.layout.startpage); 
	        	isPro = false;      	
	        	 AdView mAdView = (AdView)findViewById(R.id.adView);
	        	    AdRequest adRequest = new AdRequest.Builder().build();
	        	    mAdView.loadAd(adRequest);

	        } 
	        
	        ePro.putBoolean(GAME_PREFERENCES_PRO, isPro);
	        ePro.commit();
	               
	        
	        Display d = ((android.view.WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	        Integer screenWidth = d.getWidth();
	        Integer screenHeight = d.getHeight();
	        Log.v(LOG_TAG, "Screen Width: " + screenWidth);

	        butN[1] = (ResizableButton)findViewById(R.id.but1);
	        butN[2] = (ResizableButton)findViewById(R.id.but2);
	        butN[3] = (ResizableButton)findViewById(R.id.but3);
	        butN[4] = (ResizableButton)findViewById(R.id.but4);
	        butN[5] = (ResizableButton)findViewById(R.id.but5);
	        butN[6] = (ResizableButton)findViewById(R.id.but6);
	        butN[7] = (ResizableButton)findViewById(R.id.but7);
	        butN[8] = (ResizableButton)findViewById(R.id.but8);
	        
     
	        mGameSettings = getSharedPreferences(GAME_PREFERENCES_HISCORE, Context.MODE_PRIVATE);
	        mGameDoubleScore =getSharedPreferences(GAME_PREFERENCES_DOUBLEHISCORE, Context.MODE_PRIVATE);
	        mGameType = getSharedPreferences(GAME_PREFERENCES_TYPE, Context.MODE_PRIVATE);
	        mWidth = getSharedPreferences(GAME_PREFERENCES_WIDTH, Context.MODE_PRIVATE);
	        mHeight = getSharedPreferences(GAME_PREFERENCES_HEIGHT, Context.MODE_PRIVATE);
	   	 	scoreLoopSettings = getSharedPreferences(GAME_PREFERENCES_SCORELOOP,Context.MODE_PRIVATE);
	        
	        gameEditor = mGameType.edit();
	        eWidth = mWidth.edit();
	        eHeight = mHeight.edit();
	        eWidth.putInt(GAME_PREFERENCES_WIDTH, screenWidth);
	        eWidth.commit();
	        eHeight.putInt(GAME_PREFERENCES_HEIGHT, screenHeight);
	        eHeight.commit();
	        eScoreLoop  = scoreLoopSettings.edit();
	        
	        boxSize = mWidth.getInt(GAME_PREFERENCES_WIDTH, 0);
	        layHi = mHeight.getInt(GAME_PREFERENCES_HEIGHT, 0);
	        highScore = mGameSettings.getInt(GAME_PREFERENCES_HISCORE, 0);
	        highScoreDouble = mGameDoubleScore.getInt(GAME_PREFERENCES_DOUBLEHISCORE, 0);
		    isScoreLoop = scoreLoopSettings.getBoolean(GAME_PREFERENCES_SCORELOOP, true);

	        textAdjust();

	        back2menu();
	        
	        seqListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					gameEditor.putInt(GAME_PREFERENCES_TYPE, 3);
		   	 		gameEditor.commit();
					Intent inten1 = new Intent(Start.this, Game.class);
					startActivityForResult(inten1,0);
					
				}
			};
			
			truListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					gameEditor.putInt(GAME_PREFERENCES_TYPE, 2);
		   	 		gameEditor.commit();
					Intent inten1 = new Intent(Start.this, Game.class);
					startActivityForResult(inten1,0);
					
				}
			};
			
			douListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					gameEditor.putInt(GAME_PREFERENCES_TYPE, 1);
		   	 		gameEditor.commit();
					Intent inten1 = new Intent(Start.this, Game.class);
					startActivityForResult(inten1,0);
					
				}
			};
			
			fourListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					gameEditor.putInt(GAME_PREFERENCES_TYPE, 4);
		   	 		gameEditor.commit();
					Intent inten1 = new Intent(Start.this, Game.class);
					startActivityForResult(inten1,0);
					
				}
			};
			
			oriListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					gameEditor.putInt(GAME_PREFERENCES_TYPE, 0);
		   	 		gameEditor.commit();
					Intent inten1 = new Intent(Start.this, Game.class);
					startActivityForResult(inten1,0);
					
				}
			};
			

			
	        buttonListener3 = new OnClickListener(){	// Play Game

				@Override
				public void onClick(View arg0) {
//

					MyApplication.setGamePlaySessionStatus(MyApplication.GamePlaySessionStatus.NORMAL);
					
					//butN[5].setBackgroundResource(R.drawable.play);
					
					butN[1].setVisibility(View.INVISIBLE);
					butN[2].setVisibility(View.INVISIBLE);
					butN[3].setVisibility(View.INVISIBLE);
					butN[4].setVisibility(View.INVISIBLE);
					
					butN[5].setOnClickListener(oriListener);
					butN[5].setText("Single Mode");
					butN[6].setOnClickListener(fourListener); 
					butN[6].setText("Bonus Mode");
					butN[7].setOnClickListener(douListener);
					butN[7].setText("Double Mode");
					butN[8].setOnClickListener(seqListener);
					butN[8].setText("Sequential Mode");

					butN[5].setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		        	butN[6].setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

		        	
					 
					isContinue = false;
			        if(isContinue){
			        	butN[4].setVisibility(View.VISIBLE);
			        	butN[4].setText("Resume");
			        } else {
			        	butN[4].setVisibility(View.INVISIBLE);
			        }
			        
					pageNum = 2;
					

				}
	        	
	        };
	        
	        buttonListener4 = new OnClickListener(){	// resume

				@Override
				public void onClick(View arg0) {
					Intent inten1 = new Intent(Start.this, Game.class);
					startActivity(inten1);
				}	        	
	        };
	        
	        
	        buttonListener5 = new OnClickListener(){	// rules

				@Override
				public void onClick(View arg0) {
					Intent inten1 = new Intent(Start.this, Help.class);
					startActivity(inten1);
				}	        	
	        };
	        
		    
	        
	        buttonListener6 = new OnClickListener(){	// Google Play

	        	
				@Override
				public void onClick(View v) {
					
					pageNum = 3;
					
					beginUserInitiatedSignIn();
					
					
					butN[1].setVisibility(View.VISIBLE);
					butN[1].setText("Back To Menu");
					butN[1].setOnClickListener(backListener);
					butN[1].setCompoundDrawablesWithIntrinsicBounds(R.drawable.back1, 0, 0, 0);
					
					butN[2].setVisibility(View.INVISIBLE);					
					butN[8].setVisibility(View.INVISIBLE);
					
					
					butN[3].setText("Sign In");
					butN[3].setOnClickListener(signInGP);
					
					butN[4].setVisibility(View.VISIBLE);
					butN[4].setText("Sign Out");
					butN[4].setOnClickListener(signOutGP);
					
					butN[5].setText("Achievements");
					butN[5].setOnClickListener(achieveGP);
					butN[5].setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					
					butN[6].setText("Leaderboards");
					butN[6].setOnClickListener(leaderGP);
					butN[6].setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					
					butN[7].setText("MultiPlayer");
					butN[7].setOnClickListener(multiGP);
					
					for (int i = 3; i < 5; i++) {
						butN[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.play1, 0, 0, 0);
					}

					checkStatusGP();
					
					
					
										
				}	        	
	        };
	        
	        
	        
	        buttonListener7 = new OnClickListener() {	// More Games
				
				@Override
				public void onClick(View v) {
					String PublisherID = "pub:Surreall++Games";
					Uri marketUri = Uri.parse("market://search?q="+PublisherID);
			        Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);			        
					//startActivity(marketIntent);
			        startActivity(Intent.createChooser(marketIntent, "dialogTitle"));

					
				}
			};
			
	        buttonListener8 = new OnClickListener(){	// settings

				@Override
				public void onClick(View arg0) {
				
		   	 		Intent inten1 = new Intent(Start.this, Settings.class);
		   	 		startActivityForResult(inten1,0);
					
				}
	        	
	        };
			
			signInGP = new OnClickListener() {		// Sign Into GP
				
				@Override
				public void onClick(View v) {
					beginUserInitiatedSignIn();

				}
			};
			
			signOutGP = new OnClickListener() {		// Sign Out GP
				
				@Override
				public void onClick(View v) {

					signOut();
					checkStatusGP();
					tosty("Signed Out");
					
				}
			};
			leaderGP = new OnClickListener() { 		// LeaderBoards GP
				
				@Override
				public void onClick(View v) {
					String LEADERBOARD_ID =  "CgkI-uL-o-cWEAIQIQ";
					
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(),  LEADERBOARD_ID), 1);

					
				}
			};
			
			
			
			
			achieveGP = new OnClickListener() {		// Achievements
				
				@Override
				public void onClick(View v) {
					Integer REQUEST_ACHIEVEMENTS = 0;
					startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);

					
					
				}
			};
			
			multiGP = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					tosty("Coming Soon");
					
				}
			};
			

        
	        backListener = new OnClickListener() {	// Back BUTTON pressed
				
				@Override
				public void onClick(View arg0) {
					back2menu();
					
				}
			};
	        
	        butN[1].setOnClickListener(buttonListener1);
	        butN[2].setOnClickListener(buttonListener2);
	        butN[3].setOnClickListener(buttonListener3);
	        butN[4].setOnClickListener(buttonListener4);
	        butN[5].setOnClickListener(buttonListener5);
	        butN[6].setOnClickListener(buttonListener6);
	        butN[7].setOnClickListener(buttonListener7);
	        butN[8].setOnClickListener(buttonListener8);
	        
	        back2menu();
	        
	        
	 }
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu, menu);
			return true;
		}
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {	
	            if(pageNum == 1){
	            	finish();
	            } else if(pageNum == 2 || pageNum == 3 || pageNum == 4){ 
	            	back2menu();
	            }
	            
	
			return true;
			}

			return super.onKeyDown(keyCode, event);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			// We have only one menu option
			case R.id.preferences:
				// Launch Preference activity
				Intent i = new Intent(Start.this, Settings.class);
				startActivityForResult(i,0);
				break;

			}
			return true;
		}
		
		private void checkStatusGP(){
			if(isSignedIn()){
				for (int i = 4; i < 8; i++) {
					butN[i].setEnabled(true);
				}
				if(pageNum!=1){
					butN[3].setEnabled(false);
				} 
				scoreLoopSettings = getSharedPreferences(GAME_PREFERENCES_SCORELOOP, Context.MODE_PRIVATE);
		        eScoreLoop = scoreLoopSettings.edit();
	   	 		eScoreLoop.putBoolean(GAME_PREFERENCES_SCORELOOP, false);
	   	 		eScoreLoop.commit();
			} else {
				for (int i = 4; i < 8; i++) {
					butN[i].setEnabled(false);
				}
				butN[3].setEnabled(true);
			}
		}
		
		private void back2menu(){
        	pageNum = 1;
        	MyApplication.setGamePlaySessionStatus(MyApplication.GamePlaySessionStatus.NONE);
        	
        	for (int i = 3; i < butN.length; i++) {
				
		        butN[i].setVisibility(View.VISIBLE);
			}
        	
        	butN[1].setVisibility(View.INVISIBLE);
        	butN[2].setVisibility(View.INVISIBLE);
        	
        	butN[3].setOnClickListener(buttonListener3);
        	butN[3].setText("Start Game");
        	butN[3].setCompoundDrawablesWithIntrinsicBounds( R.drawable.start1, 0, 0, 0);
        	
        	
        	butN[4].setOnClickListener(buttonListener4);
        	butN[4].setText("Resume"); 
        	butN[4].setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
        	butN[4].setVisibility(View.INVISIBLE);
        	
        	butN[5].setOnClickListener(buttonListener5);
        	butN[5].setText("Rules");
        	butN[5].setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
        	
        	butN[6].setOnClickListener(buttonListener6);
        	butN[6].setText("Online Google Play");
        	butN[6].setCompoundDrawablesWithIntrinsicBounds( R.drawable.play1, 0, 0, 0);
        	
        	butN[7].setOnClickListener(buttonListener7);
	        butN[7].setText("* More Games *");
        	butN[7].setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        	
        	butN[8].setOnClickListener(buttonListener8);
        	butN[8].setText("Settings");   
        	butN[8].setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);

	        
	        
	        /*
	        if(isPro){
	        	butN[8].setVisibility(View.INVISIBLE);
	        }	        
	        */
	        
	        for (int i = 1; i < butN.length; i++) {
		        //butN[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
		        //butN[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
			}

	        for (int i = 1; i < 8; i++) {
				butN[i].setPadding(20, 0, 0, 0);
				butN[i].setEnabled(true);
			}
		}
		
		private void textAdjust() {
			Integer ats1 = 0;  	// Adjusted Text Size
			Integer ats2 = 0;
			Integer ms1 = 0;
//			if(boxSize <480){
//				ats1 = 10; ats2 = 11; ms1 = 0;
//			}
//			if(boxSize >=480 & boxSize < 500){
//				ats1 = 12; ats2 = 14; ms1 = 1;
//			}
//			if(boxSize >=500 & boxSize < 600){
//				ats1 = 14; ats2 = 17; ms1 = 2;
//			}
//			if(boxSize >=600 & boxSize < 700){
//				ats1 = 16; ats2 = 19; ms1 = 3;
//			}
//			if(boxSize >=700 & boxSize < 800){
//				ats1 = 18; ats2 = 22; ms1 = 5;
//			}
//			if(boxSize >=800 & boxSize < 900){
//				ats1 = 22; ats2 = 28; ms1 = 8;
//			}
//			if(boxSize >=900){
//				ats1 = 24; ats2 = 30; ms1 = 10;
//			}
			
			if(layHi <400){
				ats1 = 10; ats2 = 12; ms1 = 0;
			}
			if(layHi >=400 & layHi <480){
				ats1 = 10; ats2 = 13; ms1 = 0;
			}
			if(layHi >=480 &layHi < 800 ){
				ats1 = 10; ats2 = 14; ms1 = 0;
			}
			if(layHi >=800 & layHi < 900){
				ats1 = 12; ats2 = 15; ms1 = 1;
			}
			if(layHi >=900 & layHi < 1000){
				ats1 = 14; ats2 = 18; ms1 = 2;
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
			
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)butN[1].getLayoutParams();
			
			params.setMargins(ms1, ms1, ms1, ms1); //substitute parameters for left, top, right, bottom
			
			for (int i = 1; i < butN.length; i++) {
				butN[i].setLayoutParams(params); //TODO
			}

			params = (LinearLayout.LayoutParams)butN[3].getLayoutParams();			

			if(isPro){
				butN[8].setVisibility(View.INVISIBLE);  
			}
			
			 
			//butN[5].setBackgroundDrawable();
			//butN[5].setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.play, 0, 0);
			//b.setCompoundDrawablesWithIntrinsicBound(null,R.drawable.home_icon_test,null,null) 
			//instead of android:drawableTop 
			//and b.setPadding(0,32,0,0) instead of android:paddingTop. 

		}

	      
	      
	      public void tosty(String tost){
	  		Toast.makeText(getApplicationContext(),
	  		""+tost,
	  		Toast.LENGTH_LONG).show();
	  	}
	      
	  	protected static boolean isProInstalled(Context context) {
		    PackageManager manager = context.getPackageManager();
		    if (manager.checkSignatures(context.getPackageName(), "com.surreall.sixdiceunlocker")
		        == PackageManager.SIGNATURE_MATCH) {
		        //Pro key installed, and signatures match
		        return true;
		    }
		    return false;
		} 
	      

	 	 /*
		@Override
		public void onScoreSubmit(int status, Exception error) {
			// TODO Auto-generated method stub
			
		}
		*/
		protected void startActivityForResult(Intent leaderboardIntent,
				String requestLeaderboard) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onSignInFailed() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onSignInSucceeded() {
			// TODO Auto-generated method stub
			checkStatusGP();
			
			
		}
}