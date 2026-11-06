package com.example.mobilite.util;

/**
 * Constantes partagées. Les mettre ici évite les "nombres magiques" dispersés dans le code :
 * pour changer un seuil, on ne modifie qu'une ligne.
 *
 * Les seuils de l'algorithme seront ajustés à l'étape 5 avec des tests réels.
 */
public final class Constants {

    private Constants() { } // classe utilitaire : on ne l'instancie jamais

    /** Durée d'immobilité (en minutes) après laquelle on clôture un trajet (valeur du sujet). */
    public static final int STILL_THRESHOLD_MIN = 3;

    /** Intervalle souhaité entre deux positions GPS, en millisecondes. À régler à l'étape 2. */
    public static final long LOCATION_INTERVAL_MS = 5_000L;

    /** Identifiant du canal de notification du service de suivi. */
    public static final String NOTIF_CHANNEL_ID = "tracking_channel";
}
