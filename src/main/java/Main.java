import java.util.Scanner;

public class Main {
    public static final short MAX_PTS_VIE = 100;
    public static final short PTS_BOUCLIER = 25;
    public static final short MAX_ATTAQUE_ENNEMI = 5;
    public static final short MAX_VIE_ENNEMI = 30;
    public static final short MAX_ATTAQUE_JOUEUR = 5;
    public static final short REGENARATION_BOUCLIER_PAR_TOUR = 10;
    public static String nomPersonnage;
    public static short ptsDeVieJoueur;
    public static short ptsVieEnnemis;
    public static short ptsBouclierJoueur;
    public static short nbEnnemisTues;
    public static boolean bouclierActifJoueur = true;
    public static short attaque;
    public static String continuerPartie;
    public static short nbEnnemis;


    public static void main(String[] args) {
        //************ Début évaluation **************
        //Initialiser le personnage du joueur
        initPersonnage();
        //Initialiser les "ennemis", tableau "ennemis" avec "nbEnnemis" et nb vie ennemis => ptsVieEnnemi
        initEnnemis();
        //Initialiser les combats => tant que ennemis il y a des combats
        // Combat avec le 1er ennemi
        for (short [] ptsVieEnnemis; nbEnnemis > 0;) {
            boolean hasard;
            System.out.println("Combat avec un ennemi possédant "
                    + ptsVieEnnemis + " points de vie !");
        }
        //Affichage de l'état des joueurs avant chaque tour
        affichePersonnage();
        System.out.println(" vs ennemi ("
                + ptsVieEnnemis
                + ")");
        //Combat à mort
        short attaque;
        //Comptabiliser le nombre d'ennemis tués par le joueur
        if (ptsVieEnnemis == 0) {
            nbEnnemisTues = 1;
            nbEnnemisTues +=1;
        }
        //Pour passer à l'ennemi suivant, saisir la lettre S
        System.out.println("Saisisser S pour passer au combat suivant ou n'importe quoi d'autre pour fuir...");
        Scanner scanner = new Scanner(System.in);
        continuerPartie = scanner.nextLine();
        if (continuerPartie == "S") {
            //Si bouclier actif, il peut partiellement se regénérer avant l'ennemi suivant mais ne peut dépasser le nb max du bouclier
            if (bouclierActifJoueur == true) {
                ptsBouclierJoueur = ptsBouclierJoueur + REGENARATION_BOUCLIER_PAR_TOUR;
                if (nbEnnemisTues != nbEnnemis) {
                    //Affichage de l'état des joueurs avant chaque tour
                    affichePersonnage();
                    System.out.println(" vs ennemi ("
                            + ptsVieEnnemis
                            + ")");
                    //Combat à mort
                    attaque;
                } else {
                    System.out.println("Vous avez tué tous les ennemis !");
                }
            }
        } else {
            System.out.println("Vous avez tué"
                + nbEnnemisTues
                + " ennemi(s) mais vous êtes parti lâchement avant la fin...");
        }
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
        ptsDeVieJoueur = MAX_PTS_VIE;
        // affecter la variable ptsBouclier
        ptsBouclierJoueur = bouclierActifJoueur ? PTS_BOUCLIER : 0;
    }

    public static void affichePersonnage() {
        System.out.print(Util.color(nomPersonnage, Color.GREEN)
                + " (" + Util.color(ptsDeVieJoueur, Color.RED));
        if (bouclierActifJoueur) {
            System.out.print(" "
                    + Util.color(ptsBouclierJoueur, Color.BLUE)
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

    public static short attaque (short ptsVieEnnemis, boolean joueurAttaque){
        //Vérifier si l'un des 2 combattants est morts => si oui, on ne fait aucune attaque
        if (ptsDeVieJoueur <= 0 || ptsVieEnnemis <= 0) {
            return ptsVieEnnemis;
        } else if (hasard(0.5)) {
            //On va faire attaquer le joueur si c'est à lui d'attaquer
            if(joueurAttaque){
                ptsVieEnnemis = attaqueJoueur(ptsVieEnnemis);
            } else {
                attaqueEnnemi();
            }
            return ptsVieEnnemis;
        }
        //On renvoie le nombre de points de l'ennemi
        return ptsVieEnnemis;
    }
    public static short attaqueJoueur(short ptsVieEnnemis) {
        //Déterminer la force de l'attaque du joueur
        short forceAttaque = nombreAuHasard(MAX_ATTAQUE_JOUEUR);
        //Retrancher les points de l'attaque de l'ennemi sur les points de vie de l'ennemi
        ptsVieEnnemis -= forceAttaque;
        //Afficher les caractéristiques de l'attaque
        System.out.println(Util.color(nomPersonnage, Color.GREEN)
                + " attaque l'"
                + Util.color("ennemi", Color.YELLOW)
                + " ! Il lui fait perdre "
                + Util.color(forceAttaque, Color.PURPLE)
                + " points de dommages");
        //Retourner le nombre de points de vie de l'ennemi après l'attaque
        return ptsVieEnnemis;
    }

    public static void attaqueEnnemi() {
        short ptsDommagesToJoueur = nombreAuHasard(MAX_ATTAQUE_ENNEMI);
        System.out.print("L'"
                + Util.color("ennemi", Color.YELLOW)
                + " attaque "
                + Util.color(nomPersonnage, Color.GREEN)
                + " !");
        System.out.print("Il lui fait "
                + ptsDommagesToJoueur
                + " points de dommages !");
        if (bouclierActifJoueur && ptsBouclierJoueur > 0) {
            if (ptsBouclierJoueur >= ptsDommagesToJoueur) {
                ptsBouclierJoueur -= ptsDommagesToJoueur;
                System.out.print("Le bouclier perd "
                        + Util.color(ptsDommagesToJoueur, Color.BLUE)
                        + " points.");
                ptsDommagesToJoueur = 0;
            } else {
                ptsDommagesToJoueur -= ptsBouclierJoueur;
                ptsBouclierJoueur = 0;
            }
        }
        if (ptsDommagesToJoueur > 0) {
            ptsDeVieJoueur -= ptsDommagesToJoueur;
            System.out.print(nomPersonnage
                    + " perd "
                    + Util.color(ptsDommagesToJoueur, Color.BLUE)
                    + " points de vie !");
        }
    }

    public static short [] initEnnemis() {
        System.out.println("Combien d'ennemi voulez-vous combattre ?");
        Scanner scanner = new Scanner(System.in);
        int nbEnnemis = scanner.nextInt();
        System.out.println("Génération des ennemis...");
        short [] ptsVieEnnemis = new short [nbEnnemis];
        for (int i = 0; i < nbEnnemis; i++) {
            ptsVieEnnemis [i] = nombreAuHasard(MAX_VIE_ENNEMI);
            System.out.println("Ennemi numéro "
                + (i + 1)
                + " : "
                + ptsVieEnnemis [i]);
        }
        return ptsVieEnnemis;
    }
}

