package com.example.flori.winecellar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by flori on 19/01/2018.
 */

public class ClassNewBouteillePopUp extends AppCompatActivity{

    private EditText Apl;
    private Spinner Cep;
    private Spinner Region;
    private Spinner Couleur;
    private Button btn;
    private int caveId;

    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bouteille_pop_main);
        caveId=getIntent().getIntExtra(ActivityBouteilleMenu.KEY_CAVE, 1);

        db = new DataBaseHandler(getApplicationContext());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        Apl = findViewById(R.id.appellationVinEditText);
        Cep = findViewById(R.id.cepageSpinner);
        ArrayList<String> Cepa=db.ArrayCep();
        ArrayAdapter<String> AdaptCepa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Cepa);
        Cep.setAdapter(AdaptCepa);

        Region = findViewById(R.id.regionSpinner);
        ArrayList<String> Reg=db.ArrayReg();
        ArrayAdapter<String> AdaptReg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Reg);
        Region.setAdapter(AdaptReg);

        Couleur = findViewById(R.id.couleurSpinner);
        ArrayList<String> coul=new ArrayList<String>();
        coul.add("Rouge");
        coul.add("Blanc");
        coul.add("Rose");
        ArrayAdapter<String> AdaptCol = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, coul);
        Couleur.setAdapter(AdaptCol);
        btn = findViewById(R.id.validButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Apl.getText().toString();
                if(!(name.isEmpty()))
                {
                    db.createWine(name, Cep.getSelectedItem().toString(), Region.getSelectedItem().toString(), Couleur.getSelectedItem().toString(), caveId);
                    finish();
                }
            }
        });
    }

}
