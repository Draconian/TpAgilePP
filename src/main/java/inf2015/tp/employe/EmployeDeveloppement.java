/**
 * EmployeDeveloppement - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.erreur.ErreurJournal;

public class EmployeDeveloppement extends Employe {

    protected static final String TYPE_EMPLOYE = "Développement";
    protected static final int EMPLOYE_ID_DEBUT = 1000;
    protected static final int EMPLOYE_ID_FIN = 2000;
    protected static final int MIN_MINUTES_BUREAU_OUVRABLE = 2280; //38h
    protected static final int MIN_MINUTES_BUREAU_PAR_JOUR_OUVRABLE = 360; //6h
    protected static final int MAXIMUM_MINUTES_BUREAU = 2580; //43h

        
    public EmployeDeveloppement(int numeroEmploye, ErreurJournal erreurJournal) {
        super(numeroEmploye, erreurJournal);
        
        super.minimumMinutesParJourOuvrable = MIN_MINUTES_BUREAU_PAR_JOUR_OUVRABLE;
        super.maximumMinutesBureau = MAXIMUM_MINUTES_BUREAU;
        super.minimumMinutesBureau = MIN_MINUTES_BUREAU_OUVRABLE;
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
        super.analyserFeuilleTempsGeneral();
    }
}
