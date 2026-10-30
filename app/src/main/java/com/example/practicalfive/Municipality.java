package com.example.practicalfive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * Second screen: shows the selected municipality's name, image and summary,
 * and lets the user open the municipality's official website with an implicit
 * Intent. The colours, image and text all change according to the validated
 * municipality name received from {@link MainActivity}.
 */
public class Municipality extends AppCompatActivity {

    private TextView nameText;
    private TextView infoText;
    private ImageView municipalityImage;
    private Button visitSiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        nameText = findViewById(R.id.textMunicipalityName);
        infoText = findViewById(R.id.textMunicipalityInfo);
        municipalityImage = findViewById(R.id.imageMunicipality);
        visitSiteButton = findViewById(R.id.buttonVisitSite);

        // Re-validate the name passed from MainActivity so we never trust raw intent data.
        String raw = null;
        Intent intent = getIntent();
        if (intent != null) {
            raw = intent.getStringExtra(MainActivity.EXTRA_MUNICIPALITY);
        }
        TextProcessor processor = new TextProcessor();
        String municipality = processor.processMunicipality(raw);

        if (municipality == null) {
            // Missing or invalid data: go back to the main screen instead of crashing.
            finish();
            return;
        }

        displayMunicipality(municipality);
    }

    /** Chooses the colours, image, summary and website that match the selected municipality. */
    private void displayMunicipality(String municipality) {
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);

        int colourRes;
        int imageRes;
        int summaryIndex;
        String websiteUrl;

        switch (municipality) {
            case "Beijing":
                colourRes = R.color.municipality_beijing;
                imageRes = R.drawable.beijing;
                summaryIndex = 0;
                websiteUrl = "https://english.beijing.gov.cn/";
                break;
            case "Shanghai":
                colourRes = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                summaryIndex = 1;
                websiteUrl = "https://english.shanghai.gov.cn/";
                break;
            case "Chongqing":
                colourRes = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                summaryIndex = 2;
                websiteUrl = "https://english.cq.gov.cn/";
                break;
            case "Tianjin":
            default:
                colourRes = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                summaryIndex = 3;
                websiteUrl = "https://en.tj.gov.cn/";
                break;
        }

        int accentColour = ContextCompat.getColor(this, colourRes);
        int lightText = ContextCompat.getColor(this, R.color.color_text_light);

        nameText.setText(municipality);
        nameText.setBackgroundColor(accentColour);
        nameText.setTextColor(lightText);

        municipalityImage.setImageResource(imageRes);

        infoText.setText(summaries[summaryIndex]);
        infoText.setTextColor(accentColour);

        visitSiteButton.setBackgroundColor(accentColour);
        visitSiteButton.setTextColor(lightText);

        final String url = websiteUrl;
        visitSiteButton.setOnClickListener(v -> openOfficialWebsite(url));
    }

    /** Uses an implicit Intent (ACTION_VIEW) to open the municipality's website in a browser. */
    private void openOfficialWebsite(String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(webIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_browser, Toast.LENGTH_LONG).show();
        }
    }
}
