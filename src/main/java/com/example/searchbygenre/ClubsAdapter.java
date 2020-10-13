package com.example.searchbygenre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for displaying data from the model into a row in the recycler view
public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder>{

    public interface  OnClickListener{
        void onItemClicked(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }
    // Club Name Items.
    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ClubsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use Layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // Wrap it inside a view Holder and return it
        return new ViewHolder(todoView);
    }

    //Tells the recycler view how may items are in the List
    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        else{
            return 0;
        }
    }
    // Responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position
        String item = items.get(position);
        //  String id = items.get(id);
        // Bind the item into the specified view holder
        holder.bind(item);
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView tvItem;
        ImageView img_club_thumbnail;
        TextView club_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItem = (TextView) itemView.findViewById(android.R.id.text1);
            club_name = (TextView) itemView.findViewById(R.id.club_name_id) ;
            img_club_thumbnail = (ImageView) itemView.findViewById(R.id.club_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id_club);
        }

        // Update the view inside of the view holder with this data
        // Inform us what position was tapped
        public void bind(String item) {

            club_name.setText(item);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Remove the item from the recycler view
                    // Notify the listener on which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}
