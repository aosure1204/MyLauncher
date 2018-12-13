package com.example.wedesign.launcherapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AllAppsAdapter extends RecyclerView.Adapter<AllAppsAdapter.ViewHolder> {
    private int[] iconIds;
    private String[] titleStrs;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView appIcon;
        public TextView appTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.item_img);
            appTitle = itemView.findViewById(R.id.item_text);
        }
    }

    public AllAppsAdapter(int[] iconIds, String[] titleStrs) {
        super();

        this.iconIds = iconIds;
        this.titleStrs = titleStrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.appIcon.setImageResource(iconIds[i]);
        viewHolder.appTitle.setText(titleStrs[i]);
    }

    @Override
    public int getItemCount() {
        return iconIds.length;
    }
}
