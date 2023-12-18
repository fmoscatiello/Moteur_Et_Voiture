package com.exemple.dao;

import com.exemple.model.Moteur;

import java.util.List;

/**
 * Interface Dao pour les objets Moteur qui herite de l'interface générique Dao
 */
public interface MoteurDao extends Dao<Moteur,Integer> {
    //implémenté mon CRUD

    //Peut ajouter des methodes specifiques

    /**
     * Permet de récupérer la liste des moteurs ayant une
     * puissance supéreieur à la puissance limite
     * @param puissanceLimit , la puissance minimale sur laquelle la recherche se fera
     * @return {List} de tous les moteurs ayant une puissance supérieur à la puissanceLimit
     */
 List<Moteur> findByPuissanceSup(Integer puissanceLimit);
}
