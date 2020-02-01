package com.google.ar.sceneform.samples.bestbuyar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Custom extends AppCompatActivity {
    public static final String DIMENSIONS = "com.google.ar.sceneform.samples.bestbuyar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        Button button = (Button) findViewById(R.id.viewProductButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductViewer();
            }
        });
    }

    public void openProductViewer() {

        EditText editText2 = (EditText) findViewById(R.id.customDimensionsInput);
        int number = Integer.parseInt(editText2.getText().toString());

        Intent intent = new Intent(this, ProductViewer.class);
        intent.putExtra(DIMENSIONS, number);
        startActivity(intent);
    }

}
