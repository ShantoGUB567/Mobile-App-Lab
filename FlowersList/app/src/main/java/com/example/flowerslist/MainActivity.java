package com.example.flowerslist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etFlowerName;
    private Button btnAddFlower;
    private ListView listViewFlowers;

    private List<String> flowerList;
    private FlowerAdapter flowerAdapter;

    // ফুলের নাম (ছোট হাতের অক্ষরে) এবং তাদের Drawable রিসোর্স আইডি ম্যাপ করা হচ্ছে
    private Map<String, Integer> flowerImageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI উপাদানগুলো রেফারেন্স করা হচ্ছে
        etFlowerName = findViewById(R.id.etFlowerName);
        btnAddFlower = findViewById(R.id.btnAddFlower);
        listViewFlowers = findViewById(R.id.listViewFlowers);

        flowerList = new ArrayList<>();
        flowerImageMap = new HashMap<>();

        // ফুলের নাম এবং তাদের Drawable রিসোর্স আইডি ম্যাপ করা হচ্ছে।
        // নিশ্চিত করুন যে এই ছবিগুলো res/drawable ফোল্ডারে আছে।
        // ছবির নামগুলো ছোট হাতের অক্ষরে রাখবেন।
        flowerImageMap.put("rose", R.drawable.rose);
        flowerImageMap.put("lily", R.drawable.lily);
        flowerImageMap.put("sunflower", R.drawable.sunflower);
        flowerImageMap.put("tulip", R.drawable.tulip);
        flowerImageMap.put("daisy", R.drawable.daisy);
        flowerImageMap.put("orchid", R.drawable.orchid);
        flowerImageMap.put("jasmine", R.drawable.jasmine);
        // আরও ফুল যোগ করতে পারেন

        // ListView এর জন্য কাস্টম ArrayAdapter সেটআপ করা হচ্ছে
        flowerAdapter = new FlowerAdapter(this, R.layout.list_item_flower, flowerList);
        listViewFlowers.setAdapter(flowerAdapter);

        // Add Flower বাটনে ক্লিক লিসেনার সেট করা হচ্ছে
        btnAddFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlower();
            }
        });

        // ListView এর আইটেমে ক্লিক লিসেনার সেট করা হচ্ছে
        listViewFlowers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFlower = flowerList.get(position);
                // FlowerDetailActivity চালু করার জন্য Intent ব্যবহার করা হচ্ছে
                Intent intent = new Intent(MainActivity.this, FlowerDetailActivity.class);
                intent.putExtra("FLOWER_NAME", selectedFlower); // ফুলের নাম পাঠানো হচ্ছে
                startActivity(intent);
            }
        });
    }

    private void addFlower() {
        String flowerName = etFlowerName.getText().toString().trim();
        if (flowerName.isEmpty()) {
            Toast.makeText(this, "Please enter a flower name.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ফলের নামটি ছোট হাতের অক্ষরে পরিবর্তন করা হচ্ছে যাতে ম্যাপের সাথে মিলে
        String lowerCaseFlowerName = flowerName.toLowerCase(Locale.getDefault());

        // ডুপ্লিকেট চেক করা হচ্ছে
        if (!flowerList.contains(lowerCaseFlowerName)) {
            flowerList.add(lowerCaseFlowerName); // লিস্টে ছোট হাতের অক্ষরে যোগ করা হচ্ছে
            flowerAdapter.notifyDataSetChanged(); // Adapter কে ডেটা পরিবর্তনের কথা জানানো হচ্ছে
            etFlowerName.setText(""); // ইনপুট ফিল্ড খালি করা হচ্ছে
            Toast.makeText(this, flowerName + " added to list.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, flowerName + " is already in the list.", Toast.LENGTH_SHORT).show();
        }
    }

    // ListView এর জন্য কাস্টম ArrayAdapter ক্লাস
    private class FlowerAdapter extends ArrayAdapter<String> {

        private int layoutResource;
        private List<String> flowers;

        public FlowerAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.layoutResource = resource;
            this.flowers = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
                holder = new ViewHolder();
                holder.ivFlowerIcon = convertView.findViewById(R.id.ivFlowerIcon);
                holder.tvFlowerNameItem = convertView.findViewById(R.id.tvFlowerNameItem);
                holder.btnDeleteItem = convertView.findViewById(R.id.btnDeleteItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String flower = flowers.get(position);

            // ফুলের প্রথম অক্ষর বড় হাতের করে ডিসপ্লে করা হচ্ছে
            String displayFlowerName = flower.substring(0, 1).toUpperCase(Locale.getDefault()) + flower.substring(1).toLowerCase(Locale.getDefault());
            holder.tvFlowerNameItem.setText(displayFlowerName);

            // ফুলের ছবি সেট করা হচ্ছে
            Integer imageResId = flowerImageMap.get(flower); // ম্যাপে ছোট হাতের অক্ষরে খোঁজা হচ্ছে
            if (imageResId != null) {
                holder.ivFlowerIcon.setImageResource(imageResId);
            } else {
                holder.ivFlowerIcon.setImageResource(R.drawable.placeholder_flower); // প্লেসহোল্ডার
            }

            // ডিলিট বাটনে ক্লিক লিসেনার
            holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flowers.remove(position); // লিস্ট থেকে আইটেম মোছা হচ্ছে
                    notifyDataSetChanged(); // Adapter কে ডেটা পরিবর্তনের কথা জানানো হচ্ছে
                    Toast.makeText(getContext(), displayFlowerName + " removed.", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        // ViewHolder প্যাটার্ন ব্যবহার করা হচ্ছে পারফরম্যান্স উন্নতির জন্য
        private class ViewHolder {
            ImageView ivFlowerIcon;
            TextView tvFlowerNameItem;
            Button btnDeleteItem;
        }
    }
}
