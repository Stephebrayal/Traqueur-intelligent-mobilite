// Paquet racine de l'application
package com.example.mobilite;

// Manifest : contient les noms des permissions
import android.Manifest;
// Intent : message envoyé au service pour le démarrer ou l'arrêter
import android.content.Intent;
// PackageManager : permet de vérifier si une permission est accordée
import android.content.pm.PackageManager;
// Bundle : état sauvegardé de l'écran
import android.os.Bundle;
// Button : bouton cliquable
import android.widget.Button;
// TextView : zone de texte
import android.widget.TextView;
// Toast : petit message temporaire en bas de l'écran
import android.widget.Toast;

// EdgeToEdge : affichage plein écran, sous les barres système
import androidx.activity.EdgeToEdge;
// AppCompatActivity : classe de base d'un écran, compatible avec les anciennes versions
import androidx.appcompat.app.AppCompatActivity;
// ContextCompat : démarre un service au premier plan et vérifie les permissions de façon compatible
import androidx.core.content.ContextCompat;
// Insets, ViewCompat, WindowInsetsCompat : gestion des marges des barres système
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Notre service de suivi GPS
import com.example.mobilite.service.TrackingService;

/**
 * Écran principal (version provisoire de l'étape 2).
 * Il permet de démarrer et d'arrêter TrackingService avec deux boutons.
 * La vraie interface (liste des trajets, carte...) arrivera à l'étape 6.
 */
public class MainActivity extends AppCompatActivity {

    // Texte qui affiche l'état du suivi
    private TextView texteEtat;

    /**
     * Appelée par Android à la création de l'écran.
     * @param savedInstanceState état précédent de l'écran (null au premier lancement)
     */
    @Override // On redéfinit onCreate de la classe parente
    protected void onCreate(Bundle savedInstanceState) {
        // Appel obligatoire à la version parente
        super.onCreate(savedInstanceState);
        // Active l'affichage plein écran
        EdgeToEdge.enable(this);
        // Associe l'écran à son interface res/layout/activity_main.xml
        setContentView(R.layout.activity_main);

        // Ajuste les marges de la vue principale pour que rien ne soit caché sous les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Récupère la taille des barres système
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Applique ces tailles en marges intérieures
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Renvoie les insets pour les autres vues
            return insets;
        });

        // Récupère les vues déclarées dans le layout grâce à leur id
        texteEtat = findViewById(R.id.texteEtat);
        Button boutonDemarrer = findViewById(R.id.boutonDemarrer);
        Button boutonArreter = findViewById(R.id.boutonArreter);

        // Au clic sur "Démarrer", appelle demarrerSuivi()
        boutonDemarrer.setOnClickListener(v -> demarrerSuivi());
        // Au clic sur "Arrêter", appelle arreterSuivi()
        boutonArreter.setOnClickListener(v -> arreterSuivi());

        // TODO Étape 1 (Stephe) : demander les permissions au lancement, par exemple :
        // PermissionHelper.demanderToutes(this);
    }

    /**
     * Démarre le service de suivi GPS, si la permission de localisation est accordée.
     */
    private void demarrerSuivi() {
        // Vérifie la permission de localisation précise
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // En attendant l'étape 1, on demande à l'utilisateur de l'accorder à la main
            Toast.makeText(this,
                    "Accordez la localisation : Paramètres > Applications > Mobilite > Autorisations",
                    Toast.LENGTH_LONG).show();
            // Sort de la méthode sans démarrer le service
            return;
        }

        // Crée l'Intent qui cible TrackingService, avec l'action "démarrer"
        Intent intent = new Intent(this, TrackingService.class);
        intent.setAction(TrackingService.ACTION_START);
        // Démarre le service en mode "premier plan" (startForegroundService sur Android 8+, startService avant)
        ContextCompat.startForegroundService(this, intent);
        // Met à jour le texte d'état
        texteEtat.setText("Suivi en cours…");
    }

    /**
     * Demande au service de s'arrêter.
     */
    private void arreterSuivi() {
        // Crée l'Intent qui cible TrackingService, avec l'action "arrêter"
        Intent intent = new Intent(this, TrackingService.class);
        intent.setAction(TrackingService.ACTION_STOP);
        // Envoie l'Intent au service (il s'arrêtera lui-même dans onStartCommand)
        startService(intent);
        // Met à jour le texte d'état
        texteEtat.setText("Suivi arrêté");
    }
}
