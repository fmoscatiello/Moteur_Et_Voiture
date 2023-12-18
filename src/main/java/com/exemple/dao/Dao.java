package com.exemple.dao;

import java.util.List;

/**
 * Interface generique permettant d'imposer les methodes
 * de recuperation d'information des objets de mon application.
 * -TypeObjet : le type d'objet concerné.
 * - PrimaryKey : le type de clé primaire servant à identifier l'objet cible .
 */
public interface Dao<TypeObjet, PrimaryKey> {
    //Renseigner les contraintes de mon CRUD

    /**
     * recuperer tous les elements de mon stockage
     * @return Liste de mes elements stockés
     */
    List<TypeObjet> findAll();

    /**
     * recupere un objet specifique par son identifiant
     * @param id , identifiant de l'objet recherché
     * @return l'objet qui porte cet identifiant
     */
    TypeObjet findById(PrimaryKey id);

    /**
     * enregistrer dans mon stockage un nouvel objet
     * @param objet , le nouvek objet enregistré
     * @return l'objet enregistré
     */
    TypeObjet create(TypeObjet objet);

    /**
     * Mettre à jour l'objet fourni en paramètre dans mon stockage
     * @param objet , l'objet à mettre à jour
     */
    void update(TypeObjet objet);

    /**
     * supprimer de mon stockage l'objet qui porte l'identifiant fournis
     * @param id , l'identifiant de l'objet à supprimer
     * @return l'objet supprimé
     */
    TypeObjet delete(PrimaryKey id);
}
