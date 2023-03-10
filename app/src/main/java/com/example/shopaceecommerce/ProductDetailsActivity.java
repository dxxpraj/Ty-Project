package com.example.shopaceecommerce;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopaceecommerce.utils.AppDatabase;
import com.example.shopaceecommerce.utils.Product;
import com.example.shopaceecommerce.utils.ProductDao;
import com.google.android.material.navigation.NavigationView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailsActivity extends AppCompatActivity implements PaymentResultListener {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    ImageView pimage;
    TextView pname, pprice, pdesc, pstatus;
    Button addtocart, buynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        nav = findViewById(R.id.navmenuview);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pimage = findViewById(R.id.descImg);
        pname = findViewById(R.id.descHeader);
        pprice = findViewById(R.id.descPrice);
        pdesc = findViewById(R.id.descDesc);
        pstatus = findViewById(R.id.availability);
        addtocart = findViewById(R.id.addtocart);
        buynow = findViewById(R.id.buynow);

        String photo = getIntent().getStringExtra("pimage");
        Glide.with(this).load("http://10.0.2.2/ecommerce/admin/images/"+photo).apply(new RequestOptions()).into(pimage);
        String id = getIntent().getStringExtra("pid");
        String name = getIntent().getStringExtra("pname");
        String price = getIntent().getStringExtra("pprice");
        String desc = getIntent().getStringExtra("pdesc");
        String status = getIntent().getStringExtra("availability");

        pname.setText(name);
        pprice.setText("₹ "+price);
        pdesc.setText(desc);
        pstatus.setText(status);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "cart_db").allowMainThreadQueries().build();
                ProductDao productDao = db.ProductDao();
                Boolean check = productDao.is_exist(Integer.parseInt(id));
                if (check == false){
                    int pid = Integer.parseInt(id);
                    //String pname = t2.getText().toString();
                    int cartprice = Integer.parseInt(price);
                    int qnt = 1;
                    productDao.insertrecord(new Product(pid, name, cartprice, qnt));
                    Toast.makeText(ProductDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ProductDetailsActivity.this, "Already exist in cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String samount = String.valueOf(price);
                int amount = Math.round(Float.parseFloat(samount) * 100);

                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_PNsIWvrE2KUbGc");
                checkout.setImage(R.drawable.ic_baseline_account_circle_24);

                JSONObject object = new JSONObject();
                try {
                    object.put("name", name);
                    object.put("description", "Payment for "+name);
                    object.put("theme.color", "");
                    object.put("amount", amount);
                    object.put("contact", "");
                    object.put("email", "");
                    checkout.open(ProductDetailsActivity.this, object);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Ordered Successfully: "+s, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OrderSuccessActivity.class);
        intent.putExtra("trackid", s);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Something went wrong: "+s, Toast.LENGTH_SHORT).show();
    }
}