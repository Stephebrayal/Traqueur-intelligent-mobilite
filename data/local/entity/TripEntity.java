package com.example.mobilite.data.local.entity;

/**
 * Table "trips" : UN TRAJET QUALIFIÉ (le "Trip" du sujet).
 *
 * Colonnes prévues :
 *  - id            : clé primaire (on utilisera un identifiant unique de type UUID,
 *                    pour pouvoir l'utiliser aussi comme identifiant de document Firestore)
 *  - userId        : utilisateur propriétaire
 *  - startTime, endTime
 *  - distanceM     : distance totale en mètres
 *  - durationS     : durée en secondes
 *  - dominantMode  : mode dominant (marche, transport...)
 *  - synced        : false tant que le trajet n'est pas envoyé dans Firestore
 *
 * Le champ "synced" est ce qui permet à SyncWorker de savoir quoi envoyer.
 *
 * ÉTAPE : 4 (table), 5 (remplissage par l'algorithme), 7 (synchro).
 */
public class TripEntity {
    // TODO Étape 4
}
