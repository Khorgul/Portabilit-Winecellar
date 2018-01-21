package com.example.flori.winecellar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityCaveMenu extends AppCompatActivity {

    ListView vue;
    DataBaseHandler db;
    ArrayList<Cave> a;
    TextView tv;
    public static final String KEY_ID_CAVE=".com.id.cave.winecellar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vue = findViewById(R.id.lv);

        db = new DataBaseHandler(getApplicationContext());

        pop();

        vue.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                   {
                                       @Override
                                       public void onItemClick (AdapterView < ? > parent, View view,int position, long id)
                                       {
                                           tv =view.findViewById(R.id.textViewNom);
                                           Intent appInfo = new Intent(ActivityCaveMenu.this, ActivityBouteilleMenu.class);
                                           int selected = db.getCaveId(tv.getText().toString());
                                           appInfo.putExtra(KEY_ID_CAVE, selected);
                                           startActivity(appInfo);
                                       }
                                   });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCaveMenu.this, ClassNewCavePopUp.class);
                startActivity(intent);
                pop();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pop(){
        a = db.getAllCave();

        List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> element;

        for(int i=0; i<a.size(); i++)
        {
            element = new HashMap<String, String>();
            element.put("text1", a.get(i).getName());
            element.put("text2", a.get(i).getDate());
            liste.add(element);
        }

        ListAdapter adapter = new SimpleAdapter(this,
                liste,
                R.layout.cave_main,
                new String[] {"text1", "text2"},
                new int[] {R.id.textViewNom, R.id.textViewDate });
        vue.setAdapter(adapter);
    }
}
