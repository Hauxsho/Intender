package com.example.intender.Chat;

import android.graphics.Color;

import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.example.intender.R;

import java.util.List;

    public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolders> {
        private List<ChatObject> chatList;
        private Class<ChatAdapter> context;

        public ChatAdapter(List<ChatObject> chatList, Class<ChatAdapter> context) {
            this.chatList = chatList;
            this.context = context;
        }


        @Override
        public ChatViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_chat, null, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutView.setLayoutParams(lp);
            ChatViewHolders rcv = new ChatViewHolders(layoutView);

            return rcv;
        }

        @Override
        public void onBindViewHolder(ChatViewHolders holder, int position) {
            holder.mMessage.setText(chatList.get(position).getMessage());
            if (chatList.get(position).getCurrentUser()) {
                holder.mMessage.setGravity(Gravity.END);
                holder.mMessage.setTextColor(Color.parseColor("#404040"));
                holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
            } else {
                holder.mMessage.setGravity(Gravity.START);
                holder.mMessage.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
            }

        }

        @Override
        public int getItemCount() {
            return this.chatList.size();
        }
    }

