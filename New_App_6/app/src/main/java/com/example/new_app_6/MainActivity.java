package com.example.new_app_6;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView; // ListView Item Click Listener এর জন্য
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale; // toLowerCase() এর জন্য
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etFruitName;
    private Button btnAddFruit;
    private ListView listViewFruits;

    private List<String> fruitList;
    private FruitAdapter fruitAdapter;

    // ফলের নাম (ছোট হাতের অক্ষরে) এবং তাদের Drawable রিসোর্স আইডি ম্যাপ করা হচ্ছে
    private Map<String, Integer> fruitImageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI উপাদানগুলো রেফারেন্স করা হচ্ছে
        etFruitName = findViewById(R.id.etFruitName);
        btnAddFruit = findViewById(R.id.btnAddFruit);
        listViewFruits = findViewById(R.id.listViewFruits);

        fruitList = new ArrayList<>();
        fruitImageMap = new HashMap<>();

        // ফলের নাম এবং তাদের Drawable রিসোর্স আইডি ম্যাপ করা হচ্ছে।
        // নিশ্চিত করুন যে এই ছবিগুলো res/drawable ফোল্ডারে আছে।
        // ছবির নামগুলো ছোট হাতের অক্ষরে রাখবেন।
        fruitImageMap.put("apple", R.drawable.apple);
        fruitImageMap.put("banana", R.drawable.banana);
        fruitImageMap.put("orange", R.drawable.orange);
        fruitImageMap.put("grape", R.drawable.grape);
        fruitImageMap.put("mango", R.drawable.mango);
        fruitImageMap.put("strawberry", R.drawable.strawberry);
        fruitImageMap.put("pineapple", R.drawable.pineapple);
        fruitImageMap.put("cherry", R.drawable.cherry); // উদাহরণস্বরূপ
        fruitImageMap.put("lemon", R.drawable.lemon);   // উদাহরণস্বরূপ
        fruitImageMap.put("watermelon", R.drawable.watermelon); // উদাহরণস্বরূপ
        // আরও ফল যোগ করতে পারেন

        // ListView এর জন্য কাস্টম ArrayAdapter সেটআপ করা হচ্ছে
        fruitAdapter = new FruitAdapter(this, R.layout.list_item_fruit, fruitList);
        listViewFruits.setAdapter(fruitAdapter);

        // Add Fruit বাটনে ক্লিক লিসেনার সেট করা হচ্ছে
        btnAddFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFruit();
            }
        });

        // ListView এর আইটেমে ক্লিক লিসেনার সেট করা হচ্ছে
        listViewFruits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFruit = fruitList.get(position);
                // FruitDetailActivity চালু করার জন্য Intent ব্যবহার করা হচ্ছে
                Intent intent = new Intent(MainActivity.this, FruitDetailActivity.class);
                intent.putExtra("FRUIT_NAME", selectedFruit); // ফলের নাম পাঠানো হচ্ছে
                startActivity(intent);
            }
        });
    }

    private void addFruit() {
        String fruitName = etFruitName.getText().toString().trim();
        if (fruitName.isEmpty()) {
            Toast.makeText(this, "Please enter a fruit name.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ফলের নামটি ছোট হাতের অক্ষরে পরিবর্তন করা হচ্ছে যাতে ম্যাপের সাথে মিলে
        String lowerCaseFruitName = fruitName.toLowerCase(Locale.getDefault());

        // ডুপ্লিকেট চেক করা হচ্ছে
        if (!fruitList.contains(lowerCaseFruitName)) {
            fruitList.add(lowerCaseFruitName); // লিস্টে ছোট হাতের অক্ষরে যোগ করা হচ্ছে
            fruitAdapter.notifyDataSetChanged(); // Adapter কে ডেটা পরিবর্তনের কথা জানানো হচ্ছে
            etFruitName.setText(""); // ইনপুট ফিল্ড খালি করা হচ্ছে
            Toast.makeText(this, fruitName + " added to list.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, fruitName + " is already in the list.", Toast.LENGTH_SHORT).show();
        }
    }

    // ListView এর জন্য কাস্টম ArrayAdapter ক্লাস
    private class FruitAdapter extends ArrayAdapter<String> {

        private int layoutResource;
        private List<String> fruits;

        public FruitAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.layoutResource = resource;
            this.fruits = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
                holder = new ViewHolder();
                holder.ivFruitIcon = convertView.findViewById(R.id.ivFruitIcon);
                holder.tvFruitNameItem = convertView.findViewById(R.id.tvFruitNameItem);
                holder.btnDeleteItem = convertView.findViewById(R.id.btnDeleteItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String fruit = fruits.get(position);

            // ফলের প্রথম অক্ষর বড় হাতের করে ডিসপ্লে করা হচ্ছে
            String displayFruitName = fruit.substring(0, 1).toUpperCase(Locale.getDefault()) + fruit.substring(1).toLowerCase(Locale.getDefault());
            holder.tvFruitNameItem.setText(displayFruitName);

            // ফলের ছবি সেট করা হচ্ছে
            Integer imageResId = fruitImageMap.get(fruit); // ম্যাপে ছোট হাতের অক্ষরে খোঁজা হচ্ছে
            if (imageResId != null) {
                holder.ivFruitIcon.setImageResource(imageResId);
            } else {
                holder.ivFruitIcon.setImageResource(R.drawable.placeholder_fruit); // প্লেসহোল্ডার
            }

            // ডিলিট বাটনে ক্লিক লিসেনার
            holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fruits.remove(position); // লিস্ট থেকে আইটেম মোছা হচ্ছে
                    notifyDataSetChanged(); // Adapter কে ডেটা পরিবর্তনের কথা জানানো হচ্ছে
                    Toast.makeText(getContext(), displayFruitName + " removed.", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        // ViewHolder প্যাটার্ন ব্যবহার করা হচ্ছে পারফরম্যান্স উন্নতির জন্য
        private class ViewHolder {
            ImageView ivFruitIcon;
            TextView tvFruitNameItem;
            Button btnDeleteItem;
        }
    }
}
