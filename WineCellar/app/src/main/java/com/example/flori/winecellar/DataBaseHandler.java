package com.example.flori.winecellar;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WineManager";

    // Contacts table name
    private static final String TABLE_USER = "utilisateur";
    private static final String TABLE_CAVE = "cavevin";
    private static final String TABLE_BOUTEILLE = "bouteille";
    private static final String TABLE_WINE = "vin";
    private static final String TABLE_CEPAGE = "cepage";
    private static final String TABLE_APL = "appellation";
    private static final String TABLE_PAYS = "pays";
    private static final String TABLE_REGION = "region";
    private static final String TABLE_DOMAINE = "domaine";
    private static final String TABLE_CONTENIR = "contenir";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    ///TODO : Supprimer constraints FK, mettre dans CREATE TABLE /!\
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "create table utilisateur\n" +
                "(\n" +
                "\tpseudo varchar(20) not null,\n" +
                "\tnom varchar(20) not null,\n" +
                "\tprenom varchar(20) not null,\n" +
                "\tdatenaiss date not null,\n" +
                "\tmdp bytea not null,\n" +
                "\tdateinscription date default CURRENT_TIMESTAMP not null,\n" +
                "\temail varchar not null,\n" +
                "\tville varchar(30),\n" +
                "\tidutilisateur serial not null\n" +
                "\t\tconstraint utilisateur__pk\n" +
                "\t\t\tprimary key,\n" +
                "\tsalt bytea\n" +
                ")";
        db.execSQL(CREATE_USER_TABLE);


        String CREATE_INDEX_USER = "create unique index utilisateur_idutilisateur_uindex\n" +
                "\ton utilisateur (idutilisateur)";
        db.execSQL(CREATE_INDEX_USER);

        String CREATE_CAVE_VIN = "create table cavevin\n" +
                "(\n" +
                "\tidcave serial not null\n" +
                "\t\tconstraint cavevin_pkey\n" +
                "\t\t\tprimary key,\n" +
                "\tnomcave varchar not null,\n" +
                "\tvisibilite boolean default true not null,\n" +
                "\tdescription varchar default 'La description de ma cave à vins'\n" +
                "\tidutilisateur integer\n" +
                "\t\tconstraint cavevin_utilisateur_idutilisateur_fk\n" +
                "\t\t\treferences utilisateur\n" +
                ")";
        db.execSQL(CREATE_CAVE_VIN);

        String CREATE_BOUTEILLE_TABLE = "create table bouteille\n" +
                "(\n" +
                "\tidbouteille serial not null\n" +
                "\t\tconstraint bouteille_pkey\n" +
                "\t\t\tprimary key,\n" +
                "\tvolume integer not null,\n" +
                "\tidvin integer,\n" +
                "\tcode varchar,\n" +
                "\tmillesime varchar(20)\n" +
                ")";
        db.execSQL(CREATE_BOUTEILLE_TABLE);

        String CREATE_INDEX_BOUTEILLE = "create unique index bouteille_idbouteille_uindex\n" +
                "\ton bouteille (idbouteille)";
        db.execSQL(CREATE_INDEX_BOUTEILLE);

        String CREATE_VIN_TABLE = "create table vin\n" +
                "(\n" +
                "\tidvin serial not null\n" +
                "\t\tconstraint vin_pkey\n" +
                "\t\t\tprimary key,\n" +
                "\tcouleur varchar(20) not null,\n" +
                "\tiddomaine integer,\n" +
                "\tidcepage integer,\n" +
                "\tidapp integer,\n" +
                "\tdegres double precision\n" +
                ")";
        db.execSQL(CREATE_VIN_TABLE);

        String CREATE_VIN_INDEX = "create unique index vin_idvin_uindex\n" +
                "\ton vin (idvin)";
        db.execSQL(CREATE_VIN_INDEX);

        String CREATE_CONSTRAINT_FK_BOUTEILLE = "alter table bouteille\n" +
                "\tadd constraint bouteille_vin_idvin_fk\n" +
                "\t\tforeign key (idvin) references vin";
        db.execSQL(CREATE_CONSTRAINT_FK_BOUTEILLE);

        String CREATE_TABLE_CEPAGE = "create table cepage\n" +
                "(\n" +
                "\tidcepage serial not null\n" +
                "\t\tconstraint table_name_pkey\n" +
                "\t\t\tprimary key,\n" +
                "\tnomcepage varchar not null\n" +
                ")";
        db.execSQL(CREATE_TABLE_CEPAGE);

        String CREATE_INDEX_CEPAGE = "create unique index table_name_idcepage_uindex\n" +
                "\ton cepage (idcepage)";
        db.execSQL(CREATE_INDEX_CEPAGE);

        String CREATE_CONSTRAINT_FK_VIN = "alter table vin\n" +
                "\tadd constraint vin_cepage_idcepage_fk\n" +
                "\t\tforeign key (idcepage) references cepage\n" +
                ";";
        db.execSQL(CREATE_CONSTRAINT_FK_VIN);

        String CREATE_TABLE_TYPAPL = "create table typeappellation\n" +
                "(\n" +
                "\tnomta varchar not null,\n" +
                "\tidta serial not null\n" +
                "\t\tconstraint typeappellation_pkey\n" +
                "\t\t\tprimary key\n" +
                ")";
        db.execSQL(CREATE_TABLE_TYPAPL);

        String CREATE_INDEX_TYPAPL = "create unique index typeappellation_idta_uindex\n" +
                "\ton typeappellation (idta)";
        db.execSQL(CREATE_INDEX_TYPAPL);

        String CREATE_TABLE_APL = "create table appellation\n" +
                "(\n" +
                "\tidapp serial not null\n" +
                "\t\tconstraint appellation_pkey\n" +
                "\t\t\tprimary key,\n" +
                "\tnomappellation varchar not null,\n" +
                "\tidtype integer\n" +
                "\t\tconstraint appellation_typeappellation_idta_fk\n" +
                "\t\t\treferences typeappellation,\n" +
                "\tidregion integer\n" +
                ")";
        db.execSQL(CREATE_TABLE_APL);

        String CREATE_INDEX_APL = "create unique index appellation_idapp_uindex\n" +
                "\ton appellation (idapp)";
        db.execSQL(CREATE_INDEX_APL);

        String CREATE_CONSTRAINT_FK_VAPL = "alter table vin\n" +
                "\tadd constraint vin_appellation_idapp_fk\n" +
                "\t\tforeign key (idapp) references appellation";
        db.execSQL(CREATE_CONSTRAINT_FK_VAPL);

        String CREATE_TABLE_PAYS = "create table pays\n" +
                "(\n" +
                "\tnompays varchar not null,\n" +
                "\tidpays serial not null\n" +
                "\t\tconstraint pays_pkey\n" +
                "\t\t\tprimary key\n" +
                ")";
        db.execSQL(CREATE_TABLE_PAYS);

        String CREATE_INDEX_PAYS = "create unique index pays_idpays_uindex\n" +
                "\ton pays (idpays)";
        db.execSQL(CREATE_INDEX_PAYS);

        String CREATE_TABLE_REGION = "create table region\n" +
                "(\n" +
                "\tidregion serial not null\n" +
                "\t\tconstraint region_pkey\n" +
                "\t\t\tprimary key,\n" +
                "\tnomregion varchar not null,\n" +
                "\tidpays integer\n" +
                "\t\tconstraint region_pays_idpays_fk\n" +
                "\t\t\treferences pays\n" +
                ")";
        db.execSQL(CREATE_TABLE_REGION);

        String CREATE_INDEX_REGION = "create unique index region_idregion_uindex\n" +
                "\ton region (idregion)";
        db.execSQL(CREATE_INDEX_REGION);

        String CREATE_CONSTRAINT_FK_APL = "alter table appellation\n" +
                "\tadd constraint appellation_region_idregion_fk\n" +
                "\t\tforeign key (idregion) references region";
        db.execSQL(CREATE_CONSTRAINT_FK_APL);

        String CREATE_TABLE_DOMAINE = "create table domaine\n" +
                "(\n" +
                "\tnomdomaine varchar not null,\n" +
                "\tiddomaine serial not null\n" +
                "\t\tconstraint domaine_pkey\n" +
                "\t\t\tprimary key,\n" +
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

        String CREATE_CONSTRAINT_FK_DOMAIN = "alter table vin\n" +
                "\tadd constraint vin_domaine_iddomaine_fk\n" +
                "\t\tforeign key (iddomaine) references domaine";
        db.execSQL(CREATE_CONSTRAINT_FK_DOMAIN);

        String CREATE_TABLE_COMMENTER = "create table commenter\n" +
                "(\n" +
                "\tidbouteille integer not null\n" +
                "\t\tconstraint commenter_bouteille_idbouteille_fk\n" +
                "\t\t\treferences bouteille,\n" +
                "\tidutilisateur integer not null\n" +
                "\t\tconstraint commenter_utilisateur_idutilisateur_fk\n" +
                "\t\t\treferences utilisateur,\n" +
                "\tidcommentaire serial not null,\n" +
                "\tdatecommentaire date default ('now'::text)::date not null,\n" +
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
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

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

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
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
    }

}
