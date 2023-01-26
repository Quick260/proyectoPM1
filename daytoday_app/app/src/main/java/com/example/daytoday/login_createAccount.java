 package com.example.daytoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daytoday.databinding.ActivityLoginCreateAccountBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONObject;

import adapters.ViewPagesLogin;

 public class login_createAccount extends AppCompatActivity {
     private ActivityLoginCreateAccountBinding binding;

    ViewPagesLogin adaptadorpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adaptadorpager = new ViewPagesLogin(getSupportFragmentManager(), getLifecycle());



        binding.viewPager2.setAdapter(adaptadorpager);

        TabLayout tabLayout = findViewById(R.id.tablayout);

        final int[] strings = new int[]{
                R.string.viewPager1,
                R.string.viewPager2
        };

        new TabLayoutMediator(tabLayout, binding.viewPager2, ((tab, position) -> tab.setText(strings[position]))).attach();

    }


 }