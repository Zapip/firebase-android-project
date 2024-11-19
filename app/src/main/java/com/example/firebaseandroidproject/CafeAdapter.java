package com.example.firebaseandroidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ViewHolder> {
    Context context;
    ArrayList<Cafe> arrayList;
    OnItemClickListener onItemClickListener;

    public CafeAdapter(Context context, ArrayList<Cafe> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cafe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cafe cafe = arrayList.get(position);

        // Set text properties
        holder.name.setText(cafe.getNameET());
        holder.desc.setText(cafe.getDescET());
        holder.price.setText(cafe.getPriceET());
        holder.location.setText(cafe.getLocationET());
        holder.rating.setText(cafe.getRatingET());

        // Load image using Glide
        Glide.with(context)
                .load(cafe.getPicET())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.pic);

        // Handle item click
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(cafe));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, price, location, rating;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cafeName);
            pic = itemView.findViewById(R.id.cafeImage);
            desc = itemView.findViewById(R.id.cafeDescription);
            price = itemView.findViewById(R.id.cafePricelist);
            location = itemView.findViewById(R.id.cafeLocation);
            rating = itemView.findViewById(R.id.cafeRating);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Cafe cafe);
    }
}
