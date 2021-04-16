package ca.mcgill.ecse321.autoRepair;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity{
    private String error = null;
    private String customerUsername = null;
    private String userType = null;
    ArrayAdapter<String> plateNumberAdapter;
    ArrayAdapter<String> appointmentAdapter;
    ArrayAdapter<String> serviceAdapter;

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
                    e.printStackTrace();
                }
                refreshErrorMessage();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorMessage)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void goToSignup(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Signup()).commit();
    }

    public void signup(View v){
        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName =  findViewById(R.id.lastName);
        final EditText phoneNumber =  findViewById(R.id.phoneNumber);
        final EditText email =  findViewById(R.id.email);
        final EditText address = findViewById(R.id.address);
        final EditText zipCode = findViewById(R.id.zipCode);
        final EditText username =  findViewById(R.id.username);
        final EditText password =  findViewById(R.id.password);
        final EditText model =  findViewById(R.id.model);
        final EditText plateNumber = findViewById(R.id.plateNumber);
        final Spinner carTransmissionNew = findViewById(R.id.carTransmissionNew);

        RequestParams rp = new RequestParams();
        rp.put("firstName", firstName.getText());
        rp.put("lastName", lastName.getText());
        rp.put("email", email.getText());
        rp.put("phoneNumber", phoneNumber.getText());
        rp.put("address", address.getText());
        rp.put("zipCode", zipCode.getText());
        rp.put("username", username.getText());
        rp.put("password", password.getText());
        rp.put("model", model.getText());
        rp.put("carTransmission", carTransmissionNew.getSelectedItem().toString());
        rp.put("plateNumber", plateNumber.getText());


        if (firstName.getText().toString().equals("")||lastName.getText().toString().equals("")||phoneNumber.getText().toString().equals("")
                ||address.getText().toString().equals("")||zipCode.getText().toString().equals("")||username.getText().toString().equals("")
                ||password.getText().toString().equals("")||model.getText().toString().equals("")||
                email.getText().toString().equals("")){
            error = "Missing sign up information";
            new SweetAlertDialog(MainActivity.this)
                    .setTitleText("Missing sign up information")
                    .show();
        }

        else {
            HttpUtils.post("register_customer/", rp, new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    try {


                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText("Registration Successful, Login please!")
                                .show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Login()).commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                    try {
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText(errorMessage)
                                .show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });
        }
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

        final TextView firstNameText = (TextView) findViewById(R.id.firstNameView);
        final TextView lastNameText = (TextView) findViewById(R.id.lastNameView);
        final TextView addressText = (TextView) findViewById(R.id.addressView);
        final TextView phoneNumberText = (TextView) findViewById(R.id.phoneNumberView);
        final TextView zipCodeText = (TextView) findViewById(R.id.zipCodeView);
        final TextView emailText = (TextView) findViewById(R.id.emailView);

        RequestParams rp = new RequestParams();
        if(firstName.getText().toString().equals("")){
            rp.put("firstName", firstNameText.getText());
        }else{
            rp.put("firstName", firstName.getText());
        }

        if(lastName.getText().toString().equals("")){
            rp.put("lastName", lastNameText.getText());
        }else{
            rp.put("lastName", lastName.getText());
        }

        if(phoneNumber.getText().toString().equals("")){
            rp.put("phoneNumber", phoneNumberText.getText());
        }else{
            rp.put("phoneNumber", phoneNumber.getText());
        }

        if(email.getText().toString().equals("")){
            rp.put("email", emailText.getText());
        }else{
            rp.put("email", email.getText());
        }

        if(address.getText().toString().equals("")){
            rp.put("address", addressText.getText());
        }else{
            rp.put("address", address.getText());
        }

        if(zipCode.getText().toString().equals("")){
            rp.put("zipCode", zipCodeText.getText());
        }else{
            rp.put("zipCode", zipCode.getText());
        }



        HttpUtils.patch("edit_profile/" + "bob", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                try {
                    //JSONObject server = response.getJSONObject(0);
                    JSONObject serverResp = new JSONObject(response.toString());
                    firstName.setText("");
                    lastName.setText("");
                    phoneNumber.setText("");
                    email.setText("");
                    address.setText("");
                    zipCode.setText("");
                    getProfile(view);
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
                    Spinner plateNumberSpinner = findViewById(R.id.plateNumberRemove);
                    String carsString = "";
                    String plateNumbers[] = new String[response.length()];
                    for(int i=0; i<response.length(); i++){
                        JSONObject car = response.getJSONObject(i);
                        carsString+=car.getString("model")+", "
                                +car.getString("transmission")+", "
                                +car.getString("plateNumber")+"\n";
                        plateNumbers[i]= car.getString("plateNumber");
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(plateNumbers));

                    plateNumberAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    plateNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    plateNumberSpinner.setAdapter(plateNumberAdapter);
                    plateNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
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
        final TextView cars = (TextView) findViewById(R.id.cars);

        RequestParams rp = new RequestParams();
        rp.put("model", model.getText());
        rp.put("carTransmission", carTransmission.getSelectedItem().toString());
        rp.put("plateNumber", plateNumber.getText());

        HttpUtils.post("add_car/"+customerUsername, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    Spinner plateNumberSpinner = findViewById(R.id.plateNumberRemove);
                    JSONObject serverResp = new JSONObject(response.toString());
                    JSONArray customerCars= serverResp.getJSONArray("cars");
                    String carsString = "";
                    String plateNumbers[] = new String[response.length()];
                    for(int i=0; i<customerCars.length(); i++){
                        JSONObject car = customerCars.getJSONObject(i);
                        carsString+=car.getString("model")+", "
                                +car.getString("transmission")+", "
                                +car.getString("plateNumber")+"\n";
                        plateNumbers[i]= car.getString("plateNumber");
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(plateNumbers));


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

    public void removeCar(View v) {
        error = "";
        final Spinner plateNumber = findViewById(R.id.plateNumberRemove);
        final TextView cars = (TextView) findViewById(R.id.cars);

        RequestParams rp = new RequestParams();
        rp.put("plateNumber", plateNumber.getSelectedItem().toString());

        HttpUtils.delete("remove_car/" + customerUsername, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    Spinner plateNumberSpinner = findViewById(R.id.plateNumberRemove);
                    JSONObject serverResp = new JSONObject(response.toString());
                    JSONArray customerCars= serverResp.getJSONArray("cars");
                    String carsString = "";
                    String plateNumbers[] = new String[response.length()];
                    for(int i=0; i<customerCars.length(); i++){
                        JSONObject car = customerCars.getJSONObject(i);
                        carsString+=car.getString("model")+", "
                                +car.getString("transmission")+", "
                                +car.getString("plateNumber")+"\n";
                        plateNumbers[i]= car.getString("plateNumber");
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(plateNumbers));

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

    public void getPreviousAppointments(View v){

        RequestParams parameters = new RequestParams();
        parameters.put("username", customerUsername);

        HttpUtils.get("past_appointmentsOf/", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner appointmentSpinner = findViewById(R.id.pastAppointments);

                    String appointments[] = new String[response.length()];

                    for(int i=0; i<response.length(); i++){
                        JSONObject appointment = response.getJSONObject(i);
                        JSONObject service = appointment.getJSONObject("service");
                        JSONObject timeSlot = appointment.getJSONObject("timeSlot");

                        String appointmentString = "";
                        appointmentString+=service.getString("name")+","
                                +timeSlot.getString("startDate")+","
                                +timeSlot.getString("startTime")+"-"
                                +timeSlot.getString("endTime");
                        appointments[i]=appointmentString;
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(appointments));

                    ArrayAdapter<String> appointmentsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    appointmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    appointmentSpinner.setAdapter(appointmentsAdapter);
                    appointmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

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

    public void addReview(View v){
        Spinner appointmentSpinner = findViewById(R.id.pastAppointments);
        Spinner ratingSpinner = findViewById(R.id.rating);
        EditText description = findViewById(R.id.description);

        String appointment = appointmentSpinner.getSelectedItem().toString();
        String appointmentAttributes[] = appointment.split(",");
        String appointmentTimes [] = appointmentAttributes[2].split("-");

        RequestParams parameters = new RequestParams();
        parameters.put("startDate", appointmentAttributes[1]);
        parameters.put("startTime", appointmentTimes[0]);
        parameters.put("description", description.getText());
        parameters.put("serviceRating", ratingSpinner.getSelectedItem().toString());

        HttpUtils.post("create_review/", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Thank you for your feedback!")
                            .show();
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

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorMessage)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void getCustomerReviews(View view){
        RequestParams parameters = new RequestParams();
        parameters.put("username", customerUsername);

        HttpUtils.get("view_reviews_of_customer", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner reviewSpinner = findViewById(R.id.reviewsSpinner);

                    String reviews[] = new String[response.length()];

                    for(int i=0; i<response.length(); i++){
                        JSONObject review = response.getJSONObject(i);
                        JSONObject appointment = review.getJSONObject("appointment");
                        JSONObject service = review.getJSONObject("service");
                        JSONObject timeSlot = appointment.getJSONObject("timeSlot");


                        String reviewString = "";
                        reviewString+=service.getString("name")+","
                                +timeSlot.getString("startDate")+","
                                +timeSlot.getString("startTime")+","
                                +review.getString("serviceRating");
                        reviews[i]=reviewString;
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(reviews));

                    ArrayAdapter<String> reviewsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    reviewsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    reviewSpinner.setAdapter(reviewsAdapter);
                    reviewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

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

    public void editReview(View v){
        Spinner reviewSpinner = findViewById(R.id.reviewsSpinner);
        Spinner ratingSpinner = findViewById(R.id.newRating);
        EditText description = findViewById(R.id.newDescription);

        String review = reviewSpinner.getSelectedItem().toString();
        String reviewAttributes[] = review.split(",");

        RequestParams parameters = new RequestParams();
        parameters.put("startDate", reviewAttributes[1]);
        parameters.put("startTime", reviewAttributes[2]);
        parameters.put("newDescription", description.getText());
        parameters.put("newRating", ratingSpinner.getSelectedItem().toString());

        HttpUtils.patch("edit_review/", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {

                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Thank you for your feedback!")
                            .show();
                } catch (Exception e) {
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

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorMessage)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }


    public void deleteReview(View v){
        Spinner reviewSpinner = findViewById(R.id.reviewsSpinner);

        String review = reviewSpinner.getSelectedItem().toString();
        String reviewAttributes[] = review.split(",");

        RequestParams parameters = new RequestParams();
        parameters.put("startDate", reviewAttributes[1]);
        parameters.put("startTime", reviewAttributes[2]);

        HttpUtils.delete("delete_review/", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {

                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Review deleted successfully")
                            .show();
                } catch (Exception e) {
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

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    if(errorMessage.equals(true)){
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText("Review deleted successfully")
                                .show();
                    }
                    else{
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText(errorMessage)
                                .show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void makeAppointment(View v) {
        final Spinner service = findViewById(R.id.serviceBookAppointment);
        final DatePicker startDate = findViewById(R.id.dateBookAppointment);
        final TimePicker startTime = findViewById(R.id.timeBookAppointment);

        String day = "" + startDate.getDayOfMonth();
        String month = "" + (startDate.getMonth() + 1);
        String year = "" + startDate.getYear();

        String startDateString = year + "-" + month + "-" + day;

        RequestParams requestParams = new RequestParams();
        requestParams.put("serviceName", service.getSelectedItem().toString());
        requestParams.put("appointmentDate", startDateString);

        String hour = "" + startTime.getCurrentHour();
        String minute = "" + startTime.getCurrentMinute();

        String startTimeString = hour + ":" + minute;

        requestParams.put("appointmentTime", startTimeString);
        requestParams.put("username", customerUsername);

        HttpUtils.post("make_appointment/", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Appointment booked successfully")
                            .show();
                    getAppointmentsOfCustomer(v);

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

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorMessage)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void updateAppointment(View v) {
        final Spinner appointmentSpinner = findViewById(R.id.appointmentUpdateAppointment);
        final Spinner newService = findViewById(R.id.newServiceUpdateAppointment);
        final DatePicker newStartDate = findViewById(R.id.newDateUpdateAppointment);
        final TimePicker newStartTime = findViewById(R.id.newTimeUpdateAppointment);

        String day = "" + newStartDate.getDayOfMonth();
        String month = "" + (newStartDate.getMonth() + 1);
        String year = "" + newStartDate.getYear();

        String newStartDateString = year + "-" + month + "-" + day;

        String hour = "" + newStartTime.getCurrentHour();
        String minute = "" + newStartTime.getCurrentMinute();

        String newStartTimeString = hour + ":" + minute;

        String appointment = appointmentSpinner.getSelectedItem().toString();
        String appointmentElements[] = appointment.split(";");

        RequestParams requestParams = new RequestParams();
        requestParams.put("username", customerUsername);
        requestParams.put("appointmentDate", appointmentElements[1]);
        requestParams.put("appointmentTime", appointmentElements[2]);
        requestParams.put("newAppointmentDate", newStartDateString);
        requestParams.put("serviceName", appointmentElements[0]);
        requestParams.put("newAppointmentTime", newStartTimeString);
        requestParams.put("newServiceName", newService.getSelectedItem().toString());



        HttpUtils.patch("update_appointment/", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Appointment updated successfully")
                            .show();
                    getAppointmentsOfCustomer(v);


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

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorMessage)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void cancelAppointment(View v) {
        final Spinner appointmentSpinner = findViewById(R.id.appointmentCancelAppointment);

        String appointment = appointmentSpinner.getSelectedItem().toString();
        String appointmentElements[] = appointment.split(";");

        RequestParams requestParams = new RequestParams();
        requestParams.put("username", customerUsername);
        requestParams.put("serviceName", appointmentElements[0]);
        requestParams.put("appointmentDate", appointmentElements[1]);
        requestParams.put("appointmentTime", appointmentElements[2]);

        HttpUtils.delete("cancel_appointment/", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Appointment canceled successfully")
                            .show();
                    getAppointmentsOfCustomer(v);


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

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    if(errorMessage.equals("true")){
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText("Appointment canceled successfully!")
                                .show();
                    }
                    else {
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText(errorMessage)
                                .show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAppointmentsOfCustomer(View v) {
        error = "";
        final TextView appointments = (TextView) findViewById(R.id.myAppointments);
        RequestParams requestParams = new RequestParams();
        requestParams.put("username",customerUsername);

        HttpUtils.get("upcoming_appointmentsOf/",requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner updateAppointmentSpinner = findViewById(R.id.appointmentUpdateAppointment);
                    Spinner cancelAppointmentSpinner = findViewById(R.id.appointmentCancelAppointment);
                    String appointmentString = "";
                    String appointmentsArray[] = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject appointment = response.getJSONObject(i);
                        appointmentString += appointment.getJSONObject("service").getString("name") + ";"
                                + appointment.getJSONObject("timeSlot").getString("startDate") + ";"
                                + appointment.getJSONObject("timeSlot").getString("startTime");
                        appointmentsArray[i] = appointmentString;
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(appointmentsArray));

                    appointmentAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    appointmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    updateAppointmentSpinner.setAdapter(appointmentAdapter);
                    updateAppointmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    cancelAppointmentSpinner.setAdapter(appointmentAdapter);
                    cancelAppointmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    String displayAppointmentStrings = appointmentString + "\n";
                    appointments.setText(displayAppointmentStrings);

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

    public void getServices(View v) {
        error = "";

        HttpUtils.get("view_all_services/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner serviceBookAppointmentSpinner = findViewById(R.id.serviceBookAppointment);
                    Spinner serviceUpdateAppointmentSpinner = findViewById(R.id.newServiceUpdateAppointment);
                    String serviceArray[] = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = response.getJSONObject(i);
                        serviceArray[i] = service.getString("name");
                    }
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(serviceArray));

                    serviceAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    serviceBookAppointmentSpinner.setAdapter(serviceAdapter);
                    serviceBookAppointmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    serviceUpdateAppointmentSpinner.setAdapter(serviceAdapter);
                    serviceUpdateAppointmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            Object item = adapterView.getItemAtPosition(position);
                            if (item != null) {
                                Toast.makeText(MainActivity.this, item.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "Selected",
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


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

    public void getReviews(View v) {
        error = "";
        final TextView reviews = (TextView) findViewById(R.id.reviews); //to be done

        HttpUtils.get("view_all_reviews", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    String reviewsString = "";
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject review = response.getJSONObject(i);
                        reviewsString += review.getJSONObject("service").getString("name") + ": "
                                + review.getString("description") + ", "
                                + review.getString("serviceRating")
                                + "/5"
                                + "\n";
                    }
                    reviews.setText(reviewsString);

                } catch (JSONException e) {
                    error += e.getMessage();
                }
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

    public void getAppointments(View v){
        error="";
        final TextView appointments = (TextView) findViewById(R.id.appointments);

        HttpUtils.get("upcoming_appointments", new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    String appointmentsString = "";
                    for(int i=0; i<response.length(); i++){
                        JSONObject appointment = response.getJSONObject(i);
                        JSONObject timeSlot = appointment.getJSONObject("timeSlot");
                        JSONObject service = appointment.getJSONObject("service");
                        appointmentsString+=service.getString("name")+", "
                                +timeSlot.getString("startDate")+", "
                                +timeSlot.getString("startTime")+"-"
                                +timeSlot.getString("endTime")+"\n";
                    }
                    appointments.setText(appointmentsString);

                } catch (JSONException e) {
                    error += e.getMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
            }

        });

    }
    public void getReminders(View v){
        error="";
        final TextView reminders = (TextView) findViewById(R.id.allReminders);
        RequestParams rp = new RequestParams();
        rp.put("username", customerUsername);

        HttpUtils.get("view_reminders_for_customer/", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    String remindersString = "";
                    for(int i=0; i<response.length(); i++){
                        JSONObject reminder = response.getJSONObject(i);
                        remindersString+="Service: "+
                                reminder.getJSONObject("chosenService").getString("name")+", "+
                                reminder.getString("description")+ "\n";
                    }
                    reminders.setText(remindersString);

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

    public void getServicesHome(View v){
        error="";
        final TextView services = (TextView) findViewById(R.id.allServices); //to be done

        HttpUtils.get("/view_all_services", new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    String servicesString = "";
                    for(int i=0; i<response.length(); i++){
                        JSONObject service = response.getJSONObject(i);
                        servicesString+="Service "+
                                service.getString("name")+", "
                                +"lasts "+service.getString("duration")+" minutes"+", costs "
                                +service.getString("price")+"$"+", rating: "
                                +service.getString("rating")+"\n";
                        // +service.getString(("rating"))+"\n";
                    }
                    services.setText(servicesString);

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