package com.example.firebaseandroidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ViewHolder>{
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
        holder.name.setText(MessageFormat.format("{0} {1}", arrayList.get(position).getName()));
        holder.desc.setText(arrayList.get(position).getDesc());
        holder.pic.setText(arrayList.get(position).getPic());
        holder.price.setText(arrayList.get(position).getPrice());
        holder.location.setText(arrayList.get(position).getLocation());
        holder.rating.setText(arrayList.get(position).getRating());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, pic, price, location, rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cafeName);
            pic = itemView.findViewById(R.id.cafeImage);
            desc = itemView.findViewById(R.id.cafeDescription);
            pic = itemView.findViewById(R.id.cafeImage);
            price = itemView.findViewById(R.id.cafePricelist);
            location = itemView.findViewById(R.id.cafeLocation);
            rating = itemView.findViewById(R.id.cafeRating);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Cafe user);
    }


}
