package by.it.trudnitski.myplayer.helper;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.it.trudnitski.myplayer.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<Song> mData;

    public SongAdapter( List<Song> data ) {
        mData = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongAdapter.ViewHolder holder, final int position) {

        holder.nameView.setText(mData.get(position).getmName());
        holder.titleView.setText(mData.get(position).getmTitle());
        holder.genreView.setText(mData.get(position).getmGenre());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("broadcast");
                intent.putExtra("name", mData.get(position).getmName());
                intent.putExtra("title", mData.get(position).getmTitle());
                intent.putExtra("genre", mData.get(position).getmGenre());
                LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView titleView;
        TextView genreView;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.list_name);
            genreView = itemView.findViewById(R.id.list_genre);
            titleView = itemView.findViewById(R.id.list_title);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
