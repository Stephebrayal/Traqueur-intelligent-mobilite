// Paquet racine de l'application
package com.example.mobilite;

// Application : classe de base dont Android crée UNE instance au lancement du processus
import android.app.Application;
// NotificationChannel : "catégorie" de notifications, obligatoire depuis Android 8 (API 26)
import android.app.NotificationChannel;
// NotificationManager : service système qui gère les notifications et leurs canaux
import android.app.NotificationManager;
// Build : permet de connaître la version d'Android du téléphone
import android.os.Build;

// Constants : nos constantes partagées (ici l'identifiant du canal de notification)
import com.example.mobilite.util.Constants;

/**
 * Classe "Application" : premier code exécuté au lancement du processus de l'app,
 * avant même MainActivity. Elle n'existe qu'une seule fois pour toute la vie du processus.
 *
 * RÔLE (étape 2) : créer le canal de notification utilisé par TrackingService.
 * Sans canal, Android 8+ refuse d'afficher la notification, et un service au premier plan
 * sans notification valide fait planter l'application.
 *
 * POURQUOI ICI : si on créait le canal dans une Activity, il serait recréé à chaque ouverture d'écran.
 *
 * Plus tard : on pourra aussi y initialiser la base Room (étape 4).
 * Déclarée dans AndroidManifest.xml avec android:name=".MobilityApp".
 */
public class MobilityApp extends Application {

    /**
     * Appelée une seule fois, au démarrage du processus de l'application.
     */
    @Override // On redéfinit la méthode onCreate de la classe Application
    public void onCreate() {
        // Appel obligatoire à la version parente
        super.onCreate();
        // Crée le canal de notification du service de suivi
        creerCanalNotification();
    }

    /**
     * Crée le canal de notification "Suivi des déplacements".
     * Si le canal existe déjà, Android ne fait rien : on peut donc l'appeler sans risque à chaque lancement.
     */
    private void creerCanalNotification() {
        // Les canaux n'existent qu'à partir d'Android 8 (API 26). Notre minSdk est 24 :
        // sur Android 7, on ne fait rien (les notifications fonctionnent sans canal).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Crée le canal : identifiant technique, nom visible dans les paramètres du téléphone, importance
            NotificationChannel canal = new NotificationChannel(
                    Constants.NOTIF_CHANNEL_ID,              // identifiant (défini dans Constants)
                    "Suivi des déplacements",                // nom affiché dans Paramètres > Notifications
                    NotificationManager.IMPORTANCE_LOW       // importance basse : pas de son ni de vibration
            );
            // Description affichée sous le nom du canal dans les paramètres
            canal.setDescription("Notification affichée pendant l'enregistrement de vos trajets");

            // Récupère le gestionnaire de notifications du système
            NotificationManager manager = getSystemService(NotificationManager.class);
            // Enregistre le canal auprès du système
            manager.createNotificationChannel(canal);
        }
    }
}
