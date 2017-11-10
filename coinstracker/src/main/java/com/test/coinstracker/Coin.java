package com.test.coinstracker;

/**
 * Created by NKommuri on 11/10/2017.
 */

class Coin {
    String id, name, symbol, price_inr, setPrice, last_updated;

    public Coin(String id, String name, String symbol, String price_inr) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price_inr = price_inr;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getSetPrice() {
        return setPrice;
    }

    public Coin setSetPrice(String setPrice) {
        this.setPrice = setPrice;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice_inr() {
        return price_inr;
    }

    public void setPrice_inr(String price_inr) {
        this.price_inr = price_inr;
    }
}
