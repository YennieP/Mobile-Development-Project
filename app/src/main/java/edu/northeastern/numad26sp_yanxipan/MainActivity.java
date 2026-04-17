package edu.northeastern.numad26sp_yanxipan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAboutMe = findViewById(R.id.btn_about_me);
        btnAboutMe.setOnClickListener(v ->
                startActivity(new Intent(this, AboutMeActivity.class)));

        Button btnQuicCalc = findViewById(R.id.btn_quic_calc);
        btnQuicCalc.setOnClickListener(v ->
                startActivity(new Intent(this, QuicCalcActivity.class)));

        Button btnPrime = findViewById(R.id.btn_prime);
        btnPrime.setOnClickListener(v ->
                startActivity(new Intent(this, PrimeActivity.class)));

        Button btnContacts = findViewById(R.id.btn_contacts);
        btnContacts.setOnClickListener(v ->
                startActivity(new Intent(this, ContactsActivity.class)));

        Button btnLocation = findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(v ->
                startActivity(new Intent(this, LocationActivity.class)));
    }
}