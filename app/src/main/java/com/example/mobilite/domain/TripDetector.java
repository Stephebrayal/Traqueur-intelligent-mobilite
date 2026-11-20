package com.example.mobilite.domain;

/**
 * LE CŒUR DU PROJET, partie 1 : découper un flux de points en TRAJETS.
 *
 * Règle principale (heuristique du sujet) :
 *  - un trajet commence quand l'utilisateur se met en mouvement
 *  - un trajet se termine après une période d'immobilité (STILL) de plus de X minutes (3 min dans le sujet)
 *
 * Il faudra aussi gérer :
 *  - le bruit GPS (points avec mauvaise précision, sauts impossibles)
 *  - les pauses courtes (feu rouge, arrêt de bus) qui NE doivent PAS clore le trajet
 *  - les trous de signal
 *
 * Java pur, sans import android.* : testable avec JUnit.
 *
 * ÉTAPE : 5.
 */
public class TripDetector {
    // TODO Étape 5
}
