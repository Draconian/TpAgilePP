/**
 * JsonFabriqueObj - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import inf2015.tp.employe.Employe;
import inf2015.tp.employe.EmployeAdministration;
import inf2015.tp.employe.EmployeDeveloppement;
import inf2015.tp.employe.EmployeExploitation;
import inf2015.tp.employe.EmployeDirection;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import inf2015.tp.jour.JourWeekend;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonFabriqueObj {

    protected static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3",
        "jour4", "jour5", "weekend1", "weekend2"};
    protected ErreurJournal erreurJournal;

    public JsonFabriqueObj(ErreurJournal erreurJournal) {
        this.erreurJournal = erreurJournal;
    }

    protected Employe fabriquerEmploye(JSONObject jsonFeuilleTemps) throws Exception {
        int numeroEmploye = jsonFeuilleTemps.getInt("numero_employe");

        Employe employe = this.fabriquerEmployeSelonNumero(numeroEmploye);

        return employe;
    }

    protected Employe fabriquerEmployeSelonNumero(int numeroEmploye) throws Exception {

        if (EmployeAdministration.estEmploye(numeroEmploye)) {
            return new EmployeAdministration(numeroEmploye, this.erreurJournal);

        } else if (EmployeDeveloppement.estEmploye(numeroEmploye)) {
            return new EmployeDeveloppement(numeroEmploye, erreurJournal);

        } else if (EmployeExploitation.estEmploye(numeroEmploye)) {
            return new EmployeExploitation(numeroEmploye, erreurJournal);

        } else if (EmployeDirection.estEmploye(numeroEmploye)) {
            return new EmployeDirection(numeroEmploye, erreurJournal);

        }

        throw new Exception("Numéro d'employé inconnu");
    }

    protected JourOuvrable fabriquerJourOuvrable(String nomJour) {

        JourOuvrable jourOuvrable = new JourOuvrable(nomJour, erreurJournal);

        return jourOuvrable;
    }

    protected JourWeekend fabriquerJourWeekend(String nomJour) {
        JourWeekend jourOuvrable = new JourWeekend(nomJour, erreurJournal);

        return jourOuvrable;
    }

    protected Jour fabriquerJour(String nomJour) {
        Jour jourCreer;

        if (nomJour.startsWith("jour")) {
            jourCreer = this.fabriquerJourOuvrable(nomJour);
        } else {
            jourCreer = this.fabriquerJourWeekend(nomJour);
        }

        return jourCreer;
    }

    protected Projet fabriquerProjet(JSONObject jsonObjProjet) {
        Projet projet;

        int projetID = jsonObjProjet.getInt("projet");
        int projetMinutes = jsonObjProjet.getInt("minutes");

        projet = new Projet(projetID, projetMinutes);

        return projet;
    }

    protected Employe fabriquerFeuilleTemps(String fichierJsonContenu) throws Exception {
        JSONObject jsonFeuilleTemps = JSONObject.fromObject(fichierJsonContenu);

        Employe employe = this.fabriquerEmploye(jsonFeuilleTemps);

        for (String nomJour : JOUR_SEMAINES) {
            JSONArray jsonJourArray = jsonFeuilleTemps.getJSONArray(nomJour);

            Jour jourCreer = this.fabriquerProjetsPourJsonJour(nomJour, jsonJourArray);
            employe.getSemaine().add(jourCreer);
        }

        return employe;
    }

    private Jour fabriquerProjetsPourJsonJour(String nomJour, JSONArray jsonJourArray) {
        Jour jourCreer = this.fabriquerJour(nomJour);
        Iterator<JSONObject> iteratorProjet = jsonJourArray.iterator();

        while (iteratorProjet.hasNext()) {
            JSONObject jsonProjet = iteratorProjet.next();
            Projet projetCreer = this.fabriquerProjet(jsonProjet);
            jourCreer.ajoutProjet(projetCreer);
        }

        return jourCreer;
    }
}
