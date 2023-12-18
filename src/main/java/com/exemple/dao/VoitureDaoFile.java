package com.exemple.dao;

import com.exemple.config.MonSingloton;
import com.exemple.exception.OperationException;
import com.exemple.model.Couleur;
import com.exemple.model.Moteur;
import com.exemple.model.Voiture;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe qui permet de récuperer les info de Voiture
 * dans son stockage fichier
 */
public class VoitureDaoFile implements VoitureDao{

    /**
     * recuperer tous les elements de mon stockage
     *
     * @return Liste de mes elements stockés
     */
    @Override
    public List<Voiture> findAll() {
    return readFichierVoiture();
    }



    /**
     * recupere un objet specifique par son identifiant
     *
     * @param id , identifiant de l'objet recherché
     * @return l'objet qui porte cet identifiant
     */
    @Override
    public Voiture findById(Integer id) {
        Voiture voitureTrouve = null;
        List<Voiture> voitures = readFichierVoiture();
        for(Voiture voiture: voitures){
            if(Objects.equals(voiture.getVoiture_id(),id)){
                voitureTrouve=voiture;
                break;
            }
        }

        return voitureTrouve;
    }

    /**
     * enregistrer dans mon stockage un nouvel objet
     *
     * @param newVoiture , le nouvek objet enregistré
     * @return l'objet enregistré
     */
    @Override
    public Voiture create(Voiture newVoiture) {
        List<Voiture> voitures = readFichierVoiture();
        Integer id_max=0;

        for (Voiture voiture :voitures){
            if (voiture.getVoiture_id()>id_max){
                id_max=voiture.getVoiture_id();
            }
        }
        // incrementer l'id
        id_max++;

        newVoiture.setVoiture_id(id_max);
        voitures.add(newVoiture);
        writeFichierVoiture(voitures);
        return newVoiture;
    }



    /**
     * Mettre à jour l'objet fourni en paramètre dans mon stockage
     *
     * @param updateVoiture , l'objet à mettre à jour
     */
    @Override
    public void update(Voiture updateVoiture) {
        List<Voiture> voitures = readFichierVoiture();
        Integer index;
        Boolean find = false;
        for (index = 0; index < voitures.size(); index++) {
            if (Objects.equals(updateVoiture.getVoiture_id(),voitures.get(index).getVoiture_id())){
                find=true;
                break;
            }
        }
        if (find){
            voitures.set(index,updateVoiture);
            writeFichierVoiture(voitures);
        }else{
            throw new OperationException("Voiture n° : "
                    +updateVoiture.getVoiture_id()
                    +" n'a pas été trouvé pour la mise à jour");
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
        Voiture backupVoiture = null;
        List<Voiture> voitures = readFichierVoiture();
        Integer index;
        Boolean find = false;
        for (index = 0; index < voitures.size(); index++) {
            if (Objects.equals(id,voitures.get(index).getVoiture_id())){
                find=true;
                break;
            }
        }
        if (find){
            //la recherche a aboutie
            backupVoiture=voitures.get(index);
            voitures.remove(voitures.get(index));
            writeFichierVoiture(voitures);
        }else{
            throw new OperationException("Voiture n° : "
                    +id
                    +" n'a pas été trouvé pour la suppression");
        }
        return backupVoiture;
    }

    /**
     * Methode qui va lire le fichier de données voitures.csv
     * et récuperer les informations de voitures contenus à l'interieur
     * @return List<Voiture>
     */
    private List<Voiture> readFichierVoiture() {
        List<Voiture> voitures = new ArrayList<>();
        Path path = Paths.get("voitures.csv");
        FileReader myFileReader = null;
        // un element de memoire tampon pour la lecture
        BufferedReader myBufferedReader = null;
        String myLigne;
        Boolean header = true;
        try {
            myFileReader = new FileReader(path.toFile());
            myBufferedReader = new BufferedReader(myFileReader);
            while ((myLigne=myBufferedReader.readLine())!=null) {
                if (header) {
                    header = false;
                    continue;//passe à la boucle suivante
                }

                String[] items = myLigne.split(",");

                Voiture voiture = new Voiture();
                voiture.setVoiture_id(Integer.valueOf(items[0].strip()));
                voiture.setModele(items[1].strip());
                voiture.setCouleur(Couleur.valueOfLabel(items[2].strip()));
                //recuperer le moteur avec l'id_moteur == items[3](la 4eme colonne du CSV voitures.csv
                String csvMoteur_id = items[3].strip();
                //cas des moteurs absent null à gerer
                //si pas null:
                if(!Objects.equals(csvMoteur_id,"null")
                ||(!Objects.equals(csvMoteur_id,""))
                ){//"null" dans le csv pour pas de moteur
                    Integer moteur_id = Integer.valueOf(csvMoteur_id);
                    Moteur moteur = MonSingloton.getInstance().getMoteurDao().findById(moteur_id);
                    voiture.setMoteur(moteur);
                }
                // ajout de la voiture dans la liste
                //qu'elle ait un moteur ou pas (valeur null)
                voitures.add(voiture);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                assert myBufferedReader != null;
                myBufferedReader.close();
                myFileReader.close();
            } catch (IOException e) {
                //si on arrive pas à clore le fichier, on coupe tout:
                throw new RuntimeException(e);
            }
        }
        return voitures;
    }

    /**
     * methode qui permet d'ecrire dans le fichier pour faire des mise à jour
     * creation, mise à jour, ou suppression
     * @param voitures : liste initiale de voiture à mettre à jour
     */

    private void writeFichierVoiture(List<Voiture> voitures) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get("voitures.csv");
        lines.add("voiture_id,model,couleur,moteur_id");
        // parcours de ma liste de moteurs
        for(Voiture voiture : voitures){
            // pour chacun faire une ligne String avec ces infos
            StringBuilder sb = new StringBuilder();
            sb.append(voiture.getVoiture_id()).append(",");
            sb.append(voiture.getModele()).append(",");
            sb.append(voiture.getCouleur()).append(",");
            if(voiture.getMoteur()!=null) {
                sb.append(voiture.getMoteur().getMoteur_id());
            }else{
                sb.append("null");
            }
            String line =sb.toString();
            lines.add(line);
        }

        //on a besoin d'un ecrivain java
        FileWriter myFileWriter = null;
        BufferedWriter myBufferedWritter = null;

        try {
            myFileWriter = new FileWriter(path.toFile());
            myBufferedWritter = new BufferedWriter(myFileWriter);
            // faire l'ecriture
            for(String line : lines){
                myBufferedWritter.write(line);//ecrire sur la ligne
                myBufferedWritter.newLine(); // on dit au buffered de commencer une nouvelle ligne
                //sinon il risque de continuer à ecrire sur la même ligne
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                myBufferedWritter.close();
                myFileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param moteur_id
     * @return
     */
    @Override
    public List<Voiture> findAllByIdMoteur(Integer moteur_id) {
        return null;
    }

    /**
     * @param moteur_id
     */
    @Override
    public void retirerMoteurDeVoiture(Integer moteur_id) {

    }

    /**
     * @param moteur_id
     * @return
     */

}
