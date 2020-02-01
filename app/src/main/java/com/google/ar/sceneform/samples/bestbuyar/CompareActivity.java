package com.google.ar.sceneform.samples.bestbuyar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.ar.sceneform.samples.bestbuyar.ProductViewer;
import com.google.ar.sceneform.samples.bestbuyar.R;

public class CompareActivity extends AppCompatActivity {
    public static final String DIMENSIONS = "com.google.ar.sceneform.samples.bestbuyar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Button button = (Button) findViewById(R.id.viewCompareProducts);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductViewer();
            }
        });
    }

    public void openProductViewer() {
        Intent intent = new Intent(this, ProductViewer.class);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_tv1:
               //stub
                break;
            case R.id.checkbox_tv2:
                //stub
                break;
            case R.id.checkbox_tv3:
                //stub
                break;
            default:
                // stub
        }
    }
}
