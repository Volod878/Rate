package com.example.rate;

import org.json.JSONException;
import org.json.JSONObject;

public class Rate {

    protected String ID;
    protected String numCode;
    protected String charCode;
    protected int nominal;
    protected String name;
    protected double value;
    protected double previous;

    public Rate(JSONObject rateJson) {
        try {
            this.ID = rateJson.getString("ID");
            this.numCode = rateJson.getString("NumCode");
            this.charCode = rateJson.getString("CharCode");
            this.nominal = rateJson.getInt("Nominal");
            this.name = rateJson.getString("Name");
            this.value = rateJson.getDouble("Value");
            this.previous = rateJson.getDouble("Previous");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Rate(String charCode, double value) {
        this.charCode = charCode;
        this.value = value;
    }

    public Rate(String charCode, int nominal, String name, double value) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }
}