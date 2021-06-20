package com.light.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.BuildConfig;
import com.light.loftcoin.data.Wallet;
import com.light.loftcoin.databinding.LiWalletBinding;
import com.light.loftcoin.util.BalanceFormatter;
import com.light.loftcoin.util.ImageLoader;
import com.light.loftcoin.util.OutlineCircle;
import com.light.loftcoin.util.PriceFormatter;

import java.util.Objects;

import javax.inject.Inject;

class WalletsAdapter extends ListAdapter<Wallet, WalletsAdapter.ViewHolder> {

    private final BalanceFormatter balanceFormatter;

    private final PriceFormatter priceFormatter;

    private final ImageLoader imageLoader;

    private LayoutInflater inflater;

    @Inject
    WalletsAdapter(BalanceFormatter balanceFormatter, PriceFormatter priceFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Wallet>() {
            @Override
            public boolean areItemsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.balanceFormatter = balanceFormatter;
        this.priceFormatter = priceFormatter;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiWalletBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wallet wallet = getItem(position);
        holder.binding.symbol.setText(wallet.coin().symbol());
        holder.binding.balance1.setText(priceFormatter.format(wallet.balance()));
        final double balance = wallet.balance() * wallet.coin().price();
        holder.binding.balance2.setText(priceFormatter.format(wallet.coin().currencyCode(), balance));
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + wallet.coin().id() + ".png")
                .into(holder.binding.logo);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiWalletBinding binding;

        ViewHolder(@NonNull LiWalletBinding binding) {
            super(binding.getRoot());
            OutlineCircle.apply(binding.logo);
            this.binding = binding;
            binding.getRoot().setClipToOutline(true);

        }

    }

}