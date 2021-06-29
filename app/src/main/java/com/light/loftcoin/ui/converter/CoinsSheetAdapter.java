package com.light.loftcoin.ui.converter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.BuildConfig;
import com.light.loftcoin.data.Coin;
import com.light.loftcoin.databinding.LiCoinSheetBinding;
import com.light.loftcoin.util.ImageLoader;
import com.light.loftcoin.widget.OutlineCircle;

import java.util.Objects;

import javax.inject.Inject;

class CoinsSheetAdapter extends ListAdapter<Coin, CoinsSheetAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    @Inject
    CoinsSheetAdapter(ImageLoader imageLoader) {
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
        this.imageLoader = imageLoader;
    }

    @Override
    public Coin getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiCoinSheetBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Coin coin = getItem(position);
        holder.binding.name.setText(coin.name() + " | " + coin.symbol());
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logo);

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private LiCoinSheetBinding binding;

        ViewHolder(@NonNull LiCoinSheetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OutlineCircle.apply(binding.logo);
        }
    }
}
