package com.exemple.dao;

import com.exemple.model.Voiture;

import java.util.List;

public interface VoitureDao extends Dao<Voiture,Integer>{
    //implementé mon CRUD

    List<Voiture> findAllByIdMoteur(Integer moteur_id);
    public void retirerMoteurDeVoiture(Integer moteur_id);

}

