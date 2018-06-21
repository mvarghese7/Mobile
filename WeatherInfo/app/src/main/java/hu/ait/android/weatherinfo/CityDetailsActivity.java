package hu.ait.android.weatherinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.ait.android.weatherinfo.json2Pojo.WeatherResult;
import hu.ait.android.weatherinfo.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityDetailsActivity extends AppCompatActivity {

    private final String API_KEY = "d09fe873af8cbd3923e6a8fbdfcacfcb";
    private final String URL_BASE = "http://api.openweathermap.org/data/2.5/";
    private TextView tvCurrTemp;
    private TextView tvMinTemp;
    private TextView tvMaxTemp;
    private TextView tvDescription;
    private TextView tvHumidity;
    private TextView tvSunrise;
    private TextView tvSunset;
    private ImageView ivWeatherIcon;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("city name");

        TextView tvCityTitle;
        tvCityTitle = findViewById(R.id.tvCityTitle);
        tvCityTitle.setText(cityName);

        tvCurrTemp = findViewById(R.id.tvCurrTemp);
        tvMinTemp = findViewById(R.id.tvMinTemp);
        tvMaxTemp = findViewById(R.id.tvMaxTemp);
        tvDescription = findViewById(R.id.tvDescription);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvSunrise = findViewById(R.id.tvSunrise);
        tvSunset = findViewById(R.id.tvSunset);
        ivWeatherIcon = (ImageView) findViewById(R.id.ivWeatherIcon);


        try {
            makeRequest();
        } catch(Exception e) {
            tvDescription.setText(R.string.cannot_find_city);
        }
    }

    private void makeRequest() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        weatherAPI.getWeatherResult(cityName, getString(R.string.metric), API_KEY).enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {


                try {
                    String currTemp = "" +response.body().getMain().getTemp() + getString(R.string.degrees_celsius);
                    tvCurrTemp.setText(currTemp);

                    String minTemp = getString(R.string.min_temp)+response.body().getMain().getTempMin() +
                            getString(R.string.degrees_celsius);
                    tvMinTemp.setText(minTemp);

                    String maxTemp = getString(R.string.max_temp)+response.body().getMain().getTempMax() +
                            getString(R.string.degrees_celsius);
                    tvMaxTemp.setText(maxTemp);

                    String description = ""+response.body().getWeather().get(0).getDescription();
                    tvDescription.setText(description);

                    String humidity = getString(R.string.humidity)+response.body().getMain().getHumidity();
                    tvHumidity.setText(humidity);

                    Date sunriseTime = new Date(response.body().getSys().getSunrise());
                    String sunrise = getString(R.string.sunrise)+ new SimpleDateFormat("HH:mm").format(sunriseTime);
                    tvSunrise.setText(sunrise);

                    Date sunsetTime = new Date(response.body().getSys().getSunset());
                    String sunset = getString(R.string.sunset)+ new SimpleDateFormat("HH:mm").format(sunsetTime);
                    tvSunset.setText(sunset);

                    String iconUrl = getString(R.string.image_url_base) + response.body().getWeather().get(0).getIcon() + ".png";
                    Glide.with(CityDetailsActivity.this).load(iconUrl).into(ivWeatherIcon);

                } catch(Exception e) {
                    tvDescription.setText(R.string.cannot_find_city);
                }


            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(CityDetailsActivity.this, "Error: "+
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", t.getMessage());
            }
        });

    }

}
