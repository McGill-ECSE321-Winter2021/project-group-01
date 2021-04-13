package ca.mcgill.ecse321.autoRepair;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Signup extends Fragment {
    private String error = "";

        @Override
        public View onCreateView(
                LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState
        ) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.signup, container, false);
        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        }

    public void signup(View androidV) {
        RequestParams requestParams = new RequestParams();
        String firstName = "Bob";
        String lastName = "Sinclair";
        String phoneNumber ="12345678";
        String email = "bob@mail.ca";
        String address = "123 Ave";
        String zipCode = "A1A1A1";
        String username = "BobBob";
        String password = "bobPassword1";
        String model = "BMW X5";
        String plateNumber = "OKOKOK";
        String carTransmission = "Manual";

        requestParams.add("username", firstName);
        requestParams.add("password", lastName);
        requestParams.add("phoneNumber", phoneNumber);
        requestParams.add("email", email);
        requestParams.add("address", address);
        requestParams.add("zipCode", zipCode);
        requestParams.add("username", username);
        requestParams.add("password", password);
        requestParams.add("model", model);
        requestParams.add("plateNumber", plateNumber);
        requestParams.add("carTransmission", carTransmission);

            HttpUtils.post("register_customer", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    error +="error";
                }
//                refreshErrorMessage();
//                tv.setText("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error +=e.getMessage();
                }
//                refreshErrorMessage();
            }
        });
    }




    }

