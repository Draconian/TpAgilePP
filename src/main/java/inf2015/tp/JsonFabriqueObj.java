/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import inf2015.tp.jour.JourWeekend;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author fdufault
 */
public class JsonFabriqueObj {

    protected static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3",
        "jour4", "jour5", "weekend1", "weekend2"};

    protected static Employe fabriquerEmploye(JSONObject jsonFeuilleTemps) {
        int numeroEmploye = jsonFeuilleTemps.getInt("numero_employe");

        Employe employe = new Employe(numeroEmploye);

        return employe;
    }

    protected static JourOuvrable fabriquerJourOuvrable(String nomJour) {

        JourOuvrable jourOuvrable = new JourOuvrable(nomJour);

        return jourOuvrable;
    }

    protected static JourWeekend fabriquerJourWeekend(String nomJour) {
        JourWeekend jourOuvrable = new JourWeekend(nomJour);

        return jourOuvrable;
    }

    protected static Jour fabriquerJour(String nomJour) {
        Jour jourCreer;

        if (nomJour.startsWith("jour")) {
            jourCreer = JsonFabriqueObj.fabriquerJourOuvrable(nomJour);
        } else {
            jourCreer = JsonFabriqueObj.fabriquerJourWeekend(nomJour);
        }

        return jourCreer;
    }

    protected static Projet fabriquerProjet(JSONObject jsonObjProjet) {
        Projet projet;

        int projetID = jsonObjProjet.getInt("projet");
        int projetMinutes = jsonObjProjet.getInt("minutes");

        projet = new Projet(projetID, projetMinutes);

        return projet;
    }

    protected static Employe fabriquerFeuilleTemps(String fichierJsonContenu) {
        JSONObject jsonFeuilleTemps = JSONObject.fromObject(fichierJsonContenu);

        Employe employe = JsonFabriqueObj.fabriquerEmploye(jsonFeuilleTemps);

        for (String nomJour : JOUR_SEMAINES) {
            JSONArray jsonJourArray = jsonFeuilleTemps.getJSONArray(nomJour);

            Jour jourCreer = JsonFabriqueObj.fabriquerProjetsPourJsonJour(nomJour, jsonJourArray);
            employe.semaines.add(jourCreer);
        }

        return employe;
    }

    private static Jour fabriquerProjetsPourJsonJour(String nomJour, JSONArray jsonJourArray) {
        Jour jourCreer = JsonFabriqueObj.fabriquerJour(nomJour);
        Iterator<JSONObject> iteratorProjet = jsonJourArray.iterator();
        
        while (iteratorProjet.hasNext()) {
            JSONObject jsonProjet = iteratorProjet.next();
            Projet projetCreer = JsonFabriqueObj.fabriquerProjet(jsonProjet);
            jourCreer.ajoutProjet(projetCreer);
        }

        return jourCreer;
    }
}
