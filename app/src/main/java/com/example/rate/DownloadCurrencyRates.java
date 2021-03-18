package com.example.rate;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DownloadCurrencyRates extends AsyncTask<Void, Void, Void> {

    protected static Map<String, Rate> listValute = new HashMap<>();
    protected DataFromSite dataFromSite;

    public DownloadCurrencyRates() {
        super();
        listValute.put("RUR", new Rate("RUR", 1,"Российский рубль", 1.0));
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            System.out.println("скачиваю данные с сайта");
            URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            JSONObject dailyJson = new JSONObject(sb.toString());
            dataFromSite = new DataFromSite(dailyJson);
            JSONObject val = dataFromSite.valute;
            Iterator<String> iterator = val.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject jsonObject = new JSONObject(val.get(key).toString());
                listValute.put(key, new Rate(jsonObject));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
