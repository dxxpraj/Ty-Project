package com.example.shopaceecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shopaceecommerce.R;
import com.example.shopaceecommerce.adapter.cartadapter;
import com.example.shopaceecommerce.utils.AppDatabase;
import com.example.shopaceecommerce.utils.Product;
import com.example.shopaceecommerce.utils.ProductDao;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recview;
    TextView rateview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rateview = findViewById(R.id.rateview);
        getroomdata();
    }

    public void getroomdata(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        recview = findViewById(R.id.cartrecviewroomdb);
        recview.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products = productDao.getallproduct();

        cartadapter adapter = new cartadapter(products, rateview);
        recview.setAdapter(adapter);

        int sum = 0,i;
        for (i=0;i<products.size();i++)
            sum= sum+(products.get(i).getPrice()*products.get(i).getQnt());

        rateview.setText("Total Amount : ₹ "+sum);
    }
}