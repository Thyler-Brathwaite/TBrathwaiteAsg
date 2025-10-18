package com.example.comp3606asgbirds;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Spinner spnrBirdList;
    CheckBox boxDiscount;
    EditText inputTotal;
    ImageView picBird;
    Button btnCompute, btnText;
    ImageButton chirpBird;
    HashMap<String, Double> priceMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnrBirdList = findViewById(R.id.spnrBirdList);
        boxDiscount = findViewById(R.id.boxDiscount);
        inputTotal = findViewById(R.id.inputTotal);
        picBird = findViewById(R.id.picBird);
        btnCompute = findViewById(R.id.btnCompute);
        btnText = findViewById(R.id.btnText);
        chirpBird = findViewById(R.id.chirpBird);

        // Bird data
        priceMap.put("Canary", 500.0);
        priceMap.put("Finch", 600.0);
        priceMap.put("Robin", 700.0);

        ArrayAdapter<String> birdAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                priceMap.keySet().toArray(new String[0])
        );
        spnrBirdList.setAdapter(birdAdapter);

        spnrBirdList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString().toLowerCase();
                int imgRes = getResources().getIdentifier(selected, "drawable", getPackageName());
                picBird.setImageResource(imgRes);
                picBird.setAlpha(0f);
                picBird.animate().alpha(1f).setDuration(500).start();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnCompute.setOnClickListener(v -> {
            String chosenBird = spnrBirdList.getSelectedItem().toString();
            double price = priceMap.get(chosenBird);
            if (boxDiscount.isChecked()) {
                price *= 0.8;
            }
            inputTotal.setText(String.format("%.2f", price));
            Toast.makeText(MainActivity.this, "Total: $" + price, Toast.LENGTH_SHORT).show();
        });

        chirpBird.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Chirp Chirp", Toast.LENGTH_SHORT).show()
        );

        btnText.setOnClickListener(v -> {
            String total = inputTotal.getText().toString();
            if (total.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please compute total first!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent sms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:282882"));
            sms.putExtra("sms_body", "Bird Price: $" + total);
            startActivity(sms);
        });
    }
}