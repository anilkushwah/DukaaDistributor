package com.dollop.distributor.retrofit;


import com.dollop.distributor.model.NewOderlist;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by kanchan 17/08/2020.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("view_orders")
    Call<NewOderlist> newOderlist(@FieldMap HashMap<String, String> hm);

}
