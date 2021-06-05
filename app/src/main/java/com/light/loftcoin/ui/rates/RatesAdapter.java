package com.light.loftcoin.ui.rates;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.data.Coin;
import com.light.loftcoin.databinding.LiRatesBinding;

import java.util.List;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private final List<? extends Coin> coins;

    public RatesAdapter(List<?extends Coin> coins){
        this.coins = coins;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return coins.get(position).id();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRatesBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(coins.get(position));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final LiRatesBinding binding;
        public ViewHolder(LiRatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coin coin){
            binding.symbol.setText(coin.symbol());
        }
    }
}
