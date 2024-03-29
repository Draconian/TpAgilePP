/**
 * EmployeDirection - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.erreur.ErreurJournal;

public class EmployePresident extends Employe {

    protected static final String TYPE_EMPLOYE = "President";
    protected static final int EMPLOYE_ID = 6000;
    protected static final int MINIMUM_MINUTES_BUREAU_OUVRABLE = 240; //4h
    protected static final int MINIMUM_MINUTES_BUREAU = 2580; //43h

    public EmployePresident(int numeroEmploye, ErreurJournal erreurJournal) {
        super(numeroEmploye, erreurJournal);
        super.minimumMinutesParJourOuvrable = MINIMUM_MINUTES_BUREAU_OUVRABLE;
        super.minimumMinutesBureau = MINIMUM_MINUTES_BUREAU;

    }

    @Override
    public String getTypeEmploye() {
        return TYPE_EMPLOYE;
    }

    public static boolean estEmploye(int id) {
        return (id == EMPLOYE_ID);
    }

    @Override
    protected void analyserFeuilleTemps() {
        super.analyserFeuilleTempsDirection();
    }

    @Override
    protected void verifierProjetTransport() {
        this.validerMinutesTransport();

        super.minutesJoursOuvrableBureau += super.minutesTransportJourOuvrable;
        super.minutesWeekendBureau += super.minutesTransportJourWeekend;
    }

    @Override
    protected void validerMinutesTransport() {
        //Toujours valide.
    }
}
