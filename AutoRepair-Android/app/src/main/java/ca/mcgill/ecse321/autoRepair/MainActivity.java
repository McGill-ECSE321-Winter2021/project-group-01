
package ca.mcgill.ecse321.autoRepair;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
public class MainActivity extends AppCompatActivity {
    private String error = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void login(View androidV) {
        RequestParams requestParams = new RequestParams();
        String username = "grader";
        String password = "Password1!";
        requestParams.add("username", username);
        requestParams.add("password", password);
        HttpUtils.post("login", requestParams, new JsonHttpResponseHandler() {
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








