package com.example.rate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private DownloadCurrencyRates downloadCurrencyRates = new DownloadCurrencyRates();
    private TextView use;
    private EditText useValue;
    private TextView currency;
    private TextView currencyValue;

    private Rate rateIs, rateTo;
    protected Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        use = findViewById(R.id.use_text_view);
        useValue = findViewById(R.id.use_number_view);
        currency = findViewById(R.id.convert_text_view);
        currencyValue = findViewById(R.id.convert_number_view);
        updateRate();
        fillForm();
        useValue.setOnEditorActionListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void fillForm() {
        double valueIs, valueTo;
        String nameIs = "RUR";
        String nameTo = "USD";

        arguments = getIntent().getExtras();
        if (arguments!=null) {
            String charCode = arguments.getString("CharCode");
            double value = arguments.getDouble("Value");
            int nominal = arguments.getInt("Nominal");
            rateTo = new Rate(charCode, value);
            valueIs = (double) nominal;
            rateIs = new Rate("RUR", valueIs);
        } else {
            try {
                valueIs = Objects.requireNonNull(DownloadCurrencyRates.listValute.get(nameIs)).value;
                valueTo = Objects.requireNonNull(DownloadCurrencyRates.listValute.get(nameTo)).value;
                rateTo = new Rate(nameTo, valueTo);
                rateIs = new Rate(nameIs, valueIs);
            } catch (NullPointerException e) {
                fillForm();
            }
        }

        DecimalFormat twoSigns = new DecimalFormat("##0.00");
        use.setText(rateIs.charCode);
        useValue.setText(twoSigns.format(rateTo.value) + "");
        currency.setText(rateTo.charCode);
        currencyValue.setText(twoSigns.format(rateIs.value) + "");
    }

    public void convertTo(View view) {
        Intent intent = new Intent(MainActivity.this, ListRate.class);
        startActivity(intent);
    }

    private void updateRate() {
        Button startButton = (Button) findViewById(R.id.update_button);
        startButton.setVisibility(View.INVISIBLE);
        downloadCurrencyRates.execute();
        startButton.setVisibility(View.VISIBLE);
    }

    public void updateRate(View view) {
        Button startButton = (Button) findViewById(R.id.update_button);
        startButton.setVisibility(View.INVISIBLE);
        downloadCurrencyRates = new DownloadCurrencyRates();
        downloadCurrencyRates.execute();
        startButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        TextView use = findViewById(R.id.use_number_view);
        TextView convert = findViewById(R.id.convert_number_view);
        if (actionId == EditorInfo.IME_ACTION_GO) {
            double useValue = 0.0;
            double rateValue = 0.0;
            if (v.getId() == use.getId()) {
                try {
                    useValue = Double.parseDouble(use.getText().toString());
                    rateValue = rateTo.value / rateIs.value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double convertTo = useValue / rateValue;
                System.out.println(convertTo);
                DecimalFormat twoSigns = new DecimalFormat("##0.00");
                String string = "" + twoSigns.format(convertTo);
                System.out.println(string);
                convert.setText("" + string);
            }
            return true;
        }
        return false;
    }
}