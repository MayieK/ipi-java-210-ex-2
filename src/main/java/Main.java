import java.util.Scanner;

public class Main {
    public static final short MAX_PTS_VIE = 100;
    public static final short PTS_BOUCLIER = 25;
    public static final short MAX_ATTAQUE_ENNEMI = 5;
    public static final short MAX_VIE_ENNEMI = 30;
    public static final short MAX_ATTAQUE_JOUEUR = 5;
    public static final short REGENARATION_BOUCLIER_PAR_TOUR = 10;
    public static String nomPersonnage;
    public static short ptsDeVie;
    public static short ptsBouclier;
    public static boolean bouclierActif = true;
    public static boolean hasard;
    public static short vieEnnemis;

    public static void main(String[] args) {
        //Début évaluation
        //Initialiser le personnage du joueur
        initPersonnage();
        //Initialiser les ennemis
        initEnnemis();
        //Combat avec le 1er ennemi, celui qui commence est choisi de façon aléatoire
        System.out.println("Combat avec un ennemi possédant "
                + vieEnnemis + " points de vie !");
        //Affichage de l'état des joueurs avant chaque tour
        affichePersonnage();
        System.out.println(" vs ennemi ("
                + vieEnnemis
                + ")");

        //Comptabiliser le nombre d'ennemis tués par le joueur
        //Si bouclier actif, il peut partiellement se regénérer avant l'ennemi suivant mais ne peut dépasser le nb max du bouclier
        //Pour passer à l'ennemi suivant, saisir la lettre S
    }

    public static void initPersonnage() {
        //afficher message de saisie
        System.out.println("Saisir le nom de votre personnage");
        //lire la saisie utilisateur
        Scanner scanner = new Scanner(System.in);
        nomPersonnage = scanner.nextLine();
        // afficher le message c'est parti
        System.out.println("OK "
            + Util.color(nomPersonnage, Color.GREEN)
            + " ! C'est parti !");
        // affecter la variable ptsDeVie
        ptsDeVie = MAX_PTS_VIE;
        // affecter la variable ptsBouclier
        ptsBouclier = bouclierActif ? PTS_BOUCLIER : 0;
    }

    public static void affichePersonnage() {
        System.out.print(Util.color(nomPersonnage, Color.GREEN)
                + " (" + Util.color(ptsDeVie, Color.RED));
        if (bouclierActif) {
            System.out.print(" "
                    + Util.color(ptsBouclier, Color.BLUE)
                    + ")");
        }
    }

    public static boolean hasard(double pourcentage) {
        //pourcentage < résultat du chiffre random = true
        //sinon faux
    return pourcentage < Math.random();
    }

    public static short nombreAuHasard(short nombre) {
        return (short) Math.round(Math.random() * nombre);
    }

    public static short attaqueJoueur(short ptsVieEnnemi) {
        //Déterminer la force de l'attaque du joueur
        short forceAttaque = nombreAuHasard(MAX_ATTAQUE_JOUEUR);
        //Retrancher les points de l'attaque de l'ennemi sur les points de vie de l'ennemi
        ptsVieEnnemi -= forceAttaque;
        //Afficher les caractéristiques de l'attaque
        System.out.println(Util.color(nomPersonnage, Color.GREEN)
                + " attaque l'"
                + Util.color("ennemi", Color.YELLOW)
                + " ! Il lui fait perdre "
                + Util.color(forceAttaque, Color.PURPLE)
                + " points de dommages");
        //Retourner le nombre de points de vie de l'ennemi après l'attaque
        return ptsVieEnnemi;
    }

    public static void attaqueEnnemi() {
        short dommages = nombreAuHasard(MAX_ATTAQUE_ENNEMI);
        System.out.print("L'"
                + Util.color("ennemi", Color.YELLOW)
                + " attaque "
                + Util.color(nomPersonnage, Color.GREEN)
                + " !");
        System.out.print("Il lui fait "
                + dommages
                + " points de dommages !");
        if (bouclierActif && ptsBouclier > 0) {
            if (ptsBouclier >= dommages) {
                ptsBouclier -= dommages;
                System.out.print("Le bouclier perd "
                        + Util.color(dommages, Color.BLUE)
                        + " points.");
                dommages = 0;
            } else {
                dommages -= ptsBouclier;
                ptsBouclier = 0;
            }
        }
        if (dommages > 0) {
            ptsDeVie -= dommages;
            System.out.print(nomPersonnage
                    + " perd "
                    + Util.color(dommages, Color.BLUE)
                    + " points de vie !");
        }
    }

    public static short initEnnemis() {
        System.out.println("Combien d'ennemi voulez-vous combattre ?");
        Scanner scanner = new Scanner(System.in);
        int nbEnnemis = scanner.nextInt();
        System.out.println("Génération des ennemis...");
        short[] ennemis = new short[nbEnnemis];
        for (int i = 0; i < nbEnnemis; i++) {
            ennemis[i] = nombreAuHasard(MAX_VIE_ENNEMI);
            System.out.println("Ennemi numéro "
                + (i + 1)
                + " : "
                + ennemis[i]);
            vieEnnemis = ennemis[i];
        }
        return vieEnnemis;

    }

    public static short attaque (short ennemis, boolean joueurAttaque){
        //Vérifier si l'un des 2 combattants est mort => si oui, on ne fait aucune attaque
        if(ptsDeVie <= 0 || ennemis <= 0){
            return ennemis;
        }
        //On va faire attaquer le joueur si c'est à lui d'attaquer
        if(joueurAttaque){
            ennemis = attaqueJoueur(ennemis);
        }
        //Sinon, on fait attaquer l'ennemi
        else {
            attaqueEnnemi();
        }
        //On renvoie le nombre de points de l'ennemi
        return ennemis;
    }
}

