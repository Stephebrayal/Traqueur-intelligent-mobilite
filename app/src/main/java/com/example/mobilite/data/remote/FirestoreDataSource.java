package com.example.mobilite.data.remote;

/**
 * SEULE classe du projet qui parle à Cloud Firestore.
 *
 * Rôle :
 *  - envoyer un trajet qualifié + ses points bruts
 *    structure : trips/{tripId} et trips/{tripId}/rawPoints/{pointId}
 *  - (admin) lire la liste de TOUS les trajets de tous les utilisateurs
 *
 * POURQUOI L'ISOLER : si demain on change de cloud, on ne modifie que ce fichier.
 * Et les appels Firestore sont asynchrones (callbacks) : ce fichier les masque au reste de l'app.
 *
 * SÉCURITÉ : l'accès réel est protégé par les règles de sécurité Firestore (côté serveur),
 * PAS par ce code. Un booléen "isAdmin" dans l'app ne protège rien.
 *
 * ÉTAPE : 7 (écriture), 8 (lecture admin).
 */
public class FirestoreDataSource {
    // TODO Étape 7
}
