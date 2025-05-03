package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.coach.R;
import com.example.coach.controleur.Controle;
import com.example.coach.modele.Profil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe d'affichage de la liste interactive des profils
 */
public class HistoActivity extends AppCompatActivity {

    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);
        init();
    }

    /**
     * Initialisation complémentaire de l'activity
     */
    private void init(){
        controle = Controle.getInstance();
        creerListe();
    }

    /**
     * Création de la liste à afficher
     */
    private void creerListe(){
        ArrayList<Profil> lesprofils = controle.getLesProfils();
        if(lesprofils != null){
            Collections.sort(lesprofils, Collections.<Profil>reverseOrder());
            RecyclerView lstHisto = (RecyclerView) findViewById(R.id.lstHisto) ;
            HistoListAdapter adapter = new HistoListAdapter(HistoActivity.this, lesprofils);
            lstHisto.setAdapter(adapter);
            lstHisto.setLayoutManager(new LinearLayoutManager(HistoActivity.this));
        }
    }

    /**
     * Appelé automatiquement quand on revient sur cette activité
     */
    @Override
    protected void onResume() {
        super.onResume();
        creerListe(); // Actualise la liste affichée
    }

    /**
     * Demande d'affichage des informations du profil, dans CalculActivity
     * @param profil
     */
    public void afficheProfil(Profil profil){
        Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
        intent.putExtra("profil", profil);
        startActivity(intent);
    }
}
