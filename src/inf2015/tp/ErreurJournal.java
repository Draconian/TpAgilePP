/**
 * ErreurJournal - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import java.io.IOException;
import net.sf.json.JSONArray;

public class ErreurJournal {

    private static ErreurJournal ERREUR_JOURNAL_INSTANCE = new ErreurJournal();
    protected JSONArray erreurs = new JSONArray();

    public void ajoutErreur(String aErreurMessage) {
        this.erreurs.add(aErreurMessage);
        System.out.println(aErreurMessage);
    }

    public void ecrireErreurDansFichier(String cheminFichier) throws IOException {
        JsonUtil.ecrireJsonArrayDansFichier(this.erreurs, cheminFichier);
    }

    @Override
    public String toString() {
        //toString(5) => 5 espaces pour chaque indentation.
        return this.erreurs.toString(5);
    }

    public void effacerTout() {
        this.erreurs.clear();
    }

    public boolean contientErreur() {
        return (erreurs.size() > 0);
    }

    public static ErreurJournal Instance() {
        return ERREUR_JOURNAL_INSTANCE;
    }
}
