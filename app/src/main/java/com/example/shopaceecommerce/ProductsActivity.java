package com.example.shopaceecommerce;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.shopaceecommerce.R;
import com.example.shopaceecommerce.adapter.productsbycatadapter;
import com.example.shopaceecommerce.model.productsbycat_response_model;
import com.example.shopaceecommerce.utils.apicontroller;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    RecyclerView recview1, catRecView;

    public String catid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recview1 = findViewById(R.id.productsbycatrecview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recview1.setLayoutManager(gridLayoutManager);
        recview1.setNestedScrollingEnabled(false);

        catid = getIntent().getStringExtra("catid");
        Toast.makeText(this, catid, Toast.LENGTH_SHORT).show();

        processproductsbycat(Integer.parseInt(catid));
    }

    public void processproductsbycat(int category){
        Call<List<productsbycat_response_model>> call = apicontroller.getInstance().getapi().getProductsByCat(category);
        call.enqueue(new Callback<List<productsbycat_response_model>>() {
            @Override
            public void onResponse(Call<List<productsbycat_response_model>> call, Response<List<productsbycat_response_model>> response) {
                List<productsbycat_response_model> productsbycat = response.body();
                productsbycatadapter adapter = new productsbycatadapter(productsbycat, getApplicationContext());
                recview1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<productsbycat_response_model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}