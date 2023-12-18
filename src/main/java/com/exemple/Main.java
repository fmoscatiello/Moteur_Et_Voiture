package com.exemple;

import com.exemple.model.Couleur;
import com.exemple.model.Moteur;
import com.exemple.model.Voiture;
import com.exemple.service.MoteurService;
import com.exemple.service.VoitureService;

import java.util.Map;

public class Main {

    /**
     * methode pour retourner la liste de moteurs dans la console
     */
    public static void testMoteur(){
        MoteurService.testLecture();
    }

    /**
     * methode pour retourner la liste des voitures supérieure à la puissance renseignée en parametre
     * @param puissLimit : puissance limite en paramètre
     */
    public static void testFiltreMoteurSup(int puissLimit){
        MoteurService.testFiltreMoteurSup(puissLimit);
    }

    /**
     * methode pour creer un nouveau moteur dans la liste
     */
    public static void testCreationMoteur(){
        Moteur moteur = new Moteur();
        moteur.setModel("PSA-1256");
        moteur.setKilometrage(132698.1f);
        moteur.setPuissance(299);
        MoteurService.testCreationMoteur(moteur);
    }

    /**
     * methode pour mettre à jour les données d'un moteur deja existant
     */
    public static void testUpdateMoteur(){
        Moteur moteur = new Moteur(1001,"Ford",12300f,250);
        MoteurService.testUpdateMoteur(moteur);
    }

    /**
     * methode pour supprimer le moteur de la liste à partir de son id
     * @param id{Integer} , id moteur à supprimer
     */
    public static void testDeleteMoteur(Integer id){
        MoteurService.testDeleteMoteur(id);
    }

    /**
     * methode pour retourner les données du moteur à partir de son id
     * @param id{Integer} : id du moteur à trouver
     * @return le moteur trouvé
     */
    public static Moteur testFindMoteurById(Integer id){
       return MoteurService.testFindMoteurById(id);
    }

    /**
     * methode pour retourner la liste de voitures dans la console
     */
    public static void testVoiture(){
        VoitureService.testLecture();
    }

    /**
     * methode pour creer une nouvelle voiture dans la liste
     */
    public static void testCreationVoiture(){
        Voiture voiture = new Voiture();
        voiture.setModele("testVoiture");
        voiture.setCouleur(Couleur.G);
        voiture.setMoteur(testFindMoteurById(950));
        VoitureService.testCreationVoiture(voiture);
    }

    public static void testDeleteVoiture(Integer id){
        VoitureService.testDeleteVoiture(id);
    }

    public static void testUpdateVoiture(){
        Voiture voiture = new Voiture();
        voiture.setVoiture_id(1003);
        voiture.setModele("Updated Car");
        voiture.setCouleur(Couleur.Bl);
        voiture.setMoteur(MoteurService.testFindMoteurById(55));

        VoitureService.testUpdateVoiture(voiture);
    }

    public static String camelCase(String input) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))&&i>0){
                result += " ";
            }
            result += input.charAt(i);
        }
        result = result.strip();
        return result;
    }




    public static void main(String[] args){
        System.out.println("Demarrage de mon application!");
        //testCreationMoteur();
        //testMoteur();
        //testUpdateMoteur();
        //testMoteur();
        //testDeleteMoteur(1001);
        //testMoteur();
        //testFiltreMoteurSup(650);
        //testVoiture();
        //testDeleteMoteur(5);
        //testCreationVoiture();
       //testDeleteVoiture(1004);
        //testUpdateVoiture();
        System.out.println(camelCase("camelCasing"));

    }



}
