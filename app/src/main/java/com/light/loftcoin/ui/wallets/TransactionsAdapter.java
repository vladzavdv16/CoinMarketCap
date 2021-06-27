package com.light.loftcoin.ui.wallets;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.data.Transaction;
import com.light.loftcoin.databinding.LiTransactionBinding;
import com.light.loftcoin.util.PriceFormatter;

import java.util.Objects;

import javax.inject.Inject;

class TransactionsAdapter extends ListAdapter<Transaction, TransactionsAdapter.ViewHolder> {

    LayoutInflater inflater;
    private PriceFormatter priceFormatter;

    @Inject
    TransactionsAdapter(PriceFormatter priceFormatter) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiTransactionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transaction transaction = getItem(position);
        holder.binding.amount1.setText(priceFormatter.format(transaction.amount()));
        final double fiatAmount = transaction.amount() * transaction.coin().price();
        holder.binding.amount2.setText(priceFormatter.format(transaction.coin().currencyCode(), fiatAmount));
        holder.binding.timestamp.setText(DateFormat.getDateFormat(inflater.getContext()).format(transaction.createAt()));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LiTransactionBinding binding;

        public ViewHolder(LiTransactionBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }
    }
}
