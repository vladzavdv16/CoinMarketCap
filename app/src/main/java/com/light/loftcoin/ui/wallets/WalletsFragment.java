package com.light.loftcoin.ui.wallets;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.light.loftcoin.R;
import com.light.loftcoin.databinding.FragmentWalletsBinding;

import javax.inject.Inject;

public class WalletsFragment extends Fragment {

    @Inject
    public WalletsFragment(){
    }

    private SnapHelper helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentWalletsBinding binding = FragmentWalletsBinding.bind(view);

        final TypedValue value = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.walletCardWidth, value, true);
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        final int padding = (int) (displayMetrics.widthPixels - value.getDimension(displayMetrics)) / 2;
        binding.recycler.setPadding(padding, 0, padding, 0);
        binding.recycler.setClipToPadding(false);

        binding.recycler.addOnScrollListener(new CarouselScroller());

        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recycler.setAdapter(new WalletsAdapter());

        binding.walletCard.setVisibility(View.GONE);

        helper = new PagerSnapHelper();
        helper.attachToRecyclerView(binding.recycler);

    }

    @Override
    public void onDestroyView() {
        helper.attachToRecyclerView(null);
        super.onDestroyView();
    }


    private static class CarouselScroller extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            final int centerX = (recyclerView.getLeft() + recyclerView.getRight()) / 2;
            for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                final View child = recyclerView.getChildAt(i);
                final int childCenterX = (child.getLeft() + child.getRight()) / 2;
                final float childOffSet = Math.abs(centerX - childCenterX) / (float)centerX;
                float factor = (float)(Math.pow(0.85, childOffSet ));
                child.setScaleX(factor);
                child.setScaleY(factor);
            }
        }
    }
}
