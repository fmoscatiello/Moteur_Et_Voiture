package com.exemple.dao;
import com.exemple.exception.OperationException;
import com.exemple.model.Moteur;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe qui permet de récuperer les info de Moteur
 * dans son stockage fichier
 */
public class MoteurDaoFile implements MoteurDao {

    /**
     * recuperer tous les elements de mon stockage
     *
     * @return Liste de mes elements stockés
     */
    @Override
    public List<Moteur> findAll() {
        return readFichierMoteur();
    }

    /**
     * recupere un objet specifique par son identifiant
     *
     * @param id , identifiant de l'objet recherché
     * @return l'objet qui porte cet identifiant
     */
    @Override
    public Moteur findById(Integer id) {
        Moteur moteurTrouve = null;
        //lit le fichier
        //recuperer les moteurs contenus
        List<Moteur> moteurs = readFichierMoteur();
        //parcourir ces moteurs
        for(Moteur moteur : moteurs) {
            //comparer ses id de ces moteurs avec celui du paramètre
          if(Objects.equals(moteur.getMoteur_id(),id)) {
              //quand c'est le même , je le récupère
              moteurTrouve = moteur;
              break; // on arrete la boucle for, dès que le moteur est trouvé
          }
        }
        return moteurTrouve;
    }

    /**
     * enregistrer dans mon stockage un nouvel objet
     *
     * @param newMoteur , le nouvek objet enregistré
     * @return l'objet enregistré
     */
    @Override
    public Moteur create(Moteur newMoteur) {
        //recuperer mes moteurs du fichier
        List<Moteur> moteurs = readFichierMoteur();
        Integer id_max = 0;
        //parcourir la liste
        for(Moteur moteur : moteurs) {
            //comparer ses id de ces moteurs avec celui du paramètre
            if(moteur.getMoteur_id() > id_max){
                //comparer les id pour trouver l'id max
                // recuperer l'id max (le plus grand)
            id_max = moteur.getMoteur_id();
            }
        }

        // incrementer l'id
        id_max++;
        //attribuer ce nouvel id à mon objet à enregistrer
        newMoteur.setMoteur_id(id_max);
        //ajouter mon objet à la liste de ceux qui existent
        moteurs.add(newMoteur);
        //ecrire cette liste mise à jour dans le fichier
        writeFichierMoteur(moteurs);
        //recuperer l'objet enregistré
        return newMoteur;
    }

    /**
     * Mettre à jour l'objet fourni en paramètre dans mon stockage
     *
     * @param updateMoteur , l'objet à mettre à jour
     */
    @Override
    public void update(Moteur updateMoteur) {
        //lit le fichier
        //recuperer les moteurs contenus
        List<Moteur> moteurs = readFichierMoteur();
        // trouver sa position dans la liste
        Integer index;
        Boolean find = false;
        for (index = 0; index < moteurs.size(); index++) {
            if (Objects.equals(updateMoteur.getMoteur_id(),moteurs.get(index).getMoteur_id())){
                find=true;
                break;
            }
        }
        if (find) {
            //recherche a aboutie
            moteurs.set(index,updateMoteur);
            writeFichierMoteur(moteurs);
        }else{
            //recherche n'a pas abouti donc mon elt n'existe pas
            throw new OperationException("Moteur n° : "
                    +updateMoteur.getMoteur_id()
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
    public Moteur delete(Integer id) {
        Moteur backupMoteur = null;
        //lit le fichier
        //recuperer les moteurs contenus
        List<Moteur> moteurs = readFichierMoteur();
        // trouver sa position dans la liste
        Integer index;
        Boolean find = false;
        for (index = 0; index < moteurs.size(); index++) {
            if (Objects.equals(id,moteurs.get(index).getMoteur_id())){
                find=true;
                break;
            }
        }
        if (find) {
            //recherche a aboutie
            backupMoteur=moteurs.get(index);
            moteurs.remove(moteurs.get(index));
            writeFichierMoteur(moteurs);
        }else{
            //recherche n'a pas abouti donc mon elt n'existe pas
            throw new OperationException("Moteur n° : "
                    +id
                    +" n'a pas été trouvé pour la suppression");
        }

        return backupMoteur;
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

        List<Moteur> moteurs = readFichierMoteur();
        List<Moteur> moteursSup = new ArrayList<>();

        for(Moteur m : moteurs){
            if(m.getPuissance()>puissanceLimit){
                moteursSup.add(m);
            }
        }
        return moteursSup;
    }

    /**
     * Methode qui va lire le fichier de données
     * et récuperer les informations de moteurs contenus à l'interieur
     * @return List<Moteur>
     */
    private List<Moteur> readFichierMoteur(){
        List<Moteur> moteurs = new ArrayList<>();
        /*Path = fonction de lecture de fichier, chemin d'accès au fichier
        ATTENTION : il faudra mettre le fichier à la racine du projet ici.
        si nous le mettons dans un sous dossier, il faudra taper l'ensemble
        du chemin InOutFile/main/java...*/
        Path path = Paths.get("moteurs.csv");
        // il faut un element d'interaction avec mon fichier pour le lire
        FileReader myFileReader = null;
        // un element de memoire tampon pour la lecture
        BufferedReader myBufferedReader = null;
        String myLigne;
        Boolean header = true;

        try {
            myFileReader = new FileReader(path.toFile());
            myBufferedReader = new BufferedReader(myFileReader);
            while ((myLigne=myBufferedReader.readLine())!=null){
                if (header) {
                    header =false;
                    continue;//passe à la boucle suivante
                }
                //quoi faire avec une ligne
                //separation de la ligne en plusieurs morceaux
                // Ici, le CSV utilise le separateur ","
                String[] items = myLigne.split(",");
                String csvMoteur_id= items[0].strip();
                String csvModel= items[1].strip();
                String csvKilometrage= items[2].strip();
                String csvPuissance= items[3].strip();
                // convertir au bon format de donnée
                Integer moteur_id = Integer.valueOf(csvMoteur_id);
                Float kilometrage = Float.valueOf(csvKilometrage);
                Integer puissance = Integer.valueOf(csvPuissance);
                // créer l'objet Java
                Moteur moteur = new Moteur(moteur_id,csvModel,kilometrage,puissance);
                // le mettre dans ma liste de resultats
                moteurs.add(moteur);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally { //qu'il y a une erreur(catch) ou pas(try),le block finally s'execute!
            try {
                assert myBufferedReader != null;
                myBufferedReader.close();
                myFileReader.close();
            } catch (IOException e) {
                //si on arrive pas à clore le fichier, on coupe tout:
                throw new RuntimeException(e);
            }
        }
        return moteurs; //à coder plus tard
    }
    /**
     * methode qui permet d'ecrire dans le fichier pour faire des mise à jour
     * creation, mise à jour, ou suppression
     * @param moteurs : liste initiale de moteurs à mettre à jour
     */
    private void writeFichierMoteur(List<Moteur> moteurs){
       List<String> lines = new ArrayList<>();
       Path path = Paths.get("moteurs.csv");
       lines.add("moteur_id,model,kilometrage,puissance");
        // parcours de ma liste de moteurs
        for(Moteur moteur : moteurs){
            // pour chacun faire une ligne String avec ces infos
            StringBuilder sb = new StringBuilder();
            sb.append(moteur.getMoteur_id()).append(",");
            sb.append(moteur.getModel()).append(",");
            sb.append(moteur.getKilometrage()).append(",");
            sb.append(moteur.getPuissance());
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

}
