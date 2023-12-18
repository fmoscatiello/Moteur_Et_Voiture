package com.exemple.model;

/**
 *
 */
public enum Couleur {
    Bl("Bleu"),R("Rouge"),V("Vert"),G("Gris"),N("Noire"),Bc("Blanc");

    private final String label;

    /**
     * Constructeur d'enum Couleur
     * @param label , {String} representant le nom de la couleur
     */
    Couleur(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    /**
     * methode pour retourner le nom de la couleur à partir de son label
     * @param couleur
     * @return nomCouleur {String} , si le label existe, retourne la couleur
     * sinon, retourne null
     */

    public static String nomCouleurFromLabel(Couleur couleur){
        String nomCouleur=null;
        for (Couleur c : values()){
            if (couleur.getLabel().equals(c.getLabel())){
                nomCouleur = c.getLabel();
            }

        }
      return nomCouleur;

    }

    /**
     * Permet de retrouver l'enum correspondant à son label
     * @param label {String} : nom de la couleur recherchée
     * @return l'enum couleur si elle existe, retourne null si pas trouvé
     * exemple : taper "Bleu" => return "Bl"
     */
    public static Couleur valueOfLabel(String label){
        // values() = l'ensemble des valeurs de la classe enum Couleur
        for(Couleur c : values()){
            if(c.label.equals(label)){
                return c;
            }
        }
        return null;
    }

}
