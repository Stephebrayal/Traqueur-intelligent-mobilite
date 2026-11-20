package com.example.mobilite.data.repository;

/**
 * Source unique de vérité pour les trajets.
 *
 * Rôle :
 *  - fournir aux ViewModels la liste des trajets (LiveData depuis Room)
 *  - déclencher la clôture d'un trajet (appel à TripDetector / ModeClassifier)
 *  - planifier la synchronisation (SyncWorker)
 *  - côté admin : récupérer tous les trajets via FirestoreDataSource
 *
 * Les écrans (UI) ne parlent QU'À ce repository, jamais directement à Room ou Firestore.
 *
 * ÉTAPE : 6 (lecture pour l'UI), 7 (synchro), 8 (admin).
 */
public class TripRepository {
    // TODO Étape 6
}
