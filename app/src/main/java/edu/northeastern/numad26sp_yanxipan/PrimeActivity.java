package edu.northeastern.numad26sp_yanxipan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeActivity extends AppCompatActivity {

    // UI components
    private TextView tvCurrentNumber;
    private TextView tvLatestPrime;
    private Button btnFindPrimes;
    private Button btnTerminate;
    private CheckBox cbPacifier;

    // Search state
    private Thread searchThread;
    private boolean isSearching = false;
    private long currentNumber = 3;
    private long latestPrime = 2;

    // Handler to update UI from worker thread
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Keys for saving state
    private static final String KEY_IS_SEARCHING = "isSearching";
    private static final String KEY_CURRENT_NUMBER = "currentNumber";
    private static final String KEY_LATEST_PRIME = "latestPrime";
    private static final String KEY_PACIFIER = "pacifier";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);

        // Initialize UI
        tvCurrentNumber = findViewById(R.id.tvCurrentNumber);
        tvLatestPrime = findViewById(R.id.tvLatestPrime);
        btnFindPrimes = findViewById(R.id.btnFindPrimes);
        btnTerminate = findViewById(R.id.btnTerminate);
        cbPacifier = findViewById(R.id.cbPacifier);

        // Restore state if rotating
        if (savedInstanceState != null) {
            currentNumber = savedInstanceState.getLong(KEY_CURRENT_NUMBER, 3);
            latestPrime = savedInstanceState.getLong(KEY_LATEST_PRIME, 2);
            boolean wasSearching = savedInstanceState.getBoolean(KEY_IS_SEARCHING, false);
            boolean pacifierState = savedInstanceState.getBoolean(KEY_PACIFIER, false);

            cbPacifier.setChecked(pacifierState);
            tvCurrentNumber.setText("Checking: " + currentNumber);
            tvLatestPrime.setText("Latest Prime: " + latestPrime);

            // Resume search if it was running before rotation
            if (wasSearching) {
                startSearch(currentNumber);
            }
        }

        // Find Primes button
        btnFindPrimes.setOnClickListener(v -> {
            if (!isSearching) {
                stopSearch();
                currentNumber = 3;
                latestPrime = 2;
                tvCurrentNumber.setText("Checking: 3");
                tvLatestPrime.setText("Latest Prime: 2");
                startSearch(3);
            }
        });

        // Terminate button
        btnTerminate.setOnClickListener(v -> stopSearch());

        // Handle back button while search is running
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isSearching) {
                    new AlertDialog.Builder(PrimeActivity.this)
                            .setTitle("Terminate Search")
                            .setMessage("Are you sure you want to terminate the search and go back?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                stopSearch();
                                finish();
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    finish();
                }
            }
        });
    }

    private void startSearch(long startFrom) {
        isSearching = true;
        currentNumber = startFrom;

        searchThread = new Thread(() -> {
            long num = (startFrom < 3) ? 3 : (startFrom % 2 == 0 ? startFrom + 1 : startFrom);

            while (isSearching) {
                final long n = num;
                currentNumber = n;

                // Update current number being checked
                handler.post(() -> tvCurrentNumber.setText("Checking: " + n));

                if (isPrime(n)) {
                    latestPrime = n;
                    final long prime = n;
                    handler.post(() -> tvLatestPrime.setText("Latest Prime: " + prime));
                }
                num += 2;
            }
        });

        searchThread.start();
    }

    private void stopSearch() {
        isSearching = false;
        if (searchThread != null) {
            searchThread.interrupt();
            searchThread = null;
        }
    }

    private boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_SEARCHING, isSearching);
        outState.putLong(KEY_CURRENT_NUMBER, currentNumber);
        outState.putLong(KEY_LATEST_PRIME, latestPrime);
        outState.putBoolean(KEY_PACIFIER, cbPacifier.isChecked());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop search when activity is destroyed (not rotation)
        if (!isChangingConfigurations()) {
            stopSearch();
        }
    }
}