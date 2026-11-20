package com.example.mobilite.service;

/**
 * Reçoit les changements d'activité envoyés par l'API Activity Recognition de Google
 * (ON_FOOT, IN_VEHICLE, STILL...). Ce sont des BroadcastReceiver : Android les appelle quand
 * un événement se produit.
 *
 * Rôle : mémoriser la dernière activité connue, pour l'associer aux points GPS suivants
 * (champ activityType de RawPointEntity).
 *
 * ATTENTION : ne fonctionne pas sur émulateur, voir SimulatedActivityProvider.
 *
 * ÉTAPE : 3. Nécessite la permission ACTIVITY_RECOGNITION (demandée à l'exécution).
 */
public class ActivityReceiver {
    // TODO Étape 3 : extends BroadcastReceiver
}
