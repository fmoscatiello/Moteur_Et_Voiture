package com.exemple.service;
import com.exemple.config.MonSingloton;
import com.exemple.model.Voiture;
import java.util.List;

/**
 * Classe service des elements voitures contenant la manipulation de mes objets
 */
public class VoitureService {
    /**
     * afficher la liste des voitures
     */
    public static void testLecture(){
        System.out.println("=============== Test lecture Voiture ================");
        System.out.println("===findAll()===");
        List<Voiture> voitures = MonSingloton.getInstance().getVoitureDao().findAll();
        for (Integer i = 0; i < voitures.size(); i++) {
            System.out.println(voitures.get(i));
        }
    }
    /**
     * Cree une nouvelle voiture et l'affiche ensuite dans la console
     * @param voiture, voiture à ajouter à la BDD
     */
    public static void testCreationVoiture(Voiture voiture){
        System.out.println("=============== Ajout d'une nouvelle voiture ================");
        System.out.println(MonSingloton.getInstance().getVoitureDao().create(voiture));
    }
    /**
     * modifie la voiture existante , ses données hors Id
     * @param voiture , voiture à modifier dans la liste
     */
    public static void testUpdateVoiture(Voiture voiture){
        System.out.println("============ Modification de la voiture numéro "+voiture.getVoiture_id()+" =============");
        MonSingloton.getInstance().getVoitureDao().update(voiture);
    }
    /**
     * Supprime la voiture selectionnée via son id_voiture
     * affiche la voiture supprimée en console
     * @param id , id de la voiture à supprimer
     */
    public static void testDeleteVoiture(int id){
        System.out.println("============ Suppression de la voiture numéro "+id+" =============");
        System.out.println(MonSingloton.getInstance().getVoitureDao().delete(id));
    }

}
