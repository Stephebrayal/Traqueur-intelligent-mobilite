package com.example.mobilite.domain.model;

/**
 * Les modes de transport que notre application sait distinguer.
 *
 * Correspondance avec l'API Activity Recognition de Google :
 *  - WALK    <- ON_FOOT (marche)
 *  - BIKE    <- ON_BICYCLE
 *  - VEHICLE <- IN_VEHICLE (bus, voiture, tram : indistinguables par l'API seule,
 *               on pourra les raffiner avec la vitesse et les arrêts fréquents)
 *  - STILL   <- STILL (immobile)
 *  - UNKNOWN : activité non déterminée
 *
 * Fichier volontairement déjà rempli : un enum est trivial et sans dépendance.
 */
public enum TransportMode {
    WALK,
    BIKE,
    VEHICLE,
    STILL,
    UNKNOWN
}
