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
        try {
            ErreurJournal erreurJournal = new ErreurJournal();
            JsonFabriqueObj fabrique = new JsonFabriqueObj(erreurJournal);
            Employe employe = fabrique.fabriquerFeuilleTempsDuFichierJson(args[0]);
        } catch (Exception e) {
            System.out.println("MAIN: " + e.getLocalizedMessage());
        }


    }
}
