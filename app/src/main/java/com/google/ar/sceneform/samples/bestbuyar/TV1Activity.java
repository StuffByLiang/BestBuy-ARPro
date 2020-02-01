package com.google.ar.sceneform.samples.bestbuyar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class TV1Activity extends AppCompatActivity {
    public static final String DIMENSIONS = "com.google.ar.sceneform.samples.bestbuyar";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv1);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.viewProductButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductViewer();
            }
        });
    }

    public void openProductViewer() {
        Intent intent = new Intent(this, ProductViewer.class);
        intent.putExtra(DIMENSIONS, 90);
        startActivity(intent);
        /*
        IN PRODUCT VIEWER:

        Intent intent = getIntent();
        int dimensions = intent.getIntExtra(ThisActivityName.DIMENSIONS);
        */
    }
}
