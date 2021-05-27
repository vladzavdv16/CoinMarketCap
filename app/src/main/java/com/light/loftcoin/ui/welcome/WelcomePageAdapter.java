package com.light.loftcoin.ui.welcome;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.light.loftcoin.R;
import com.light.loftcoin.databinding.WelcomePageBinding;

public class WelcomePageAdapter extends RecyclerView.Adapter<WelcomePageAdapter.ViewHolder> {

    private final int[] IMAGE = {
            R.drawable.welcome_page_1,
            R.drawable.welcome_page_2,
            R.drawable.welcome_page_3
    };
    private final int[] TITLE = {
            R.string.welcome_page_1_title,
            R.string.welcome_page_2_title,
            R.string.welcome_page_3_title
    };

    private final int[] SUBTITLE = {
            R.string.welcome_page_1_subtitle,
            R.string.welcome_page_2_subtitle,
            R.string.welcome_page_3_subtitle

    };

    private LayoutInflater inflater;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(WelcomePageBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomePageAdapter.ViewHolder holder, int position) {
        holder.binding.image.setImageResource(IMAGE[position]);
        holder.binding.title.setText(TITLE[position]);
        holder.binding.subtitle.setText(SUBTITLE[position]);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    @Override
    public int getItemCount() {
        return IMAGE.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final WelcomePageBinding binding;

        public ViewHolder(WelcomePageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
