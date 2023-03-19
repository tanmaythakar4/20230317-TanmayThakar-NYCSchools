package com.dynamo.jpmc.nycschools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by tanmaythakar on 3/19/23.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
