package com.example.coach.vue;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.coach.R;
import com.example.coach.controleur.Controle;
import com.example.coach.vue.HistoActivity;

/**
 * Classe d'affichage du menu
 */
public class MainActivity extends AppCompatActivity {

    private Controle controle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * Initialisations
     */
    private void init(){
        controle = Controle.getInstance();
        creerMenu();;
    }

    /**
     * Demande de créer les écoutes sur les butons du menu
     */
    private void creerMenu(){
        ecouteMenu((ImageButton)findViewById(R.id.btnMonIMG), CalculActivity.class);
         ecouteMenu((ImageButton)findViewById(R.id.btnMonHistorique), HistoActivity.class);
    }

    /**
     * Met en place une écoute événementielle sur une image
     * @param btn l'image "bouton" concernée
     * @param classe la classe correspondant à l'activity à ouvrir sur le clic du bouton
     */
    private void ecouteMenu(ImageButton btn, Class classe){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, classe);
                startActivity(intent);
            }
        });
    }
}