package com.example.mobilite.service;

/**
 * Fournisseur d'activité SIMULÉ, pour travailler sans téléphone physique.
 *
 * Sur l'émulateur, l'API Activity Recognition ne renvoie rien de réel. Ce composant produit à la
 * place une activité plausible (par exemple déduite de la vitesse du point GPS simulé, ou choisie
 * à la main depuis un petit panneau de debug).
 *
 * Idée d'architecture : une même interface "source d'activité", deux implémentations
 *  - la vraie (ActivityReceiver) sur téléphone
 *  - celle-ci sur émulateur
 * Le reste de l'app ne sait pas laquelle est utilisée.
 *
 * ÉTAPE : 3. À retirer ou désactiver pour la version finale.
 */
public class SimulatedActivityProvider {
    // TODO Étape 3
}
