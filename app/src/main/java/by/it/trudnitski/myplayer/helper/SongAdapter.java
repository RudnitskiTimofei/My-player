package by.it.trudnitski.myplayer.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.it.trudnitski.myplayer.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<Song> mData;
    private OnSongsListener mOnSongsListener;

    public SongAdapter(List<Song> data, OnSongsListener onSongsListener) {
        mData = data;
        mOnSongsListener = onSongsListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, mOnSongsListener);
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
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameView;
        TextView titleView;
        TextView genreView;
        CardView cardView;
        OnSongsListener mOnSongsListener;

        ViewHolder(@NonNull View itemView, OnSongsListener onSongsListener) {
            super(itemView);
            nameView = itemView.findViewById(R.id.list_name);
            genreView = itemView.findViewById(R.id.list_genre);
            titleView = itemView.findViewById(R.id.list_title);
            cardView = itemView.findViewById(R.id.card_view);
            mOnSongsListener = onSongsListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnSongsListener.OnSongsClick(getAdapterPosition());
        }

    }
    public interface OnSongsListener {
        void OnSongsClick(int position);
    }
}
