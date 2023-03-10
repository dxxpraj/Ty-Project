package com.example.shopaceecommerce.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.shopaceecommerce.R;
import com.example.shopaceecommerce.utils.AppDatabase;
import com.example.shopaceecommerce.utils.Product;
import com.example.shopaceecommerce.utils.ProductDao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class cartadapter extends RecyclerView.Adapter<cartadapter.myviewholder> {
    List<Product> products;
    TextView rateview;

    public cartadapter(List<Product> products, TextView rateview) {
        this.products = products;
        this.rateview = rateview;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecartrow,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.recid.setText(String.valueOf(products.get(position).getPid()));
        holder.recpname.setText(String.valueOf(products.get(position).getPname()));
        holder.recpprice.setText(String.valueOf(products.get(position).getPrice()));
        holder.recqnt.setText(String.valueOf(products.get(position).getQnt()));

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = Room.databaseBuilder(holder.recid.getContext(),AppDatabase.class, "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = db.ProductDao();
                productDao.deleteById(products.get(position).getPid());
                products.remove(position);
                notifyItemRemoved(position);
                updateprice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView recid,recpname,recqnt,recpprice;
        ImageButton delbtn;
        public myviewholder(@NonNull @NotNull View itemView){
            super(itemView);

            recid = itemView.findViewById(R.id.recid);
            recpname = itemView.findViewById(R.id.recpname);
            recpprice = itemView.findViewById(R.id.recpprice);
            recqnt = itemView.findViewById(R.id.recqnt);
            delbtn = itemView.findViewById(R.id.delbtn);
        }
    }

    public void updateprice(){
        int sum = 0, i;
        for (i=0;i<products.size();i++)
            sum = sum+(products.get(i).getPrice()*products.get(i).getQnt());

        rateview.setText("Total Amount : ₹ "+sum);
    }
}
