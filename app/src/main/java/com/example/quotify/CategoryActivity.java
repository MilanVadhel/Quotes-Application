package com.example.quotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quotify.Adapter.CategoryAdapter;
import com.example.quotify.Model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ArrayList<Category> categoryArrayList;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    GridLayoutManager gridLayoutManager;
    EditText seach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView=findViewById(R.id.categoryRecyclerView);

        categoryArrayList =new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Categories");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Category category=snapshot.getValue(Category.class);
                    categoryArrayList.add(category);
                }
                gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                categoryAdapter=new CategoryAdapter(getApplicationContext(),categoryArrayList);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem item=menu.findItem(R.id.search_category);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
