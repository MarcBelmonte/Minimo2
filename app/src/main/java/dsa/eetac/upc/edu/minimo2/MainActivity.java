package dsa.eetac.upc.edu.minimo2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private RecyclerAdapter myAdapter;
    private DibaAPI myApi;
   // ProgressBar pb1;

    TextView tvCityName;
    TextView tvCityIne;
    ImageView ivCityImage;

    ProgressDialog progressDialog;

    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvCityName = findViewById(R.id.cityName);
        tvCityIne = findViewById(R.id.cityId);
        ivCityImage = findViewById(R.id.photo);

        Intent intent = getIntent();

        //Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();



        myApi = DibaAPI.createAPIRest();
        getData();
    }
    private void getData() {
        Call<Cities> elementCall = myApi.getData();

        elementCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if(response.isSuccessful()){
                    Cities cities = response.body();

                    List<Element> elementList = cities.getElements();

                    for(int i = 0; i<elementList.size(); i++){
                        Log.i("Nom: " + elementList.get(i).getMunicipiNom(), response.message());
                    }

                    if(elementList.size() != 0){
                        myAdapter.addElements(elementList);
                    }

                    progressDialog.hide();
                } else {
                    Log.e("Response failure", String.valueOf(response.errorBody()));

                    progressDialog.hide();

                    //Show the alert dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage(response.message())
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, which) -> finish());

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Log.e("No connection", t.getMessage());

                progressDialog.hide();

                //Show the alert dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(t.getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> finish());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            });
        }

    @Override
    protected void onResume() {
        super.onResume();
        if (token != null) {
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            token = data.getStringExtra("token");
        }
    }
}


