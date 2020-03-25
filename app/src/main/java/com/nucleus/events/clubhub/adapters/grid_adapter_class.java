package com.nucleus.events.clubhub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nucleus.events.clubhub.Model_grid_item;
import com.nucleus.events.clubhub.R;

import java.util.List;

public class grid_adapter_class extends RecyclerView.Adapter<grid_adapter_class.ViewHolder> {

    List<Model_grid_item> model_grid_items;

    public grid_adapter_class(List<Model_grid_item> model_grid_items) {
        this.model_grid_items = model_grid_items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.club_grid_item_layout,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return model_grid_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cimage;
        TextView cname , ccatagory , ccollage , crating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cimage = (ImageView)itemView.findViewById(R.id.grid_club_image);
            cname = (TextView)itemView.findViewById(R.id.grid_club_name);
            ccatagory = (TextView) itemView.findViewById(R.id.grid_club_catagory);
            ccollage = (TextView)itemView.findViewById(R.id.grid_club_collage);
            crating = (TextView) itemView.findViewById(R.id.grid_ratingview);
        }



    }
}


