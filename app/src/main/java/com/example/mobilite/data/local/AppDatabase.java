package com.example.mobilite.data.local;

/**
 * Point d'entrée de Room (la couche d'accès à SQLite).
 *
 * RÔLE : classe abstraite annotée @Database(entities = {...}, version = 1) qui
 *  - déclare les tables (les entités)
 *  - expose les DAO (rawPointDao(), tripDao())
 *  - existe en UN SEUL exemplaire (singleton), car ouvrir plusieurs connexions est coûteux
 *
 * POURQUOI ROOM : stockage "offline-first". Un trajet fait sans réseau ne doit jamais être perdu :
 * on enregistre toujours en local d'abord, on synchronise ensuite.
 *
 * ÉTAPE : 4. Nécessite la dépendance Room (ajoutée à ce moment-là).
 */
public class AppDatabase {
    // TODO Étape 4 : abstract class AppDatabase extends RoomDatabase
}
