package com.example.mobilite.util;

/**
 * Centralise la demande des permissions dangereuses, qui doivent être accordées par l'utilisateur
 * pendant l'exécution :
 *  1. localisation précise (ACCESS_FINE_LOCATION)
 *  2. reconnaissance d'activité (ACTIVITY_RECOGNITION)
 *  3. notifications (POST_NOTIFICATIONS, Android 13+)
 *  4. localisation en arrière-plan (ACCESS_BACKGROUND_LOCATION) : doit être demandée
 *     SÉPARÉMENT et APRÈS la localisation au premier plan (règle d'Android 10+).
 *
 * C'est le piège n°1 du projet : sans le bon enchaînement, le suivi s'arrête dès qu'on verrouille l'écran.
 *
 * ÉTAPE : 1.
 */
public class PermissionHelper {
    // TODO Étape 1
}
