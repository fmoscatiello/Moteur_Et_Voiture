package com.exemple.dao;

import com.exemple.config.MonSingloton;
import com.exemple.exception.OperationException;
import com.exemple.model.Moteur;
import com.exemple.model.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoteurDaoJdbc implements MoteurDao{
    /**
     * recuperer tous les elements de mon stockage
     *
     * @return Liste de mes elements stockés
     */
    @Override
    public List<Moteur> findAll() {
        //liste pour stocker les données des moteurs stockées en BDD
        List<Moteur> moteurs = new ArrayList<>();
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "SELECT moteur_id,model,kilometrage,puissance FROM moteur";
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
                Integer moteur_id = rs.getInt("moteur_id");
                String model = rs.getString("model");
                Float kilometrage = rs.getFloat("kilometrage");
                Integer puissance = rs.getInt("puissance");

                //creation de l'objet moteur
                Moteur moteur = new Moteur(moteur_id,model,kilometrage,puissance);
                //ajout à la liste de recuperation
                moteurs.add(moteur);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return moteurs;
    }

    /**
     * recupere un objet specifique par son identifiant
     *
     * @param id , identifiant de l'objet recherché
     * @return l'objet qui porte cet identifiant
     */
    @Override
    public Moteur findById(Integer id) {
        Moteur moteur = null;
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "SELECT moteur_id,model,kilometrage,puissance FROM moteur WHERE moteur_id = ?";
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

            while (rs.next()){
                //traiter mon resultat de requete : correspondance de typage
                Integer moteur_id = rs.getInt("moteur_id");
                String model = rs.getString("model");
                Float kilometrage = rs.getFloat("kilometrage");
                Integer puissance = rs.getInt("puissance");

                //creation de l'objet moteur
                 moteur = new Moteur(moteur_id,model,kilometrage,puissance);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return moteur;
    }

    /**
     * enregistrer dans mon stockage un nouvel objet
     *
     * @param newMoteur , le nouvel objet enregistré
     * @return l'objet enregistré
     */
    @Override
    public Moteur create(Moteur newMoteur) {
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "INSERT INTO moteur(model, kilometrage, puissance) VALUES (?,?,?)";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;

        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,newMoteur.getModel());
            ps.setFloat(2,newMoteur.getKilometrage());
            ps.setInt(3,newMoteur.getPuissance());

            //retourne le nombre de lignes qui a été alteré dans la BDD
            //ici, comme on insere un seul element, rows = 1
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new OperationException("Erreur à l'enregitrement de l'objet : " + newMoteur);
            }else {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Integer moteur_id = rs.getInt(1);
                    newMoteur.setMoteur_id(moteur_id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return newMoteur;
    }

    /**
     * Mettre à jour l'objet fourni en paramètre dans mon stockage
     *
     * @param moteur , l'objet à mettre à jour
     */
    @Override
    public void update(Moteur moteur) {
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "UPDATE moteur SET model=? , kilometrage=?, puissance=? WHERE moteur_id=?";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;
        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, moteur.getModel());
            ps.setFloat(2,moteur.getKilometrage());
            ps.setInt(3,moteur.getPuissance());
            ps.setInt(4,moteur.getMoteur_id());
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new OperationException("Erreur à la modification de l'objet : " + moteur);
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
    public Moteur delete(Integer id) {
        Moteur deletedMoteur=new Moteur();
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "DELETE FROM moteur WHERE moteur_id=?";
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;

        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery);
            //recupère l'objet supprimé avant de le supprimer
            deletedMoteur= findById(id);
            //Remplace le "?" dans la query par l'id Moteur passé en paramètre
            ps.setInt(1,id);
            MonSingloton.getInstance().getVoitureDao().retirerMoteurDeVoiture(id);
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

        return deletedMoteur;
    }

    /**
     * Permet de récupérer la liste des moteurs ayant une
     * puissance supéreieur à la puissance limite
     *
     * @param puissanceLimit , la puissance minimale sur laquelle la recherche se fera
     * @return {List} de tous les moteurs ayant une puissance supérieur à la puissanceLimit
     */
    @Override
    public List<Moteur> findByPuissanceSup(Integer puissanceLimit) {
        List<Moteur> moteurs = new ArrayList<>();
        Connection connection = null;
        //ATTENTION! ne JAMAIS faire de SELECT *
        String myQuery = "SELECT moteur_id,model,kilometrage,puissance FROM moteur WHERE puissance > ?";;
        //PreparedStatement : chaine d'instruction pré-compilée avant l'envoi
        PreparedStatement ps = null;
        //recupère le resultat de la requete
        ResultSet rs = null;
        try {
            connection = MonSingloton.getInstance().getConnection();
            ps = connection.prepareStatement(myQuery);
            ps.setInt(1,puissanceLimit);
            // execute la requet et récupère le resultat de celle-ci
            rs = ps.executeQuery();
            //parcours du resultat
            while (rs.next()){
                //traiter mon resultat de requete : correspondance de typage
                Integer moteur_id = rs.getInt("moteur_id");
                String model = rs.getString("model");
                Float kilometrage = rs.getFloat("kilometrage");
                Integer puissance = rs.getInt("puissance");

                //creation de l'objet moteur
                Moteur moteur = new Moteur(moteur_id,model,kilometrage,puissance);
                //ajout à la liste de recuperation
                moteurs.add(moteur);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MonSingloton.getInstance().deconnexion(ps,rs);
        }
        return moteurs;
    }
}
