package work.checkarr.com.checkarr.API;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import work.checkarr.com.checkarr.Classes.Authentication;

public interface RetroService {

    /*@GET("/users/{user}")
    Call<ResponseBody> greetUser(@Path("user") @NonNull String user);

    @GET("/test")
    Call<ResponseBody> testCode();*/

    @Headers("Content-type: application/json")
    @POST("/api/auth/login")
    Call<Authentication> getVectors(@Body @NonNull JsonObject var1);

    @Headers("Content-type: application/json")
    @POST("/api/auth/register")
    Call<ResponseBody> createUser(@Body @NonNull JsonObject data);
}
