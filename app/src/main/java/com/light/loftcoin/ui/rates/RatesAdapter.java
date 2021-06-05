package com.light.loftcoin.ui.rates;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.R;
import com.light.loftcoin.data.Coin;
import com.light.loftcoin.databinding.LiRatesBinding;

import java.util.Formatter;
import java.util.Objects;

class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

//    private final Formatter<Double> priceFormatter;

    private LayoutInflater inflater;

    private int colorNegative = Color.RED;

    private int colorPositive = Color.GREEN;

    RatesAdapter() {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
//        this.priceFormatter = priceFormatter;
//        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRatesBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.symbol.setText(getItem(position).symbol());
//        final Coin coin = getItem(position);
//        holder.binding.price.setText(priceFormatter.format(coin.price()));
//        holder.binding.change.setText(String.format(Locale.US, "%.2f%%", coin.change24h()));
//        if (coin.change24h() > 0) {
//            holder.binding.change.setTextColor(colorPositive);
//        } else {
//            holder.binding.change.setTextColor(colorNegative);
//        }
//        Picasso.get()
//                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
//                .into(holder.binding.logo);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final Context context = recyclerView.getContext();
        inflater = LayoutInflater.from(context);
//        TypedValue v = new TypedValue();
//        context.getTheme().resolveAttribute(R.attr.textNegative, v, true);
//        colorNegative = v.data;
//        context.getTheme().resolveAttribute(R.attr.textPositive, v, true);
//        colorPositive = v.data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiRatesBinding binding;

        public ViewHolder(@NonNull LiRatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
