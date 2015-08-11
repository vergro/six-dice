package com.surreall.sixdice;
/* TODO


 * Achievements
  	- yahtzee on first roll

 * Challenges:


 */

import java.util.Random;

//The required ScoreloopUI imports

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
/*
import com.scoreloop.client.android.core.model.Achievement;
import com.scoreloop.client.android.core.model.Score;
import com.scoreloop.client.android.ui.ChallengesScreenActivity;
import com.scoreloop.client.android.ui.EntryScreenActivity;
import com.scoreloop.client.android.ui.OnScoreSubmitObserver;
import com.scoreloop.client.android.ui.ScoreloopManagerSingleton;
import com.scoreloop.client.android.ui.ShowResultOverlayActivity;
*/
import com.surreall.sixdice.Preferences;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
//import android.util.Log;
//import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class yatzee extends Main {

    private SoundManager mSoundManager;
    //final Score myComplexScore = new Score(new Double(12), null); 

    // *****  GOOGLE STUFF
    //AdView ad;	 
    private InterstitialAd interstitial;

    String LEADERBOARD_ID;
    //String IDsingle, IDdouble, IDsequential, IDbonus;
    Integer lifScore, lifYat, lifGame;
    SharedPreferences mLTS, mLTY, mLTG;
    Editor eLTS, eLTY, eLTG;
    String[] achStringGP = new String[40];

    // *****

    //Achievement achUpgrade;
    //Achievement[] _achievements= new Achievement[40];
    Drawable originalBackground;

    ProgressDialog dialog;
    Random randomGenerator = new Random();
    Random dr0 = new Random();
    CheckBox muteCB;

    Boolean isCheat;
    Boolean isQuick;
    Boolean isOver = false;
    Boolean isAnimating = false;
    Boolean isGreen = false;
    Boolean isUp = false;
    Boolean isColOne = false;
    Boolean upClick = false;
    Boolean extraPlay = true;
    Boolean isPlay = true;
    Boolean isScratch = false;
    Boolean isHint;
    Boolean isSound;
    Boolean isScoreLoop, isPlayServices;
    Boolean isChallenge = false;
    Boolean isChallengeComplete = false;
    Boolean isSeq;
    Boolean isPro;
    Boolean firstPlay;

    OnClickListener rollListener;
    OnClickListener caliListener;
    OnClickListener dieListener;
    OnClickListener upListener;
    OnClickListener loListener, submitListener;
    OnClickListener onlineListener;

    SharedPreferences preferences;
    SharedPreferences mGameSettings;
    SharedPreferences mGameDoubleScore;
    SharedPreferences mTrueScore, mSeqScore;
    SharedPreferences mFourScore, mFiveScore, mSixScore;
    SharedPreferences mGameSound;
    SharedPreferences mGameType;
    SharedPreferences scoreLoopSettings;
    SharedPreferences mPSsettings;
    SharedPreferences mHint;
    SharedPreferences mHeight, mWidth;
    SharedPreferences mLtScore, mLtYahtzees, mLtGames;
    SharedPreferences mRadioSound, mPro;

    // Dice Save
    SharedPreferences mUpSaveOr, mLoSaveOr;
    SharedPreferences mUpSave2, mLoSave2;
    SharedPreferences mUpsetSave, mLosetSave;
    SharedPreferences mUpsetSave2, mLosetSave2;
    SharedPreferences mRollsLeft, mDieVal, mContinue, mTurn, mXtra, mBlack;

    //Dice Save
    Editor eContinue, eRollsLeft, eDieVal, eTurn, eXtra, eBlack;
    Editor eUpSaveOr, eLoSaveOr;
    Editor eUpSave2, eLoSave2;
    Editor eUpsetSave, eLosetSave;
    Editor eUpsetSave2, eLosetSave2;

    Editor editor, editor2, editor3;
    Editor editorPS;
    Editor editorDouble, editorSeq, editorTrue, editorFour, editorFive, editorSix;
    Editor gameEditor;
    Editor eLtScore, eLtYahtzees, eLtGames, eRadioSound, ePro;

    Double finalTotal;
    Integer radioSound;
    Integer boxSize = 0;
    Integer highScore, highScoreTemp, highScoreDouble, highScoreTrue, highScoreSeq, highScoreFour, highScoreFive;
    Integer lifetimeScore, lifetimeYahtzees, lifetimeGames;

    Integer gameNum;
    Integer turnNum;
    Integer rawUp = 0, rawJ;    // The temporary value to be added while box is green In UP column
    Integer rawUp2 = 0, rawJ2;
    Integer rawLo = 0, rawI;    // The DOWN column
    Integer rawLo2 = 0, rawI2 = 0;
    Integer oldUpSum = 0, oldLoSum = 0;
    Integer grandTotal = 0;
    Integer rollsLeft = 4;
    Integer upBonus = 0, upBonus2 = 0, extras = 0, extraCount = 0;
    Integer upSum = 0, loSum = 0, upTotal = 0, loTotal = 0;
    Integer upSum2 = 0, loSum2 = 0, upTotal2 = 0, loTotal2 = 0;
    Integer dieSumTotal = 0, dieNum = 0;
    Integer layHi = 0;
    Integer raco = 0;    // Count
    Integer currentGreen = 33;    // Current Green
    Integer totalRolls = 0;
    Integer targetNum = 0;
    Integer bonusRollCount = 0;

    Integer[] setBlack = new Integer[]{0, 0, 0, 0};
    Integer[] RandVal = new Integer[]{0, 0, 0, 0, 0, 0, 0};                // Randomly generated values of die
    Integer[] RandTem = new Integer[]{0, 0, 0, 0, 0, 0};                        // Temporary random values during dice roll
    Integer[] DieVal = new Integer[]{0, 0, 0, 0, 0, 0};                        // Value on faces of Dice
    Integer[] DieSet = new Integer[]{0, 0, 0, 0, 0, 0};                        // State of Dice (clicked=1, not=0)
    Integer[] DieSum = new Integer[]{0, 0, 0, 0, 0, 0};                        // Sum of each number [Sum of ones, sum of twos...]
    Integer[] UpVal = new Integer[]{0, 0, 0, 0, 0, 0};                        // The values in the upper boxes
    Integer[] UpVal2 = new Integer[]{0, 0, 0, 0, 0, 0};
    Integer[] LoVal = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};                // The values in the lower boxes
    Integer[] LoVal2 = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Integer[] UpSet = new Integer[]{0, 0, 0, 0, 0, 0};                        // Box is white = 0, Box is blue = 1
    Integer[] UpSet2 = new Integer[]{0, 0, 0, 0, 0, 0};
    Integer[] LoSet = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};                // Box is white = 0, Box is blue = 1
    Integer[] LoSet2 = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Integer[] highScoreArray = new Integer[5];
    Integer[] upHint = new Integer[6];
    Integer[] loHint = new Integer[10];
    Integer[] ChalVal = new Integer[40];

    ImageView[] imageButtons = new ImageView[6];    // the dice
    ImageButton[] ovalButtons = new ImageButton[6];        // the ovals
    RadioButton radio_ori, radio_new, radio_mute;

    AutoResizeTextView[] upButtons = new AutoResizeTextView[9];
    AutoResizeTextView[] upButtons2 = new AutoResizeTextView[9];
    AutoResizeTextView[] loButtons = new AutoResizeTextView[12];
    AutoResizeTextView[] loButtons2 = new AutoResizeTextView[12];
    AutoResizeTextView grandButton;
    AutoResizeTextView submitScore;
    AutoResizeTextView rollit;

    String[] achString = new String[40];
    String[] achMessage = new String[40];
    AutoResizeTextView[] upText = new AutoResizeTextView[9];
    AutoResizeTextView[] loText = new AutoResizeTextView[12];
    TextView grandText, overT, gameT, startT;
    TextView twoPair;

    AutoResizeTextView hiScore, hiScoreText;

    String challengesT;
    String rollT;
    String grandT;
    String againT;
    String lastT;
    String bonusRT;
    String oriChaT;
    String douChaT;
    String truChaT;
    String seqChaT;
    String bonChaT;
    String oriHiT;
    String douHiT;
    String truHiT;
    String seqHiT;
    String bonHiT;
    String extraT;
    String playAgainT;
    String newHiT;
    String gameOverT;
    String submitT;
    String unlockedT;
    String confirm1T;
    String confirm2T;
    String yesT;
    String noT;
    String exitGameT;
    String challenge;
    String highscore;

    LinearLayout Ovals;
    LinearLayout diceMenu, rollMenu;

    AdRequest adRequest;

    int[] loTextIds = new int[]{
            R.id.pairT, R.id.twoTripleT, R.id.threeKT, R.id.fourKT, R.id.fullT, R.id.tinyT,
            R.id.smallT, R.id.largeT, R.id.chanceT, R.id.fiveKT, R.id.extrasT, R.id.totalTwoT
    };
    int[] upTextIds = new int[]{
            R.id.oneT, R.id.twoT, R.id.threeT, R.id.fourT, R.id.fiveT, R.id.sixT, R.id.totalOneT, R.id.bonusT, R.id.totalTwoT2
    };
    int[] upIds = new int[]{
            R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.upSub, R.id.bonus, R.id.upTotal};
    int[] upIds2 = new int[]{
            R.id.one_2, R.id.two_2, R.id.three_2, R.id.four_2, R.id.five_2, R.id.six_2, R.id.upSub_2, R.id.bonus_2, R.id.upTotal_2};
    int[] loIds = new int[]{
            R.id.pair, R.id.triple, R.id.threeK, R.id.fourK, R.id.full, R.id.tiny, R.id.small,
            R.id.large, R.id.chance, R.id.fiveK, R.id.loBonus, R.id.loTotal};
    int[] loIds2 = new int[]{
            R.id.pair_2, R.id.triple_2, R.id.threeK_2, R.id.fourK_2, R.id.full_2, R.id.tiny_2, R.id.small_2,
            R.id.large_2, R.id.chance_2, R.id.fiveK_2, R.id.loBonus_2, R.id.loTotal_2};
    int[] imageRes = new int[]{
            R.drawable.die1, R.drawable.die2, R.drawable.die3,
            R.drawable.die4, R.drawable.die5, R.drawable.die6};
    int[] druk = new int[]{
            R.drawable.druk01, R.drawable.druk02, R.drawable.druk03,
            R.drawable.druk04, R.drawable.druk05, R.drawable.druk06};
    int[] imageBlank = new int[]{
            R.drawable.blank1, R.drawable.blank2, R.drawable.blank3,
            R.drawable.blank4, R.drawable.blank5, R.drawable.blank6};
    int[] imageIds = new int[]{
            R.id.die1, R.id.die2, R.id.die3, R.id.die4, R.id.die5, R.id.die6};
    int[] ovaIds = new int[]{
            R.id.ova1, R.id.ova2, R.id.ova3, R.id.ova4, R.id.ova5, R.id.ova6};

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mGameType = getSharedPreferences(GAME_PREFERENCES_TYPE, Context.MODE_PRIVATE);
        gameEditor = mGameType.edit();
        gameNum = mGameType.getInt(GAME_PREFERENCES_TYPE, 1);
        gameNum++;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getGameHelper().setMaxAutoSignInAttempts(0);

        // -- Google
        // Create the interstitial.

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.inter_ad_unit_id));

        // Create ad request.
        adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
        // --

        isCheat = false;
        isQuick = false;


        firstPlay = true;

        mPro = getSharedPreferences(GAME_PREFERENCES_PRO, Context.MODE_PRIVATE);
        ePro = mPro.edit();
        isPro = mPro.getBoolean(GAME_PREFERENCES_PRO, false);


        if (isPro) {
            setContentView(R.layout.mainpro);
        } else {
            setContentView(R.layout.main);

            AdView mAdView = (AdView) findViewById(R.id.ad);
            AdRequest adRequest = new AdRequest.Builder().build();
            try {
                mAdView.loadAd(adRequest);
            } catch (NullPointerException e) {

            }
        }

        setupSound();

        setupPreferences();

        hiScore = (AutoResizeTextView) findViewById(R.id.gameHiScore);
        hiScoreText = (AutoResizeTextView) findViewById(R.id.gameHiScoreText);
        Ovals = (LinearLayout) findViewById(R.id.ovals);
        diceMenu = (LinearLayout) findViewById(R.id.dicemenu);
        rollMenu = (LinearLayout) findViewById(R.id.rollmenu);
        rollit = (AutoResizeTextView) findViewById(R.id.roll);
        rollit.setText("Roll");
        grandButton = (AutoResizeTextView) findViewById(R.id.GrandTotal);
        originalBackground = grandButton.getBackground();
        grandButton.setText("Grand Total = " + grandTotal.toString());
        grandButton.setBackgroundColor(Color.TRANSPARENT);
        grandButton.setTextColor(Color.WHITE);
        grandButton.setPadding(20, 0, 0, 0);

        radioSound = mRadioSound.getInt(GAME_PREFERENCES_RADIO_SOUND, 1);

        for (int i = 0; i < upText.length; i++) {
            upText[i] = (AutoResizeTextView) findViewById(upTextIds[i]);
        }
        for (int i = 0; i < 6; i++) {
            imageButtons[i] = (ImageView) findViewById(imageIds[i]);
            imageButtons[i].setBackgroundResource(imageBlank[i]);
            ovalButtons[i] = (ImageButton) findViewById(ovaIds[i]);
            ovalButtons[i].setBackgroundResource(R.drawable.blankoval);
        }
        for (int i = 0; i < 9; i++) {
            upButtons[i] = (AutoResizeTextView) findViewById(upIds[i]);
            upButtons2[i] = (AutoResizeTextView) findViewById(upIds2[i]);
        }
        for (int i = 0; i < loText.length; i++) {
            loText[i] = (AutoResizeTextView) findViewById(loTextIds[i]);
        }

        for (int i = 0; i < 12; i++) {
            loButtons[i] = (AutoResizeTextView) findViewById(loIds[i]);
            loButtons2[i] = (AutoResizeTextView) findViewById(loIds2[i]);
        }

        loButtons[10].setText("0");
        hiScoreText();
        textAdjust();


        if (gameNum == 5) {                        // Bonus Roll Mode
            turnNum = 32;
            bonusRollCount = 10;
        }


        if (gameNum == 1) {                        // Single Mode

            turnNum = 16;

        } else if (gameNum == 2 || gameNum == 5) {                // Double and Bonus
            turnNum = 32;


        } else if (gameNum == 4) {                // Sequential Mode			
            turnNum = 32;
            for (int i = 0; i < setBlack.length; i++) {
                setBlack[i] = 0;
            }

            for (int i = 2; i < 4; i++) {
                setBlack[i] = 6;
            }
        }

        if (isQuick) {
            turnNum = 3;
        }

        // if original yahtzee  or true yahtzee set all in column two to 1
        if (gameNum == 1) {
            for (int i = 0; i < 6; i++) {
                UpSet2[i] = 1;
            }
            for (int i = 0; i < 10; i++) {
                LoSet2[i] = 1;
            }
            for (int i = 0; i < upButtons2.length; i++) {
                upButtons2[i].setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < loButtons2.length; i++) {
                loButtons2[i].setVisibility(View.INVISIBLE);
            }
        }

        onlineListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isSignedIn()) {
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), LEADERBOARD_ID), 1);
                }

            }
        };

        submitListener = new OnClickListener() {        // submit score

            @Override
            public void onClick(View v) {
                submitCheck();
            }
        };

        rollListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoResizeTextView b = (AutoResizeTextView) v;
                clearHint();

                if (isCheat) {
                    rollsLeft = 2;
                    b.setTextColor(Color.RED);
                }

                rollsLeft--;
                totalRolls++;
                if (rollsLeft == 4) {
                    resetGame();
                } else {

                    if (isGreen) {

                        addLastRoll();
                        isGreen = false;
                        setYellow();
                        extraPlay = true;
                    }
                    if (rollsLeft == 3) {
                        b.setText("Rolls Left: 3");
                    } else if (rollsLeft == 2) {
                        b.setText("Rolls Left: 2");
                        //hiEditor.getInt("HiScore");
                    } else if (rollsLeft == 1) {
                        b.setText("Last Roll");
                    } else if (rollsLeft == 0) {

                        if (gameNum == 5 | gameNum == 3) {

                            b.setText("Bonus Roll " + bonusRollCount.toString());
                            b.setTextColor(Color.RED);

                            if (bonusRollCount < 1) {
                                b.setEnabled(false);
                            }


                        } else {
                            b.setText("");
                            b.setEnabled(false);
                        }
                    } else {
                        bonusRollCount--;
                        b.setText("");
                        b.setEnabled(false);
                    }


                    rollit.setClickable(false);
                    isAnimating = true;
                    animateDize();
                    soundPlay(1);        // play dice roll sound

                }
            }
        };

        upListener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                AutoResizeTextView b = (AutoResizeTextView) v;
                // ---------------------------- COLUMN 1 RIGHT SIDE
                if (((rollsLeft != 4) || (isGreen)) & isAnimating == false) {                    // Start 
                    for (int j = 0; j < 6; j++) {
                        if (b.getId() == upIds[j]) {        // Which box user clicked on
                            if (UpSet[j] == 0) {            // If Box is White do this
                                soundPlay(3);
                                upClick = true;            //
                                dieSumSet();

                                rawReset();
                                rawUp = DieSum[j];

                                greenCheck();            // check to see if there is a green box already, if not progress to next turn

                                rawJ = j;                    //keep track of what j is
                                upSum = rawUp;

                                for (int i = 0; i < 6; i++) {    // add together all values of upSum
                                    upSum += UpVal[i];
                                }
                                ;

                                UpSet[j] = 1;
                                raco += 1;
                                extrasCheck();
                                upTotal = upSum + upBonus;

                                refresh1();

                                for (int i = 0; i < 6; i++) {
                                    if (b.getId() == upIds[i]) {
                                        upButtons[i].setBackgroundColor(Color.parseColor("#A3FF71"));//click on it to turn it green
                                        currentGreen = i; // current green
                                        upButtons[i].setTextColor(Color.BLACK);
                                        upButtons[i].setText(DieSum[i].toString());
                                        isGreen = true;
                                        isUp = true;
                                        isColOne = true;
                                    }
                                }
                                if (turnNum < 1) {
                                    setYellow();
                                    isGreen = false;
                                    addLastRoll();
                                    gameOver();

                                }
                            }
                        }
                    }
                    for (int j = 0; j < 6; j++) {            // ---------------------------- COLUMN 2 RIGHT SIDE
                        if (b.getId() == upIds2[j]) {        // Which box user clicked on
                            if (UpSet2[j] == 0) {            // If Box is White do this
                                soundPlay(3);
                                upClick = true;
                                dieSumSet();

                                rawReset();
                                rawUp2 = DieSum[j];
                                greenCheck();            // check to see if there is a green box already, if not progress to next turn


                                //set the upper value to sum of valid numbers to be added
                                rawJ2 = j;                    //keep track of what j is

                                upSum2 = rawUp2;
                                for (int i = 0; i < 6; i++) {    // add together all values of upSum
                                    upSum2 += UpVal2[i];
                                }
                                ;

                                UpSet2[j] = 1;
                                raco += 1;
                                extrasCheck();
                                upTotal2 = upSum2 + upBonus2;
                                refresh1();
                                if (gameNum == 2) {
                                    refresh2();
                                }
                                ;

                                for (int i = 0; i < 6; i++) {
                                    if (b.getId() == upIds2[i]) {
                                        upButtons2[i].setBackgroundColor(Color.parseColor("#A3FF71"));//click on it to turn it green
                                        currentGreen = i; // current green
                                        upButtons2[i].setTextColor(Color.BLACK);
                                        upButtons2[i].setText(DieSum[i].toString());
                                        isGreen = true;
                                        isUp = true;
                                        isColOne = false;
                                    }
                                }
                                if (turnNum < 1) {
                                    setYellow();
                                    isGreen = false;
                                    addLastRoll();
                                    gameOver();
                                }
                            }
                        }
                    }
                }                //	End
            }

        };

        loListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                AutoResizeTextView b = (AutoResizeTextView) v;

                if (((rollsLeft != 4) || (isGreen)) & isAnimating == false) {


                    dieSumSet();
                    dieSumTotal = 0;
                    for (int i = 0; i < 6; i++) {
                        dieSumTotal += DieVal[i] + 1;
                    }
                    for (int j = 0; j < 6; j++) {            // ---------------------------- COLUMN 1 LEFT SIDE
                        if (b.getId() == loIds[0]) {        // Three pair
                            if (LoSet[0] == 0) {            // If Box is White do this
                                //soundPlay(3);
                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {
                                    if (DieSum[m] > m + 1) {
                                        dieNum += 1;
                                    }
                                    if (DieSum[m] == (m + 1) * 4) {
                                        dieNum += 1;
                                    }
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum += 3;
                                    }
                                }
                                if (dieNum > 2) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(7);
                                    }

                                    if (dieSumTotal > 25) {
                                        rawLo = dieSumTotal;
                                    } else {
                                        rawLo = 25;
                                    }

                                    rawI = 0;

                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }

                                    rawLo = 0;
                                    rawI = 0;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[1]) {        // 2 Triples 
                            if (LoSet[1] == 0) {            // If Box is White do this
                                //soundPlay(3);
                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {
                                    if (DieSum[m] > (m + 1) * 2) {
                                        dieNum += 1;
                                    }
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum += 2;
                                    }
                                }
                                if (dieNum > 1) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(8);
                                    }

                                    if (dieSumTotal > 25) {
                                        rawLo = dieSumTotal;
                                    } else {
                                        rawLo = 25;
                                    }
                                    rawI = 1;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 1;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[2]) {        // 4 of a kind
                            if (LoSet[2] == 0) {            // If Box is White do this
                                //soundPlay(3);
                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {

                                    if (DieSum[m] > (m + 1) * 3) {
                                        dieNum += 1;
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(9);
                                    }
                                    rawLo = dieSumTotal;
                                    rawI = 2;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 2;
                                }
                                greenCheck();
                            }
                        }

                        if (b.getId() == loIds[3]) {        // 5 of a kind
                            if (LoSet[3] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {

                                    if (DieSum[m] > (m + 1) * 4) {
                                        dieNum += 1;
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(9);
                                    }
                                    rawLo = dieSumTotal;
                                    rawI = 3;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 3;
                                }
                                greenCheck();
                            }
                        }

                        if (b.getId() == loIds[4]) {        // Full House
                            if (LoSet[4] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {

                                    if (DieSum[m] == (m + 1) * 3) {
                                        dieNum += 5;
                                    }
                                    if (DieSum[m] == (m + 1) * 4) {
                                        dieNum += 5;
                                    }
                                    if (DieSum[m] == (m + 1) * 2) {
                                        dieNum += 2;
                                    }
                                    if (DieSum[m] == (m + 1) * 5) {
                                        dieNum = 7;
                                    }
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum = 7;
                                    }
                                }
                                if (dieNum > 6) {
                                    rawLo = 35;
                                    rawI = 4;
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(10);
                                    }
                                } else {
                                    rawLo = 0;
                                    rawI = 4;
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[5]) {        // 4X Straight
                            if (LoSet[5] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 3; m++) {
                                    if (DieSum[m] > 0) {
                                        if (DieSum[m + 1] > 0) {
                                            if (DieSum[m + 2] > 0) {
                                                if (DieSum[m + 3] > 0) {
                                                    dieNum = 1;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(11);
                                    }
                                    rawLo = 30;
                                    rawI = 5;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 5;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[6]) {        // 5X Straight
                            if (LoSet[6] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 2; m++) {
                                    if (DieSum[m] > 0) {
                                        if (DieSum[m + 1] > 0) {
                                            if (DieSum[m + 2] > 0) {
                                                if (DieSum[m + 3] > 0) {
                                                    if (DieSum[m + 4] > 0) {
                                                        dieNum = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(12);
                                    }
                                    rawLo = 40;
                                    rawI = 6;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 6;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[7]) {        // 6X Straight
                            if (LoSet[7] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                int m = 0;
                                if (DieSum[m] > 0) {
                                    if (DieSum[m + 1] > 0) {
                                        if (DieSum[m + 2] > 0) {
                                            if (DieSum[m + 3] > 0) {
                                                if (DieSum[m + 4] > 0) {
                                                    if (DieSum[m + 5] > 0) {
                                                        dieNum = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(13);
                                    }
                                    rawLo = 50;
                                    rawI = 7;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 7;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[8]) {        // Chance
                            if (LoSet[8] == 0) {            // If Box is White do this
                                if (radioSound == 0) {
                                    soundPlay(3);
                                } else {
                                    soundPlay(14);
                                }
                                upClick = false;
                                rawReset();
                                rawLo = dieSumTotal;
                                rawI = 8;
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds[9]) {        // 6 of a kind
                            if (LoSet[9] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum = 2;
                                    }
                                }
                                if (dieNum > 1) {
                                    rawLo = 60;
                                    rawI = 9;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo = 0;
                                    rawI = 9;
                                }
                                greenCheck();
                            }
                        }
                        loSum = rawLo;

//						if(gameNum == 2){
//							refresh2();
//						}
                        for (int k = 0; k < 10; k++) {                // Put number into white box 
                            if ((b.getId() == loIds[k]) && LoSet[k] == 0) {

                                loButtons[k].setText(rawLo.toString());
                                loButtons[k].setBackgroundColor(Color.parseColor("#A3FF71"));  //click on it to turn green
                                currentGreen = k; // current green
                                loButtons[k].setTextColor(Color.BLACK);
                                LoSet[k] = 1;
                                isGreen = true;
                                isUp = false;
                                isColOne = true;
                                rawLo2 = 0;
                            }
                        }
                    }
                    for (int j = 0; j < 6; j++) {            // ---------------------------- COLUMN 2 LEFT SIDE
                        if (b.getId() == loIds2[0]) {        // Three pair
                            if (LoSet2[0] == 0) {            // If Box is White do this
                                //soundPlay(3);
                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {
                                    if (DieSum[m] > m + 1) {
                                        dieNum += 1;
                                    }

                                    if (DieSum[m] == (m + 1) * 4) {
                                        dieNum += 1;
                                    }
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum += 3;
                                    }

                                }
                                if (dieNum > 2) {
                                    //LoVal[0]=dieSumTotal;  	// 	<--
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(7);
                                    }

                                    if (dieSumTotal > 25) {
                                        rawLo2 = dieSumTotal;
                                    } else {
                                        rawLo2 = 25;
                                    }
                                    rawI2 = 0;

                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 0;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[1]) {        // 2 Triples
                            if (LoSet2[1] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {
                                    if (DieSum[m] > (m + 1) * 2) {
                                        dieNum += 1;
                                    }
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum += 2;
                                    }
                                }
                                if (dieNum > 1) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(8);
                                    }

                                    if (dieSumTotal > 25) {
                                        rawLo2 = dieSumTotal;
                                    } else {
                                        rawLo2 = 25;
                                    }
                                    rawI2 = 1;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 1;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[2]) {        // 4 of a kind
                            if (LoSet2[2] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {

                                    if (DieSum[m] > (m + 1) * 3) {
                                        dieNum += 1;
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(9);
                                    }
                                    rawLo2 = dieSumTotal;
                                    rawI2 = 2;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 2;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[3]) {        // 5 of a kind
                            if (LoSet2[3] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {

                                    if (DieSum[m] > (m + 1) * 4) {
                                        dieNum += 1;
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(9);
                                    }
                                    rawLo2 = dieSumTotal;
                                    rawI2 = 3;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 3;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[4]) {        // Full House
                            if (LoSet2[4] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {

                                    if (DieSum[m] == (m + 1) * 3) {
                                        dieNum += 5;
                                    }
                                    if (DieSum[m] == (m + 1) * 4) {
                                        dieNum += 5;
                                    }
                                    if (DieSum[m] == (m + 1) * 2) {
                                        dieNum += 2;
                                    }
                                    if (DieSum[m] == (m + 1) * 5) {
                                        dieNum = 7;
                                    }
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum = 7;
                                    }
                                }
                                if (dieNum > 6) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(10);
                                    }
                                    rawLo2 = 35;
                                    rawI2 = 4;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 4;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[5]) {        // 4X Straight
                            if (LoSet2[5] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 3; m++) {
                                    if (DieSum[m] > 0) {
                                        if (DieSum[m + 1] > 0) {
                                            if (DieSum[m + 2] > 0) {
                                                if (DieSum[m + 3] > 0) {
                                                    dieNum = 1;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(11);
                                    }
                                    rawLo2 = 30;
                                    rawI2 = 5;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 5;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[6]) {        // 5X Straight
                            if (LoSet2[6] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 2; m++) {
                                    if (DieSum[m] > 0) {
                                        if (DieSum[m + 1] > 0) {
                                            if (DieSum[m + 2] > 0) {
                                                if (DieSum[m + 3] > 0) {
                                                    if (DieSum[m + 4] > 0) {
                                                        dieNum = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(12);
                                    }
                                    rawLo2 = 40;
                                    rawI2 = 6;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 6;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[7]) {        // 6X Straight
                            if (LoSet2[7] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                int m = 0;
                                if (DieSum[m] > 0) {
                                    if (DieSum[m + 1] > 0) {
                                        if (DieSum[m + 2] > 0) {
                                            if (DieSum[m + 3] > 0) {
                                                if (DieSum[m + 4] > 0) {
                                                    if (DieSum[m + 5] > 0) {
                                                        dieNum = 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (dieNum > 0) {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(13);
                                    }
                                    rawLo2 = 50;
                                    rawI2 = 7;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 7;
                                }
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[8]) {        // Chance
                            if (LoSet2[8] == 0) {            // If Box is White do this
                                if (radioSound == 0) {
                                    soundPlay(3);
                                } else {
                                    soundPlay(14);
                                }
                                upClick = false;
                                rawReset();
                                rawLo2 = dieSumTotal;
                                rawI2 = 8;
                                greenCheck();
                            }
                        }
                        if (b.getId() == loIds2[9]) {        // 6 of a kind
                            if (LoSet2[9] == 0) {            // If Box is White do this

                                upClick = false;
                                rawReset();
                                dieNum = 0;
                                for (int m = 0; m < 6; m++) {
                                    if (DieSum[m] == (m + 1) * 6) {
                                        dieNum = 2;
                                    }
                                }
                                if (dieNum > 1) {
                                    rawLo2 = 60;
                                    rawI2 = 9;
                                } else {
                                    if (radioSound == 0) {
                                        soundPlay(3);
                                    } else {
                                        soundPlay(6);
                                    }
                                    rawLo2 = 0;
                                    rawI2 = 9;
                                }
                                greenCheck();
                            }
                        }
                        for (int k = 0; k < 10; k++) {                // Put number into white box 
                            if ((b.getId() == loIds2[k]) && LoSet2[k] == 0) {

                                loButtons2[k].setText(rawLo2.toString());
                                loButtons2[k].setBackgroundColor(Color.parseColor("#A3FF71"));  //click on it to turn green
                                currentGreen = k; // current green
                                loButtons2[k].setTextColor(Color.BLACK);
                                LoSet2[k] = 1;
                                isGreen = true;
                                isUp = false;
                                isColOne = false;
                                rawLo = 0;
                            }
                        }
                    }

                    loSum = rawLo;
                    for (int i = 0; i < 10; i++) {
                        loSum += LoVal[i];
                    }
                    extrasCheck();

                    loTotal = loSum + extras;
                    refresh1();
                    if (gameNum == 2) {
                        refresh2();
                    }
                    if ((gameNum == 1) & turnNum < 1) {        // original or true
                        setYellow();
                        isGreen = false;
                        addLastRoll();
                        gameOver();
                    }
                    if (gameNum == 2 || gameNum == 4 || gameNum == 3 || gameNum == 5) {
                        loSum2 = rawLo2;
                        for (int i = 0; i < 10; i++) {
                            loSum2 += LoVal2[i];
                        }

                        loTotal2 = loSum2;
                        //refresh2();
                        if (turnNum < 1) {
                            setYellow();
                            isGreen = false;
                            addLastRoll();
                            gameOver();
                        }
                    }
                }
            }

        };

        dieListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView b = (ImageView) v;
                //tosty(layHi.toString()+ " X " + boxSize.toString());
                if ((rollsLeft != 4) && (turnNum > 0) && (!isAnimating)) {
                    soundPlay(2);    // play button click sound
                    for (int i = 0; i < 6; i++) {
                        if (b.getId() == imageIds[i]) {
                            // Toggle color of die
                            if (DieSet[i] == 0) {
                                DieSet[i] = 1;
                                imageButtons[i].setBackgroundResource(druk[DieVal[i]]);
                                ovalButtons[i].setBackgroundResource(R.drawable.greenoval);
                            } else {
                                DieSet[i] = 0;
                                imageButtons[i].setBackgroundResource(imageRes[DieVal[i]]);
                                ovalButtons[i].setBackgroundResource(R.drawable.blankoval);
                            }
                        }
                    }
                }
            }
        };

        // END LISTENERS

        grandButton.setOnClickListener(submitListener);
        grandButton.setClickable(false);
        rollit.setOnClickListener(rollListener);
        for (int i = 0; i < 6; i++) {
            imageButtons[i].setOnClickListener(dieListener);
        }
        for (int i = 0; i < 6; i++) {
            upButtons[i].setOnClickListener(upListener);
            upButtons2[i].setOnClickListener(upListener);

        }
        for (int i = 0; i < 10; i++) {
            loButtons[i].setOnClickListener(loListener);
            loButtons2[i].setOnClickListener(loListener);

        }
        redrawBlack();
        resetGame();

    }

    public void setupSound() {
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.diceroll);
        mSoundManager.addSound(2, R.raw.click1);
        mSoundManager.addSound(3, R.raw.card);
        mSoundManager.addSound(4, R.raw.match);
        mSoundManager.addSound(5, R.raw.game_over);
        mSoundManager.addSound(6, R.raw.scratch);
        mSoundManager.addSound(7, R.raw.twopair);
        mSoundManager.addSound(8, R.raw.threekind);
        mSoundManager.addSound(9, R.raw.fourkind);
        mSoundManager.addSound(10, R.raw.fullhouse);
        mSoundManager.addSound(11, R.raw.threexstr);
        mSoundManager.addSound(12, R.raw.fourxstr);
        mSoundManager.addSound(13, R.raw.fivexstr);
        mSoundManager.addSound(14, R.raw.chance);
    }

    public void setupPreferences() {
        rollT = getResources().getString(R.string.rollT);
        grandT = getResources().getString(R.string.grandT);
        againT = getResources().getString(R.string.againT);
        lastT = getResources().getString(R.string.lastT);
        bonusRT = getResources().getString(R.string.bonusRT);
        oriChaT = getResources().getString(R.string.oriChaT);
        douChaT = getResources().getString(R.string.douChaT);
        truChaT = getResources().getString(R.string.truChaT);
        seqChaT = getResources().getString(R.string.seqChaT);
        bonChaT = getResources().getString(R.string.bonChaT);
        oriHiT = getResources().getString(R.string.oriHiT);
        douHiT = getResources().getString(R.string.douHiT);
        truHiT = getResources().getString(R.string.truHiT);
        seqHiT = getResources().getString(R.string.seqHiT);
        bonHiT = getResources().getString(R.string.bonHiT);
        extraT = getResources().getString(R.string.extraT);
        playAgainT = getResources().getString(R.string.playAgainT);
        newHiT = getResources().getString(R.string.newHiT);
        gameOverT = getResources().getString(R.string.gameOverT);
        submitT = getResources().getString(R.string.submitT);
        challengesT = getResources().getString(R.string.challengesT);
        unlockedT = getResources().getString(R.string.unlockedT);
        confirm1T = getResources().getString(R.string.confirm1T);
        confirm2T = getResources().getString(R.string.confirm2T);
        noT = getResources().getString(R.string.noT);
        yesT = getResources().getString(R.string.yesT);
        exitGameT = getResources().getString(R.string.exitGameT);
        challenge = getResources().getString(R.string.challenge);
        highscore = getResources().getString(R.string.highscore);


        mGameSettings = getSharedPreferences(GAME_PREFERENCES_HISCORE, Context.MODE_PRIVATE);
        mGameDoubleScore = getSharedPreferences(GAME_PREFERENCES_DOUBLEHISCORE, Context.MODE_PRIVATE);
        scoreLoopSettings = getSharedPreferences(GAME_PREFERENCES_SCORELOOP, Context.MODE_PRIVATE);
        mPSsettings = getSharedPreferences(GAME_PLAY_SERVICES, Context.MODE_PRIVATE);
        mGameSound = getSharedPreferences(GAME_PREFERENCES_SOUND, Context.MODE_PRIVATE);
        mHint = getSharedPreferences(GAME_PREFERENCES_HINT, Context.MODE_PRIVATE);
        mWidth = getSharedPreferences(GAME_PREFERENCES_WIDTH, Context.MODE_PRIVATE);
        mHeight = getSharedPreferences(GAME_PREFERENCES_HEIGHT, Context.MODE_PRIVATE);
        mTrueScore = getSharedPreferences(GAME_PREFERENCES_TRUE_HISCORE, Context.MODE_PRIVATE);
        mSeqScore = getSharedPreferences(GAME_PREFERENCES_SEQ_HISCORE, Context.MODE_PRIVATE);
        mFourScore = getSharedPreferences(GAME_PREFERENCES_FOUR_HISCORE, Context.MODE_PRIVATE);
        mFiveScore = getSharedPreferences(GAME_PREFERENCES_FIVE_HISCORE, Context.MODE_PRIVATE);
        mLtScore = getSharedPreferences(GAME_PREFERENCES_LIFETIME_SCORE, Context.MODE_PRIVATE);
        mLtYahtzees = getSharedPreferences(GAME_PREFERENCES_LIFETIME_YAHTZEE, Context.MODE_PRIVATE);
        mLtGames = getSharedPreferences(GAME_PREFERENCES_LIFETIME_GAMES, Context.MODE_PRIVATE);
        mRadioSound = getSharedPreferences(GAME_PREFERENCES_RADIO_SOUND, Context.MODE_PRIVATE);

        // Load Achievements
        //ScoreloopManagerSingleton.get().loadAchievements(null);


        // **** GP
        mPSsettings = getSharedPreferences(GAME_PLAY_SERVICES, Context.MODE_PRIVATE);
        mLTG = getSharedPreferences(GAME_PLAY_LTG, Context.MODE_PRIVATE);
        mLTY = getSharedPreferences(GAME_PLAY_LTY, Context.MODE_PRIVATE);
        mLTS = getSharedPreferences(GAME_PLAY_LTS, Context.MODE_PRIVATE);
        editorPS = mPSsettings.edit();
        eLTG = mLTG.edit();
        eLTY = mLTY.edit();
        eLTS = mLTS.edit();

        isPlayServices = isSignedIn();
        lifScore = mLTS.getInt(GAME_PLAY_LTS, 0);
        lifYat = mLTY.getInt(GAME_PLAY_LTY, 0);
        lifGame = mLTG.getInt(GAME_PLAY_LTG, 0);


        if (gameNum == 1) {            // Single
            LEADERBOARD_ID = getResources().getString(R.string.sin_lb);
        } else if (gameNum == 2) {    // Double 
            LEADERBOARD_ID = getResources().getString(R.string.dou_lb);
        } else if (gameNum == 5) {    // Bonus
            LEADERBOARD_ID = getResources().getString(R.string.bon_lb);
        } else if (gameNum == 4) {    // Sequential
            LEADERBOARD_ID = getResources().getString(R.string.seq_lb);
        }
        // ****

        editor = mGameSettings.edit();
        editorDouble = mGameDoubleScore.edit();
        editor3 = scoreLoopSettings.edit();
        editorPS = mPSsettings.edit();
        editor2 = mGameSound.edit();
        editorSeq = mSeqScore.edit();
        editorTrue = mTrueScore.edit();
        editorFour = mFourScore.edit();
        editorFive = mFiveScore.edit();
        eLtScore = mLtScore.edit();
        eLtYahtzees = mLtYahtzees.edit();
        eLtGames = mLtGames.edit();
        eRadioSound = mRadioSound.edit();

        highScore = mGameSettings.getInt(GAME_PREFERENCES_HISCORE, 0);
        highScoreDouble = mGameDoubleScore.getInt(GAME_PREFERENCES_DOUBLEHISCORE, 0);
        highScoreSeq = mSeqScore.getInt(GAME_PREFERENCES_SEQ_HISCORE, 0);
        highScoreTrue = mTrueScore.getInt(GAME_PREFERENCES_TRUE_HISCORE, 0);
        highScoreFour = mFourScore.getInt(GAME_PREFERENCES_FOUR_HISCORE, 0);
        highScoreFive = mFiveScore.getInt(GAME_PREFERENCES_FIVE_HISCORE, 0);
        lifetimeScore = mLtScore.getInt(GAME_PREFERENCES_LIFETIME_SCORE, 0);
        lifetimeYahtzees = mLtYahtzees.getInt(GAME_PREFERENCES_LIFETIME_YAHTZEE, 0);
        lifetimeGames = mLtGames.getInt(GAME_PREFERENCES_LIFETIME_GAMES, 0);


        isSound = mGameSound.getBoolean(GAME_PREFERENCES_SOUND, true);

        layHi = mHeight.getInt(GAME_PREFERENCES_HEIGHT, 0);
        boxSize = mWidth.getInt(GAME_PREFERENCES_WIDTH, 0);

    }

    private void redrawBlack() {
        if (gameNum == 4) {
            for (int i = 0; i < 6; i++) {
                if (setBlack[0] + 1 > i) {
                    if (UpSet[i] == 0) {
                        upButtons[i].setBackgroundColor(Color.WHITE);
                        upButtons[i].setClickable(true);
                    }
                } else {
                    upButtons[i].setClickable(false);
                    upButtons[i].setBackgroundColor(Color.BLACK);

                }
            }

            for (int i = 0; i < 6; i++) {
                if (setBlack[1] + 1 > i) {
                    if (UpSet2[i] == 0) {
                        upButtons2[i].setClickable(true);
                        upButtons2[i].setBackgroundColor(Color.WHITE);
                    }
                } else {
                    upButtons2[i].setClickable(false);
                    upButtons2[i].setBackgroundColor(Color.BLACK);
                }
            }
            /*
	    	for (int i = 0; i < 9; i++) {
	    		if(setBlack[2]+1> i) {
	    			if(LoSet[i]==0){
	    				loButtons[i].setClickable(true);
	    				loButtons[i].setBackgroundColor(Color.WHITE);
	    			}
				} else {
					loButtons[i].setBackgroundColor(Color.BLACK);
					loButtons[i].setClickable(false);
				}
			}
	    	
	    	for (int i = 0; i < 9; i++) {
	    		if(setBlack[3]+1> i) {
	    			if(LoSet2[i]==0){
	    				loButtons2[i].setClickable(true);
	    				loButtons2[i].setBackgroundColor(Color.WHITE);
	    			}
				} else {
					loButtons2[i].setClickable(false);
					loButtons2[i].setBackgroundColor(Color.BLACK);
				}
			}
			
			*/
        }

    }

    private void hiScoreText() {




            if (gameNum == 1) {
                hiScoreText.setText(highscore + " " + highScore.toString());
            }
            if (gameNum == 2) {
                hiScoreText.setText(highscore + " " + highScoreDouble.toString());
            }
            if (gameNum == 3) {
                hiScoreText.setText(highscore + " " + highScoreTrue.toString());
            }
            if (gameNum == 4) {
                hiScoreText.setText(highscore + " " + highScoreSeq.toString());
            }
            if (gameNum == 5) {
                hiScoreText.setText(highscore + " " + highScoreFour.toString());
            }



        if (gameNum == 1) {
            hiScore.setText(oriChaT);

        }
        if (gameNum == 2) {
            hiScore.setText(douChaT);
        }
        if (gameNum == 3) {
            hiScore.setText(truChaT);
        }
        if (gameNum == 4) {
            hiScore.setText(seqChaT);
        }
        if (gameNum == 5) {
            hiScore.setText(bonChaT);
        }


    }

    private void textAdjust() {


        Integer ats1 = 0;    // Adjusted Text Size
        Integer ats2 = 0;
        Integer ms1 = 0; // Margin Size
        Integer ms2 = 0;
        Integer ms3 = 0;
        Integer ms4 = 0;
        Integer ht1 = 0;
        //Integer wt1 = 0;
		/* weights for normal
		 * wholemenu = 0.26
		 * totalmenu = 0.59
		 * dicemenu = 0.55
		 * rollmenu = 0.56
		 *
		 * weights for small
		 * wholemenu = 0.23
		 * totalmenu = 0.6
		 * dicemenu = 0.55
		 * rollmenu = 0.57
		 *
		 * weights for sixdice
		 * wholemenu = 0.25
		 * totalmenu = 0.59
		 * dicemenu = 0.56
		 * rollmenu = 0.56
		 *
		 * weights for small sixdice
		 * wholemenu = 0.22
		 * totalmenu = 0.61
		 * dicemenu = 0.58
		 * rollmenu = 0.58
		 *
		 *
		 *
		 *
		 *
		 */
        //LayoutParams param1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (float) 1.0);
        //	QVGA:		320x240
        //	WVGA400		400x240
        // 	WQVGA432: 	432x240
        // 	HVGA:		480x320
        //	WVGA:		800x480
        //	WVGA854		854x480
        //  HP Touch 	1024x768
        // 	HXGA		1280x800


        if (layHi < 400) {
            ats1 = 11;
            ats2 = 11;
            ms1 = 0;
            ms2 = 2;
            ms3 = 3;
            ms4 = 5;
            ht1 = 9;        // QVGA
        }
        if (layHi >= 400 & layHi < 480) {
            ats1 = 11;
            ats2 = 12;
            ms1 = 0;
            ms2 = 2;
            ms3 = 3;
            ms4 = 3;
            ht1 = 9;
        }
        if (layHi >= 480 & layHi < 800) {
            ats1 = 12;
            ats2 = 12;
            ms1 = 0;
            ms2 = 2;
            ms3 = 4;
            ms4 = 9;
            ht1 = 9;        // HVGA
        }
        if (layHi >= 800 & layHi < 900) {
            ats1 = 12;
            ats2 = 14;
            ms1 = 2;
            ms2 = 4;
            ms3 = 2;
            ms4 = 5;
            ht1 = 10;
        }
        if (layHi >= 900 & layHi < 1000) {
            ats1 = 14;
            ats2 = 17;
            ms1 = 4;
            ms2 = 5;
            ms3 = 3;
            ms4 = 4;
            ht1 = 11;
        }
        if (layHi >= 1000 & layHi < 1100) {
            ats1 = 16;
            ats2 = 20;
            ms1 = 25;
            ms2 = 6;
            ms3 = 6;
            ms4 = 4;
            ht1 = 12;
        }
        if (layHi >= 1100 & layHi < 1200) {
            ats1 = 18;
            ats2 = 22;
            ms1 = 26;
            ms2 = 8;
            ms3 = 8;
            ms4 = 4;
            ht1 = 13;
        }
        if (layHi >= 1200 & layHi < 1300) {
            ats1 = 22;
            ats2 = 32;
            ms1 = 26;
            ms2 = 10;
            ms3 = 8;
            ms4 = 4;
            ht1 = 14;
        }
        if (layHi >= 1300) {
            ats1 = 24;
            ats2 = 34;
            ms1 = 26;
            ms2 = 12;
            ms3 = 10;
            ms4 = 4;
            ht1 = 15;
        }

        hiScoreText.setBackgroundColor(Color.TRANSPARENT);
        hiScoreText.setClickable(false);
        hiScore.setBackgroundColor(Color.TRANSPARENT);
        hiScore.setClickable(false);


        for (int i = 0; i < upText.length; i++) {
            //upText[i].setTextSize(ats1);
            upText[i].setBackgroundColor(Color.TRANSPARENT);
            upText[i].setClickable(false);
        }
        for (int i = 0; i < loText.length; i++) {
            //loText[i].setTextSize(ats1);
            loText[i].setBackgroundColor(Color.TRANSPARENT);
            loText[i].setClickable(false);
        }

        LinearLayout.LayoutParams params2;
        LinearLayout.LayoutParams params3;

        for (int i = 0; i < upButtons.length; i++) {
            params2 = (LinearLayout.LayoutParams) upButtons[i].getLayoutParams();
            params2.setMargins(ms2, ms2, ms2, ms2);
            upButtons[i].setLayoutParams(params2);
            //upButtons[i].setTextSize(ats1+2);

        }
        for (int i = 0; i < upButtons2.length; i++) {
            params2 = (LinearLayout.LayoutParams) upButtons2[i].getLayoutParams();
            params2.setMargins(ms2, ms2, ms2, ms2);
            upButtons2[i].setLayoutParams(params2);
            //upButtons2[i].setTextSize(ats1+2);
        }
        for (int i = 0; i < loButtons.length; i++) {
            params2 = (LinearLayout.LayoutParams) loButtons[i].getLayoutParams();
            params2.setMargins(ms2, ms2, ms2, ms2);
            loButtons[i].setLayoutParams(params2);
            //loButtons[i].setTextSize(ats1+2);
        }
        for (int i = 0; i < loButtons2.length; i++) {
            params2 = (LinearLayout.LayoutParams) loButtons2[i].getLayoutParams();
            params2.setMargins(ms2, ms2, ms2, ms2);
            loButtons2[i].setLayoutParams(params2);
            //loButtons2[i].setTextSize(ats1+2);
        }

        Integer msx = 0;

        if (layHi < 480) {
            msx = 2;
        }
        if (layHi >= 480) {
            msx = 3;
        }
        if (layHi >= 1200) {
            msx = 3;
        }

        //left,top,right,bottom
/*		for (int i = 0; i < imageButtons.length; i++) {
			params2 = (LinearLayout.LayoutParams)imageButtons[i].getLayoutParams();
			params2.setMargins(ms3, ms4, ms3, msx);
			imageButtons[i].setLayoutParams(params2);
		}*/


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rollit.getLayoutParams();
        //rollit.setTextSize(ats2+2);

        params.setMargins(ms1, ms2, ms1, ms2); //substitute parameters for left, top, right, bottom
        //rollit.setLayoutParams(params);
        //grandButton.setTextSize(ats1+2);

        LinearLayout.LayoutParams Ovalparam = (LinearLayout.LayoutParams) Ovals.getLayoutParams();
        final float scale = getBaseContext().getResources().getDisplayMetrics().density;
        int pixels = (int) ((ht1) * scale + 0.5f);

        Ovalparam.height = pixels;
        Ovals.setLayoutParams(Ovalparam);
    }

    public void animateDize() {
        new CountDownTimer(900, 150) {
            //int diceR1, diceR2, diceR3, diceR4, diceR5;
            int randHold;
            Integer j = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < 6; i++) {

                    for (int p = 0; p < 6; p++) {
                        randHold = RandTem[p];
                        while (randHold == RandTem[p]) {
                            RandTem[p] = dr0.nextInt(6);    // crate a different random face for the dice while rolling
                        }
                    }

                    if (DieSet[i] == 0) {
                        imageButtons[i].setBackgroundResource(imageRes[RandTem[i]]);
                    }
                }


                j++;
            }

            @Override
            public void onFinish() {
                rollit.setClickable(true);

                isAnimating = false;
                randomDize();
            }
        }.start();
    }

    public void randomDize() {
        int randSpot;

        for (int i = 0; i < 6; i++) {

            randSpot = randomGenerator.nextInt(6);
            if (DieSet[i] == 0) {
                DieVal[i] = randSpot;
                imageButtons[i].setBackgroundResource(imageRes[randSpot]);
            }
        }
        //clearHint();
        isHint = mHint.getBoolean(GAME_PREFERENCES_HINT, false);
        isHint = false;
        if (isHint) {
            showHint();
        }
    }

    public void upCalc() {
        upSum = rawUp;

        for (int i = 0; i < 6; i++) {    // add together all values of upSum
            upSum += UpVal[i];
        }
        ;

        if (upSum > 83) {
            upBonus = 35;
        } else {
            upBonus = 0;
        }
        upButtons[7].setText(upBonus.toString());        // Display Bonus
        upTotal = upSum + upBonus;

        if (gameNum == 2 || gameNum == 3 || gameNum == 4 || gameNum == 5) {
            upSum2 = rawUp2;

            for (int i = 0; i < 6; i++) {    // add together all values of upSum
                upSum2 += UpVal2[i];
            }
            ;

            if (upSum2 > 83) {
                upBonus2 = 35;
            } else {
                upBonus2 = 0;
            }
            upButtons2[7].setText(upBonus2.toString());
            upTotal2 = upSum2 + upBonus2;
        }
    }

    public void loCalc() {
        loSum = rawLo;
        for (int i = 0; i < 10; i++) {
            loSum += LoVal[i];
        }
        extrasCheck();

        loTotal = loSum + extras;
        if (gameNum == 2 || gameNum == 3 || gameNum == 4 || gameNum == 5) {
            loSum2 = rawLo2;
            for (int i = 0; i < 10; i++) {
                loSum2 += LoVal2[i];
            }
            loTotal2 = loSum2;
        }


    }

    public void extrasCheck() {
        for (Integer i = 0; i < 6; i++) {
            if (DieSum[i] == (i + 1) * 6 & extraPlay) {
                lifetimeYahtzees++;
                eLtYahtzees.putInt(GAME_PREFERENCES_LIFETIME_YAHTZEE, lifetimeYahtzees);
                eLtYahtzees.commit();


                if (isSignedIn()) {
                    // Total Yachts
                    ChalVal[25] = 10;
                    achStringGP[25] = getResources().getString(R.string.achievement_yacht_newbie);

                    ChalVal[26] = 50;
                    achStringGP[26] = getResources().getString(R.string.achievement_yacht_specialist);

                    ChalVal[27] = 250;
                    achStringGP[27] = getResources().getString(R.string.achievement_yacht_master);

                    ChalVal[28] = 1000;
                    achStringGP[28] = getResources().getString(R.string.achievement_yacht_overload);
                    // Total Yachts
                    try {
                        for (int j = 0; j < 4; j++) {
                            Games.Achievements.increment(getApiClient(), achStringGP[25 + j], 1);
                        }
                    } catch (IllegalStateException e) {
                        String tag = "GP Fail = ";
                        Log.v(tag, e.getMessage());
                        tosty("Please Sign into Google Play");
                    }

                }
                extraCount += 1;
                soundPlay(4);
                extraPlay = false;
                if (gameNum == 1) {
                    if (extraCount > 1 & LoSet[9] > 0) {
                        tosty("Extra Yacht!");
                        extras += 100;
                        loButtons[10].setText(extras.toString());
                    }
                } else {
                    if (extraCount > 2 & LoSet[9] > 0 & LoSet2[9] > 0) {
                        tosty("Extra Yacht!");
                        extras += 100;
                        loButtons[10].setText(extras.toString());
                    }
                }

            }
        }

    }

    public void greenCheck() {

        upCalc();
        loCalc();

        refresh1();
        grandTotal = upTotal + loTotal;
        if (gameNum == 2 || gameNum == 4 || gameNum == 5 || gameNum == 3) {
            grandTotal += upTotal2;
            grandTotal += loTotal2;
            //refresh2();
        }
        grandButton.setText("Grand Total = " + grandTotal.toString());

//    	if(gameNum == 2){
//    		refresh2();
//    	}    	

        if (isGreen) {            // First check if a box is green and reset it

            if (isUp) {
                if (isColOne) {
                    upButtons[currentGreen].setBackgroundColor(Color.WHITE);        //first change last green back to white
                    UpVal[currentGreen] = 0;
                    UpSet[currentGreen] = 0;
                    upButtons[currentGreen].setText("");
                } else {
                    upButtons2[currentGreen].setBackgroundColor(Color.WHITE);        //first change last green back to white
                    UpVal2[currentGreen] = 0;
                    UpSet2[currentGreen] = 0;
                    upButtons2[currentGreen].setText("");

                }
            } else {
                if (isColOne) {
                    loButtons[currentGreen].setBackgroundColor(Color.WHITE);        //first change last green back to white
                    LoVal[currentGreen] = 0;
                    LoSet[currentGreen] = 0;
                    loButtons[currentGreen].setText("");
                } else {
                    loButtons2[currentGreen].setBackgroundColor(Color.WHITE);        //first change last green back to white
                    LoVal2[currentGreen] = 0;
                    LoSet2[currentGreen] = 0;
                    loButtons2[currentGreen].setText("");
                }

            }

        } else {
            nextTurn();
        }
    }

    public void refresh1() {
        upButtons[6].setText(upSum.toString());            // Display subtotal
        upButtons[7].setText(upBonus.toString());        // Display Bonus
        upButtons[8].setText(upTotal.toString());        // Display Total
        loButtons[10].setText(extras.toString());
        loButtons[11].setText(loTotal.toString());

        upButtons2[6].setText(upSum2.toString());        // Display subtotal
        upButtons2[7].setText(upBonus2.toString());        // Display Bonus
        upButtons2[8].setText(upTotal2.toString());        // Display Total
        loButtons2[11].setText(loTotal2.toString());
    }

    public void refresh2() {

    }

    public void rawReset() {
        rawLo = 0;
        rawLo2 = 0;
        rawUp = 0;
        rawUp2 = 0;
    }

    public void setYellow() {
        if (isUp) {
            if (isColOne) {
                upButtons[currentGreen].setBackgroundColor(Color.parseColor("#FDF57F"));
            } else {
                upButtons2[currentGreen].setBackgroundColor(Color.parseColor("#FDF57F"));
            }
        } else {
            if (isColOne) {
                loButtons[currentGreen].setBackgroundColor(Color.parseColor("#FDF57F"));
                LoSet[currentGreen] = 1;
            } else {
                loButtons2[currentGreen].setBackgroundColor(Color.parseColor("#FDF57F"));
                LoSet2[currentGreen] = 1;
            }
        }
    }

    public void addLastRoll() {

        if (rawUp + rawUp2 + rawLo + rawLo2 == 0) {
            isScratch = true;
        }
        if (upClick) {
            if (isColOne) {
                UpVal[rawJ] = rawUp;        // finally add the upper value 
                setBlack[0]++;
            } else {
                UpVal2[rawJ2] = rawUp2;
                setBlack[1]++;
            }
        } else {
            if (isColOne) {
                LoVal[rawI] = rawLo;        // or lower
                setBlack[2]++;
            } else {
                LoVal2[rawI2] = rawLo2;
                setBlack[3]++;
            }
        }

        redrawBlack();
        rawReset();

        upCalc();


        grandTotal = upTotal + loTotal + upTotal2 + loTotal2;

        refresh1();

        if (isPlay) {

            grandButton.setText("Grand Total = " + grandTotal.toString());
        }
        highScore = mGameSettings.getInt(GAME_PREFERENCES_HISCORE, 0);
        highScoreDouble = mGameDoubleScore.getInt(GAME_PREFERENCES_DOUBLEHISCORE, 0);
        highScoreTrue = mTrueScore.getInt(GAME_PREFERENCES_TRUE_HISCORE, 0);
        highScoreSeq = mSeqScore.getInt(GAME_PREFERENCES_SEQ_HISCORE, 0);
        highScoreFour = mFourScore.getInt(GAME_PREFERENCES_FOUR_HISCORE, 0);
        highScoreFive = mFiveScore.getInt(GAME_PREFERENCES_FIVE_HISCORE, 0);


        if (gameNum == 1) {
            if (grandTotal > highScore) {
                editor.putInt(GAME_PREFERENCES_HISCORE, grandTotal);
                editor.commit();
                setResult(RESULT_OK);
                highScore = grandTotal;
                hiScoreText();
            }
            ;
        } else if (gameNum == 2) {
            if (grandTotal > highScoreDouble) {
                editorDouble.putInt(GAME_PREFERENCES_DOUBLEHISCORE, grandTotal);
                editorDouble.commit();
                setResult(RESULT_OK);
                highScoreDouble = grandTotal;
                hiScoreText();
            }
            ;
        } else if (gameNum == 3) {
            if (grandTotal > highScoreFour) {
                editorFour.putInt(GAME_PREFERENCES_FOUR_HISCORE, grandTotal);
                editorFour.commit();
                setResult(RESULT_OK);
                highScoreFour = grandTotal;
                hiScoreText();
            }
            ;
        } else if (gameNum == 4) {
            if (grandTotal > highScoreSeq) {
                editorSeq.putInt(GAME_PREFERENCES_SEQ_HISCORE, grandTotal);
                editorSeq.commit();
                setResult(RESULT_OK);
                highScoreSeq = grandTotal;
                hiScoreText();
            }
            ;
        } else if (gameNum == 5) {
            if (grandTotal > highScoreFour) {
                editorFour.putInt(GAME_PREFERENCES_FOUR_HISCORE, grandTotal);
                editorFour.commit();
                setResult(RESULT_OK);
                highScoreFour = grandTotal;
                hiScoreText();
            }
            ;
        }
    }

    public void nextTurn() {

        rollsLeft = 4;
        turnNum -= 1;
        rollit.setText("Roll");
        rollit.setEnabled(true);
        rollit.setTextColor(Color.BLACK);
        for (int i = 0; i < 6; i++) {
            DieSet[i] = 0;
            imageButtons[i].setBackgroundResource(imageBlank[DieVal[i]]);
            ovalButtons[i].setBackgroundResource(R.drawable.blankoval);
        }

        if (turnNum < 1) {

            //addLastRoll();

        }
    }

    @SuppressWarnings("deprecation")
    public void gameOver() {
        if (!isOver) {
            isOver = true;
            isPlay = false;
            rollit.setText("Play Again?");
            rollit.setTextColor(Color.RED);
            rollsLeft = 5;
            isGreen = false;
            firstPlay = false;


            lifetimeScore += grandTotal;
            eLtScore.putInt(GAME_PREFERENCES_LIFETIME_SCORE, lifetimeScore);
            eLtScore.commit();

            lifetimeGames++;
            eLtGames.putInt(GAME_PREFERENCES_LIFETIME_GAMES, lifetimeGames);
            eLtGames.commit();

            // ** GP  **


            if (isSignedIn()) {
                // Total Games
                ChalVal[29] = 10;
                achStringGP[29] = getResources().getString(R.string.achievement_just_gettin_started);

                ChalVal[30] = 30;
                achStringGP[30] = getResources().getString(R.string.achievement_getting_the_hang_of_it);

                ChalVal[31] = 150;
                achStringGP[31] = getResources().getString(R.string.achievement_professional_gamer);

                ChalVal[32] = 500;
                achStringGP[32] = getResources().getString(R.string.achievement_gaming_addict);

                // Total Games Played
                try {
                    for (int i = 0; i < 4; i++) {
                        Games.Achievements.increment(getApiClient(), achStringGP[29 + i], 1);
                    }
                } catch (IllegalStateException e) {
                    String tag = "GP Fail = ";
                    Log.v(tag, e.getMessage());
                    tosty("Please Sign into Google Play");
                }

            }


            // **     **


            //isScoreLoop = scoreLoopSettings.getBoolean(GAME_PREFERENCES_SCORELOOP, true);

            highScoreArray[0] = highScore = mGameSettings.getInt(GAME_PREFERENCES_HISCORE, 0);
            highScoreArray[1] = highScoreDouble = mGameDoubleScore.getInt(GAME_PREFERENCES_DOUBLEHISCORE, 0);
            highScoreArray[2] = highScoreTrue = mTrueScore.getInt(GAME_PREFERENCES_TRUE_HISCORE, 0);
            highScoreArray[3] = highScoreSeq = mSeqScore.getInt(GAME_PREFERENCES_SEQ_HISCORE, 0);
            highScoreArray[4] = highScoreFour = mFourScore.getInt(GAME_PREFERENCES_FOUR_HISCORE, 0);
            //highScoreArray[5] = highScoreFive = mFiveScore.getInt(GAME_PREFERENCES_FIVE_HISCORE, 0);


            if (!isChallenge) {
                for (int i = 0; i < highScoreArray.length; i++) {
                    if (gameNum == i + 1) {
                        if (highScoreArray[i] <= grandTotal) {
                            hiScore.setText("New Hi Score!");
                            hiScore.setTextColor(Color.GREEN);
                            soundPlay(4);

                        } else {
                            hiScore.setText("GAME OVER!");
                            soundPlay(5);
                        }
                    }
                }
            }


            if (isSignedIn()) {
                grandButton.setClickable(true);
                grandButton.setBackgroundColor(Color.LTGRAY);
                grandButton.setTextColor(Color.BLACK);

                grandButton.setBackgroundDrawable(originalBackground);
                grandButton.setText("Submit Score? " + grandTotal.toString());
                if (isSignedIn()) {
                    grandButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play1, 0, 0, 0);

                }
                //TODO


            }

            if (isPlayServices) {
                achievementsGP();
            }
        }

    }

    private void achievementsGP() {
        //***** GOOGLE PLAY *****//
        achStringGP[21] = getResources().getString(R.string.achievement_2000_lifetime_points);
        achStringGP[22] = getResources().getString(R.string.achievement_10000_lifetime_points);
        achStringGP[23] = getResources().getString(R.string.achievement_50000_lifetime_points);
        achStringGP[24] = getResources().getString(R.string.achievement_250000_lifetime_points);


        Double tempor;
        Integer remainPoints;
        Integer submitPoints;

        lifScore += grandTotal;
        remainPoints = lifScore % 100;

        if (lifScore > 100) {
            tempor = Math.floor(lifScore / 100);
            submitPoints = tempor.intValue();
            lifScore = remainPoints;
            try {
                Games.Achievements.increment(getApiClient(), achStringGP[23], submitPoints);        // 50,000
                Games.Achievements.increment(getApiClient(), achStringGP[24], submitPoints);        // 250,000		
            } catch (IllegalStateException e) {
                String tag = "GP Fail = ";
                Log.v(tag, e.getMessage());
                tosty("Please Sign into Google Play");
            }
        }


        eLTS.putInt(GAME_PLAY_LTS, lifScore);
        eLTS.commit();


        // Lifetime Points
        try {
            Games.Achievements.increment(getApiClient(), achStringGP[21], grandTotal);        // 2,000
            Games.Achievements.increment(getApiClient(), achStringGP[22], grandTotal);        // 10,000
        } catch (IllegalStateException e) {
            String tag = "GP Fail = ";
            Log.v(tag, e.getMessage());
            tosty("Please Sign into Google Play");
        }

        // Single Achievements
        try {
            if (grandTotal > 380 & gameNum == 1) {    // 1.  goodscore 
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_good_score));
            }
            if (grandTotal > 750 & gameNum == 2) {    // 2. doublegoodscore
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_double_good_score));
            }
            if (grandTotal > 550 & gameNum == 1) {    // 3.  greatscore
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_great_score));
            }
            if (grandTotal > 1100 & gameNum == 2) {   // 4. doublegreatscore
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_double_great_score));
            }
            if (grandTotal > 700 & gameNum == 4) {    // 5. seqnature
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_sequential_in_nature));
            }
            if (extraCount > 2) {                    // 6. dooubleextra
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_double_extra));
            }
            if (!isScratch & gameNum == 2) {            // 7. Without a scratch
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_without_a_scratch));
            }
            if (!isScratch & gameNum == 4) {        // 8. no scratch seq
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_no_scratch_sequential));
            }
            if (grandTotal == 6) {                    // 9. minimalist
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_minimalist));
            }
            if (grandTotal == 50) {                    // 10. 50 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_exactly_50));
            }
            if (grandTotal == 100) {                    // 11. 100 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_precisely_100));
            }
            if (grandTotal == 200) {                    // 12. 200 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_perfect_200));
            }
            if (grandTotal == 300) {                    // 13. 300 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_300_on_the_nose));
            }
            if (grandTotal == 400) {                    // 14. 400 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_just_400));
            }
            if (grandTotal == 500) {                    // 15. 500 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_only_500));
            }
            if (grandTotal == 750) {                    // 16. 750 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_merely_750));
            }
            if (grandTotal == 1000) {                    // 17. 1000 on the nose
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_exactly_1000));
            }
            if (grandTotal >= 950 & gameNum == 5) {   // 18. goodbonus
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_good_bonus));
            }
            if (grandTotal >= 1200 & gameNum == 5) {   // 19. greatbonus
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_great_bonus));
            }
            if (grandTotal >= 1000 & gameNum == 5 & extraCount <= 2) {   // 20.  impossiblebonus
                Games.Achievements.unlock(getApiClient(), getResources().getString(R.string.achievement_impossible_bonus));
            }
        } catch (IllegalStateException e) {
            String tag = "GP Fail = ";
            Log.v(tag, e.getMessage());
        }
    }

    private void achievementsCheck() {


        //*** SCORELOOP ***//

        // Achievement String
        achString[0] = "com.surreall.sixdice.goodscore";
        achMessage[0] = "Good Score";
        achString[1] = "com.surreall.sixdice.doublegoodscore";
        achMessage[1] = "Double Good Score";
        achString[2] = "com.surreall.sixdice.greatscore";
        achMessage[2] = "Great Score";
        achString[3] = "com.surreall.sixdice.doublegreatscore";
        achMessage[3] = "Double Great Score";
        achString[5] = "com.surreall.sixdice.seqnature";
        achMessage[5] = "Sequential In Nature";
        achString[6] = "com.surreall.sixdice.doubleextra";
        achMessage[6] = "Double Extra";
        achString[7] = "com.surreall.sixdice.noscratch";
        achMessage[7] = "Without A Scratch";
        achString[8] = "com.surreall.sixdice.noscratchseq";
        achMessage[8] = "No Scratch Sequential";
        achString[9] = "com.surreall.sixdice.minimalist";
        achMessage[9] = "Minimalist";
        achString[10] = "com.surreall.sixdice.50onthenose";
        achMessage[10] = "Exactly 50";
        achString[11] = "com.surreall.sixdice.100onthenose";
        achMessage[11] = "Precicely 100";
        achString[12] = "com.surreall.sixdice.200onthenose";
        achMessage[12] = "Perfectly 200";
        achString[13] = "com.surreall.sixdice.300onthenose";
        achMessage[13] = "300 On The Nose";
        achString[14] = "com.surreall.sixdice.goodbonus";
        achMessage[14] = "Good Bonus";
        achString[15] = "com.surreall.sixdice.greatbonus";
        achMessage[15] = "Great Bonus";
        achString[16] = "com.surreall.sixdice.impossiblebonus";
        achMessage[16] = "Impossible Bonus";


        achString[21] = "com.surreall.sixdice.2000points";
        achMessage[21] = "2000 Lifetime Points";
        achString[22] = "com.surreall.sixdice.10kpoints";
        achMessage[22] = "10,000 Lifetime Points";
        achString[23] = "com.surreall.sixdice.50kpoints";
        achMessage[23] = "50,000 Lifetime Points";
        achString[24] = "com.surreall.sixdice.250kpoints";
        achMessage[24] = "250,000 Lifetime Points";

        achString[26] = "com.surreall.sixdice.10yat";
        achMessage[26] = "Yacht Newbie";
        achString[27] = "com.surreall.sixdice.50yat";
        achMessage[27] = "Yacht Specialist";
        achString[28] = "com.surreall.sixdice.500yat";
        achMessage[28] = "Yacht Overload";
        achString[29] = "com.surreall.sixdice.2500yat";
        achMessage[29] = "Yacht Insanity";

        achString[30] = "com.surreall.sixdice.10games";
        achMessage[30] = "Just Gettin' Started";
        achString[31] = "com.surreall.sixdice.50games";
        achMessage[31] = "Getting The Hang Of It";
        achString[32] = "com.surreall.sixdice.250games";
        achMessage[32] = "Professional Gamer";

        ChalVal[21] = 2000;
        ChalVal[22] = 10000;
        ChalVal[23] = 50000;
        ChalVal[24] = 250000;

        ChalVal[26] = 10;
        ChalVal[27] = 50;
        ChalVal[28] = 500;

        ChalVal[30] = 10;
        ChalVal[31] = 50;
        ChalVal[32] = 250;
        //_achievements[20] = ScoreloopManagerSingleton.get().getAchievement("com.surreall.yacht.500points");


        String toastMessage = "";


        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.toastImage);
        image.setImageResource(R.drawable.toastimage);
        TextView toastText = (TextView) layout.findViewById(R.id.toastText);
        toastText.setText("Achievement Unlocked\n" + toastMessage);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);


        // Life Time Achievements (ltc = lifetime count)


        // LifeTime Score
        /*
        for (int ltc = 21; ltc < 25; ltc++) { 
        	_achievements[ltc] = ScoreloopManagerSingleton.get().getAchievement(achString[ltc]);
        	//Integer achVal = _achievements[ltc].getAward().getAchievingValue();
        	Integer achVal = ChalVal[ltc];
        	
        	Integer gtt = 0;
        	
        	if(lifetimeScore < achVal){
        		if (!_achievements[ltc].isAchieved()) {
        			gtt = lifetimeScore;
        			_achievements[ltc].setValue(gtt);
        		}
        	} else {
        		if (!_achievements[ltc].isAchieved()) {
        			gtt = achVal;
        			_achievements[ltc].setValue(gtt);
    	    		toastMessage = achMessage[ltc];
    	    		toastText.setText("Achievement Unlocked\n" + toastMessage);
    	    		toast.show();
        		 } 
        	}  
		}
        
        // LifeTime Yahtzees
        for (int ltc = 26; ltc < 29; ltc++) { 
        	_achievements[ltc] = ScoreloopManagerSingleton.get().getAchievement(achString[ltc]);
        	//Integer achVal = _achievements[ltc].getAward().getAchievingValue();
        	Integer achVal = ChalVal[ltc];
        	
        	Integer gtt = 0;
        	
        	if(lifetimeYahtzees < achVal){
        		if (!_achievements[ltc].isAchieved()) {
        			gtt = lifetimeYahtzees;
        			_achievements[ltc].setValue(gtt);
        		}
        	} else {
        		if (!_achievements[ltc].isAchieved()) {
        			gtt = achVal;
        			_achievements[ltc].setValue(gtt);
    	    		toastMessage = achMessage[ltc];
    	    		toastText.setText("Achievement Unlocked\n" + toastMessage);
    	    		toast.show();
        		 } 
        	}  
		}
        
        // LifeTime Games
        for (int ltc = 30; ltc < 33; ltc++) { 
        	_achievements[ltc] = ScoreloopManagerSingleton.get().getAchievement(achString[ltc]);
        	//Integer achVal = _achievements[ltc].getAward().getAchievingValue();
        	Integer achVal = ChalVal[ltc];
        	
        	Integer gtt = 0;
        	
        	if(lifetimeGames < achVal){
        		if (!_achievements[ltc].isAchieved()) { 
        			gtt = lifetimeGames;
        			_achievements[ltc].setValue(gtt);
        		}
        	} else {
        		if (!_achievements[ltc].isAchieved()) {
        			gtt = achVal;
        			_achievements[ltc].setValue(gtt);
    	    		toastMessage = achMessage[ltc];
    	    		toastText.setText("Achievement Unlocked\n" + toastMessage);
    	    		toast.show();
        		 } 
        	}  
		}
		
        
        
        if(grandTotal > 380 & gameNum == 1){   // goodscore 
        	_achievements[0] = ScoreloopManagerSingleton.get().getAchievement(achString[0]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[0].getAward().getIdentifier(), true, true);
        }
        
        if(grandTotal > 750 & gameNum == 2){   // doublegoodscore
        	_achievements[1] = ScoreloopManagerSingleton.get().getAchievement(achString[1]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[1].getAward().getIdentifier(), true, true);
        }
        if(grandTotal > 550 & gameNum == 1){   // greatscore
        	_achievements[2] = ScoreloopManagerSingleton.get().getAchievement(achString[2]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[2].getAward().getIdentifier(), true, true);
        }
        if(grandTotal > 1100 & gameNum == 2){   // doublegreatscore
        	_achievements[3] = ScoreloopManagerSingleton.get().getAchievement(achString[3]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[3].getAward().getIdentifier(), true, true);
        }

        if(grandTotal > 700 & gameNum == 4){   // seqnature
        	_achievements[5] = ScoreloopManagerSingleton.get().getAchievement(achString[5]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[5].getAward().getIdentifier(), true, true);
        }

        if(extraCount > 2 ){   // dooubleextra
        	_achievements[6] = ScoreloopManagerSingleton.get().getAchievement(achString[6]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[6].getAward().getIdentifier(), true, true);
        }
        if(!isScratch & gameNum == 2){ // Without a scratch
        	_achievements[7] = ScoreloopManagerSingleton.get().getAchievement("com.surreall.sixdice.noscratch");
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[7].getAward().getIdentifier(), true, true);
        }
        if(!isScratch & gameNum == 4){   // no scratch seq
        	_achievements[8] = ScoreloopManagerSingleton.get().getAchievement(achString[8]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[8].getAward().getIdentifier(), true, true);
        }
        if(grandTotal == 6){					// minimalist
        	_achievements[9] = ScoreloopManagerSingleton.get().getAchievement(achString[9]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[9].getAward().getIdentifier(), true, true);
        }
        if(grandTotal == 50){ 	 				// 50 on the nose
        	_achievements[10] = ScoreloopManagerSingleton.get().getAchievement("com.surreall.sixdice.50onthenose");
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[10].getAward().getIdentifier(), true, true);
        }  
        if(grandTotal == 100){					// 100 on the nose
        	_achievements[11] = ScoreloopManagerSingleton.get().getAchievement("com.surreall.sixdice.100onthenose");
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[11].getAward().getIdentifier(), true, true);
        }
        if(grandTotal == 200){					// 200 on the nose
        	_achievements[12] = ScoreloopManagerSingleton.get().getAchievement("com.surreall.sixdice.200onthenose");
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[12].getAward().getIdentifier(), true, true);
        }
        if(grandTotal == 300){					// 300 on the nose
        	_achievements[13] = ScoreloopManagerSingleton.get().getAchievement("com.surreall.sixdice.300onthenose");
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[13].getAward().getIdentifier(), true, true);
        }
        
        if(grandTotal >= 950 & gameNum == 5){   // goodbonus
        	_achievements[14] = ScoreloopManagerSingleton.get().getAchievement(achString[14]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[14].getAward().getIdentifier(), true, true);
        }
        if(grandTotal >= 1200 & gameNum == 5){   // goodbonus
        	_achievements[15] = ScoreloopManagerSingleton.get().getAchievement(achString[15]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[15].getAward().getIdentifier(), true, true);
        }
        
        if(grandTotal >= 1000 & gameNum == 5 & extraCount <= 2 ){   // impossiblebonus
        	_achievements[16] = ScoreloopManagerSingleton.get().getAchievement(achString[16]);
        	ScoreloopManagerSingleton.get().achieveAward(_achievements[16].getAward().getIdentifier(), true, true);
        }
        
         */

    }

    public void resetHint() {
        for (int i = 0; i < 6; i++) {
            upHint[i] = 0;
        }
        for (int i = 0; i < 9; i++) {
            loHint[i] = 0;
        }

    }

    public void clearHint() {
        for (int i = 0; i < upText.length; i++) {
            upText[i].setTextColor(Color.WHITE);
        }
        for (int i = 0; i < loText.length; i++) {
            loText[i].setTextColor(Color.WHITE);

        }
    }

    public void showHint() {
        targetNum = 0;
        findHint();
        resetHint();
        findHint();
        for (int i = 0; i < 6; i++) {
            if (upHint[i] == 1) {
                upText[i].setTextColor(Color.YELLOW);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (loHint[i] == 1) {
                loText[i].setTextColor(Color.YELLOW);
            }
        }
    }

    public void findHint() {            // Determine Hint  Number		dieSumSet();		dieSumTotal = 0;
        for (int i = 0; i < 5; i++) {
            dieSumTotal += DieVal[i] + 1;
        }

        if (gameNum == 1 || gameNum == 2 || gameNum == 3 || gameNum == 5) {
            for (int i = 0; i < 6; i++) {                    // Check for upper values
                if (DieSum[i] >= targetNum & (UpSet[i] == 0 || UpSet2[i] == 0)) {
                    targetNum = DieSum[i];
                    upHint[i] = 1;
                }
            }
            // Two pair
            dieNum = 0;
            for (int m = 0; m < 6; m++) {
                if (DieSum[m] > m + 1) {
                    dieNum += 1;
                }
                if (DieSum[m] > (m + 1) * 3) {
                    dieNum += 1;
                }
            }
            if (dieNum > 1) {
                if (dieSumTotal >= targetNum & (LoSet[0] == 0 || LoSet2[0] == 0)) {
                    targetNum = dieSumTotal;
                    loHint[0] = 1;
                }
            }
            // 3 of a kind
            dieNum = 0;
            for (int m = 0; m < 6; m++) {
                if (DieSum[m] > (m + 1) * 2) {
                    dieNum += 1;
                }
            }
            if (dieNum > 0) {
                if (dieSumTotal >= targetNum & (LoSet[1] == 0 || LoSet2[1] == 0)) {
                    targetNum = dieSumTotal;
                    loHint[1] = 1;
                }
            }
            // 4 of a kind
            dieNum = 0;
            for (int m = 0; m < 6; m++) {
                if (DieSum[m] > (m + 1) * 3) {
                    dieNum += 1;
                }
            }
            if (dieNum > 0) {
                if (dieSumTotal >= targetNum & (LoSet[2] == 0 || LoSet2[2] == 0)) {
                    targetNum = dieSumTotal;
                    loHint[2] = 1;
                }
            }
            // Full House
            dieNum = 0;
            for (int m = 0; m < 6; m++) {

                if (DieSum[m] == (m + 1) * 3) {
                    dieNum += 5;
                }
                if (DieSum[m] == (m + 1) * 4) {
                    dieNum += 5;
                }
                if (DieSum[m] == (m + 1) * 2) {
                    dieNum += 2;
                }
                if (DieSum[m] == (m + 1) * 5) {
                    dieNum = 7;
                }
                if (DieSum[m] == (m + 1) * 6) {
                    dieNum = 7;
                }
            }
            if (dieNum > 4) {
                if (25 >= targetNum & (LoSet[3] == 0 || LoSet2[3] == 0)) {
                    targetNum = 25;
                    loHint[3] = 1;
                }
            }
            // 3X straight
            dieNum = 0;
            for (int m = 0; m < 4; m++) {
                if (DieSum[m] > 0) {
                    if (DieSum[m + 1] > 0) {
                        if (DieSum[m + 2] > 0) {
                            dieNum = 1;
                        }
                    }
                }

            }
            if (dieNum > 0) {
                if (20 >= targetNum & (LoSet[4] == 0 || LoSet2[4] == 0)) {
                    targetNum = 20;
                    loHint[4] = 1;
                }
            }
            // 4X straight
            dieNum = 0;
            for (int m = 0; m < 3; m++) {
                if (DieSum[m] > 0) {
                    if (DieSum[m + 1] > 0) {
                        if (DieSum[m + 2] > 0) {
                            if (DieSum[m + 3] > 0) {
                                dieNum = 1;
                            }
                        }
                    }
                }

            }
            if (dieNum > 0) {
                if (30 >= targetNum & (LoSet[5] == 0 || LoSet2[5] == 0)) {
                    targetNum = 30;
                    loHint[5] = 1;
                }
            }
            // 5X straight
            dieNum = 0;
            for (int m = 0; m < 2; m++) {
                if (DieSum[m] > 0) {
                    if (DieSum[m + 1] > 0) {
                        if (DieSum[m + 2] > 0) {
                            if (DieSum[m + 3] > 0) {
                                if (DieSum[m + 4] > 0) {
                                    dieNum = 1;
                                }
                            }
                        }
                    }
                }

            }
            if (dieNum > 0) {
                if (40 >= targetNum & (LoSet[6] == 0 || LoSet2[6] == 0)) {
                    targetNum = 40;
                    loHint[6] = 1;
                }
            }

            // Chance
            if (dieSumTotal >= targetNum & (LoSet[7] == 0 || LoSet2[7] == 0)) {
                targetNum = dieSumTotal;
                loHint[7] = 1;
            }
            // Yahtzee
            dieNum = 0;
            for (int m = 0; m < 6; m++) {
                if (DieSum[m] == (m + 1) * 5) {
                    dieNum = 2;
                }
            }
            if (dieNum > 1) {
                if (50 >= targetNum & (LoSet[8] == 0 || LoSet2[8] == 0)) {
                    targetNum = 50;
                    loHint[8] = 1;
                }
            }
        }
    }

    public void resetGame() {

        if (interstitial.isLoaded()) {
            interstitial.show();
        }
        interstitial.loadAd(adRequest);

        rollit.setTextColor(Color.BLACK);
        rollit.setText("Roll");
        grandButton.setText("Grand Total = 0");
        grandButton.setBackgroundColor(Color.TRANSPARENT);
        grandButton.setTextColor(Color.WHITE);
        grandButton.setOnClickListener(submitListener);
        grandButton.setClickable(false);
        highScore = mGameSettings.getInt(GAME_PREFERENCES_HISCORE, 0);
        hiScoreText();

        grandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        upButtons[6].setText("0");
        upButtons[7].setText("0");
        upButtons[8].setText("0");
        loButtons[10].setText("0");
        loButtons[11].setText("0");

        upButtons2[6].setText("0");
        upButtons2[7].setText("0");
        upButtons2[8].setText("0");
        loButtons2[10].setText("0");
        loButtons2[11].setText("0");
        for (int i = 0; i < 6; i++) {
            upButtons2[i].setBackgroundColor(Color.WHITE);
            upButtons2[i].setText("");
            UpSet2[i] = 0;
            UpVal2[i] = 0;
        }
        for (int i = 0; i < 10; i++) {
            loButtons2[i].setBackgroundColor(Color.WHITE);
            loButtons2[i].setText("");
            LoSet2[i] = 0;
            LoVal2[i] = 0;
        }
        clearHint();
        grandTotal = 0;
        rollsLeft = 4;
        totalRolls = 0;
        upBonus = 0;
        extras = 0;
        extraCount = 0;
        upSum = 0;
        loSum = 0;
        upTotal = 0;
        loTotal = 0;
        isPlay = true;
        isScratch = false;
        isOver = false;
        for (int i = 0; i < 6; i++) {
            upButtons[i].setBackgroundColor(Color.WHITE);
            upButtons[i].setText("");
            UpSet[i] = 0;
            UpVal[i] = 0;
        }
        for (int i = 0; i < 10; i++) {
            loButtons[i].setBackgroundColor(Color.WHITE);
            loButtons[i].setText("");
            LoSet[i] = 0;
            LoVal[i] = 0;
        }

        if (gameNum == 5 || gameNum == 3) {                        // Bonus Roll Yahtzee
            bonusRollCount = 10;
        }

        if (gameNum == 1) {
            turnNum = 16;
        } else if (gameNum == 2 || gameNum == 5) {
            turnNum = 32;
        } else if (gameNum == 3) {
            bonusRollCount = 10;
        } else {
            turnNum = 32;
            for (int i = 0; i < setBlack.length; i++) {
                setBlack[i] = 0;
            }

            for (int i = 2; i < 4; i++) {
                setBlack[i] = 6;
            }
            redrawBlack();
        }
        if (isQuick) {
            turnNum = 3;
        }
        textAdjust();


    }

    public void dieSumSet() {
        for (int j = 0; j < 6; j++) {
            DieSum[j] = 0;
            for (int i = 0; i < 6; i++) {
                if (DieVal[i] == j) {
                    DieSum[j] = DieSum[j] + 1 + j;
                }
            }
        }
    }

    public void tosty(String tost) {
        Toast.makeText(getApplicationContext(),
                "" + tost,
                Toast.LENGTH_LONG).show();
    }

    public void soundPlay(int s) {

		
		/*
		isSound = mGameSound.getBoolean(GAME_PREFERENCES_SOUND, true);
		
		if(isSound){
			mSoundManager.playSound(s);	// play diceroll sound
		}
		*/

        radioSound = mRadioSound.getInt(GAME_PREFERENCES_RADIO_SOUND, 1);

        if (radioSound != 2) {
            mSoundManager.playSound(s);
        }
    }

    public void submitCheck() {

        if (isSignedIn()) {

            try {
                Games.Leaderboards.submitScore(getApiClient(), LEADERBOARD_ID, grandTotal);
                tosty("game submitted"); //TODO
            } catch (IllegalStateException e) {
                String tag = "GP Fail = ";
                Log.v(tag, e.getMessage());
            }

            grandButton.setOnClickListener(onlineListener);
            grandButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play1, 0, 0, 0);
            grandButton.setText("View Online Scores?");


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // We have only one menu option
            case R.id.preferences:
                // Launch Preference activity
                Intent i = new Intent(yatzee.this, Preferences.class);
                startActivity(i);
                break;

        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        String challengeTxt;
        if (isPlay) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                challengeTxt = "Are you sure you want to exit?";
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage(challengeTxt)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                System.gc();
                                finish();

                                if (interstitial.isLoaded()) {
                                    interstitial.show();
                                }
                                interstitial.loadAd(adRequest);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.setTitle("Exit Game?");
                alert.setIcon(R.drawable.icon);
                alert.show();

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);

    }

}
