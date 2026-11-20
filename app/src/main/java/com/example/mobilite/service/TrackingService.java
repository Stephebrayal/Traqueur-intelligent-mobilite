// Paquet des services Android de l'application
package com.example.mobilite.service;

// Manifest : contient les noms des permissions (ex. ACCESS_FINE_LOCATION)
import android.Manifest;
// Notification : objet notification affiché dans la barre d'état
import android.app.Notification;
// PendingIntent : "intention différée", ici pour ouvrir l'app quand on touche la notification
import android.app.PendingIntent;
// Service : composant Android qui tourne sans interface
import android.app.Service;
// Intent : message utilisé pour démarrer/arrêter le service
import android.content.Intent;
// ServiceInfo : contient le type de service au premier plan (ici "location")
import android.content.pm.ServiceInfo;
// PackageManager : permet de vérifier si une permission est accordée
import android.content.pm.PackageManager;
// Location : une position GPS (latitude, longitude, vitesse, précision, heure...)
import android.location.Location;
// Build : permet de connaître la version d'Android du téléphone
import android.os.Build;
// IBinder : utilisé pour lier un service à une activité (on ne s'en sert pas ici)
import android.os.IBinder;
// Looper : boucle de messages d'un thread ; on reçoit les positions sur le thread principal
import android.os.Looper;
// Log : écrit des messages dans Logcat
import android.util.Log;

// NonNull : annotation qui indique qu'un paramètre n'est jamais null
import androidx.annotation.NonNull;
// Nullable : annotation qui indique qu'une valeur peut être null
import androidx.annotation.Nullable;
// NotificationCompat : construit des notifications compatibles avec toutes les versions d'Android
import androidx.core.app.NotificationCompat;
// ContextCompat : vérifie les permissions de façon compatible
import androidx.core.content.ContextCompat;

// Classes de la bibliothèque de localisation de Google (play-services-location)
import com.google.android.gms.location.FusedLocationProviderClient; // client qui fournit les positions
import com.google.android.gms.location.LocationCallback;            // reçoit les nouvelles positions
import com.google.android.gms.location.LocationRequest;             // décrit la fréquence et la précision voulues
import com.google.android.gms.location.LocationResult;              // lot de positions reçues
import com.google.android.gms.location.LocationServices;            // point d'entrée pour obtenir le client
import com.google.android.gms.location.Priority;                    // niveaux de précision (haute, équilibrée...)

// Nos classes : l'écran principal (ouvert depuis la notification) et nos constantes
import com.example.mobilite.MainActivity;
import com.example.mobilite.util.Constants;

/**
 * Foreground Service : collecte la position GPS en continu, même écran éteint.
 *
 * POURQUOI UN SERVICE AU PREMIER PLAN : Android tue agressivement les processus en arrière-plan
 * pour économiser la batterie. Un foreground service affiche une notification permanente, et
 * c'est ce qui lui donne le droit de survivre. C'est LA réponse au problème de "gestion des
 * restrictions d'arrière-plan" demandé dans le compte-rendu.
 *
 * Fonctionnement :
 *  - MainActivity envoie un Intent avec l'action ACTION_START ou ACTION_STOP
 *  - START : on affiche la notification, puis on demande des positions à FusedLocationProviderClient
 *  - à chaque position reçue : on l'écrit dans Logcat (étape 4 : on l'enregistrera dans Room)
 *  - STOP : on arrête les positions, on retire la notification et on arrête le service
 *
 * Déclaré dans AndroidManifest.xml avec android:foregroundServiceType="location".
 */
public class TrackingService extends Service {

    // Étiquette pour filtrer nos messages dans Logcat (taper "TrackingService" dans le filtre)
    private static final String TAG = "TrackingService";

    // Action envoyée par MainActivity pour démarrer le suivi (le préfixe évite les collisions de noms)
    public static final String ACTION_START = "com.example.mobilite.action.START_TRACKING";
    // Action envoyée par MainActivity pour arrêter le suivi
    public static final String ACTION_STOP = "com.example.mobilite.action.STOP_TRACKING";

