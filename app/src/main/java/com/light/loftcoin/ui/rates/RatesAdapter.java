package com.light.loftcoin.ui.rates;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.BuildConfig;
import com.light.loftcoin.R;
import com.light.loftcoin.data.Coin;
import com.light.loftcoin.databinding.LiRatesBinding;
import com.light.loftcoin.util.ImageLoader;
import com.light.loftcoin.util.PercentFormatter;
import com.light.loftcoin.util.PriceFormatter;
import com.light.loftcoin.widget.OutlineCircle;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private Context context;

    private final PercentFormatter percentFormatter;

    private ImageLoader imageLoader;

    private LayoutInflater inflater;

    private int colorNegative = Color.RED;

    private int colorPositive = Color.GREEN;

    private PriceFormatter priceFormatter;

    @Inject
    RatesAdapter(PriceFormatter priceFormatter, PercentFormatter percentFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            public Object getChangePayload(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return newItem;
            }
        });
        this.priceFormatter = priceFormatter;
        this.percentFormatter = percentFormatter;
        this.imageLoader = imageLoader;
        setHasStableIds(true);
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
        final Coin coin = getItem(position);
        if (position % 2 == 1) {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),
                    R.color.dark));
        } else {
            holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),
                    R.color.dark_two));
        }
        holder.binding.symbol.setText(coin.symbol());
        holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
        holder.binding.change.setText(percentFormatter.format(coin.change24h()));
        if (coin.change24h() > 0) {
            holder.binding.change.setTextColor(colorPositive);
        } else {
            holder.binding.change.setTextColor(colorNegative);
        }
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logo);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Coin coin = (Coin) payloads.get(0);
            holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
            holder.binding.change.setText(percentFormatter.format(coin.change24h()));

        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final Context context = recyclerView.getContext();
        inflater = LayoutInflater.from(context);
        TypedValue v = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.textNegative, v, true);
        colorNegative = v.data;
        context.getTheme().resolveAttribute(R.attr.textPositive, v, true);
        colorPositive = v.data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiRatesBinding binding;

        public ViewHolder(@NonNull LiRatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OutlineCircle.apply(binding.logo);
        }
    }
}
