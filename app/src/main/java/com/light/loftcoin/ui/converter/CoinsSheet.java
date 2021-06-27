package com.light.loftcoin.ui.converter;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.light.loftcoin.BaseComponent;
import com.light.loftcoin.R;
import com.light.loftcoin.databinding.DialogCurrencyBinding;
import com.light.loftcoin.widget.RecyclerViews;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class CoinsSheet extends BottomSheetDialogFragment {

    static final String KEY_MODE = "mode";

    static final int MODE_FROM = 1;

    static final int MODE_TO = 2;

    private final ConverterComponent component;

    private ConverterViewModel viewModel;

    private DialogCurrencyBinding binding;

    private CoinsSheetAdapter adapter;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private int mode;

    @Inject
    CoinsSheet(BaseComponent baseComponent) {
        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireParentFragment(), component.viewModelFactory())
                .get(ConverterViewModel.class);

        adapter = component.coinsSheetAdapter();
        mode = requireArguments().getInt(KEY_MODE, MODE_FROM);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_currency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DialogCurrencyBinding.bind(view);

        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recycler.setAdapter(adapter);

        disposable.add(viewModel.topCoins().subscribe(adapter::submitList));
        disposable.add(RecyclerViews.onClick(binding.recycler)
                .map((position) -> adapter.getItem(position))
                .subscribe(coin -> {
                    if (MODE_FROM == mode) {
                        viewModel.fromCoin(coin);
                    } else {
                        viewModel.toCoin(coin);
                    }
                    Timber.d("%s", mode);
                    dismissAllowingStateLoss();
                }));
    }

    @Override
    public void onDestroyView() {
        binding.recycler.setAdapter(adapter);
        disposable.dispose();
        super.onDestroyView();
    }
}
