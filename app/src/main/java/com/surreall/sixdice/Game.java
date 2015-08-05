package com.surreall.sixdice;

import android.os.Bundle;

/**
 * Created by Owner on 7/31/2015.
 */
public class Game extends Main{
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getGameHelper().setMaxAutoSignInAttempts(0);
    }
}
