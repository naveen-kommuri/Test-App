package com.test.coinstracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by NKommuri on 11/10/2017.
 */

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.CustomHolder> {
    Context context;
    ArrayList<Coin> coins;

    public CoinsAdapter(Context context, ArrayList<Coin> coins) {
        this.context = context;
        this.coins = coins;
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coin, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        holder.name.setText(coins.get(position).getName());
        holder.price.setText(coins.get(position).getSymbol());
        holder.setprice.setText(coins.get(position).getPrice_inr());
        holder.symbol.setText(coins.get(position).getSetPrice());
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        TextView name, price, symbol, setprice;

        public CustomHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            symbol = itemView.findViewById(R.id.symbol);
            setprice = itemView.findViewById(R.id.setprice);
        }
    }
}
