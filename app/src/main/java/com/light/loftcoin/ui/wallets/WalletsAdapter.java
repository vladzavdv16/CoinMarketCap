package com.light.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.databinding.LiWalletBinding;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.ViewHolder> {

    private LayoutInflater inflater;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiWalletBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(LiWalletBinding binding) {
            super(binding.getRoot());
        }
    }
}
