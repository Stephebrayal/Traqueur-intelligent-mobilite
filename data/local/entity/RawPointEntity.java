package com.example.mobilite.data.local.entity;

/**
 * Table "raw_points" : UNE LIGNE = UN POINT GPS BRUT, tel que reçu du capteur.
 *
 * Colonnes prévues :
 *  - id            : clé primaire auto-incrémentée
 *  - tripId        : trajet auquel le point appartient (NULL tant que le trajet n'est pas clôturé)
 *  - latitude, longitude
 *  - speed         : vitesse en m/s fournie par le GPS
 *  - accuracy      : précision en mètres (sert à filtrer les points aberrants)
 *  - timestamp     : instant de la mesure (millisecondes depuis 1970)
 *  - activityType  : dernière activité connue (ON_FOOT, IN_VEHICLE, STILL...)
 *
 * POURQUOI GARDER LE BRUT : le sujet exige de stocker les données brutes ET l'itinéraire qualifié.
 * Garder le brut permet aussi de re-calculer les trajets si on améliore l'algorithme.
 *
 * ÉTAPE : 4.
 */
public class RawPointEntity {
    // TODO Étape 4 : annoter @Entity, ajouter les champs
}
