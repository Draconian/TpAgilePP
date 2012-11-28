/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import inf2015.tp.Jour.TypeJour;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author fdufault
 */
public class EmployeTest {

    @Test
    public void testSemaineContientUnSeulCongeParental() {
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 400));
        jour2.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 300));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.verifierCongeParental();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testerTempsEmployerDirectionAvecErreur() {
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);

        Employe employe = new Employe(null, null);
        jour1.ajoutProjet(new Projet(100, 500));
        jour2.ajoutProjet(new Projet(101, 500));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsDirection();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testerTempsEmployerDirectionSansErreur() {
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);

        Employe employe = new Employe(null, null);
        jour1.ajoutProjet(new Projet(100, 1440));
        jour2.ajoutProjet(new Projet(100, 1440));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsDirection();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 0);

    }

    @Test
    public void plusDe24HJourneeNormale() {
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        Employe employe = new Employe(null, null);
        jour1.ajoutProjet(new Projet(100, 1450));
        jour2.ajoutProjet(new Projet(110, 1439));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsDirection();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testplusDe24hMoinsDe32HJourneeFerie() {
        int minutes = 480;
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_FERIE, minutes));
        jour1.ajoutProjet(new Projet(100, 1439));
        jour1.verifierMinutes(jour1.getMinutesBureau());
        assertEquals(ErreurJournal.Instance().erreurs.size(), 0);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testPlusDe32hJourneeFerie() {
        int minutes = 480;
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_FERIE, minutes));
        jour1.ajoutProjet(new Projet(100, 1450));
        jour1.verifierMinutes(jour1.getMinutesBureau());
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testExploitationNbHeureMinimum() {
        int nbExpecter = 0;

        Employe employe = new Employe(null, null);

        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(10, 1200));
        jour2.ajoutProjet(new Projet(10, 1200));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);

        employe.calculerFeuilleTemps();

        employe.analyserFeuilleTempsExploitation();
        assertEquals(nbExpecter, ErreurJournal.Instance().erreurs.size());

    }

    @Test
    public void testExploitationMoinsHeureQueMinimum() {
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(10, 1000));
        jour2.ajoutProjet(new Projet(10, 1000));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsExploitation();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testAdministrationMoinsHeureQueMinimum() {
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(10, 1000));
        jour2.ajoutProjet(new Projet(10, 1000));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsAdministration();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testAdministrationPlusHeureQueMaximum() {
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(10, 1246));
        jour2.ajoutProjet(new Projet(10, 1245));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsAdministration();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
        ErreurJournal.Instance().effacerTout();
    }

    @Test
    public void testAdministrationFeuilleValide() {
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);
        jour1.ajoutProjet(new Projet(10, 1244));
        jour2.ajoutProjet(new Projet(10, 1244));
        employe.semaines.add(jour1);
        employe.semaines.add(jour2);
        employe.calculerFeuilleTemps();
        employe.analyserFeuilleTempsAdministration();
        assertEquals(ErreurJournal.Instance().erreurs.size(), 0);
        ErreurJournal.Instance().effacerTout();
    }
}
