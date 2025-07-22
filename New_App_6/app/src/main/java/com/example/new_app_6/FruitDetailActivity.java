package com.example.new_app_6;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Locale; // toLowerCase() এর জন্য
import java.util.Map;

public class FruitDetailActivity extends AppCompatActivity {

    private TextView tvDetailFruitName;
    private ImageView ivDetailFruitImage;

    // ফলের নাম (ছোট হাতের অক্ষরে) এবং তাদের Drawable রিসোর্স আইডি ম্যাপ করা হচ্ছে
    private Map<String, Integer> fruitImageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);

        // UI উপাদানগুলো রেফারেন্স করা হচ্ছে
        tvDetailFruitName = findViewById(R.id.tvDetailFruitName);
        ivDetailFruitImage = findViewById(R.id.ivDetailFruitImage);

        // ম্যাপ ইনিসিয়ালাইজ করা হচ্ছে
        fruitImageMap = new HashMap<>();
        fruitImageMap.put("apple", R.drawable.apple);
        fruitImageMap.put("banana", R.drawable.banana);
        fruitImageMap.put("orange", R.drawable.orange);
        fruitImageMap.put("grape", R.drawable.grape);
        fruitImageMap.put("mango", R.drawable.mango);
        fruitImageMap.put("strawberry", R.drawable.strawberry);
        fruitImageMap.put("pineapple", R.drawable.pineapple);
        fruitImageMap.put("cherry", R.drawable.cherry);
        fruitImageMap.put("lemon", R.drawable.lemon);
        fruitImageMap.put("watermelon", R.drawable.watermelon);
        // নিশ্চিত করুন যে এই ম্যাপটি MainActivity এর ম্যাপের সাথে সামঞ্জস্যপূর্ণ

        // MainActivity থেকে পাঠানো ডেটা গ্রহণ করা হচ্ছে
        String fruitName = getIntent().getStringExtra("FRUIT_NAME");

        if (fruitName != null && !fruitName.isEmpty()) {
            // ফলের নাম প্রদর্শন করা হচ্ছে (প্রথম অক্ষর বড় হাতের করে)
            String displayFruitName = fruitName.substring(0, 1).toUpperCase(Locale.getDefault()) + fruitName.substring(1).toLowerCase(Locale.getDefault());
            tvDetailFruitName.setText(displayFruitName);

            // ফলের ছবি প্রদর্শন করা হচ্ছে
            Integer imageResId = fruitImageMap.get(fruitName); // ছোট হাতের অক্ষরে ম্যাপ থেকে খোঁজা হচ্ছে
            if (imageResId != null) {
                ivDetailFruitImage.setImageResource(imageResId);
            } else {
                ivDetailFruitImage.setImageResource(R.drawable.placeholder_fruit); // প্লেসহোল্ডার
            }
        } else {
            tvDetailFruitName.setText("No Fruit Selected");
            ivDetailFruitImage.setImageResource(R.drawable.placeholder_fruit);
        }
    }
}
