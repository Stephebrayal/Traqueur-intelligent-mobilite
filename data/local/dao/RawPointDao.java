package com.example.mobilite.data.local.dao;

/**
 * DAO = "Data Access Object" : une interface qui liste les requêtes SQL possibles sur une table.
 * Room génère l'implémentation automatiquement.
 *
 * Requêtes prévues pour les points bruts :
 *  - insérer un point
 *  - lire les points pas encore rattachés à un trajet (tripId IS NULL)
 *  - rattacher une liste de points à un trajet une fois celui-ci détecté
 *
 * IMPORTANT : Room interdit d'exécuter ces requêtes sur le thread principal (l'UI se figerait).
 * On les appellera depuis un ExecutorService (voir TrackingRepository).
 *
 * ÉTAPE : 4.
 */
public class RawPointDao {
    // TODO Étape 4 : devient "public interface RawPointDao" avec @Dao
}
