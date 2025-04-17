package com.example.coach.modele;

import android.util.Log;

import com.example.coach.controleur.Controle;
import com.example.coach.outils.AccesHTTP;
import com.example.coach.outils.AsyncResponse;
import com.example.coach.outils.MesOutils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Classe gérant les demandes d'envoi et de réception par rapport au serveur distant
 */
public class AccesDistant implements AsyncResponse {

    private static final String SERVERADDR = "http://10.0.2.2/coach/serveurcoach.php";
    private static AccesDistant instance;
    private Controle controle;

    /**
     * Constructeur privé
     */
    private AccesDistant() {
        controle = Controle.getInstance(null);
    }

    /**
     * Création d'une instance unique de la classe
     */
    public static AccesDistant getInstance() {
        if (instance == null) {
            instance = new AccesDistant();
        }
        return instance;
    }

    /**
     * Retour du serveur distant
     */
    @Override
    public void processFinish(String output, String message, String result) {
        Log.d("serveur", "************ " + output);

        try {
            JSONObject retour = new JSONObject(output);
            String code = retour.getString("code");
            message = retour.getString("message");
            result = retour.getString("result");

            Log.d("serveur", "code = " + code);
            Log.d("serveur", "message = " + message);
            Log.d("serveur", "result = " + result);

            if (message.equals("dernier")) {
                if (!result.equals("")) {
                    JSONObject info = new JSONObject(result);
                    Integer poids = info.getInt("poids");
                    Integer taille = info.getInt("taille");
                    Integer age = info.getInt("age");
                    Integer sexe = info.getInt("sexe");

                    // Utilisation d’un format de date MySQL : "yyyy-MM-dd HH:mm:ss"
                    Date dateMesure = MesOutils.convertStringToDate(info.getString("datemesure"), "yyyy-MM-dd HH:mm:ss");

                    Profil profil = new Profil(dateMesure, poids, taille, age, sexe);
                    controle.setProfil(profil);
                } else {
                    Log.d("serveur", "************ Aucun profil trouvé en base !");
                }
            }

        } catch (JSONException e) {
            Log.d("erreur", "************ JSON Exception: " + e.toString());
        }
    }

    /**
     * Envoi vers le serveur distant
     */
    public void envoi(String operation, JSONObject lesDonneesJSON) {
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        accesDonnees.execute(SERVERADDR);
    }
}