    // Identifiant de la notification du service (n'importe quel entier différent de 0)
    private static final int NOTIFICATION_ID = 1;

    // Client Google qui fusionne GPS, Wi-Fi et réseau mobile pour donner la meilleure position
    private FusedLocationProviderClient fusedClient;
    // Objet qui sera appelé à chaque nouvelle position
    private LocationCallback locationCallback;
    // Vrai si on reçoit déjà des positions (évite de s'abonner deux fois)
    private boolean suiviEnCours = false;

    /**
     * Appelée une seule fois, à la création du service.
     * On prépare ici les objets réutilisés pendant toute la vie du service.
     */
    @Override // On redéfinit onCreate de la classe Service
    public void onCreate() {
        // Appel obligatoire à la version parente
        super.onCreate();
        // Récupère le client de localisation fourni par Google Play Services
        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        // Crée le "callback" : l'objet dont la méthode onLocationResult est appelée à chaque mise à jour
        locationCallback = new LocationCallback() {
            @Override // On redéfinit la méthode appelée quand de nouvelles positions arrivent
            public void onLocationResult(@NonNull LocationResult result) {
                // Un résultat peut contenir plusieurs positions (si Android les a regroupées) : on les parcourt toutes
                for (Location position : result.getLocations()) {
                    // Traite chaque position une par une
                    traiterPosition(position);
                }
            }
        };
    }

    /**
     * Appelée à CHAQUE appel de startService / startForegroundService depuis MainActivity.
     * @param intent  l'Intent envoyé (contient l'action START ou STOP) ; peut être null
     * @param flags   informations supplémentaires du système (non utilisées)
     * @param startId numéro de cette demande de démarrage (non utilisé)
     * @return le comportement souhaité si Android tue le service
     */
    @Override // On redéfinit onStartCommand de la classe Service
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        // Lit l'action contenue dans l'Intent (null si l'Intent est null)
        String action = (intent != null) ? intent.getAction() : null;

        // Si on a demandé l'arrêt : on arrête tout
        if (ACTION_STOP.equals(action)) {
            // Arrête le suivi et le service
            arreterSuivi();
        } else {
            // Sinon (ACTION_START) : on démarre le suivi
            demarrerSuivi();
        }

