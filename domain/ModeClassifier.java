package com.example.mobilite.domain;

/**
 * LE CŒUR DU PROJET, partie 2 : attribuer un MODE à chaque segment d'un trajet.
 *
 * Entrées : les points d'un trajet (vitesse, activité reconnue, timing).
 * Sorties : une liste de SegmentData + le mode dominant (celui de plus longue durée).
 *
 * Approche retenue : HEURISTIQUES (règles lisibles) plutôt que Machine Learning.
 * Exemples de règles : vitesse moyenne faible + activité ON_FOOT = marche ;
 * vitesse élevée ou IN_VEHICLE = véhicule ; lissage pour éviter qu'un segment de 5 secondes
 * "clignote" entre deux modes.
 *
 * ÉTAPE : 5.
 */
public class ModeClassifier {
    // TODO Étape 5
}
