package com.example.hiddenpixel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class activity_homepage extends AppCompatActivity {

    private ViewPager viewPager;
    private ImagePagerAdapter adapter;
    private ArrayList<Integer> images;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 1000; // Delay in milliseconds
    private final long PERIOD_MS = 3000;
    BottomNavigationView nav;
    ImageButton user_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        nav=findViewById(R.id.bottomNavigationView);
        navigation();

        Button encode = findViewById(R.id.encode);
        Button decode = findViewById(R.id.decode);
        user_details = findViewById(R.id.userbtn);

        user_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(),activity_userProfile.class);
                startActivity(profile);
            }
        });

        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent en = new Intent(activity_homepage.this, activity_encode.class);
                startActivity(en);
            }
        });



        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent de = new Intent(activity_homepage.this, activity_decode.class);
                startActivity(de);
            }
        });

        viewPager = findViewById(R.id.viewPager);
        images = new ArrayList<>();

        // Add your image resource IDs to the 'images' ArrayList
        images.add(R.drawable.carousel1);
        images.add(R.drawable.carousel3);
        images.add(R.drawable.carousel2);
        adapter = new ImagePagerAdapter(this, images);
        viewPager.setAdapter(adapter);

        // Set up a timer to change images automatically
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    public void navigation(){
        nav.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.navigation_home) {
                    intent = new Intent(activity_homepage.this, activity_homepage.class);
                } else if (item.getItemId() == R.id.navigation_encode) {
                    intent = new Intent(activity_homepage.this, activity_encode.class);
                } else if (item.getItemId() == R.id.navigation_decode) {
                    intent = new Intent(activity_homepage.this, activity_decode.class);
                } else if (item.getItemId() == R.id.navigation_about) {
                    intent = new Intent(activity_homepage.this, activity_about.class);
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public class ImagePagerAdapter extends PagerAdapter {
        private Context context;
        private ArrayList<Integer> images;

        public ImagePagerAdapter(Context context, ArrayList<Integer> images) {
            this.context = context;
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.view_pager_item, container, false);

            ImageView imageView = itemView.findViewById(R.id.imageView);
            imageView.setImageResource(images.get(position));

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}






