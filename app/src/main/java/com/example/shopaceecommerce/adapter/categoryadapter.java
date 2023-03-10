package com.example.shopaceecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopaceecommerce.R;
import com.example.shopaceecommerce.ProductsActivity;
import com.example.shopaceecommerce.model.category_response_model;

import java.util.List;

public class categoryadapter extends RecyclerView.Adapter<categoryadapter.myviewholder> {
    List<category_response_model> categories;
    Context context;

    public categoryadapter(List<category_response_model> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecategorycard,parent,false);
        return new categoryadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        final category_response_model temp = categories.get(position);

        holder.catName.setText(categories.get(position).getCatname());
        Glide.with(holder.catName.getContext()).load("http://10.0.2.2/ecommerce/admin/images/"+categories.get(position).getCatimage()).into(holder.catImg);

        holder.catName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("catid", temp.getCatid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView catImg;
        TextView catName;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            catImg = itemView.findViewById(R.id.catImg);
            catName = itemView.findViewById(R.id.catName);
        }
    }
}
