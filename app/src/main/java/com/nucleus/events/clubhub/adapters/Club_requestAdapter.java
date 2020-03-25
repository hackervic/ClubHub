package com.nucleus.events.clubhub.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nucleus.events.clubhub.Model.Club_request_model;
import com.nucleus.events.clubhub.R;
import com.nucleus.events.clubhub.Request_club_profile;

import java.util.List;

public class Club_requestAdapter extends RecyclerView.Adapter<Club_requestAdapter.ViewHolder> {


    Context context;
    List<Club_request_model> club_request_models;

    DatabaseReference databaseReference, databaseReference1;
    String str ,send_uid ;


    public Club_requestAdapter(Context context, List<Club_request_model> club_request_models) {
        this.context = context;
        this.club_request_models = club_request_models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs_reqest, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Club_request_model clubRequestModel = club_request_models.get(position);
        holder.club_name.setText(clubRequestModel.getCname());
        holder.club_catagory.setText(clubRequestModel.getCcatagory());
        holder.club_collage.setText(clubRequestModel.getCcollage());
        Glide.with(context).load(clubRequestModel.getClogo()).into(holder.c_image);
        String st = club_request_models.get(position).getClubuid();
        Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Clubs").child(st);


        holder.club_approved.setVisibility(View.INVISIBLE);
        holder.get_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String st = club_request_models.get(position).getClubuid();
                        Toast.makeText(context, st, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Request_club_profile.class);
                        intent.putExtra("send_uid",st);
                        context.startActivity(intent);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String st = club_request_models.get(position).getClubuid();
                Toast.makeText(context, st, Toast.LENGTH_SHORT).show();




            }
        });

//        holder.decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                databaseReference1.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        dataSnapshot.getRef().child("approved").setValue("false");
//                        Toast.makeText(context, "Club Disapproved S8uccessfully", Toast.LENGTH_SHORT).show();
//                        holder.linearLayout.setVisibility(View.INVISIBLE);
//                        holder.club_approved.setText("Club DisApproved");
//                        holder.club_approved.setVisibility(View.VISIBLE);
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });


        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String sti = dataSnapshot.child("approved").getValue().toString();
                    if (sti.equals("true")) {
                        holder.linearLayout.setVisibility(View.GONE);
                        holder.club_approved.setText("Club Approved ");
                        holder.club_approved.setVisibility(View.VISIBLE);

                    }
                    else if(sti.equals("false"))
                    {
                        holder.linearLayout.setVisibility(View.GONE);
                        holder.club_approved.setText("Club Not Approved ");
                        holder.club_approved.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return club_request_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView club_name;
        public TextView club_catagory;
        public TextView club_collage;
        TextView club_approved;
        ImageView c_image;
        Button get_detail;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            club_name = itemView.findViewById(R.id.r_c_name);
            club_catagory = itemView.findViewById(R.id.r_C_catagory);
            club_collage = itemView.findViewById(R.id.r_c_collage);
            c_image = itemView.findViewById(R.id.r_c_image);
            get_detail = itemView.findViewById(R.id.btn_g_detail);
            club_approved = itemView.findViewById(R.id.club_approved_tv);
            linearLayout = itemView.findViewById(R.id.l2);


        }


        @Override
        public void onClick(View v) {

        }
    }
}
