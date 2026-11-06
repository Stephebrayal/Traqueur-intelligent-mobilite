package com.example.mobilite.service;

/**
 * Foreground Service : collecte la position GPS en continu, même écran éteint.
 *
 * POURQUOI UN SERVICE AU PREMIER PLAN : Android tue agressivement les processus en arrière-plan
 * pour économiser la batterie. Un foreground service affiche une notification permanente, et
 * c'est ce qui lui donne le droit de survivre. C'est LA réponse au problème de "gestion des
 * restrictions d'arrière-plan" demandé dans le compte-rendu.
 *
 * Rôle :
 *  - démarrer avec une notification ("Suivi en cours")
 *  - demander des positions à FusedLocationProviderClient
 *  - passer chaque point à TrackingRepository
 *  - s'arrêter proprement
 *
 * ÉTAPE : 2. À déclarer dans AndroidManifest.xml avec le type "location".
 */
public class TrackingService {
    // TODO Étape 2 : extends android.app.Service
}
