/**
 * MinuteHeureConvertion - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

public class MinuteHeureConvertion {

    public static float minutesVersHeures(int minutes) {
        return (float) minutes / (float) 60;
    }

    public static int heuresVersMinutes(int heures) {
        return heures * 60;
    }

    public static int heuresVersMinutes(float heures) {
        return (int) (heures * 60.0);
    }
}
