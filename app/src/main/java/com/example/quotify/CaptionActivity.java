package com.example.quotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quotify.Adapter.CaptionAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CaptionActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    RecyclerView recyclerView;

    DatabaseReference databaseReference;
    String cname;
    ArrayList<String> CAPTION_LIST;
    CaptionAdapter captionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption);
        recyclerView=findViewById(R.id.captionRecyclerView);
        Intent intent=getIntent();
        cname=intent.getStringExtra("CNAME");
        getSupportActionBar().setTitle(cname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CAPTION_LIST=new ArrayList<>();

        databaseReference=FirebaseDatabase.getInstance().getReference(cname);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    CAPTION_LIST.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        CAPTION_LIST.add(snapshot.getValue(String.class));
                    }
                }

                final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
               // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                captionAdapter=new CaptionAdapter(getApplicationContext(),CAPTION_LIST);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(captionAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
