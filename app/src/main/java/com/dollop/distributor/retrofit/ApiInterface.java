package com.dollop.distributor.retrofit;


import com.dollop.distributor.model.CityData;
import com.dollop.distributor.model.CreditRequestResponse;
import com.dollop.distributor.model.Datum;
import com.dollop.distributor.model.NewOderlist;
import com.dollop.distributor.model.NotificationData;
import com.dollop.distributor.model.OfferDTO;
import com.dollop.distributor.model.OrderModel;
import com.dollop.distributor.model.RatingDTO;
import com.dollop.distributor.model.StateData;
import com.dollop.distributor.model.TokanResponse;
import com.dollop.distributor.model.UpdateStock;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Anil 17/08/2020.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("view_orders")
    Call<NewOderlist> newOderlist(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("order_history")
    Call<OrderModel> getAllOrder(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("order_history")
    Call<Datum> getDetailOrder(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("get_credit_request")
    Call<CreditRequestResponse> get_credit_request(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("get_offer")
    Call<OfferDTO> get_offer(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("distributor_update_token")
    Call<TokanResponse> distributor_update_token(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST("get_all_notification")
    Call<NotificationData> get_all_notification(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("get_city")
    Call<CityData> get_city(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("get_state")
    Call<StateData> getState(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST("get_review_rating")
    Call<RatingDTO> get_review_rating(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST("update_product_stock_quantity")
    Call<UpdateStock> update_product_stock_quantity(@FieldMap HashMap<String, String> hm);


}
