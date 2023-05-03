package com.yumel.rehber;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar())
                .hide();
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        Button enter = findViewById(R.id.enter);
        enter.setAnimation(animationIn);
        enter.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainMenu.class);
            Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
            enter.setAnimation(animationOut);
            startActivity(intent);
        });

    }


}