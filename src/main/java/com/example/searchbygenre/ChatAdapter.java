package com.example.searchbygenre;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    List<ChatMessage> chatMessageList;
    Context mContext;

    public ChatAdapter(Context mContext, List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.message,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        holder.MessageUser.setText(chatMessageList.get(position).getMessageUser());
        holder.MessageText.setText(chatMessageList.get(position).getMessageText());
        holder.MessageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                chatMessageList.get(position).getMessageTime()));
       // holder.MessageTime.setText((int) chatMessageList.get(position).getMessageTime());
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView MessageUser;
        TextView MessageText;
        TextView MessageTime ;

        public MyViewHolder(View itemView) {
            super(itemView);

            MessageUser = (TextView) itemView.findViewById(R.id.messageUser) ;
            MessageText = (TextView) itemView.findViewById(R.id.messageText);
            MessageTime  = (TextView) itemView.findViewById(R.id.messageDate);
        }
    }
}
