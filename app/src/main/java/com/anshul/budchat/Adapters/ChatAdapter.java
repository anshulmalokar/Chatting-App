package com.anshul.budchat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshul.budchat.Models.MessagesModel;
import com.anshul.budchat.R;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<MessagesModel> messagesModels;
    Context context;
    int Sender_View_Type=1;
    int Reciever_View_Type=2;

    public ChatAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType==Sender_View_Type){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //Identify
        if(messagesModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
            return Sender_View_Type;
        else
            return Reciever_View_Type;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel messagesModel=messagesModels.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMessage.setText(messagesModel.getMessage());
        }else{
            ((RecieverViewHolder)holder).recieverMessage.setText(messagesModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView recieverMessage,recieverTime;
        public RecieverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recieverMessage=itemView.findViewById(R.id.recieverText);
            recieverTime=itemView.findViewById(R.id.recieverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMessage,senderTime;
        public SenderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            senderMessage=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }


}
