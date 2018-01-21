package com.example.flori.winecellar;

/**
 * Created by Utilisateur on 18/01/2018.
 */

public class Vin {
    protected String appellation, cepage, region, couleur;

    protected int idvin, idbouteille;

    protected int qte;

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getIdvin() {
        return idvin;
    }

    public void setIdvin(int idvin) {
        this.idvin = idvin;
    }

    public int getIdbouteille() {
        return idbouteille;
    }

    public void setIdbouteille(int idbouteille) {
        this.idbouteille = idbouteille;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getCepage() {
        return cepage;
    }

    public void setCepage(String cepage) {
        this.cepage = cepage;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Vin() {}
}
