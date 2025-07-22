package com.example.flowerslist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FlowerDetailActivity extends AppCompatActivity {

    private TextView tvDetailFlowerName;
    private ImageView ivDetailFlowerImage;

    // ফুলের নাম (ছোট হাতের অক্ষরে) এবং তাদের Drawable রিসোর্স আইডি ম্যাপ করা হচ্ছে
    private Map<String, Integer> flowerImageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_detail);

        // UI উপাদানগুলো রেফারেন্স করা হচ্ছে
        tvDetailFlowerName = findViewById(R.id.tvDetailFlowerName);
        ivDetailFlowerImage = findViewById(R.id.ivDetailFlowerImage);

        // ম্যাপ ইনিসিয়ালাইজ করা হচ্ছে
        flowerImageMap = new HashMap<>();
        flowerImageMap.put("rose", R.drawable.rose);
        flowerImageMap.put("lily", R.drawable.lily);
        flowerImageMap.put("sunflower", R.drawable.sunflower);
        flowerImageMap.put("tulip", R.drawable.tulip);
        flowerImageMap.put("daisy", R.drawable.daisy);
        flowerImageMap.put("orchid", R.drawable.orchid);
        flowerImageMap.put("jasmine", R.drawable.jasmine);
        // নিশ্চিত করুন যে এই ম্যাপটি MainActivity এর ম্যাপের সাথে সামঞ্জস্যপূর্ণ

        // MainActivity থেকে পাঠানো ডেটা গ্রহণ করা হচ্ছে
        String flowerName = getIntent().getStringExtra("FLOWER_NAME");

        if (flowerName != null && !flowerName.isEmpty()) {
            // ফুলের নাম প্রদর্শন করা হচ্ছে (প্রথম অক্ষর বড় হাতের করে)
            String displayFlowerName = flowerName.substring(0, 1).toUpperCase(Locale.getDefault()) + flowerName.substring(1).toLowerCase(Locale.getDefault());
            tvDetailFlowerName.setText(displayFlowerName);

            // ফুলের ছবি প্রদর্শন করা হচ্ছে
            Integer imageResId = flowerImageMap.get(flowerName); // ছোট হাতের অক্ষরে ম্যাপ থেকে খোঁজা হচ্ছে
            if (imageResId != null) {
                ivDetailFlowerImage.setImageResource(imageResId);
            } else {
                ivDetailFlowerImage.setImageResource(R.drawable.placeholder_flower); // প্লেসহোল্ডার
            }
        } else {
            tvDetailFlowerName.setText("No Flower Selected");
            ivDetailFlowerImage.setImageResource(R.drawable.placeholder_flower);
        }
    }
}
