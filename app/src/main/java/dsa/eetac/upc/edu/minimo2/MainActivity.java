package dsa.eetac.upc.edu.minimo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RestAPI myApi;
    ProgressBar pb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        launchAPI();
        getCities();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void launchAPI(){
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApi = retrofit.create(RestAPI.class);
    }
    private void getCities(){
        RestAPI diba = myApi;
        Call<Cities> citiesCall = diba.cities("1","11");
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                int statusCode = response.code();
                Cities cities = response.body();
                recyclerView.setAdapter(new RecyclerAdapter(((Cities) cities).getElements()));
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    retrofit2.Callback<List<Element>> Callback = new Callback<List<Element>>(){
        @Override
        public void onResponse(Call<List<Element>> call, Response<List<Element>> response) {
            if (response.isSuccessful()) {
                List<Element> data = new ArrayList<>();
                data.addAll(response.body());
                recyclerView.setAdapter(new RecyclerAdapter(data));
            } else {
                Log.d("Callback", "Code: " + response.code() + " Message: " + response.message());
            }
    }
        @Override
        public void onFailure(Call<List<Element>> call, Throwable t) {
            t.printStackTrace();
        }
    };
}


