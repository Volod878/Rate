package com.example.rate;

import org.json.JSONException;
import org.json.JSONObject;

public class DataFromSite {
    protected String date;
    protected String previousDate;
    protected String previousURL;
    protected String timestamp;
    protected JSONObject valute;

    public DataFromSite(JSONObject dateFromSite) {
        try {
            this.date = dateFromSite.getString("Date");
            this.previousDate = dateFromSite.getString("PreviousDate");
            this.previousURL = dateFromSite.getString("PreviousURL");
            this.timestamp = dateFromSite.getString("Timestamp");
            this.valute = dateFromSite.getJSONObject("Valute");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
