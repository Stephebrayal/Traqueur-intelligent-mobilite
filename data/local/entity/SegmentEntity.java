package com.example.mobilite.data.local.entity;

/**
 * Table "segments" : UN TRONÇON D'UN TRAJET AVEC UN SEUL MODE.
 *
 * Exemple du sujet : un trajet = Marche (5 min) + Bus (10 min) + Marche (3 min)
 * -> 1 TripEntity + 3 SegmentEntity.
 *
 * Colonnes prévues :
 *  - id, tripId (lien vers le trajet)
 *  - mode          : WALK / VEHICLE / ...
 *  - startTime, endTime
 *  - distanceM
 *
 * ÉTAPE : 4 (table), 5 (remplissage par ModeClassifier).
 */
public class SegmentEntity {
    // TODO Étape 4
}
