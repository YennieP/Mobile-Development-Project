package edu.northeastern.numad26sp_yanxipan;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private static final String KEY_TOTAL_DISTANCE = "total_distance";
    private static final String KEY_LAST_LAT       = "last_lat";
    private static final String KEY_LAST_LON       = "last_lon";

    private TextView tvLatLon;
    private TextView tvDistance;
    private Button   btnReset;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private float   totalDistance = 0f;   // metres
    private float   lastLat       = Float.NaN;
    private float   lastLon       = Float.NaN;
    private boolean listenerRegistered = false;

    // ── Permission launcher ───────────────────────────────────────────────────

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> {
                        Boolean fine   = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarse = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (Boolean.TRUE.equals(fine) || Boolean.TRUE.equals(coarse)) {
                            startLocationUpdates();
                        } else {
                            tvLatLon.setText("Location permission denied.");
                        }
                    });

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tvLatLon   = findViewById(R.id.tv_lat_lon);
        tvDistance = findViewById(R.id.tv_distance);
        btnReset   = findViewById(R.id.btn_reset);

        // Restore state after rotation
        if (savedInstanceState != null) {
            totalDistance = savedInstanceState.getFloat(KEY_TOTAL_DISTANCE, 0f);
            lastLat       = savedInstanceState.getFloat(KEY_LAST_LAT, Float.NaN);
            lastLon       = savedInstanceState.getFloat(KEY_LAST_LON, Float.NaN);
        }
        updateDistanceDisplay();

        btnReset.setOnClickListener(v -> {
            totalDistance = 0f;
            lastLat       = Float.NaN;
            lastLon       = Float.NaN;
            updateDistanceDisplay();
            Toast.makeText(this, "Distance reset.", Toast.LENGTH_SHORT).show();
        });

        locationManager  = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = buildLocationListener();

        requestLocationPermission();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(KEY_TOTAL_DISTANCE, totalDistance);
        outState.putFloat(KEY_LAST_LAT, lastLat);
        outState.putFloat(KEY_LAST_LON, lastLon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-register listener if permission already granted (e.g. after rotation)
        if (hasLocationPermission() && !listenerRegistered) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    // ── Back button confirmation ──────────────────────────────────────────────

    @Override
    public void onBackPressed() {
        showExitConfirmation();
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Leave Location Tracker?")
                .setMessage("Your total distance record will be lost. Are you sure you want to leave?")
                .setPositiveButton("Leave", (dialog, which) -> finish())
                .setNegativeButton("Stay", null)
                .show();
    }

    // ── Permission ────────────────────────────────────────────────────────────

    private void requestLocationPermission() {
        if (hasLocationPermission()) {
            startLocationUpdates();
        } else {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // ── Location updates ──────────────────────────────────────────────────────

    private LocationListener buildLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                tvLatLon.setText(String.format(Locale.US,
                        "Lat: %.6f°\nLon: %.6f°", lat, lon));

                // Accumulate distance
                if (!Float.isNaN(lastLat) && !Float.isNaN(lastLon)) {
                    float[] result = new float[1];
                    Location.distanceBetween(lastLat, lastLon, lat, lon, result);
                    totalDistance += result[0];
                    updateDistanceDisplay();
                }
                lastLat = (float) lat;
                lastLon = (float) lon;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                tvLatLon.setText("Location provider disabled.");
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                tvLatLon.setText("Waiting for location...");
            }
        };
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (listenerRegistered) return;
        // minTime=2000ms, minDistance=0m — update as frequently as possible
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2000, 0f, locationListener);
        // Fallback to network provider
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 2000, 0f, locationListener);
        listenerRegistered = true;
        tvLatLon.setText("Waiting for location...");
    }

    private void stopLocationUpdates() {
        if (listenerRegistered && locationManager != null) {
            locationManager.removeUpdates(locationListener);
            listenerRegistered = false;
        }
    }

    // ── Display helpers ───────────────────────────────────────────────────────

    private void updateDistanceDisplay() {
        if (totalDistance < 1000) {
            tvDistance.setText(String.format(Locale.US, "%.1f m", totalDistance));
        } else {
            tvDistance.setText(String.format(Locale.US, "%.3f km", totalDistance / 1000f));
        }
    }
}