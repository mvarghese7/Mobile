package hu.ait.android.shoppinglist.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.shoppinglist.MainActivity;
import hu.ait.android.shoppinglist.R;
import hu.ait.android.shoppinglist.data.Item;
import hu.ait.android.shoppinglist.data.AppDatabase;


public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>  {



    private List<Item> itemList;

    private Context context;

    public ItemRecyclerAdapter(List<Item> items, Context context) {
        itemList = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(viewRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tvItemName.setText(itemList.get(holder.getAdapterPosition()).getItemTitle());

        holder.tvPrice.setText(context.getString(R.string.priceLabel) + itemList.get(holder.getAdapterPosition()).getPrice());

        if(itemList.get(holder.getAdapterPosition()).getCategory() == 0) {
            holder.ivIcon.setImageResource(R.drawable.food);
        } else if(itemList.get(holder.getAdapterPosition()).getCategory() == 1) {
            holder.ivIcon.setImageResource(R.drawable.clothes);
        } else if(itemList.get(holder.getAdapterPosition()).getCategory() == 2) {
            holder.ivIcon.setImageResource(R.drawable.toiletries);
        }

        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).editItem(itemList.get(holder.getAdapterPosition()));
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(Item item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(Item item) {
        int editPos = findItemIndexByItemId(item.getItemId());
        itemList.set(editPos, item);
        notifyItemChanged(editPos);

    }

    private int findItemIndexByItemId(long todoId) {
        for (int i = 0; i < itemList.size(); i++) {
            if(itemList.get(i).getItemId() == todoId) {
                return i;
            }
        }

        return -1;
    }

    public void removeItem(int position) {
        final Item itemToDelete = itemList.get(position);
        itemList.remove(itemToDelete);
        notifyItemRemoved(position);
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(context).itemDao().delete(
                        itemToDelete);
            }
        }.start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvItemName;
        private TextView tvPrice;
        private CheckBox cbPurchased;
        private Button btnViewDetails;
        private Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cbPurchased = itemView.findViewById(R.id.cbPurchased);
            btnViewDetails = itemView.findViewById(R.id.btnViewItemDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}