/**
 * EmployeAdministration - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.erreur.ErreurEmployeTeletravailMaximum;
import inf2015.tp.erreur.ErreurJournal;

public class EmployeAdministration extends Employe {

    protected static final String TYPE_EMPLOYE = "Administration";
    protected static final int EMPLOYE_ID_DEBUT = 1;
    protected static final int EMPLOYE_ID_FIN = 1000;
    protected static final int MINIMUM_MINUTES_BUREAU = 2250; //37.5h
    protected static final int MINIMUM_MINUTES_BUREAU_OUVRABLE = 240; //4h
    protected static final int MAXIMUM_MINUTES_TELETRAVAIL = 600; //10h
    protected static final int MAXIMUM_MINUTES_BUREAU = 2490; //41.5h

    public EmployeAdministration(int numeroEmploye, ErreurJournal erreurJournal) {
        super(numeroEmploye, erreurJournal);

        this.minimumMinutesParJourOuvrable = MINIMUM_MINUTES_BUREAU_OUVRABLE;
        this.maximumMinutesBureau = MAXIMUM_MINUTES_BUREAU;
        this.minimumMinutesBureau = MINIMUM_MINUTES_BUREAU;
        this.maximumMinutesTeletravail = MAXIMUM_MINUTES_TELETRAVAIL;
    }

    @Override
    public String getTypeEmploye() {
        return TYPE_EMPLOYE;
    }

    public static boolean estEmploye(int id) {
        return (EMPLOYE_ID_DEBUT <= id && id < EMPLOYE_ID_FIN);
    }

    @Override
    public void analyserFeuilleTemps() {
        super.analyserFeuilleTempsGeneral();
        
        this.verifierTeleTravail();
       
    }
    
    protected void verifierTeleTravail() {
         if (super.minutesTeleTravail > this.maximumMinutesTeletravail) {
            super.erreurJournal.ajoutErreur(new ErreurEmployeTeletravailMaximum(this, this.maximumMinutesTeletravail));
        }
    }
}
