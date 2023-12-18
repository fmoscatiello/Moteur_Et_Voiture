package com.exemple.config;

import com.exemple.dao.*;

import java.sql.*;

/**
 * MonSingloton , classe qui permet d'instancier un singloton
 * qui est l'élement de communication unique de mon application.
 * Il stocke sa propre instanciation singloton
 */
public class MonSingloton {

    // un attribut qui est sa propre instanciation
    private static MonSingloton singloton = null;
    private final MoteurDao moteurDao = new MoteurDaoJdbc();
    private final VoitureDao voitureDao = new VoitureDaoJdbc();
    //attribut de connexion , pour acceder à la BDD
    private Connection connection;
    private String url;
    private String username;
    private String password;


    //constructeur privé
    private MonSingloton(){

    };
    //methode de classe permettant d'instancier un seul et UNIQUE singloton de mon application
    public static MonSingloton getInstance(){
        if (singloton==null){
            singloton=new MonSingloton();
        }
        return singloton;
    }

    //getter pour retourner le moteurDao
    public MoteurDao getMoteurDao() {
        return this.moteurDao;
    }

    //getter pour retourner la voitureDao
    public VoitureDao getVoitureDao() {
        return this.voitureDao;
    }

    /**
     * methode qui permet de renseigner à monSingloton des informations
     * de connexion à la BDD
     */
    private void ajoutInfoDeConnection(){
        this.url="jdbc:mysql://localhost:3306/formation-jdbc";
        this.username="root";
        this.password="root";
    }

    /**
     * Methode qui permet de charger le driver de la base de donnée MySQL
     */
    private void chargerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * methode qui permet de se connecter à la BDD mySQL
     * avec les infos de connexions
     * @throws SQLException , si on ne peut pas communiquer avec la BDD
     * @return une connexion
     */
    public Connection getConnection() throws SQLException {

        // charger mes infos de connexion
        ajoutInfoDeConnection();
        // charger mon Driver afin de communiquer avec la BDD
        chargerDriver();
        //creer ma connexion à partir de ses infos
        //le DriverManager est l'interprete entre JAVA et mySQL
        this.connection = DriverManager.getConnection(
                this.url,
                this.username,
                this.password);

        return this.connection;
    };

    /**
     * methode qui permet de se deconnecter de la BDD
     */
    public void deconnexion(PreparedStatement ps , ResultSet rs){
        try {
            //fermeture des ps et rs AVANT la fermeture de la connexion globale
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
