package com.light.loftcoin.ui.currency;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.light.loftcoin.R;
import com.light.loftcoin.data.CurrencyRepo;
import com.light.loftcoin.databinding.DialogCurrencyBinding;

public class CurrencyDialog extends AppCompatDialogFragment {

    private DialogCurrencyBinding binding;
    private CurrencyRepo currencyRepo;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        currencyRepo = new CurrencyRepoImpl(requireContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DialogCurrencyBinding.inflate(requireActivity().getLayoutInflater());
        return new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.currency)
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
