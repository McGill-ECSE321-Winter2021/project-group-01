package ca.mcgill.ecse321.autoRepair;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity{
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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Login()).commit();
        }
        bottomNav.setVisibility(View.GONE);

    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
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
            };

    /**
     * @author Marc Saber
     * Logs in the customer
     * @param view :
     */
    public void login(View view){
        final EditText username = findViewById(R.id.Username);
        final EditText password = findViewById(R.id.Password);
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
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText("Access Denied")
                                .show();
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * @author Marc Saber
     * Takes you to the sign up page
     * @param view :
     */
    public void goToSignup(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Signup()).commit();
    }

    /**
     * @author Marc Saber
     * Signs up the customer
     * @param view :
     */
    public void signup(View view){
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
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText(errorResponse.get("message").toString())
                                .show();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    }

    /**
     * @author Eric Chehata
     * Logs out the customer
     * @param view :
     */
    public void logout(View view){
        customerUsername=null;
        userType=null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Login()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);
    }

    /**
     * @author Eric Chehata
     * Edits the profie of a customer
     * @param view :
     */
    public void editProfile(View view){

        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName = findViewById(R.id.lastName);
        final EditText address = findViewById(R.id.address);
        final EditText phoneNumber = findViewById(R.id.phoneNumber);
        final EditText zipCode = findViewById(R.id.zipCode);
        final EditText email = findViewById(R.id.email);

        final TextView firstNameText = findViewById(R.id.firstNameView);
        final TextView lastNameText = findViewById(R.id.lastNameView);
        final TextView addressText = findViewById(R.id.addressView);
        final TextView phoneNumberText = findViewById(R.id.phoneNumberView);
        final TextView zipCodeText = findViewById(R.id.zipCodeView);
        final TextView emailText = findViewById(R.id.emailView);

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
                    firstName.setText("");
                    lastName.setText("");
                    phoneNumber.setText("");
                    email.setText("");
                    address.setText("");
                    zipCode.setText("");
                    getProfile(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author Eric Chehata
     * Gets the profile of a customer
     * @param view :
     */
    public void getProfile(View view) {
        final TextView firstName = findViewById(R.id.firstNameView);
        final TextView lastName = findViewById(R.id.lastNameView);
        final TextView address = findViewById(R.id.addressView);
        final TextView phoneNumber = findViewById(R.id.phoneNumberView);
        final TextView zipCode = findViewById(R.id.zipCodeView);
        final TextView email = findViewById(R.id.emailView);
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author Eric Chehata
     * Gets all the cars belonging to a given customer
     * @param view :
     */
    public void getCars(View view){
        final TextView cars = findViewById(R.id.cars);

        HttpUtils.get("cars/"+customerUsername, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner plateNumberSpinner = findViewById(R.id.plateNumberRemove);
                    StringBuilder carsString = new StringBuilder();
                    String[] plateNumbers = new String[response.length()];
                    for(int i=0; i<response.length(); i++){
                        JSONObject car = response.getJSONObject(i);
                        carsString.append(car.getString("model")).append(", ").append(car.getString("transmission")).append(", ").append(car.getString("plateNumber")).append("\n");
                        plateNumbers[i]= car.getString("plateNumber");
                    }
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(plateNumbers));

                    plateNumberAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
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
                    cars.setText(carsString.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * @author Eric Chehata
     * Adds a car that belongs to a given customer to the system
     * @param view :
     */
    public void addCar(View view){
        final EditText model = findViewById(R.id.model);
        final EditText plateNumber = findViewById(R.id.plateNumber);
        final Spinner carTransmission = findViewById(R.id.carTransmission);
        final TextView cars = findViewById(R.id.cars);

        RequestParams rp = new RequestParams();
        rp.put("model", model.getText());
        rp.put("carTransmission", carTransmission.getSelectedItem().toString());
        rp.put("plateNumber", plateNumber.getText());

        HttpUtils.post("add_car/"+customerUsername, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    JSONArray customerCars= serverResp.getJSONArray("cars");
                    StringBuilder carsString = new StringBuilder();
                    for(int i=0; i<customerCars.length(); i++){
                        JSONObject car = customerCars.getJSONObject(i);
                        carsString.append(car.getString("model")).append(", ").append(car.getString("transmission")).append(", ").append(car.getString("plateNumber")).append("\n");
                    }
                    cars.setText(carsString.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author Eric Chehata
     * Removes a car that belongs to a given cutsomer from the system
     * @param view :
     */
    public void removeCar(View view) {
        final Spinner plateNumber = findViewById(R.id.plateNumberRemove);
        final TextView cars = findViewById(R.id.cars);

        RequestParams rp = new RequestParams();
        rp.put("plateNumber", plateNumber.getSelectedItem().toString());

        HttpUtils.delete("remove_car/" + customerUsername, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    JSONArray customerCars= serverResp.getJSONArray("cars");
                    StringBuilder carsString = new StringBuilder();
                    for(int i=0; i<customerCars.length(); i++){
                        JSONObject car = customerCars.getJSONObject(i);
                        carsString.append(car.getString("model")).append(", ").append(car.getString("transmission")).append(", ").append(car.getString("plateNumber")).append("\n");
                    }
                    cars.setText(carsString.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author Mohammad Saeid Nafar
     * Gets the previous appointments of a customer in the system
     * @param view :
     */
    public void getPreviousAppointments(View view){

        RequestParams parameters = new RequestParams();
        parameters.put("username", customerUsername);

        HttpUtils.get("past_appointmentsOf/", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner appointmentSpinner = findViewById(R.id.pastAppointments);

                    String[] appointments = new String[response.length()];

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
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(appointments));

                    ArrayAdapter<String> appointmentsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * @author Mohammad Saeid Nafar
     * Adds a customer review for a given appointment to the system
     * @param view :
     */
    public void addReview(View view){
        Spinner appointmentSpinner = findViewById(R.id.pastAppointments);
        Spinner ratingSpinner = findViewById(R.id.rating);
        EditText description = findViewById(R.id.description);

        String appointment = appointmentSpinner.getSelectedItem().toString();
        String[] appointmentAttributes = appointment.split(",");
        String[] appointmentTimes = appointmentAttributes[2].split("-");

        RequestParams parameters = new RequestParams();
        parameters.put("startDate", appointmentAttributes[1]);
        parameters.put("startTime", appointmentTimes[0]);
        parameters.put("description", description.getText());
        parameters.put("serviceRating", ratingSpinner.getSelectedItem().toString());

        HttpUtils.post("create_review/", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Thank you for your feedback!")
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * @author Mohammad Saeid Nafar
     * Gets the reviews of a given customer from the system
     * @param view :
     */
    public void getCustomerReviews(View view){
        RequestParams parameters = new RequestParams();
        parameters.put("username", customerUsername);

        HttpUtils.get("view_reviews_of_customer", parameters, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner reviewSpinner = findViewById(R.id.reviewsSpinner);

                    String[] reviews = new String[response.length()];

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
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(reviews));

                    ArrayAdapter<String> reviewsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * @author Mohammad Saeid Nafar
     * Edits a customer review given a new service rating and a new description
     * @param view :
     */
    public void editReview(View view){
        Spinner reviewSpinner = findViewById(R.id.reviewsSpinner);
        Spinner ratingSpinner = findViewById(R.id.newRating);
        EditText description = findViewById(R.id.newDescription);

        String review = reviewSpinner.getSelectedItem().toString();
        String[] reviewAttributes = review.split(",");

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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * @author Mohammad Saeid Nafar
     * Deletes a customer review from the system
     * @param view :
     */
    public void deleteReview(View view){
        Spinner reviewSpinner = findViewById(R.id.reviewsSpinner);

        String review = reviewSpinner.getSelectedItem().toString();
        String[] reviewAttributes = review.split(",");

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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorMessage, Throwable throwable) {
                try {
                    if(errorMessage.equals("true"))
                        new SweetAlertDialog(MainActivity.this)
                            .setTitleText("Review deleted successfully")
                            .show();
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

    /**
     * @author Tamara Zard Aboujaoudeh
     * Makes an appointment
     * @param view
     */
    public void makeAppointment(View view) {
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
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(response.get("message").toString())
                            .show();
                    getAppointmentsOfCustomer(view);

                } catch (JSONException e) {
                   e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * @author Tamara Zard Aboujaoudeh
     * This method is to update an appointment
     * @param view
     */
    public void updateAppointment(View view) {
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
        String[] appointmentElements = appointment.split(";");

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
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(response.get("message").toString())
                            .show();
                    getAppointmentsOfCustomer(view);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /**
     * @author Tamara Zard Aboujaoudeh
     * This method is to cancel an appointment
     * @param view
     */
    public void cancelAppointment(View view) {
        final Spinner appointmentSpinner = findViewById(R.id.appointmentCancelAppointment);

        String appointment = appointmentSpinner.getSelectedItem().toString();
        String[] appointmentElements = appointment.split(";");

        RequestParams requestParams = new RequestParams();
        requestParams.put("username", customerUsername);
        requestParams.put("serviceName", appointmentElements[0]);
        requestParams.put("appointmentDate", appointmentElements[1]);
        requestParams.put("appointmentTime", appointmentElements[2]);

        HttpUtils.delete("cancel_appointment/", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(response.get("message").toString())
                            .show();
                    getAppointmentsOfCustomer(view);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    /**
     * @author Tamara Zard Aboujaoudeh
     * This method is to get all the appointments of a specific customer for the spinners
     * @param view
     */
    public void getAppointmentsOfCustomer(View view) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("username",customerUsername);

        HttpUtils.get("upcoming_appointmentsOf/",requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner updateAppointmentSpinner = findViewById(R.id.appointmentUpdateAppointment);
                    Spinner cancelAppointmentSpinner = findViewById(R.id.appointmentCancelAppointment);
                    String appointmentString = "";
                    String[] appointmentsArray = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject appointment = response.getJSONObject(i);
                        appointmentString += appointment.getJSONObject("service").getString("name") + ";"
                                + appointment.getJSONObject("timeSlot").getString("startDate") + ";"
                                + appointment.getJSONObject("timeSlot").getString("startTime");
                        appointmentsArray[i] = appointmentString;
                    }
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(appointmentsArray));

                    appointmentAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * This method is to get all the available services
     * @param view
     */
    public void getServices(View view) {

        HttpUtils.get("view_all_services/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner serviceBookAppointmentSpinner = findViewById(R.id.serviceBookAppointment);
                    Spinner serviceUpdateAppointmentSpinner = findViewById(R.id.newServiceUpdateAppointment);
                    String[] serviceArray = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = response.getJSONObject(i);
                        serviceArray[i] = service.getString("name");
                    }
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(serviceArray));

                    serviceAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();

                } catch (JSONException e) {
                  e.printStackTrace();
                }

            }
        });

    }

    /**
     * @author Fadi Tawfik Beshay
     * This method gets all the reviews in the system
     * @param view
     */
    public void getReviews(View view) {
        final TextView reviews = findViewById(R.id.reviews);

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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * @author Fadi Tawfik Beshay
     * This method gets all the upcoming appointments for a customer for the home page
     * @param view
     */
    public void getAppointments(View view){
        final TextView appointments = findViewById(R.id.appointments);

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
                   e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * @author Robert Aprahamia
     * This method gets all the reminders in the system
     * @param view
     */
    public void getReminders(View view){
        final TextView reminders = findViewById(R.id.allReminders);
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
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * @author Robert Aprahamian
     * This method gets all the available services to display on the home
     * @param view
     */
    public void getServicesHome(View view){
        final TextView services =  findViewById(R.id.allServices);

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
                    }
                    services.setText(servicesString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText(errorResponse.get("message").toString())
                            .show();
                } catch (JSONException e) {
                   e.printStackTrace();
                }
            }

        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}