/**
 * EmployeDirection - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.erreur.ErreurJournal;

public class EmployeDirection extends Employe {

    protected static final String TYPE_EMPLOYE = "Direction";
    protected static final int EMPLOYE_ID_DEBUT = 5000;
    protected static final int EMPLOYE_ID_FIN = 6000;
    protected static final int MIN_MINUTES_BUREAU_OUVRABLE = 240; //4h
    protected static final int MINIMUM_MINUTES_BUREAU = 2580; // 0h

    public EmployeDirection(int numeroEmploye, ErreurJournal erreurJournal) {
        super(numeroEmploye, erreurJournal);

        super.minimumMinutesParJourOuvrable = MIN_MINUTES_BUREAU_OUVRABLE;
        super.minimumMinutesBureau = MINIMUM_MINUTES_BUREAU;

    }

    @Override
    public String getTypeEmploye() {
        return TYPE_EMPLOYE;
    }

    public static boolean estEmploye(int id) {
        return (EMPLOYE_ID_DEBUT <= id && id < EMPLOYE_ID_FIN);
    }

    @Override
    protected void analyserFeuilleTemps() {
        super.analyserFeuilleTempsDirection();
    }

    @Override
    protected void verifierProjetTransport() {
        int minutesTransport = super.minutesTransportJourOuvrable + super.minutesTransportJourWeekend;

        this.validerMinutesTransport();

        super.minutesTeleTravail += minutesTransport;
    }
}
