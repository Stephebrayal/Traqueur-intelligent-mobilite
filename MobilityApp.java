package com.example.mobilite;

/**
 * Classe "Application" : premier code exécuté au lancement du processus de l'app,
 * avant même MainActivity. Elle n'existe qu'une seule fois pour toute la vie du processus.
 *
 * RÔLE : initialisations globales, faites une seule fois :
 *  - créer le canal de notification du service de tracking (obligatoire depuis Android 8)
 *  - initialiser ce qui doit l'être globalement (base Room, Firebase)
 *
 * POURQUOI ICI : si on créait le canal dans une Activity, il serait recréé à chaque ouverture d'écran.
 *
 * ÉTAPE : 0 (squelette), 2 (canal de notification).
 * À faire plus tard : "extends android.app.Application" + onCreate(),
 * puis déclarer dans AndroidManifest.xml : android:name=".MobilityApp"
 */
public class MobilityApp {
    // TODO Étape 2
}
