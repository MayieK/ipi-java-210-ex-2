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
    public static short nbEnnemisTues;
    public static boolean bouclierActif = true;

    public static void main(String[] args) {
        initPersonnage();
    }
    public static void initPersonnage() {
        //afficher message de saisie
        System.out.println("Saisir le nom de votre personnage");
                //lire la saisie utilisateur
        Scanner scanner = new Scanner(System.in);
        nomPersonnage = Util.color(scanner.nextLine(), Color.GREEN);
                // afficher le message c'est parti
        System.out.println("OK " + nomPersonnage + " ! C'est parti !");
                // affecter la variable ptsDeVie
        ptsDeVie = MAX_PTS_VIE;
        // affecter la variable ptsBouclier
        ptsBouclier = PTS_BOUCLIER;

        scanner.close();
    }
    public static boolean hasard(double pourcentage) {
        //pourcentage < résultat du chiffre random = true
        //sinon faux
        return pourcentage < Math.random();
    }
    public static short nombreAuHasard(short nombre) {
        return (short) Math.round(Math.random() * nombre);
    }
}

