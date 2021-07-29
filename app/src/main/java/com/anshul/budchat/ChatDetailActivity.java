package com.anshul.budchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.anshul.budchat.Adapters.ChatAdapter;
import com.anshul.budchat.Models.MessagesModel;
import com.anshul.budchat.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        getSupportActionBar().hide();

        final String senderId=auth.getUid();
        String recieveId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");

        binding.userNameChat.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_baseline_person_pin_24).into(binding.chatProfilePic);

        binding.chatBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<MessagesModel> messagesModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messagesModels,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);

        final String senderRoom=senderId+recieveId;
        final String ecieverRoom=recieveId+senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        messagesModels.clear();
                        for(DataSnapshot Snapshot1:snapshot.getChildren()){
                            MessagesModel model=Snapshot1.getValue(MessagesModel.class);
                            messagesModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=binding.etMessage.getText().toString();
                final MessagesModel modle=new MessagesModel(senderId,message);
                modle.setTimeStamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("chats").
                        child(senderRoom).
                        push().
                        setValue(modle).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(ecieverRoom)
                                .push()
                                .setValue(modle).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });
    }
}