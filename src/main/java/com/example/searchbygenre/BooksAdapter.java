package com.example.searchbygenre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import org.parceler.Parcels;
import com.bumptech.glide.Glide;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {
    private Context mContext ;
    private List<Book> mData ;

    public interface  OnClickListener{
        void onItemClicked(int position);
    }

    public BooksAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_book_title.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getThumbnail()).into(holder.ivCover);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BookActivity.class);
                System.out.println("Clicked ");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                String Title = mData.get(position).getTitle();
                String Description = mData.get(position).getDescription();
                String Category = mData.get(position).getCategory();
                String Thumbnail = mData.get(position).getThumbnail();

                Book book = new Book(Title, Category, Description, Thumbnail);
                // Passing data to the book activity
                    intent.putExtra("book", Parcels.wrap(book));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_title;
        ImageView ivCover;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            ivCover = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}
