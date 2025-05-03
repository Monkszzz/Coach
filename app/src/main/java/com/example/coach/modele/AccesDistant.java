package com.example.coach.modele;

import android.util.Log;

import com.example.coach.controleur.Controle;
import com.example.coach.outils.AccesHTTP;
import com.example.coach.outils.AsyncResponse;
import com.example.coach.outils.MesOutils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe gérant les demandes d'envoi et de réception par rapport au serveur distant
 * (page PHP qui exploite la BDD au format MySQL)
 */
public class AccesDistant implements AsyncResponse {

    private static final String SERVERADDR = "http://192.168.0.16/coach/serveurcoach.php";
    private static AccesDistant instance;
    private Controle controle;

    /**
     * Constructeur privé
     */
    private AccesDistant(){
        controle = Controle.getInstance();
    }

    /**
     * Création d'une instance unique de la classe
     * @return instance unique de la classe
     */
    public static AccesDistant getInstance(){
        if(instance == null){
            instance = new AccesDistant();
        }
        return instance;
    }

    /**
     * Retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output, String message, String result) {
        Log.d("serveur", "************ " + output);
        try {
            JSONObject retour = new JSONObject(output);
            String code = retour.getString("code");
            String retourMessage = retour.getString("message");
            String retourResult = retour.getString("result");

            if (!code.equals("200")) {
                Log.d("erreur", "************ retour serveur code=" + code + " result=" + retourResult);
            } else {
                if (retourMessage.equals("tous")) {
                    JSONArray resultJson = new JSONArray(retourResult);
                    ArrayList<Profil> lesProfils = new ArrayList<>();
                    for (int k = 0; k < resultJson.length(); k++) {
                        JSONObject info = new JSONObject(resultJson.get(k).toString());
                        Integer poids = info.getInt("poids");
                        Integer taille = info.getInt("taille");
                        Integer age = info.getInt("age");
                        Integer sexe = info.getInt("sexe");
                        Date dateMesure = MesOutils.convertStringToDate(info.getString("datemesure"),
                                "yyyy-MM-dd hh:mm:ss");
                        Profil profil = new Profil(dateMesure, poids, taille, age, sexe);
                        lesProfils.add(profil);
                    }
                    controle.setLesProfils(lesProfils);
                }
            }
        } catch (JSONException e) {
            Log.d("erreur", "************ output n'est pas au format JSON");
        }
    }


    /**
     * Envoi vers le serveur distant
     * @param operation
     * @param lesDonneesJSON
     */
    public void envoi(String operation, JSONObject lesDonneesJSON){
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        accesDonnees.execute(SERVERADDR);
    }

}