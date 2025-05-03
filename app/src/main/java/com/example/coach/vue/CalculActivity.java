package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coach.R;
import com.example.coach.controleur.Controle;
import com.example.coach.modele.Profil;
import com.example.coach.outils.MesOutils;

/**
 * Classe d'affichage (activity) CalculActivity
 * Permet le calcul d'un IMG
 */
public class CalculActivity extends AppCompatActivity {

    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblIMG;
    private ImageView imgSmiley;
    private Button btnCalc;
    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);
        init();
    }

    /**
     * Initialisations à l'ouverture :
     * - récupération des objets graphiques
     * - création du contrôleur
     * - demande d'écoute (évenements sur objets graphiques)
     */
    private void init() {
        txtPoids = (EditText) findViewById(R.id.txtPoids) ;
        txtTaille = (EditText) findViewById(R.id.txtTaille);
        txtAge = (EditText) findViewById(R.id.txtAge);
        rdHomme = (RadioButton) findViewById(R.id.rdHomme);
        rdFemme = (RadioButton) findViewById(R.id.rdFemme);
        lblIMG = (TextView) findViewById(R.id.lblIMG);
        imgSmiley = (ImageView) findViewById(R.id.imgSmiley);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        controle = Controle.getInstance();
        ecouteCalcul();
        recupProfil();
    }

    /**
     * Ecoute l'événement clic sur le bouton btnCalc
     */
    private void ecouteCalcul() {
        btnCalc.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // cache le clavier numérique
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnCalc.getWindowToken(), 0);
                // récupère les informations et affiche le résultat
                Integer poids = 0, taille = 0, age = 0, sexe = 0;
                try {
                    poids = Integer.parseInt(txtPoids.getText().toString());
                    taille = Integer.parseInt(txtTaille.getText().toString());
                    age = Integer.parseInt(txtAge.getText().toString());
                }catch(Exception e){}
                if(rdHomme.isChecked()) {
                    sexe = 1;
                }
                if(poids == 0 || taille == 0 || age == 0){
                    Toast.makeText(CalculActivity.this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                    afficheResult(poids, taille, age, sexe);
                }
            }
        });
    }

    /**
     * Affiche l'image et le message correspondant au calcul de l'img
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    private void afficheResult(Integer poids, Integer taille, Integer age, Integer sexe) {
        controle.creerProfil(poids, taille, age, sexe);
        float img = controle.getImg();
        String message = controle.getMessage();
        switch(message){
            case "normal":
                imgSmiley.setImageResource(R.drawable.normal);
                lblIMG.setTextColor(Color.parseColor("#228B22"));
                break;
            case "trop faible":
                imgSmiley.setImageResource(R.drawable.maigre);
                lblIMG.setTextColor(Color.RED);
                break;
            case "trop élevé":
                imgSmiley.setImageResource(R.drawable.graisse);
                lblIMG.setTextColor(Color.RED);
                break;
        }
        lblIMG.setText(MesOutils.format2Decimal(img)+" : IMG "+message);
    }

    /**
     * Récupère les informations d'un profil et les afiche
     */
    public void recupProfil(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Profil profil = (Profil) bundle.get("profil");
            txtPoids.setText(profil.getPoids().toString());
            txtTaille.setText(profil.getTaille().toString());
            txtAge.setText(profil.getAge().toString());
            if (profil.getSexe() == 0) {
                rdFemme.setChecked(true);
            } else {
                rdHomme.setChecked(true);
            }
        }
    }
}