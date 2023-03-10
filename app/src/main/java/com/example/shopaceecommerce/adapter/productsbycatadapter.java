package com.example.shopaceecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopaceecommerce.R;
import com.example.shopaceecommerce.model.productsbycat_response_model;

import java.util.List;

public class productsbycatadapter extends RecyclerView.Adapter<productsbycatadapter.myviewholder> {
    List<productsbycat_response_model> productsbycat;
    Context context;

    public productsbycatadapter(List<productsbycat_response_model> productsbycat, Context context) {
        this.productsbycat = productsbycat;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecard,parent,false);
        return new productsbycatadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(productsbycat.get(position).getPname());
        holder.t2.setText("Just ₹ "+productsbycat.get(position).getPprice());
        Glide.with(holder.t1.getContext()).load("http://10.0.2.2/ecommerce/admin/images/"+productsbycat.get(position).getPimage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return productsbycat.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView t1, t2;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
        }
    }
}
