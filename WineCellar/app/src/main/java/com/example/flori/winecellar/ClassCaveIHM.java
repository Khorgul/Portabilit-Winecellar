package com.example.flori.winecellar;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by flori on 16/01/2018.
 */

public class ClassCaveIHM extends Activity {

    TextView Red;
    TextView White;
    TextView Pink;
    TextView Nb;
    TextView Name;
    TextView Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cave_main);
    }

}
