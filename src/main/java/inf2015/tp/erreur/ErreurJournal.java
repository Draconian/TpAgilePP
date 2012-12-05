/**
 * ErreurJournal - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.JsonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;

public class ErreurJournal {

    private static ErreurJournal ERREUR_JOURNAL_INSTANCE = new ErreurJournal();
    protected ArrayList<Erreur> erreurs = new ArrayList<Erreur>();

    public void ajoutErreur(Erreur nouvelleErreur) {
        this.erreurs.add(nouvelleErreur);
        System.out.println(nouvelleErreur);
    }

    public Erreur getErreurAIndex(int index) {
        return this.erreurs.get(index);
    }

    public int getNombresErreurs() {
        return erreurs.size();
    }

    public void ecrireErreurDansFichier(String cheminFichier) {
        try {
            if (!this.estVide()) {
                JsonUtil.ecrireJsonArrayDansFichier(this.convertirEnJsonArray(), cheminFichier);
            }
        } catch (IOException ex) {
            Logger.getLogger(ErreurJournal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void effacerTout() {
        this.erreurs.clear();
    }

    public boolean estVide() {
        return erreurs.isEmpty();
    }

    public JSONArray convertirEnJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (Erreur erreur : this.erreurs) {
            jsonArray.add(erreur.afficherErreur());
        }

        return jsonArray;
    }
}
