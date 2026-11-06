package com.example.mobilite.data.local.dao;

/**
 * DAO des trajets et segments.
 *
 * Requêtes prévues :
 *  - insérer un trajet et ses segments
 *  - récupérer la liste des trajets sous forme de LiveData : l'écran se met à jour
 *    tout seul quand la table change
 *  - récupérer les trajets non synchronisés (synced = 0) pour SyncWorker
 *  - marquer un trajet comme synchronisé
 *
 * ÉTAPE : 4 (insertion/lecture), 7 (requêtes de synchro).
 */
public class TripDao {
    // TODO Étape 4 : devient "public interface TripDao" avec @Dao
}
