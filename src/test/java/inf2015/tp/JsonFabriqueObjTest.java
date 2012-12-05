/**
 * JsonFabriqueObjTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import inf2015.tp.employe.Employe;
import inf2015.tp.employe.EmployeAdministration;
import inf2015.tp.employe.EmployeDeveloppement;
import inf2015.tp.employe.EmployeDirection;
import inf2015.tp.employe.EmployeExploitation;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import inf2015.tp.jour.JourWeekend;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;

public class JsonFabriqueObjTest {

    public JsonFabriqueObjTest() {
    }

    @Test
    public void testFabriquerEmploye() throws Exception {
        int numeroEmploye = 500;
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        String fichierJsonContenu = "{\"numero_employe\": 500,\"jour1\": []}";
        JSONObject jsonEmploye = JSONObject.fromObject(fichierJsonContenu);
        Employe employe = fabrique.fabriquerEmploye(jsonEmploye);

        assertEquals(numeroEmploye, employe.getNumeroEmploye());
    }

    @Test
    public void testFabriquerJourOuvrable() {
        String nomJour = "jour1";
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);

        JourOuvrable jour = fabrique.fabriquerJourOuvrable(nomJour);

        JourOuvrable jourExpecte = new JourOuvrable(nomJour, null);

        assertEquals(jourExpecte.getNomJour(), jour.getNomJour());
    }

    @Test
    public void testFabriquerJourWeekend() {
        String nomJour = "weekend1";
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        JourWeekend jour = fabrique.fabriquerJourWeekend(nomJour);

        JourWeekend jourExpecte = new JourWeekend(nomJour, null);

        assertEquals(jourExpecte.getNomJour(), jour.getNomJour());
    }

    @Test
    public void testFabriquerJour() {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        String nomJourWeekend = "weekend1";
        String nomJourOuvrable = "jour1";
        Jour jourRecu;

        jourRecu = fabrique.fabriquerJour(nomJourWeekend);
        assertEquals(jourRecu.getClass(), JourWeekend.class);

        jourRecu = fabrique.fabriquerJour(nomJourOuvrable);
        assertEquals(jourRecu.getClass(), JourOuvrable.class);
    }

    @Test
    public void testFabriquerProjet() {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        String fichierJsonContenu = "{\"numero_employe\": 500,\"jour1\": [ {\"projet\": 500,\"minutes\": 840}]}";
        JSONArray jsonJour1 = JSONObject.fromObject(fichierJsonContenu).getJSONArray("jour1");
        JSONObject jsonObjProjet = jsonJour1.getJSONObject(0);
        Projet projetCreer = fabrique.fabriquerProjet(jsonObjProjet);

        Projet projetExpecter = new Projet(500, 840);

        assertEquals(projetExpecter.minutes, projetCreer.minutes);
        assertEquals(projetExpecter.projetID, projetCreer.projetID);
    }

    @Test
    public void testFabriquerFeuilleTemps1Jour1Projet() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        String fichierJsonContenu = "{\"numero_employe\": 500,\"jour1\": [ {\"projet\": 800,\"minutes\": 840}], \"jour2\": [], \"jour3\": [], \"jour4\": [], "
                + "\"jour5\": [], \"weekend1\":[], \"weekend2\": [] }";
        Employe employeCreer = fabrique.fabriquerFeuilleTempsDuTexteJson(fichierJsonContenu);

        assertEquals("jour1", employeCreer.getSemaine().get(0).getNomJour());
        assertEquals(500, employeCreer.getNumeroEmploye());
        assertEquals(840, employeCreer.getSemaine().get(0).getProjetsJournee().get(0).minutes);
        assertEquals(800, employeCreer.getSemaine().get(0).getProjetsJournee().get(0).projetID);
    }

    @Test
    public void testFabriquerFeuilleTempsMultiple() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        String fichierJsonContenu = "{\"numero_employe\": 500,\"jour1\": [ {\"projet\": 800,\"minutes\": 840}], \"jour2\": [], \"jour3\": [], \"jour4\": [], "
                + "\"jour5\": [], \"weekend1\":[{\"projet\": 800,\"minutes\": 840}, {\"projet\": 500,\"minutes\": 1200}], \"weekend2\": [] }";
        Employe employeCreer = fabrique.fabriquerFeuilleTempsDuTexteJson(fichierJsonContenu);

        assertEquals("jour1", employeCreer.getSemaine().get(0).getNomJour());
        assertEquals(500, employeCreer.getNumeroEmploye());
        assertEquals(840, employeCreer.getSemaine().get(0).getProjetsJournee().get(0).minutes);
        assertEquals(800, employeCreer.getSemaine().get(0).getProjetsJournee().get(0).projetID);
        assertEquals(employeCreer.getSemaine().get(0).getClass(), JourOuvrable.class);


        assertEquals("weekend1", employeCreer.getSemaine().get(5).getNomJour());
        assertEquals(1200, employeCreer.getSemaine().get(5).getProjetsJournee().get(1).minutes);
        assertEquals(500, employeCreer.getSemaine().get(5).getProjetsJournee().get(1).projetID);
        assertEquals(employeCreer.getSemaine().get(5).getClass(), JourWeekend.class);
    }
    
    @Test 
    public void testFabriquerEmployeSelonNumeroAdministration() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        
        Employe employe = fabrique.fabriquerEmployeSelonNumero(500);
        
        assertEquals(EmployeAdministration.class, employe.getClass());
    }
    
    @Test 
    public void testFabriquerEmployeSelonNumeroDeveloppement() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        
        Employe employe = fabrique.fabriquerEmployeSelonNumero(1500);
        
        assertEquals(EmployeDeveloppement.class, employe.getClass());
    }
    
    @Test 
    public void testFabriquerEmployeSelonNumeroExplotation() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        
        Employe employe = fabrique.fabriquerEmployeSelonNumero(2500);
        
        assertEquals(EmployeExploitation.class, employe.getClass());
    }
    
    @Test 
    public void testFabriquerEmployeSelonNumeroDirection() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        
        Employe employe = fabrique.fabriquerEmployeSelonNumero(5500);
        
        assertEquals(EmployeDirection.class, employe.getClass());
    }
    
    @Test(expected=Exception.class)
    public void testFabriquerEmployeSelonNumeroInvalide() throws Exception {
        JsonFabriqueObj fabrique = new JsonFabriqueObj(null);
        
        fabrique.fabriquerEmployeSelonNumero(0);
    }
}
