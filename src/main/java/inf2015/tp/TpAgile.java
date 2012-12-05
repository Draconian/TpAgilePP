/**
 * TPAgile - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import inf2015.tp.employe.Employe;
import inf2015.tp.erreur.ErreurJournal;

public class TpAgile {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Erreur dans les arguments passés au programme: TpAgile [chemin feuille temps] [chemin erreur sortie]");
            System.exit(-1);
        }

        TpAgile.validerFeuilleTempsEmploye(args[0], args[1]);


    }

    private static void validerFeuilleTempsEmploye(String fichierEntre, String fichierSortie) {
        boolean estFeuilleTempsValide = false;
        ErreurJournal erreurJournal = new ErreurJournal();

        try {

            JsonFabriqueObj fabrique = new JsonFabriqueObj(erreurJournal);
            Employe employe = fabrique.fabriquerFeuilleTempsDuFichierJson(fichierEntre);

            estFeuilleTempsValide = employe.validerFeuilleDeTemps();

        } catch (Exception e) {
            System.out.println("MAIN: " + e.getLocalizedMessage());
            erreurJournal.effacerTout();
        }

        if (estFeuilleTempsValide) {
            System.out.println("Feuille de temps est valide.");
        } else {
            System.out.println("Feuille de temps est PAS valide.");
        }

        erreurJournal.ecrireErreurDansFichier(fichierSortie);
    }
}
