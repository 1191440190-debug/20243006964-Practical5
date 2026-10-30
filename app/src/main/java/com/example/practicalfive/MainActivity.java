package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main screen: asks the user for a municipality name, validates it with
 * {@link TextProcessor} and, when valid, forwards the normalised name to the
 * {@link Municipality} screen using an explicit Intent.
 */
public class MainActivity extends AppCompatActivity {

    /** Intent extra key shared between MainActivity and Municipality. */
    public static final String EXTRA_MUNICIPALITY = "municipality";

    private EditText municipalityInput;
    private TextProcessor textProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        municipalityInput = findViewById(R.id.editTextMunicipality);
        textProcessor = new TextProcessor();

        // Trigger validation when the keyboard's Go action is pressed.
        municipalityInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE) {
                submitMunicipality();
                return true;
            }
            return false;
        });
    }

    /** Reads the input, validates it and either shows an error or opens the second Activity. */
    private void submitMunicipality() {
        String rawInput = municipalityInput.getText().toString();
        String normalised = textProcessor.processMunicipality(rawInput);

        if (normalised == null) {
            // Distinguish an empty/blank field from an unsupported name.
            if (rawInput.trim().isEmpty()) {
                municipalityInput.setError(getString(R.string.error_empty_input));
            } else {
                municipalityInput.setError(getString(R.string.error_unknown_municipality));
            }
            return;
        }

        Intent intent = new Intent(MainActivity.this, Municipality.class);
        intent.putExtra(EXTRA_MUNICIPALITY, normalised);
        startActivity(intent);
    }
}
