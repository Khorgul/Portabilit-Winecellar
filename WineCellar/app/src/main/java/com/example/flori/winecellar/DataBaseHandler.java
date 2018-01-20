package com.example.flori.winecellar;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WineManager";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "create table utilisateur\n" +
                "(\n" +
                "\tpseudo varchar(20) not null,\n" +
                "\tnom varchar(20) not null,\n" +
                "\tprenom varchar(20) not null,\n" +
                "\tdatenaiss date not null,\n" +
                "\tmdp bytea not null,\n" +
                "\tdateinscription date default CURRENT_TIMESTAMP not null,\n" +
                "\temail varchar not null,\n" +
                "\tville varchar(30),\n" +
                "\tidutilisateur integer primary key autoincrement,\n" +
                "\tsalt bytea\n" +
                ")";
        db.execSQL(CREATE_TABLE_USER);

        String CREATE_INDEX_USER = "create unique index utilisateur_idutilisateur_uindex\n" +
                "\ton utilisateur (idutilisateur)";
        db.execSQL(CREATE_INDEX_USER);

        String CREATE_TABLE_CAVEVIN = "create table cavevin\n" +
                "(\n" +
                "\tidcave integer primary key autoincrement,\n" +
                "\tnomcave varchar not null,\n" +
                "\tvisibilite boolean default true not null,\n" +
                "\tdescription varchar default 'La description de ma cave à vins',\n" +
                "\tidutilisateur integer\n" +
                "\t\tconstraint cavevin_utilisateur_idutilisateur_fk\n" +
                "\t\t\treferences utilisateur\n" +
                ")";
        db.execSQL(CREATE_TABLE_CAVEVIN);

        String CREATE_TABLE_DOMAINE = "create table domaine\n" +
                "(\n" +
                "\tnomdomaine varchar not null,\n" +
                "\tiddomaine integer primary key autoincrement,\n" +
                "\tadresse varchar,\n" +
                "\tcodepostale varchar,\n" +
                "\tville varchar,\n" +
                "\temail varchar,\n" +
                "\ttelephone varchar\n" +
                ")";
        db.execSQL(CREATE_TABLE_DOMAINE);

        String CREATE_INDEX_DOMAINE = "create unique index domaine_iddomaine_uindex\n" +
                "\ton domaine (iddomaine)";
        db.execSQL(CREATE_INDEX_DOMAINE);

        String CREATE_TABLE_REGION = "create table region\n" +
                "(\n" +
                "\tidregion integer primary key autoincrement,\n" +
                "\tnomregion varchar not null,\n" +
                "\tidpays integer\n" +
                "\t\tconstraint region_pays_idpays_fk\n" +
                "\t\t\treferences pays\n" +
                ")";
        db.execSQL(CREATE_TABLE_REGION);

        String CREATE_INDEX_REGION = "create unique index region_idregion_uindex\n" +
                "\ton region (idregion)";
        db.execSQL(CREATE_INDEX_REGION);

        String CREATE_TABLE_PAYS = "create table pays\n" +
                "(\n" +
                "\tnompays varchar not null,\n" +
                "\tidpays integer primary key autoincrement\n" +
                ")";
        db.execSQL(CREATE_TABLE_PAYS);

        String CREATE_INDEX_PAYS = "create unique index pays_idpays_uindex\n" +
                "\ton pays (idpays)";
        db.execSQL(CREATE_INDEX_PAYS);

        String CREATE_TABLE_TYPAPL = "create table typeappellation\n" +
                "(\n" +
                "\tnomta varchar not null,\n" +
                "\tidta integer primary key autoincrement\n" +
                ")";
        db.execSQL(CREATE_TABLE_TYPAPL);

        String CREATE_INDEX_TYPAPL = "create unique index typeappellation_idta_uindex\n" +
                "\ton typeappellation (idta)";
        db.execSQL(CREATE_INDEX_TYPAPL);

        String CREATE_TABLE_APL = "create table appellation\n" +
                "(\n" +
                "\tidapp integer primary key autoincrement,\n" +
                "\tnomappellation varchar not null,\n" +
                "\tidtype integer\n" +
                "\t\tconstraint appellation_typeappellation_idta_fk\n" +
                "\t\t\treferences typeappellation,\n" +
                "\tidregion integer\n" +
                "  \t\tCONSTRAINT appellation_region_idregion_fk\n" +
                "  \t\t\tREFERENCES region\n" +
                ")";
        db.execSQL(CREATE_TABLE_APL);

        String CREATE_INDEX_APL = "create unique index appellation_idapp_uindex\n" +
                "\ton appellation (idapp)";
        db.execSQL(CREATE_INDEX_APL);

        String CREATE_TABLE_CEPAGE = "create table cepage\n" +
                "(\n" +
                "\tidcepage integer primary key autoincrement,\n" +
                "\tnomcepage varchar not null\n" +
                ")";
        db.execSQL(CREATE_TABLE_CEPAGE);

        String CREATE_INDEX_CEPAGE = "create unique index table_name_idcepage_uindex\n" +
                "\ton cepage (idcepage)";
        db.execSQL(CREATE_INDEX_CEPAGE);

        String CREATE_TABLE_VIN = "create table vin\n" +
                "(\n" +
                "\tidvin integer primary key autoincrement,\n" +
                "\tcouleur varchar(20) not null,\n" +
                "\tiddomaine integer\n" +
                "\t\tconstraint vin_domaine_iddomaine_fk\n" +
                "  \t\t\t\treferences domaine,\n" +
                "\tidcepage integer\n" +
                "  \t\tconstraint vin_cepage_idcepage_fk\n" +
                "  \t\t\treferences cepage,\n" +
                "\tidapp integer\n" +
                "  \t\tconstraint vin_appellation_idapp_fk\n" +
                "  \t\t\t\treferences appellation,\n" +
                "\tdegres double precision\n" +
                ")";
        db.execSQL(CREATE_TABLE_VIN);

        String CREATE_INDEX_VIN = "create unique index vin_idvin_uindex\n" +
                "\ton vin (idvin)";
        db.execSQL(CREATE_INDEX_VIN);

        String CREATE_TABLE_BOUTEILLE = "create table bouteille\n" +
                "(\n" +
                "\tidbouteille integer primary key autoincrement,\n" +
                "\tvolume integer not null,\n" +
                "\tidvin integer\n" +
                "  \t\tconstraint bouteille_vin_idvin_fk\n" +
                "  \t\treferences vin,\n" +
                "\tcode varchar,\n" +
                "\timage bytea,\n" +
                "\tmillesime varchar(20)\n" +
                ")";
        db.execSQL(CREATE_TABLE_BOUTEILLE);

        String CREATE_INDEX_BOUTEILLE = "create unique index bouteille_idbouteille_uindex\n" +
                "\ton bouteille (idbouteille)";
        db.execSQL(CREATE_INDEX_BOUTEILLE);

        String CREATE_TABLE_COMMENTER = "create table commenter\n" +
                "(\n" +
                "\tidbouteille integer not null\n" +
                "\t\tconstraint commenter_bouteille_idbouteille_fk\n" +
                "\t\t\treferences bouteille,\n" +
                "\tidutilisateur integer not null\n" +
                "\t\tconstraint commenter_utilisateur_idutilisateur_fk\n" +
                "\t\t\treferences utilisateur,\n" +
                "\tidcommentaire serial not null,\n" +
                "\tdatecommentaire date default CURRENT_TIMESTAMP not null,\n" +
                "\tconstraint commenter_idcommentaire_idbouteille_idutilisateur_pk\n" +
                "\t\tprimary key (idcommentaire, idbouteille, idutilisateur)\n" +
                ")";
        db.execSQL(CREATE_TABLE_COMMENTER);

        String CREATE_INDEX_COMMENTER = "create unique index commenter_idbouteille_idcommentaire_idutilisateur_uindex\n" +
                "\ton commenter (idbouteille, idcommentaire, idutilisateur)";
        db.execSQL(CREATE_INDEX_COMMENTER);

        String CREATE_TABLE_CONTENIR = "create table contenir\n" +
                "(\n" +
                "\tidbouteille integer\n" +
                "\t\tconstraint contenir_bouteille_idbouteille_fk\n" +
                "\t\t\treferences bouteille,\n" +
                "\tidcave integer\n" +
                "\t\tconstraint contenir_cavevin_idcave_fk\n" +
                "\t\t\treferences cavevin,\n" +
                "\tquantite integer\n" +
                ")";
        db.execSQL(CREATE_TABLE_CONTENIR);

        initDB(db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS pays");
        db.execSQL("DROP TABLE IF EXISTS region");
        db.execSQL("DROP TABLE IF EXISTS domaine");
        db.execSQL("DROP TABLE IF EXISTS typeappellation");
        db.execSQL("DROP TABLE IF EXISTS appellation");
        db.execSQL("DROP TABLE IF EXISTS domaine");
        db.execSQL("DROP TABLE IF EXISTS cepage");
        db.execSQL("DROP TABLE IF EXISTS contenir");
        db.execSQL("DROP TABLE IF EXISTS bouteille");
        db.execSQL("DROP TABLE IF EXISTS vin");
        db.execSQL("DROP TABLE IF EXISTS cavevin");
        db.execSQL("DROP TABLE IF EXISTS utilisateur");
        db.execSQL("DROP TABLE IF EXISTS commenter");

        // Create tables again
        onCreate(db);
    }


    //Init db
    public void initDB(SQLiteDatabase db) {

        //CEPAGE
        ContentValues values = new ContentValues();
        values.put("nomcepage", "Abourjou");
        values.put("nomcepage", "Arrufiac");
        values.put("nomcepage", "Blanqueiro");
        values.put("nomcepage", "Biancu gentille");
        values.put("nomcepage", "Calitor");
        values.put("nomcepage", "César");
        values.put("nomcepage", "Chaselas");

        // insert row

        db.insert("cepage", null, values);


        //REGION
        values = new ContentValues();
        values.put("nomregion", "Alsace");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Beaujolais");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Bourgogne");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Loire");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Corse");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Lorraine");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Savoie");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Bordeaux");
        values.put("idpays", "1");
        db.insert("region", null, values);

        values = new ContentValues();
        values.put("nomregion", "Champagne");
        values.put("idpays", "1");
        db.insert("region", null, values);

        //TYPEAPL
        values = new ContentValues();
        values.put("nomta", "AOC");
        values.put("nomta", "AOVDQS");
        values.put("nomta", "IGP");
        db.insert("typeappellation", null, values);

        //APPELLATION
        values = new ContentValues();
        values.put("nomappellation", "alsace grand cru");
        values.put("idtype", "1");
        db.insert("appellation", null, values);

        values = new ContentValues();
        values.put("nomappellation", "beaujolais");
        values.put("idtype", "3");
        db.insert("appellation", null, values);

        values = new ContentValues();
        values.put("nomappellation", "fleurie");
        values.put("idtype", "1");
        db.insert("appellation", null, values);

        values = new ContentValues();
        values.put("nomappellation", "morgon");
        values.put("idtype", "2");
        db.insert("appellation", null, values);

        Log.d("INSERT", "COMPLETE");
    }
    /** TABLES :
     *
     *  appellation(idapp int, nomappellation string, idtype int, idregion int)
     *
     *  bouteille(idbouteille int, volume int, idvin int, code string, image bytea, millesime string)
     *
     *  cavevin(idcave int, nomcave string, iduser int)
     *
     *  domaine(nomdomaine string, iddomaine int, addresse string, codepostale string, ville string, email string, telephone string)
     *
     *  pays(nompays string, idpays int)
     *
     *  region(idregion int, nomregion string, idpays int)
     *
     *  typeappellation(nomta string, idta int)
     *
     *  utilisateur(pseudo string, nom string, prenom string, datenaiss date, mdp bytea, dateinscription date, email string, ville string)
     *
     *  vin(idvin int, couleur string, iddomaine int, idcepage int, idapp int, degres double)
     *
     *  contenir(idbouteille int, idcave int, quantite int)
     */

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //couleurvin
    public String getCouleur(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select couleur from vin where idvin like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getInt(c.getColumnIndex("idvin"))== id) {
                        return c.getString(c.getColumnIndex("couleur"));
                    }
                }
            } while(c.moveToNext());
        }
        return null;
    }

    //idcepage nomcepage
    public String getNomCepage(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select nomcepage from cepage where idcepage like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getInt(c.getColumnIndex("idcepage"))== id) {
                        return c.getString(c.getColumnIndex("nomcepage"));
                    }
                }
            } while(c.moveToNext());
        }
        return null;
    }

    public int getIdCepage(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select idcepage from cepage where nomcepage like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getString(c.getColumnIndex("nomcepage")).equals(id)) {
                        return c.getInt(c.getColumnIndex("idcepage"));
                    }
                }
            } while(c.moveToNext());
        }
        return 1;
    }

    //region name
    public String getNomRegion(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select nomregion from region where idregion like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getInt(c.getColumnIndex("idregion"))== id) {
                        return c.getString(c.getColumnIndex("nomregion"));
                    }
                }
            } while(c.moveToNext());
        }
        return null;
    }

    //region id
    public int getIdRegion(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select idregion from region where nomregion like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getString(c.getColumnIndex("nomregion"))== id) {
                        return c.getInt(c.getColumnIndex("idregion"));
                    }
                }
            } while(c.moveToNext());
        }
        return 1;
    }

    //region APL
    public String getNomAppellation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select nomappellation from appellation where idapp like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getInt(c.getColumnIndex("idapp"))== id) {
                        return c.getString(c.getColumnIndex("nomappellation"));
                    }
                }
            } while(c.moveToNext());
        }
        return null;
    }

    public int getIdAppellation(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select idapp from appellation where nomappellation like "+id;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getString(c.getColumnIndex("nomappellation"))== id) {
                        return c.getInt(c.getColumnIndex("idapp"));
                    }
                }
            } while(c.moveToNext());
        }
        return 1;
    }

    //Adding new Cave

    public long createCave(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nomcave", name);

        // insert row

        return db.insert("cavevin", null, values);
    }

    //Getting Cave
    public Cave getCave(String nom)
    {
        Log.d("NOM", nom);
        String selectQuery = "SELECT idcave FROM CAVEVIN WHERE nomcave like '" + nom+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getString(c.getColumnIndex("nomcave"))== nom) {
                        Cave a = new Cave(c.getString(c.getColumnIndex("nomcave")));
                        a.setId(c.getInt(c.getColumnIndex("idcave")));
                        return a;
                    }
                }
            } while(c.moveToNext());
        }
        return new Cave(nom);
    }

    //Getting Cavename
    public Cave getCave(int id)
    {
        String selectQuery = "SELECT idcave, nomcave FROM CAVEVIN WHERE idcave like " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getInt(c.getColumnIndex("idcave"))== id) {
                        Cave a = new Cave(c.getString(c.getColumnIndex("nomcave")));
                        a.setId(c.getInt(c.getColumnIndex("idcave")));
                        return a;
                    }
                }
            } while(c.moveToNext());
        }
        return null;
    }

    //Getting CaveId
    public int getCaveId(String nom)
    {
        String selectQuery = "SELECT idcave FROM CAVEVIN WHERE nomcave like '" + nom+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getString(c.getColumnIndex("nomcave"))==nom)
                        return c.getInt(c.getColumnIndex("idcave"));
                }
            } while(c.moveToNext());
        }
        return 1;
    }

    public int getBouteilleId(int nom)
    {
        String selectQuery = "SELECT idbouteille FROM bouteille WHERE idvin like " + nom;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through all rows and checking name
        if(c.moveToFirst()) {
            do {
                {
                    if(c.getInt(c.getColumnIndex("idvin"))==nom)
                        return c.getInt(c.getColumnIndex("idbouteille"));
                }
            } while(c.moveToNext());
        }
        return 1;
    }

    //Getting all caves
    public ArrayList<Cave> getAllCave(){
        ArrayList<Cave> caves = new ArrayList<Cave>();
        String selectQuery = "SELECT  * FROM CAVEVIN";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Cave t = new Cave((c.getString((c.getColumnIndex("nomcave")))));

                // adding to tags list
                caves.add(t);
            } while (c.moveToNext());
        }
        return caves;
    }

    //Getting all Vins
    public ArrayList<Vin> getAllVin(Cave cave){
        ArrayList<Vin> Vins = new ArrayList<Vin>();
        String selectQuery = "SELECT  idbouteille FROM contenir where idcave like " + cave.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                if(c.getInt(c.getColumnIndex("idbouteille"))==cave.getId()) {
                    Vin v = new Vin();
                    v.setIdbouteille(c.getInt(c.getColumnIndex("idbouteille")));

                    String selectQuery2 = "SELECT  idvin FROM bouteille where idbouteille like " +
                    v.getIdbouteille();
                    Cursor c1 = db.rawQuery(selectQuery, null);
                    if (c.moveToFirst()) {

                        do{
                            if(c.getInt(c.getColumnIndex("idbouteille"))==v.getIdbouteille())
                            {
                                v.setIdvin(c.getInt(c.getColumnIndex("idvin")));
                                v.setAppellation(getNomAppellation(v.getIdvin()));
                                v.setCepage(getNomCepage(v.getIdvin()));
                                v.setRegion(getNomRegion(v.getIdvin()));
                                v.setCouleur(getCouleur(v.getIdvin()));
                            }
                        } while (c.moveToNext());
                    }


                    // adding to tags list
                    Vins.add(v);
                }
            } while (c.moveToNext());
        }
        return Vins;
    }

    public ArrayList<String> ArrayReg(){
        ArrayList<String> reg = new ArrayList<String>();
        String selectQuery = "SELECT nomregion FROM region";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String t = c.getString((c.getColumnIndex("nomregion")));

                // adding to tags list
                reg.add(t);
            } while (c.moveToNext());
        }
        return reg;
    }

    public ArrayList<String> ArrayCep(){
        ArrayList<String> reg = new ArrayList<String>();
        String selectQuery = "SELECT nomcepage FROM cepage";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String t = c.getString((c.getColumnIndex("nomcepage")));

                // adding to tags list
                reg.add(t);
            } while (c.moveToNext());
        }
        return reg;
    }

    public Vin createWine(String name, String cep, String reg, String coul) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase sc = this.getReadableDatabase();

        int ce=getIdCepage(cep);
        int re=getIdRegion(reg);

        ContentValues values = new ContentValues();
        values.put("nomappellation", name);
        values.put("idregion", re);

        // insert row

        db.insert("appellation", null, values);

        int apl=getIdAppellation(name);

        values = new ContentValues();
        values.put("couleur", coul);
        values.put("idcepage", ce);
        values.put("idapp", apl);

        db.insert("vin", null, values);

        int vin = getIdVin(apl);

        values=new ContentValues();
        values.put("volume", 1);
        values.put("idvin", vin);

        db.insert("bouteille", null, values);
        Vin a = new Vin();
        a.setCouleur(coul);
        a.setRegion(reg);
        a.setCepage(cep);
        a.setAppellation(name);
        a.setIdvin(vin);
        a.setIdbouteille(getIdVin(vin));
        return a;
    }

    private int getIdVin(int apl) {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "Select idvin from region where idapp like "+apl;
            Cursor c = db.rawQuery(selectQuery, null);

            //looping through all rows and checking name
            if(c.moveToFirst()) {
                do {
                    {
                        if(c.getInt(c.getColumnIndex("idapp"))== apl) {
                            return c.getInt(c.getColumnIndex("idvin"));
                        }
                    }
                } while(c.moveToNext());
            }
            return 1;
        }

    /*

    ///TODO
    //Adding new Wine
    public long createVin(Vin vin, Cave cave) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("couleur", vin.getCouleur());
        values.put("idcepage", vin.getCepage());
        values.put("idapp", vin.getNom());

        // insert row

        long tag = db.insert("vin", null, values);

        //get idcave

        int caveid = getCaveId(cave.getName());

        ContentValues content = new ContentValues();
        content.put("idbouteille", tag);
        content.put("idcave", caveid);
        content.put("quantite", bout);

    }

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/

}
