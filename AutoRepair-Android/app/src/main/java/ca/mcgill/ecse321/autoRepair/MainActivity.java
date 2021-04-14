package ca.mcgill.ecse321.autoRepair;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity{
    private String error = null;
    private String customerUsername = null;
    private String userType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Login()).commit();
        }
      bottomNav.setVisibility(View.GONE);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {

                        case R.id.nav_home:
                            selectedFragment = new Home();
                            break;

                        case R.id.nav_appointment:
                            selectedFragment = new Appointment();
                            break;

                        case R.id.nav_review:
                            selectedFragment = new Review();
                            break;

                        case R.id.nav_account:
                            selectedFragment = new Profile();
                            break;

                        case R.id.nav_car:
                            selectedFragment = new Car();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }

    }

    public void login(View v){
        final EditText username = (EditText) findViewById(R.id.Username);
        final EditText password = (EditText) findViewById(R.id.Password);
        RequestParams rp = new RequestParams();
        rp.put("username", username.getText());
        rp.put("password", password.getText());

        HttpUtils.post("login", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    customerUsername = serverResp.getString("username");
                    userType = serverResp.getString("userType");
                    if(!userType.equals("customer")){
                        error = "Access denied";
                        customerUsername = null;
                        userType = null;
                    }
                    else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Home()).commit();
                        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
                        bottomNav.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                // refreshErrorMessage();
                //  ((TextView) v.findViewById(R.id.newevent_name)).setText("");
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }

    public void goToSignup(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Signup()).commit();
    }

    public void logout(View v){
        customerUsername=null;
        userType=null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Login()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);
    }




    public void editProfile(View view){
        error ="";

        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText address = (EditText) findViewById(R.id.address);
        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        final EditText zipCode = (EditText) findViewById(R.id.zipCode);
        final EditText email = (EditText) findViewById(R.id.email);

        RequestParams rp = new RequestParams();
        rp.put("firstName", firstName.getText());
        rp.put("lastName", lastName.getText());
        rp.put("phoneNumber", phoneNumber.getText());
        rp.put("email", email.getText());
        rp.put("address", address.getText());
        rp.put("zipCode", zipCode.getText());


        HttpUtils.patch("edit_profile/" + "bob", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try {
                    //JSONObject server = response.getJSONObject(0);
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                // refreshErrorMessage();
                //  ((TextView) v.findViewById(R.id.newevent_name)).setText("");
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void getProfile(View v) {
        error = "";
        final TextView firstName = (TextView) findViewById(R.id.firstNameView);
        final TextView lastName = (TextView) findViewById(R.id.lastNameView);
        final TextView address = (TextView) findViewById(R.id.addressView);
        final TextView phoneNumber = (TextView) findViewById(R.id.phoneNumberView);
        final TextView zipCode = (TextView) findViewById(R.id.zipCodeView);
        final TextView email = (TextView) findViewById(R.id.emailView);
        HttpUtils.get("view_customer/" + customerUsername, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {

                    JSONObject profile = response.getJSONObject("profile");
                    firstName.setText(profile.getString("firstName"));
                    lastName.setText(profile.getString("lastName"));
                    address.setText(profile.getString("address"));
                    phoneNumber.setText(profile.getString("phoneNumber"));
                    zipCode.setText(profile.getString("zipCode"));
                    email.setText(profile.getString("email"));

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                // refreshErrorMessage();
                //  ((TextView) v.findViewById(R.id.newevent_name)).setText("");
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void getCars(View v){
        error="";
        final TextView cars = (TextView) findViewById(R.id.cars);

        HttpUtils.get("cars/"+customerUsername, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    String carsString = "";
                    for(int i=0; i<response.length(); i++){
                        JSONObject car = response.getJSONObject(i);
                        carsString+=car.getString("model")+", "
                                +car.getString("transmission")+", "
                                +car.getString("plateNumber")+"\n";
                    }
                    cars.setText(carsString);

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                // refreshErrorMessage();
                //  ((TextView) v.findViewById(R.id.newevent_name)).setText("");
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }

        });

    }


    public void addCar(View v){
        error ="";
        final EditText model = findViewById(R.id.model);
        final EditText plateNumber = findViewById(R.id.plateNumber);
        final Spinner carTransmission = findViewById(R.id.carTransmission);

        RequestParams rp = new RequestParams();
        rp.put("model", model.getText());
        rp.put("carTransmission", carTransmission.getSelectedItem().toString());
        rp.put("plateNumber", plateNumber.getText());

        HttpUtils.post("add_car/"+customerUsername, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                // refreshErrorMessage();
                //  ((TextView) v.findViewById(R.id.newevent_name)).setText("");
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void removeCar(View v) {
        error = "";
        final EditText plateNumber = findViewById(R.id.plateNumberRemove);

        RequestParams rp = new RequestParams();
        rp.put("plateNumber", plateNumber.getText());

        HttpUtils.delete("remove_car/" + customerUsername, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());

                } catch (JSONException e) {
                    error += e.getMessage();
                }
                // refreshErrorMessage();
                //  ((TextView) v.findViewById(R.id.newevent_name)).setText("");
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


}