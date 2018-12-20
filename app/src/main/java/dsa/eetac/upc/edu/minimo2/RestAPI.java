package dsa.eetac.upc.edu.minimo2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestAPI {
    public final String BASE_URL = "https://do.diba.cat/api/dataset/municipis/format/json/pag-ini/1/pag-fi/";

    //URL API
    //Llamamos a la función para obtener su info
    @GET("\"pag-ini/{var1}/pag-fi/{num2}\"")
    Call<Cities> cities(@Path("var1") String var1, @Path("var2") String var2);
}

