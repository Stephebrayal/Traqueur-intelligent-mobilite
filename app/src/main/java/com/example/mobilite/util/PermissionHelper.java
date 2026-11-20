package com.example.mobilite.util;

/**
 * Centralise la demande des permissions dangereuses, qui doivent être accordées par l'utilisateur
 * pendant l'exécution :
 *  1. localisation précise (ACCESS_FINE_LOCATION)
 *  2. reconnaissance d'activité (ACTIVITY_RECOGNITION)
 *  3. notifications (POST_NOTIFICATIONS, Android 13+)
 *  4. localisation en arrière-plan (ACCESS_BACKGROUND_LOCATION) : doit être demandée
 *     SÉPARÉMENT et APRÈS la localisation au premier plan (règle d'Android 10+).
 *
 * C'est le piège n°1 du projet : sans le bon enchaînement, le suivi s'arrête dès qu'on verrouille l'écran.
 *
 * ÉTAPE : 1.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public final class PermissionHelper {

    private PermissionHelper() { }

    /** Permissions demandables ensemble (tout sauf la localisation en arrière-plan). */
    public static String[] foregroundPermissions() {
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            list.add(Manifest.permission.ACTIVITY_RECOGNITION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        return list.toArray(new String[0]);
    }

    public static boolean hasFineLocation(Context context) {
        return isGranted(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static boolean hasActivityRecognition(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return true;
        return isGranted(context, Manifest.permission.ACTIVITY_RECOGNITION);
    }

    public static boolean hasNotifications(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true;
        return isGranted(context, Manifest.permission.POST_NOTIFICATIONS);
    }

    public static boolean hasBackgroundLocation(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return true;
        return isGranted(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
    }

    private static boolean isGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
