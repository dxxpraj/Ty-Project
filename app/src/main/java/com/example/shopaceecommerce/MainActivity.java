package com.example.shopaceecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shopaceecommerce.adapter.categoryadapter;
import com.example.shopaceecommerce.adapter.productadapter;
import com.example.shopaceecommerce.model.category_response_model;
import com.example.shopaceecommerce.model.product_response_model;
import com.example.shopaceecommerce.utils.apicontroller;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;

    RecyclerView recview, catRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("ShopAce");

        catRecView = findViewById(R.id.catRecView);
        recview = findViewById(R.id.recview);
        //recview.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 4);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        catRecView.setLayoutManager(gridLayoutManager1);
        catRecView.setNestedScrollingEnabled(false);
        recview.setLayoutManager(gridLayoutManager);
        recview.setNestedScrollingEnabled(false);

        processcategory();
        processdata();

        nav = findViewById(R.id.navmenuview);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_logout:
                        logoutuser();
                        drawerLayout.closeDrawer(GravityCompat.START);
                    case R.id.nav_cart:
                        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutuser(){
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    public void processcategory(){
        Call<List<category_response_model>> call = apicontroller.getInstance().getapi().getCategories();
        call.enqueue(new Callback<List<category_response_model>>() {
            @Override
            public void onResponse(Call<List<category_response_model>> call, Response<List<category_response_model>> response) {
                List<category_response_model> categories = response.body();
                categoryadapter adapter = new categoryadapter(categories, getApplicationContext());
                catRecView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<category_response_model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void processdata(){
        Call<List<product_response_model>> call = apicontroller.getInstance().getapi().getdata();
        call.enqueue(new Callback<List<product_response_model>>() {
            @Override
            public void onResponse(Call<List<product_response_model>> call, Response<List<product_response_model>> response) {
                List<product_response_model> data = response.body();
                productadapter adapter = new productadapter(data, getApplicationContext());
                recview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<product_response_model>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}