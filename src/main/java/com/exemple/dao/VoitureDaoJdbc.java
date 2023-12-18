package com.exemple.dao;

import com.exemple.config.MonSingloton;
import com.exemple.exception.OperationException;
import com.exemple.model.Couleur;
import com.exemple.model.Moteur;
import com.exemple.model.Voiture;
import com.exemple.dao.MoteurDaoJdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureDaoJdbc implements VoitureDao{
    /**
     * recuperer tous les elements de mon stockage
     *
     * @return Liste de mes elements stockés, ici la liste coplete des voitures dans la BDD
     */
    @Override
    public List<Voiture> findAll() {
        //liste pour stocker les données des voitures stockées en BDD
        List<Voiture> voitures = new ArrayList<>();
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "SELECT voiture_id,model,couleur,moteur_id FROM voiture";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;
        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery);
            // execute la requet et récupère le resultat de celle-ci
            rs = ps.executeQuery();
            //parcours du resultat
            while (rs.next()){
                //traiter mon resultat de requete : correspondance de typage
                Integer voiture_id = rs.getInt("voiture_id");
                String model = rs.getString("model");
                Couleur couleur = Couleur.valueOfLabel(rs.getString("couleur"));
                Integer moteur_id = rs.getInt("moteur_id");

                Moteur moteur = MonSingloton.getInstance().getMoteurDao().findById(moteur_id);

                //creation de l'objet voiture
                Voiture voiture = new Voiture(voiture_id,model,couleur,moteur);
                //ajout à la liste de recuperation
                voitures.add(voiture);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return voitures;
    }

    /**
     * recupere un objet specifique par son identifiant
     *
     * @param id , identifiant de l'objet recherché
     * @return l'objet qui porte cet identifiant
     */
    @Override
    public Voiture findById(Integer id) {
        //objet voiture à retourner
        Voiture voiture = null;
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "SELECT voiture_id ,model ,couleur ,moteur_id FROM voiture WHERE voiture_id = ?";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery);
            //à la premiere inconnue 1, remplacer le ? dans la requete par la valeur id
            ps.setInt(1,id);
            // execute la requete et récupère le resultat de celle-ci
            rs = ps.executeQuery();

            if (rs.next()){
                //traiter mon resultat de requete : correspondance de typage
                Integer voiture_id = rs.getInt("voiture_id");
                String model = rs.getString("model");
                Couleur couleur = Couleur.valueOfLabel(rs.getString("couleur"));
                Integer moteur_id = rs.getInt("moteur_id");
                Moteur moteur = MonSingloton.getInstance().getMoteurDao().findById(moteur_id);

                //creation de l'objet voiture
                voiture = new Voiture(voiture_id,model,couleur,moteur);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return voiture;
    }

    /**
     * enregistrer dans mon stockage un nouvel objet
     *
     * @param newVoiture , le nouvel objet enregistré
     * @return l'objet enregistré
     */
    @Override
    public Voiture create(Voiture newVoiture) {
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "INSERT INTO voiture(model, couleur, moteur_id) VALUES (?,?,?)";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;

        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,newVoiture.getModele());
            ps.setString(2,Couleur.nomCouleurFromLabel(newVoiture.getCouleur()));
            ps.setInt(3,newVoiture.getMoteur().getMoteur_id());

            //retourne le nombre de lignes qui a été alteré dans la BDD
            //ici, comme on insere un seul element, rows = 1
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new OperationException("Erreur à l'enregitrement de l'objet : " + newVoiture);
            }else {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Integer voiture_id = rs.getInt(1);
                    newVoiture.setVoiture_id(voiture_id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return newVoiture;
    }

    /**
     * Mettre à jour l'objet fourni en paramètre dans mon stockage
     *
     * @param Updatedvoiture , l'objet à mettre à jour
     */
    @Override
    public void update(Voiture Updatedvoiture) {

        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "UPDATE voiture SET model=? , couleur=?, moteur_id=? WHERE voiture_id=?";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;
        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery, Statement.RETURN_GENERATED_KEYS);
            //recupere toutes les datas de la voiture avant la modif dans la BDD;
            //Voiture voitureInitialeAvantModif = MonSingloton.getInstance().getVoitureDao().findById(Updatedvoiture.getVoiture_id());

            ps.setString(1, Updatedvoiture.getModele());
            ps.setString(2, Couleur.nomCouleurFromLabel(Updatedvoiture.getCouleur()));
            if (Updatedvoiture.getMoteur() == null) {
                ps.setInt(3,-1);
            }
            else{
                ps.setInt(3,Updatedvoiture.getMoteur().getMoteur_id());
            }
            ps.setInt(4,Updatedvoiture.getVoiture_id());
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new OperationException("Erreur à la modification de l'objet : " + Updatedvoiture);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
    }

    /**
     * supprimer de mon stockage l'objet qui porte l'identifiant fournis
     *
     * @param id , l'identifiant de l'objet à supprimer
     * @return l'objet supprimé
     */
    @Override
    public Voiture delete(Integer id) {
        Voiture deletedVoiture=new Voiture();
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "DELETE FROM voiture WHERE voiture_id = ?";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;

        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery);
            //recupère l'objet supprimé avant de le supprimer
            deletedVoiture= findById(id);
            //remplace le ? dans la query par l'id Moteur passé en paramètre
            ps.setInt(1,id);
            //retourne le nombre de lignes qui a été alteré dans la BDD
            //ici, comme on insere un seul element, rows = 1
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new OperationException("Erreur à la suppression du moteur avec l'id: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }

        return deletedVoiture;
    }

    /**
     * Retourne toutes les voitures correspondant à un moteur donné en paramètre
     * @param moteur_id_a_trouver
     * @return voitures , la liste des voitures correspondant à l'id moteur passé en paramètre
     *
     */
    @Override
    public List<Voiture> findAllByIdMoteur(Integer moteur_id_a_trouver) {
        List<Voiture> voitures = new ArrayList<>();
        Moteur moteurAtrouver  = new Moteur();
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "SELECT voiture_id,model,couleur,moteur_id FROM voiture WHERE moteur_id = ?";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;
        //à la premiere inconnue 1, remplacer le ? dans la requete par la valeur id
        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery);
            ps.setInt(1,moteur_id_a_trouver);
            // execute la requete et récupère le resultat de celle-ci
            rs = ps.executeQuery();

            while (rs.next()){
                //traiter mon resultat de requete : correspondance de typage
                Integer voiture_id = rs.getInt("voiture_id");
                String model = rs.getString("model");
                Couleur couleur = Couleur.valueOfLabel(rs.getString("couleur"));
                Integer moteur_id = rs.getInt("moteur_id");
                Moteur moteur = MonSingloton.getInstance().getMoteurDao().findById(moteur_id_a_trouver);

                //creation de l'objet voiture
                Voiture voiture = new Voiture(voiture_id,model,couleur,moteur);

                //ajout dans la liste
                voitures.add(voiture);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }

        return voitures;
    }

    /**
     * remplace toutes les moteurs des voitures liées à moteur_id par "null"
     * ceci afin de pouvoir plus tard supprimer un moteur dans la BDD
     * Sans avoir la contrainte de clé étrangère
     * @param moteur_id
     */
    @Override
    public void retirerMoteurDeVoiture(Integer moteur_id) {
        List<Voiture> findVoitureByIdMoteur = MonSingloton
                .getInstance()
                .getVoitureDao()
                .findAllByIdMoteur(moteur_id);
        for (Voiture voiture : findVoitureByIdMoteur) {
            voiture.setMoteur(null);
            MonSingloton.getInstance().getVoitureDao().update(voiture);
        }
    }

}
