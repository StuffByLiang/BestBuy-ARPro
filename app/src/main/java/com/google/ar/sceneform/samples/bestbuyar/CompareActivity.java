package com.google.ar.sceneform.samples.bestbuyar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.ar.sceneform.samples.bestbuyar.ProductViewer;
import com.google.ar.sceneform.samples.bestbuyar.R;

public class CompareActivity extends AppCompatActivity {
    public static final String DIMENSIONS = "com.google.ar.sceneform.samples.bestbuyar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Button button = (Button) findViewById(R.id.viewProductButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductViewer();
            }
        });
    }

    public void openProductViewer() {

        EditText editText = (EditText) findViewById(R.id.customDimensionsInput);
        int number = Integer.parseInt(editText.getText().toString());

        Intent intent = new Intent(this, ProductViewer.class);
        intent.putExtra(DIMENSIONS, number);
        startActivity(intent);
    }
}
