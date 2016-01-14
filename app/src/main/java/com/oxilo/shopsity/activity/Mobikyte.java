package com.oxilo.shopsity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.oxilo.shopsity.R;

public class Mobikyte extends Activity {

    //Activity Transition Instance
    ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobikyte);
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.image)).start(savedInstanceState);

        TextView already_registered = (TextView)findViewById(R.id.already_registered);
        already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Mobikyte.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ActivityTransitionLauncher.with(Mobikyte.this).from(view).launch(intent);
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (exitTransition!=null){
            exitTransition.exit(Mobikyte.this);
        }
    }
}
