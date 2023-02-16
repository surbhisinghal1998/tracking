package akaalwebsoft.com.wsmtrackerinjava.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static String liveUrl = "https://track.webschoolmanager.com/api/Data/";
    public static Retrofit liveretrofit = null;
    public static Retrofit liveapiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                Log.d("log: ", s);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging).connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        if (liveretrofit == null) {

            liveretrofit = new Retrofit.Builder()
                    .baseUrl(liveUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory
                            .create(gson))
                    .build();

        }
        return liveretrofit;
    }
}
