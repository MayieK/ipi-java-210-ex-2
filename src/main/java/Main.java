import java.awt.Color;
import java.util.Scanner;

public class Main{
    public static final short MAX_PTS_VIE = 100;
    public static final short PTS_BOUCLIER = 25;
    public static final short MAX_ATTAQUE_ENNEMI = 5;
    public static final short MAX_VIE_ENNEMI = 30;
    public static final short MAX_ATTAQUE_JOUEUR = 5;
    public static final short REGENARATION_BOUCLIER_PAR_TOUR = 10;
    public static String nomPersonnage;
    public static short ptsDeVie;
    public static short ptsBouclier;
    public static short nbEnnemisTues;
    public static boolean bouclierActif = true;

    public static void main(String[] args){
        initPersonnage();
        affichePersonnage();
        bouclierActif = false;
        affichePersonnage();
        short ennemi = 5;
        ennemi = attaqueJoueur(ennemi);
        System.out.println("Il reste " + ennemi + " points de vie à l'ennemi");
    }
    public static void initPersonnage(){
        //afficher message de saisie
        System.out.println("Saisir le nom de votre personnage");
        //lire la saisie utilisateur
        Scanner scanner = new Scanner(System.in);
        nomPersonnage = scanner.nextLine();
        // afficher le message c'est parti
        System.out.println("OK " + Util.color(nomPersonnage, Color.GREEN) + " ! C'est parti !");
        // affecter la variable ptsDeVie
        ptsDeVie = MAX_PTS_VIE;
        // affecter la variable ptsBouclier
        ptsBouclier = bouclierActif ? PTS_BOUCLIER : 0;
        scanner.close();
    }
    public static void affichePersonnage(){
        System.out.print(Util.color(nomPersonnage, Color.GREEN) + " (" + Util.color(ptsDeVie, Color.RED));
        if(bouclierActif){
            System.out.print(" " + Util.color(ptsBouclier, Color.BLUE));
            System.out.print(")");
        }
    }
    public static boolean hasard(double pourcentage){
        //pourcentage < résultat du chiffre random = true
        //sinon faux
        return pourcentage < Math.random();
    }
    public static short nombreAuHasard(short nombre){
        return (short) Math.round(Math.random() * nombre);
    }
    public static short attaqueJoueur(short ptsVieEnnemi){
        //Déterminer la force de l'attaque du joueur
        short forceAttaque = nombreAuHasard(MAX_ATTAQUE_JOUEUR);
        //Retrancher les points de l'attaque de l'ennemi sur les points de vie de l'ennemi
        ptsVieEnnemi -= forceAttaque;
        //Afficher les caractéristiques de l'attaque
        System.out.println(Util.color(nomPersonnage, Color.GREEN)
                + " attaque l'" + Util.color("ennemi", Color.YELLOW) + " ! Il lui fait perdre "
                + Util.color(forceAttaque, Color.PURPLE) + " points de dommages");
        //Retourner le nombre de points de vie de l'ennemi après l'attaque
        return ptsVieEnnemi;
    }
    public static void attaqueEnnemi(){
        short dommages = nombreAuHasard(MAX_ATTAQUE_ENNEMI);
        System.out.print("L'" + Util.color("ennemi", Color.YELLOW) + " attaque " + Util.color(nomPersonnage, Color.GREEN) + " !");
        System.out.print("Il lui fait" + dommages + " points de dommages !");
        if(bouclierActif && ptsBouclier > 0) {
            if (ptsBouclier >= dommages) {
                ptsBouclier -= dommages;
                System.out.print("Le bouclier perd" + Util.color(dommages, Color.BLUE) + " points.");
                dommages = 0;
            } else{
                dommages -= ptsBouclier;
                ptsBouclier = 0;
            }
        }
        if(dommages > 0){
            ptsDeVie -= dommages;
            System.out.print(nomPersonnage + " perd" + Util.color(dommages, Color.BLUE) + " points de vie !");
        }
    }

}


