package hu.ait.android.weatherinfo;

import android.content.Context;
import android.content.Intent;
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

import hu.ait.android.weatherinfo.data.AppDatabase;
import hu.ait.android.weatherinfo.data.City;


public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder>  {

    private List<City> cityList;

    private Context context;

    public CityRecyclerAdapter(List<City> cities, Context context) {
        cityList = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,
                parent, false);

        return new ViewHolder(viewRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tvCityName.setText(cityList.get(holder.getAdapterPosition()).getCityName());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCity(holder.getAdapterPosition());
            }
        });

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INTENT TO DETAILS ACTIVITY SCREEN
                Intent intent = new Intent(context, CityDetailsActivity.class);
                intent.putExtra("city name", holder.tvCityName.getText());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void addCity(City city) {
        cityList.add(city);
        notifyDataSetChanged();
    }

    private int findCityIndexByCityId(long todoId) {
        for (int i = 0; i < cityList.size(); i++) {
            if(cityList.get(i).getCityId() == todoId) {
                return i;
            }
        }

        return -1;
    }

    public void removeCity(int position) {
        final City cityToDelete = cityList.get(position);
        cityList.remove(cityToDelete);
        notifyItemRemoved(position);
        new Thread() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(context).cityDao().delete(
                        cityToDelete);
            }
        }.start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvCityName;
        private Button btnDelete;
        private Button btnDetails;

        public ViewHolder(View cityView) {
            super(cityView);

            tvCityName = cityView.findViewById(R.id.tvCityName);
            btnDelete = cityView.findViewById(R.id.btnDelete);
            btnDetails = cityView.findViewById(R.id.btnDetails);
        }
    }
}