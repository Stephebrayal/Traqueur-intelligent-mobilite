package com.example.mobilite.domain.model;

/**
 * Un segment calculé par l'algorithme : (mode, début, fin, distance).
 *
 * POURQUOI UN OBJET À PART DE SegmentEntity : le package "domain" ne doit pas dépendre
 * d'Android ni de Room, pour pouvoir être testé avec de simples tests JUnit sur ordinateur.
 * On convertira SegmentData <-> SegmentEntity dans la couche data.
 *
 * ÉTAPE : 5.
 */
public class SegmentData {
    // TODO Étape 5 : champs mode, startTime, endTime, distanceM + getters
}
