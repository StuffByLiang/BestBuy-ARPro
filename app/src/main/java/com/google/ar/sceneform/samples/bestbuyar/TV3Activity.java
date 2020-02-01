package com.google.ar.sceneform.samples.bestbuyar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TV3Activity extends AppCompatActivity {
    public static final String DIMENSIONS = "com.google.ar.sceneform.samples.bestbuyar";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv3);
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
        intent.putExtra(DIMENSIONS, 65);
        startActivity(intent);
    }
}
