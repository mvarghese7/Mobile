package hu.ait.android.rosesandthorns.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.rosesandthorns.R;
import hu.ait.android.rosesandthorns.data.Entry;

public class EntryRecyclerAdapter extends RecyclerView.Adapter<EntryRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Entry> entryList;
    private List<String> entryKeys;
    private String uId;
    private int lastPosition = -1;

    public EntryRecyclerAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;
        this.entryList = new ArrayList<Entry>();
        this.entryKeys = new ArrayList<String>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.entry_for_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvUsername.setText(
                entryList.get(holder.getAdapterPosition()).getDisplayName());
        holder.tvEntryRose.setText(
                entryList.get(holder.getAdapterPosition()).getRose());
        holder.tvEntryThorn.setText(
                entryList.get(holder.getAdapterPosition()).getThorn());
        holder.tvEntryBud.setText(
                entryList.get(holder.getAdapterPosition()).getBud());
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public void addEntry(Entry newEntry, String key) {
        entryList.add(newEntry);
        entryKeys.add(key);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUsername;
        public TextView tvEntryRose;
        public TextView tvEntryThorn;
        public TextView tvEntryBud;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvEntryRose = itemView.findViewById(R.id.tvEntryRose);
            tvEntryThorn = itemView.findViewById(R.id.tvEntryThorn);
            tvEntryBud = itemView.findViewById(R.id.tvEntryBud);
        }
    }

}
