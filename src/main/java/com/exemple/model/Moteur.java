package com.exemple.model;
import com.exemple.exception.KilometrageException;
import com.exemple.exception.PuissanceException;

import java.util.StringJoiner;

/**
 * Classe Moteur qui représente un moteur de voiture
 * avec ses propriétés : moteur_id, model, kilometrage et puissance.
 */


public class Moteur {
// attributs
    private Integer moteur_id;
    private  String model;
    private Float kilometrage;
    private Integer puissance;

    // constructeur
    public Moteur() {
        super();
    }
    public Moteur(Integer moteur_id, String model, Float kilometrage, Integer puissance) {
        this.moteur_id = moteur_id;
        this.model = model;
        this.kilometrage = kilometrage;
        this.puissance = puissance;
    }

    public Integer getMoteur_id() {
        return this.moteur_id;
    }

    public void setMoteur_id(Integer moteur_id) {
        this.moteur_id = moteur_id;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getKilometrage() {
        return this.kilometrage;
    }

    public void setKilometrage(Float kilometrage) {
        if(kilometrage >=0.0f){
            this.kilometrage = kilometrage;
        }
        else {
            //gestion des erreurs, km doit etre >=0
            //1 - envoi d'un message d'erreur
            System.out.println("Erreur sur la valeur du kilometrage : "+kilometrage);
            throw new KilometrageException("Erreur sur la valeur du kilometrage : "+kilometrage);
            //2- tant pis pour toi
            //this.kilometrage = 0.0f;
        }
    }

    public Integer getPuissance() {
        return this.puissance;
    }

    public void setPuissance(Integer puissance) {
        if (puissance >= 0) {
            this.puissance = puissance;
        }
        else {
            //gestion des erreurs, km doit etre >=0
            //1 - envoi d'un message d'erreur
            System.out.println("La puissance doit etre positive: " + puissance);
            throw new PuissanceException("La puissance doit etre positive: " + puissance);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Moteur.class.getSimpleName() + "{", "}")
                .add("moteur_id=" + moteur_id)
                .add("model='" + model + "'")
                .add("kilometrage=" + kilometrage)
                .add("puissance=" + puissance)
                .toString();
    }

}
