package com.chops.app.retrofit;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST(APIClient.Append_URL + "register.php")
    Call<JsonObject> getSignUp(@Body JsonObject object);

    @POST(APIClient.Append_URL + "login.php")
    Call<JsonObject> getSignIn(@Body JsonObject object);

    @POST(APIClient.Append_URL + "home.php")
    Call<JsonObject> getHome(@Body JsonObject object);


    @POST(APIClient.Append_URL + "cplist.php")
    Call<JsonObject> getCategory(@Body JsonObject object);



    @POST(APIClient.Append_URL + "category.php")
    Call<JsonObject> getCategoryAll(@Body JsonObject object);

    @POST(APIClient.Append_URL + "address.php")
    Call<JsonObject> UpdateAddress(@Body JsonObject object);

    @POST(APIClient.Append_URL + "alist.php")
    Call<JsonObject> getAddress(@Body JsonObject object);

    @POST(APIClient.Append_URL + "uprofile.php")
    Call<JsonObject> UpdateProfile(@Body JsonObject object);

    @POST(APIClient.Append_URL + "dlist.php")
    Call<JsonObject> deleteAddress(@Body JsonObject object);

    @POST(APIClient.Append_URL + "order.php")
    Call<JsonObject> sendOrder(@Body JsonObject object);

    @POST(APIClient.Append_URL + "search.php")
    Call<JsonObject> getSearch(@Body JsonObject object);

    @POST(APIClient.Append_URL + "olist.php")
    Call<JsonObject> getOlist(@Body JsonObject object);

    @POST(APIClient.Append_URL + "fpassword.php")
    Call<JsonObject> sendOtp(@Body JsonObject object);

    @POST(APIClient.Append_URL + "cpassword.php")
    Call<JsonObject> ChangePassword(@Body JsonObject object);

    @POST(APIClient.Append_URL + "popular.php")
    Call<JsonObject> getPopular(@Body JsonObject object);

    @POST(APIClient.Append_URL + "noti.php")
    Call<JsonObject> getNoti(@Body JsonObject object);

    @POST(APIClient.Append_URL + "n_read.php")
    Call<JsonObject> readNoti(@Body JsonObject object);

    @POST(APIClient.Append_URL + "order_cancel.php")
    Call<JsonObject> orderCancel(@Body JsonObject object);
}
