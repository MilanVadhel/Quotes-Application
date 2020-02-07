package com.example.quotify.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quotify.R;

import java.util.ArrayList;

public class CaptionAdapter extends RecyclerView.Adapter<CaptionAdapter.CaptionViewHolder>
{

    Context context;
    ArrayList<String> caption_list=new ArrayList<>();

    public CaptionAdapter(Context context, ArrayList<String> caption_list) {
        this.context = context;
        this.caption_list = caption_list;
    }

    @NonNull
    @Override
    public CaptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.caption_item,parent,false);
        return new CaptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionViewHolder holder, final int position) {
        holder.Caption.setText(caption_list.get(position));
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody="Your Body Here";
                String shareSub=caption_list.get(position);
                intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                intent.putExtra(Intent.EXTRA_TEXT,shareSub);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

               // context.startActivity(Intent.createChooser(intent,"Share via"));
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
        holder.copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager= (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("",caption_list.get(position));
                clipboardManager.setPrimaryClip(clipData);
                clipData.getDescription();
                Toast.makeText(context, "Coppied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return caption_list.size();
    }

    public class CaptionViewHolder extends RecyclerView.ViewHolder
    {
        TextView Caption;
        ImageButton copyButton,shareButton;
        public CaptionViewHolder(@NonNull View itemView) {
            super(itemView);
            Caption=itemView.findViewById(R.id.caption);
            copyButton=itemView.findViewById(R.id.copyBtn);
            shareButton=itemView.findViewById(R.id.shareBtn);
        }
    }
}
