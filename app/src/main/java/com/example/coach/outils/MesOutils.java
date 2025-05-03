package com.example.coach.outils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe abstraite contenant différents outils
 */
public abstract class MesOutils {

    /**
     * Conversion d'une date du format String vers le format Date (avec format par défaut)
     * @param uneDate au format String
     * @return la date au format Date
     */
    public static Date convertStringToDate(String uneDate) {
        // Appel de la méthode surchargée avec le format attendu par défaut
        return convertStringToDate(uneDate, "EEE MMM dd HH:mm:ss 'GMT+00:00' yyyy");
    }

    /**
     * Conversion d'une date du format String vers le format Date (avec format personnalisé)
     * @param uneDate au format String
     * @param expectedPattern le format attendu (ex: "yyyy-MM-dd HH:mm:ss")
     * @return la date au format Date
     */
    public static Date convertStringToDate(String uneDate, String expectedPattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern, Locale.FRENCH);
            return formatter.parse(uneDate);
        } catch (ParseException e) {
            Log.d("erreur", "************ ParseException: " + e.toString());
            return null;
        }
    }

    /**
     * Conversion d'une date du format Date vers le format String
     * @param uneDate au format Date
     * @return la date au format String
     */
    public static String convertDateToString(Date uneDate) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date.format(uneDate);
    }

    public static String format2Decimal (Float unevaleur){
        return String.format("%.01f", unevaleur);
    }
}
