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
import com.example.shopaceecommerce.ProductDetailsActivity;
import com.example.shopaceecommerce.model.product_response_model;

import java.util.List;

public class productadapter extends RecyclerView.Adapter<productadapter.myviewholder> {
    List<product_response_model> data;
    Context context;

    public productadapter(List<product_response_model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecard,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        final product_response_model temp = data.get(position);

        holder.t1.setText(data.get(position).getPname());
        holder.t2.setText("Just ₹ "+data.get(position).getPprice());
        Glide.with(holder.t1.getContext()).load("http://10.0.2.2/ecommerce/admin/images/"+data.get(position).getPimage()).into(holder.img);

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("pid", temp.getPid());
                intent.putExtra("pimage", temp.getPimage());
                intent.putExtra("pname", temp.getPname());
                intent.putExtra("pprice", temp.getPprice());
                intent.putExtra("pdesc", temp.getPdesc());
                intent.putExtra("availability", temp.getAvailability());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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
