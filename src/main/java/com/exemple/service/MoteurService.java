package com.exemple.service;
import com.exemple.config.MonSingloton;
import com.exemple.model.Moteur;
import java.util.List;

/**
 * Classe service des elements moteur contenant la manipulation de mes objets
 */
public class MoteurService {
    /**
     * afficher la liste des moteurs
     */
    public static void testLecture(){
        System.out.println("=============== Test lecture Moteur ================");
        System.out.println("===findAll()===");
        List<Moteur> moteurs = MonSingloton.getInstance().getMoteurDao().findAll();
        for (Integer i = 0; i < moteurs.size(); i++) {
            System.out.println(moteurs.get(i));
        }
    }

    /**
     * afficher la liste des moteurs > à la puissance limite
     * @param puissLimite puissance limite minimale
     */
    public static void testFiltreMoteurSup(Integer puissLimite){
        System.out.println("=============== Afficher les moteurs > "+ puissLimite +"chevaux ================");
        List<Moteur> moteurs = MonSingloton.getInstance().getMoteurDao().findByPuissanceSup(puissLimite);
        for (Integer i = 0; i < moteurs.size(); i++) {
            System.out.println(moteurs.get(i));
        }
    }

    /**
     * Cree un nouveau moteur et l'affiche ensuite dans la console
     * @param moteur moteur à ajouter à la BDD
     */
    public static void testCreationMoteur(Moteur moteur){
        System.out.println("=============== Ajout du nouveau moteur ================");
        System.out.println(MonSingloton.getInstance().getMoteurDao().create(moteur));
    }

    /**
     * modifie le moteur existant , ses données hors Id
     * @param moteur , moteur à modifier dans la liste
     */
    public static void testUpdateMoteur(Moteur moteur){
        System.out.println("============ Modification du nouveau moteur numéro "+moteur.getMoteur_id()+" =============");
        MonSingloton.getInstance().getMoteurDao().update(moteur);

    }

    /**
     * Supprime le moteur selectionné via son id_moteur
     * affiche le moteur supprimé en console
     * @param id , id du moteur à supprimer
     */
    public static void testDeleteMoteur(int id){
        System.out.println("============ Suppression du moteur numéro "+id+" =============");
        System.out.println(MonSingloton.getInstance().getMoteurDao().delete(id));
    }

    /**
     * retourne le moteur en fonction de l'id passé en paramètre
     * @param id , id en paramètre
     * @return le moteur en fonction de l'id passé en paramètre
     */
    public static Moteur testFindMoteurById(int id){
        System.out.println("============ Affichage du moteur numéro "+id+" =============");
        System.out.println(MonSingloton.getInstance().getMoteurDao().findById(id));
        return MonSingloton.getInstance().getMoteurDao().findById(id);
    }

}
