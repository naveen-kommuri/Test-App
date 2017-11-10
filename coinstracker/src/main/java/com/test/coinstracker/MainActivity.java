package com.test.coinstracker;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NKommuri on 11/10/2017.
 */

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addCoin;
    CoinsAdapter coinsAdapter;
    RecyclerView rv_coins;
    ArrayList<Coin> coins;
    AlertDialog dialog;
    TextView count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCoin = findViewById(R.id.fab);
        rv_coins = findViewById(R.id.coins);
        count = findViewById(R.id.count);
        rv_coins.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        coins = new ArrayList<>();
        coinsAdapter = new CoinsAdapter(MainActivity.this, coins);
        rv_coins.setAdapter(coinsAdapter);
        addCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);

                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.create_coin_dialog, null);
                final EditText et_symbol = view.findViewById(R.id.et_symbol);
                final EditText et_time = view.findViewById(R.id.et_time);
                final EditText et_price = view.findViewById(R.id.et_price);
                final EditText et_set_price = view.findViewById(R.id.et_set_price);
//                final EditText et_set_id = view.findViewById(R.id.et_set_id);


//                final EditText et_set_name = view.findViewById(R.id.et_set_name);
                view.findViewById(R.id.fetch).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_symbol.getText().toString().trim().length() != 0) {
                            ApiInterface apiService = ApiClient.getClient(MainActivity.this).create(ApiInterface.class);
                            Call<ArrayList<Coin>> call = apiService.getCoin("bitcoin-cash", "inr");
                            call.enqueue(new Callback<ArrayList<Coin>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Coin>> call, Response<ArrayList<Coin>> response) {
                                    Log.d("Coinse", "getCoinsgInfoList URL " + response.raw().request().url());
                                    Log.d("Coinse", "getCoinsInfoList Resp " + new Gson().toJson(response.body()));

                                    et_price.setText(response.body().get(0).getPrice_inr());
                                    et_symbol.setText(response.body().get(0).getSymbol());
                                    et_time.setText(response.body().get(0).getLast_updated());
                                    et_symbol.setTag(response.body().get(0));
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Coin>> call, Throwable t) {
                                    Log.d("Coinse", "getCoinsgInfoList URL " + call.request().url());
                                    Log.d("Coinse", "getCoinsInfoList Resp " + t.getLocalizedMessage());
                                    et_price.setText("");
                                }
                            });
                            Toast.makeText(MainActivity.this, "Fetch", Toast.LENGTH_SHORT).show();
                            et_symbol.setTag(new Coin("", "", "", ""));
                        }
                    }
                });
                view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_price.getText().toString().trim().length() != 0) {
                            addToSp((Coin) et_symbol.getTag());
                            dialog.dismiss();
                        } else
                            Toast.makeText(MainActivity.this, "Get Price", Toast.LENGTH_SHORT).show();
                    }
                });
                view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
                Toast.makeText(MainActivity.this, "Add to List", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToSp(Coin coin) {
        if (coin.getSymbol().trim().length() != 0) {
            JSONArray jsonArray = getCoins();
            boolean isExist;
            int existIndex = -1;
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject coinJson = jsonArray.getJSONObject(i);
                    isExist = coinJson.optString("symbol") == coin.getSymbol();
                    if (isExist)
                        existIndex = i;
                }
                if (existIndex == -1)
                    jsonArray.put(coin);
                else
                    jsonArray.put(existIndex, coin);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            saveCoinsArray(jsonArray);
        }
    }

    private void updateCoins() {
        try {
            JSONArray jsonArray = getCoins();
            coins.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject coinJson = jsonArray.getJSONObject(i);
                coins.add(new Coin(coinJson.optString("id"), coinJson.optString("name"), coinJson.optString("symbol"), coinJson.optString("price_inr")).setSetPrice("set_price"));
            }
            coinsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Resume", coins.size() + "");
        updateCoins();
        count.setText(coins.size() + "");
    }

    private SharedPreferences getPref() {
        return getSharedPreferences("coins", MODE_PRIVATE);
    }

    private JSONArray getCoins() {
        try {
            return new JSONArray(getPref().getString("coin_values", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private void saveCoinsArray(JSONArray coinsArray) {
        getPref().edit().putString("coin_values", coinsArray.toString()).commit();
    }
}
