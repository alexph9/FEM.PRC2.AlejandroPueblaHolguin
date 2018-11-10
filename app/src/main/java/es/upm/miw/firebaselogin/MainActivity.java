package es.upm.miw.firebaselogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

// Firebase
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    final static String LOG_TAG = "MiW";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase db;
    private DatabaseReference dbRefDelivery;
    private DatabaseReference dbRefCountry;
    private Button logoutButton;
    private Button saveDeliveryButton;
    private EditText gameNameET;
    private EditText deliveryDateET;
    private Spinner countrySpinner;

    private static final String API_BASE_URL = "https://restcountries.eu";
    private CountryRESTAPIService apiService;

    private static final int RC_SIGN_IN = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton = findViewById(R.id.logoutButton);
        saveDeliveryButton = findViewById(R.id.deliveryButton);
        gameNameET = findViewById(R.id.editTextName);
        deliveryDateET = findViewById(R.id.editTextDate);
        countrySpinner = findViewById(R.id.spinnerCountry);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(CountryRESTAPIService.class);

        db = FirebaseDatabase.getInstance();
        dbRefDelivery = db.getReference("delivery");
        dbRefCountry = db.getReference("country");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    CharSequence username = user.getDisplayName();
                    Toast.makeText(MainActivity.this, "Username: " + username, Toast.LENGTH_LONG).show();
                    Log.i(LOG_TAG, "onAuthStateChanged() Username: " + username);
                    ((TextView) findViewById(R.id.textView)).setText("Username: " + username);
                } else {
                    // user is signed out
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().
                                    createSignInIntentBuilder().
                                    setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    )).
                                    setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */).
                                    build(),
                            RC_SIGN_IN);
                }
            }
        };

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
            }
        });

        saveDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Llamada a APIREST
                String countryName = countrySpinner.getSelectedItem().toString();
                Call<List<Country>> call_async = apiService.getCountryByName(countryName);
                call_async.enqueue(new Callback<List<Country>>() {

                    @Override
                    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                        List<Country> countryList = response.body();
                        if (null != countryList) {
                            String user = mFirebaseAuth.getCurrentUser().getDisplayName();
                            String gameName = gameNameET.getText().toString();
                            String date = deliveryDateET.getText().toString();
                            String countryName = countrySpinner.getSelectedItem().toString();
                            Country countryInfo = new Country();
                            for (Country country : countryList) {
                                countryInfo = country;
                            }
                            Delivery delivery = new Delivery(user, gameName, date, countryName);

                            if(delivery.getCountry().isEmpty() || delivery.getGame().isEmpty() || delivery.getSendDate().isEmpty()) {
                                Toast.makeText(MainActivity.this, getString(R.string.deliveryEmptyToast), Toast.LENGTH_SHORT).show();
                            } else {
                                dbRefDelivery.push().setValue(delivery);
                                dbRefCountry.push().setValue(countryInfo);
                                Toast.makeText(MainActivity.this, getString(R.string.deliverySuccessToast), Toast.LENGTH_SHORT).show();
                                gameNameET.setText("");
                                deliveryDateET.setText("");

                            }
                        } else {
                            Toast.makeText(
                                    MainActivity.this,
                                    R.string.countryNotExistToast,
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Country>> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "ERROR: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.signed_in, Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "onActivityResult " + getString(R.string.signed_in));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.signed_cancelled, Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, "onActivityResult " + getString(R.string.signed_cancelled));
                finish();
            }
        }
    }

}
