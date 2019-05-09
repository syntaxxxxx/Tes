package fendri.inovatif.latihan.networking;

import fendri.inovatif.latihan.model.ResponseBerita;
import fendri.inovatif.latihan.model.ResponseImageSlider;
import fendri.inovatif.latihan.model.ResponseLogin;
import fendri.inovatif.latihan.model.ResponseRegister;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<ResponseRegister> register(
            @Field("vsnama") String nama,
            @Field("vsemail") String email,
            @Field("vspassword") String password,
            @Field("vsgambar") String gambar,
            @Field("vslevel") String level);

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<ResponseRegister> login (
            @Field("edtemail") String email,
            @Field("edtpassword") String password,
            @Field("vslevel") String level);

    @GET("api/get-banner.php")
    Call<ResponseImageSlider> getImagesSlider(
            @Query("idlogin") String idlogin
            );

    @GET("api/get-news.php")
    Call<ResponseBerita> getBerita(
            @Query("idlogin") String idlogin
    );

//    @GET("api/get-galeri.php")
//    Call<ResponseGaleri> getGaleri(
//            @Query("idlogin") String idlogin
//    );
}