package com.example.mobilite;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilite.util.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;

    // Lanceur 1 : demande groupée (localisation, activité, notifications)
    private final ActivityResultLauncher<String[]> foregroundLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> onForegroundResult());

    // Lanceur 2 : demande seule de la localisation en arrière-plan
    private final ActivityResultLauncher<String> backgroundLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> refreshStatus());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startPermissionFlow());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshStatus(); // à jour aussi après un retour depuis les paramètres
    }

    private void startPermissionFlow() {
        if (PermissionHelper.hasFineLocation(this)) {
            askBackgroundIfNeeded();
        } else {
            foregroundLauncher.launch(PermissionHelper.foregroundPermissions());
        }
    }

    private void onForegroundResult() {
        refreshStatus();
        if (PermissionHelper.hasFineLocation(this)) {
            askBackgroundIfNeeded();
        } else {
            showSettingsDialog();
        }
    }

    private void askBackgroundIfNeeded() {
        if (PermissionHelper.hasBackgroundLocation(this)) return;

        new AlertDialog.Builder(this)
                .setTitle("Suivi en arrière-plan")
                .setMessage("Pour enregistrer vos trajets écran verrouillé, choisissez "
                        + "« Toujours autoriser » à l'écran suivant.")
                .setPositiveButton("Continuer", (d, w) ->
                        backgroundLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
                .setNegativeButton("Plus tard", null)
                .show();
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Localisation nécessaire")
                .setMessage("Sans la localisation précise, l'app ne peut pas enregistrer "
                        + "vos trajets. Vous pouvez l'activer dans les paramètres.")
                .setPositiveButton("Ouvrir les paramètres", (d, w) -> openAppSettings())
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    private void refreshStatus() {
        statusText.setText(
                "Localisation précise : " + label(PermissionHelper.hasFineLocation(this)) + "\n"
                        + "Localisation arrière-plan : " + label(PermissionHelper.hasBackgroundLocation(this)) + "\n"
                        + "Reconnaissance d'activité : " + label(PermissionHelper.hasActivityRecognition(this)) + "\n"
                        + "Notifications : " + label(PermissionHelper.hasNotifications(this)));
    }

    private String label(boolean granted) {
        return granted ? "✅ accordée" : "❌ refusée";
    }
}