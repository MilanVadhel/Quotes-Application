package com.example.quotify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quotify.CaptionActivity;
import com.example.quotify.Model.Category;
import com.example.quotify.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collection;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> implements Filterable
{

    Context context;
    ArrayList<Category> categoryArrayList;
    ArrayList<Category> categoryArrayListAll;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        categoryArrayListAll=new ArrayList<>(categoryArrayList);
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, final int position) {
        holder.cname.setText(categoryArrayList.get(position).getCname());
        Picasso.with(context).load(categoryArrayList.get(position).getImg()).into(holder.cimg);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CaptionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                String categoryName=categoryArrayList.get(position).getCname();
                intent.putExtra("CNAME",categoryName);
                context.startActivity(intent);
            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic(categoryArrayList.get(position).getCname());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Category> filteredList=new ArrayList<>();
            if(charSequence.toString().isEmpty())
            {
                filteredList.addAll(categoryArrayListAll);
            }
            else
            {
                for(Category category:categoryArrayList)
                {
                    if(category.getCname().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filteredList.add(category);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            categoryArrayList.clear();
            categoryArrayList.addAll((Collection<? extends Category>) filterResults.values);
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return filter;
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder {
        TextView cname;
        ImageView cimg,heart;
        CardView card_view;
        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            card_view=itemView.findViewById(R.id.cardView);
            cname=itemView.findViewById(R.id.categoryName);
            cimg=itemView.findViewById(R.id.categoryImg);
            heart=itemView.findViewById(R.id.heartBtn);
        }
    }
}
