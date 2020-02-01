package com.google.ar.sceneform.samples.bestbuyar;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton tv1Button = (ImageButton) findViewById(R.id.tv1);
        tv1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTV1();
            }
        });

        ImageButton tv2Button = (ImageButton) findViewById(R.id.tv2);
        tv2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTV2();
            }
        });

        ImageButton tv3Button = (ImageButton) findViewById(R.id.tv3);
        tv3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTV3();
            }
        });

        Button custom = (Button) findViewById(R.id.viewCustomButton);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openCustom(); }
        });

        Button compare = (Button) findViewById(R.id.viewCompare);
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openCompare(); }
        });
    }

    public void openTV1() {
        Intent intent = new Intent(this, TV1Activity.class);
        startActivity(intent);
    }

    public void openTV2() {
        Intent intent = new Intent(this, TV2Activity.class);
        startActivity(intent);
    }

    public void openTV3() {
        Intent intent = new Intent(this, TV3Activity.class);
        startActivity(intent);
    }

    public void openCustom() {
        Intent intent = new Intent(this, CustomActivity.class);
        startActivity(intent);
    }

    public void openCompare() {
        Intent intent = new Intent(this, CompareActivity.class);
        startActivity(intent);
    }
}
