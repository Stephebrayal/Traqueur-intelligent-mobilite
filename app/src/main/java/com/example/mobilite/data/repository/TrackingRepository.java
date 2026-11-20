package com.example.mobilite.data.repository;

/**
 * Intermédiaire entre le service de collecte et la base locale (points bruts).
 *
 * Rôle : TrackingService lui dit "voici un point", et lui s'occupe de l'enregistrer
 * dans Room sur un thread secondaire (ExecutorService).
 *
 * POURQUOI UN REPOSITORY : le service n'a pas à connaître Room ni les threads.
 * Le motif Repository sépare "qui a besoin de données" de "où elles sont stockées".
 *
 * ÉTAPE : 4.
 */
public class TrackingRepository {
    // TODO Étape 4
}
