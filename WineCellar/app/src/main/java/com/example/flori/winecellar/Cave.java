package com.example.flori.winecellar;

/**
 * Created by Utilisateur on 18/01/2018.
 */

public class Cave {

    protected String Red="0", White="0", Pink="0", Nb="0", Name, Date="";

    protected int id;

    public Cave(String n)
    {
        Name=n;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRed() {
        return Red;
    }

    public void setRed(String red) {
        Red = red;
    }

    public String getWhite() {
        return White;
    }

    public void setWhite(String white) {
        White = white;
    }

    public String getPink() {
        return Pink;
    }

    public void setPink(String pink) {
        Pink = pink;
    }

    public String getNb() {
        return Nb;
    }

    public void setNb(String nb) {
        Nb = nb;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
