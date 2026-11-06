package com.example.mobilite.sync;

/**
 * Tâche de fond WorkManager : envoie dans Firestore les trajets pas encore synchronisés.
 *
 * POURQUOI WORKMANAGER : il garantit l'exécution même si l'app est fermée ou le téléphone
 * redémarré, avec réessai automatique si le réseau est absent. Un simple thread ne le permet pas.
 *
 * Déroulé :
 *  1. lire dans Room les trajets avec synced = false
 *  2. pour chacun, l'envoyer avec ses points bruts (FirestoreDataSource)
 *  3. si succès, le marquer synced = true
 *  4. si échec réseau, demander un réessai plus tard
 *
 * ÉTAPE : 7.
 */
public class SyncWorker {
    // TODO Étape 7 : extends androidx.work.Worker
}