        // START_NOT_STICKY : si Android tue le service, il ne le relance pas tout seul.
        // Choix simple pour l'instant ; on reverra ce point à l'étape 9 (restrictions batterie).
        return START_NOT_STICKY;
    }

    /**
     * Passe le service au premier plan (avec sa notification), puis s'abonne aux positions GPS.
     */
    private void demarrerSuivi() {
        // Si on reçoit déjà des positions, inutile de recommencer
        if (suiviEnCours) {
            return;
        }

        // Vérifie que la permission de localisation précise est accordée.
        // Sans elle, demander des positions provoquerait une SecurityException (plantage).
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Signale le problème dans Logcat
            Log.w(TAG, "Permission de localisation absente : suivi impossible");
            // Arrête le service, puisqu'il ne peut rien faire
            stopSelf();
            // Sort de la méthode
            return;
        }

        // Passe le service au premier plan : affiche la notification et déclare le type "location".
        // OBLIGATOIRE dans les secondes qui suivent startForegroundService, sinon Android plante l'app.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ : on précise le type de service au premier plan ("location")
            startForeground(NOTIFICATION_ID, creerNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            // Android 7 à 9 : la notion de type n'existe pas encore
            startForeground(NOTIFICATION_ID, creerNotification());
        }

        // Décrit ce qu'on veut recevoir : haute précision (GPS), une position toutes les LOCATION_INTERVAL_MS
        LocationRequest requete = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,                 // précision maximale (utilise le GPS)
                Constants.LOCATION_INTERVAL_MS)                  // intervalle souhaité : 5 secondes
                .setMinUpdateIntervalMillis(Constants.LOCATION_INTERVAL_MS / 2) // jamais plus d'une position toutes les 2,5 s
                .build();                                        // construit la requête

        // S'abonne aux positions : locationCallback sera appelé sur le thread principal (Looper.getMainLooper())
        fusedClient.requestLocationUpdates(requete, locationCallback, Looper.getMainLooper());

        // Mémorise que le suivi est actif
        suiviEnCours = true;
        // Trace dans Logcat
        Log.d(TAG, "Suivi GPS démarré");
    }

    /**
     * Se désabonne des positions, retire la notification et arrête le service.
     */
    private void arreterSuivi() {
        // Se désabonne des positions GPS (économise la batterie)
        fusedClient.removeLocationUpdates(locationCallback);
        // Mémorise que le suivi est arrêté
        suiviEnCours = false;
        // Quitte le premier plan et retire la notification
        stopForeground(STOP_FOREGROUND_REMOVE);
        // Arrête complètement le service (Android appellera ensuite onDestroy)
        stopSelf();
        // Trace dans Logcat
        Log.d(TAG, "Suivi GPS arrêté");
    }

    /**
     * Traite UNE position GPS reçue.
     * @param position la position fournie par FusedLocationProviderClient
     */
    private void traiterPosition(Location position) {
        // Pour l'instant : on affiche la position dans Logcat pour vérifier que la collecte fonctionne
        Log.d(TAG, "Position : lat=" + position.getLatitude()      // latitude en degrés
                + " lon=" + position.getLongitude()                 // longitude en degrés
                + " vitesse=" + position.getSpeed() + " m/s"        // vitesse mesurée par le GPS
                + " précision=" + position.getAccuracy() + " m"     // rayon d'incertitude en mètres
                + " heure=" + position.getTime());                  // millisecondes depuis le 01/01/1970

        // TODO Étape 4 : enregistrer la position dans Room via TrackingRepository
    }

    /**
     * Construit la notification permanente affichée pendant le suivi.
     * @return la notification prête à être affichée
     */
    private Notification creerNotification() {
        // Intent qui ouvre MainActivity quand l'utilisateur touche la notification
        Intent ouvrirApp = new Intent(this, MainActivity.class);
        // L'enveloppe dans un PendingIntent (exécuté plus tard par le système).
        // FLAG_IMMUTABLE est obligatoire depuis Android 12.
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, ouvrirApp, PendingIntent.FLAG_IMMUTABLE);

        // Construit la notification sur le canal créé dans MobilityApp
        return new NotificationCompat.Builder(this, Constants.NOTIF_CHANNEL_ID)
                .setContentTitle("Suivi des déplacements actif")             // titre
                .setContentText("Vos trajets sont en cours d'enregistrement") // texte
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)         // icône système (en attendant la nôtre)
                .setContentIntent(pendingIntent)                             // action au toucher
                .setOngoing(true)                                            // ne peut pas être balayée
                .build();                                                    // construit l'objet Notification
    }

    /**
     * Appelée quand le service est détruit : on s'assure de ne plus recevoir de positions.
     */
    @Override // On redéfinit onDestroy de la classe Service
    public void onDestroy() {
        // Se désabonne des positions par sécurité (au cas où le service serait détruit sans ACTION_STOP)
        fusedClient.removeLocationUpdates(locationCallback);
        // Appel obligatoire à la version parente
        super.onDestroy();
    }

    /**
     * Utilisé pour lier une activité au service (bound service). On n'en a pas besoin :
     * notre service est seulement démarré/arrêté par des Intents, donc on renvoie null.
     */
    @Nullable // Indique que la méthode peut renvoyer null
    @Override // On redéfinit onBind de la classe Service
    public IBinder onBind(Intent intent) {
        // Pas de liaison possible
        return null;
    }
}
