package com.example.flori.winecellar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by flori on 19/01/2018.
 */

public class ActivityBouteilleMenu extends AppCompatActivity{

    public static final String KEY_CAVE=".com.example.flori.Cellarium.Cave";

    ListView vue;
    DataBaseHandler db;
    Cave cave = new Cave("a");
    ArrayList<Vin> a;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int id = getIntent().getIntExtra(ActivityCaveMenu.KEY_ID_CAVE,1);

        vue = findViewById(R.id.lv);

        db = new DataBaseHandler(getApplicationContext());
        Log.d("id", String.valueOf(id));

        cave = db.getCave(id);
        cave.setId(id);

        pop();

        vue.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view, int position, long id)
            {
                Intent appInfo = new Intent(ActivityBouteilleMenu.this, ClassNewCavePopUp.class);
                startActivity(appInfo);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityBouteilleMenu.this, ClassNewBouteillePopUp.class);
                startActivity(intent);
                intent.putExtra(KEY_CAVE, cave.getId());
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
        a = db.getAllVin(cave);

        List<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> element;

        for(int i=0; i<a.size(); i++)
        {
            element = new HashMap<String, String>();
            element.put("text1", a.get(i).getAppellation());
            element.put("text2", a.get(i).getRegion());
            element.put("text3", a.get(i).getCepage());
            element.put("text4", String.valueOf(a.get(i).getQte()));
            liste.add(element);
        }

        ListAdapter adapter = new SimpleAdapter(this,
                liste,
                R.layout.bouteille_main,
                new String[] {"text1", "text2", "text3", "text4"},
                new int[] {R.id.textViewAPL, R.id.textViewRegion, R.id.textViewCepage, R.id.textViewCouleur });
        vue.setAdapter(adapter);
    }

}
