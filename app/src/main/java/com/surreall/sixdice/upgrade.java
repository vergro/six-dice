package com.surreall.sixdice;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class upgrade extends Activity{

    Button marketKey;
    OnClickListener marketListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade);

        marketKey = (Button)findViewById(R.id.marketkey);
        marketListener = new OnClickListener() {

            @Override
            public void onClick(View v) {

                String appName = "com.surreall.sixdiceupgrade";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
                }

            }




        };

        marketKey.setOnClickListener(marketListener);

    }

};
