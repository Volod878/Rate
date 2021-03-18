package com.example.rate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ListRate extends AppCompatActivity {

    private final Map<String, Rate> listValute = DownloadCurrencyRates.listValute;
    private final List<String> rates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rate);

        ListView listView = (ListView) findViewById(R.id.list);

        for (Map.Entry<String, Rate> map : listValute.entrySet()) rates.add("(" + map.getKey() + ") " + map.getValue().name);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, rates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            TextView textView = (TextView) itemClicked;
            String charCode = textView.getText().toString().substring(1, 4);
            System.out.println(charCode);
            Double value = Objects.requireNonNull(listValute.get(charCode)).value;
            int nominal = Objects.requireNonNull(listValute.get(charCode)).nominal;
            Intent intent = new Intent(ListRate.this, MainActivity.class);
            intent.putExtra("CharCode", charCode);
            intent.putExtra("Value", value);
            intent.putExtra("Nominal", nominal);
            startActivity(intent);
        });
    }


}