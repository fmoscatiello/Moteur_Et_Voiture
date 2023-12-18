package com.exemple.model;

import java.util.StringJoiner;

/**
 * Classe Voiture qui contient un nom de modèle, une couleur , ainsi qu'un moteur
 */
public class Voiture{
    private Integer voiture_id;
    private String model;
    private Couleur couleur;
    private Moteur moteur;
    public Voiture() {
    }

    public Voiture(Integer voiture_id,String model, Couleur couleur, Moteur moteur) {
        this.voiture_id=voiture_id;
        this.model = model;
        this.couleur = couleur;
        this.moteur = moteur;
    }

    public Integer getVoiture_id() {
        return this.voiture_id;
    }

    public void setVoiture_id(Integer voiture_id) {
        this.voiture_id = voiture_id;
    }

    public String getModele() {
        return this.model;
    }

    public void setModele(String modele) {
        this.model = modele;
    }

    public Couleur getCouleur() {
        return this.couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Moteur getMoteur() {
        return this.moteur;
    }

    public void setMoteur(Moteur moteur) {
        this.moteur = moteur;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Voiture.class.getSimpleName() + "[", "]")
                .add("voiture_id=" + voiture_id)
                .add("modele='" + model + "'")
                .add("couleur=" + couleur)
                .add("moteur=" + moteur)
                .toString();
    }
}
