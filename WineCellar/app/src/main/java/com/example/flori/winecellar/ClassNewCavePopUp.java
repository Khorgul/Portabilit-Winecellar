package com.example.flori.winecellar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by flori on 16/01/2018.
 */

public class ClassNewCavePopUp extends AppCompatActivity {

    private EditText tv;
    private Button btn;
    String n;

    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cave_pop_main);

        db = new DataBaseHandler(getApplicationContext());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.4));

        tv = findViewById(R.id.editTextName);

        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tv.getText().toString();
                if(!(name.isEmpty()))
                {
                    n = new String(name);
                    db.createCave(new String(n));
                    finish();
                }
            }
        });
    }
}
