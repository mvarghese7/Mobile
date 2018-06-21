package hu.ait.android.weatherinfo.network;


import hu.ait.android.weatherinfo.json2Pojo.WeatherResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather")
    Call<WeatherResult> getWeatherResult(@Query("q") String city, @Query("units") String units, @Query("APPID") String apiKey);
}
