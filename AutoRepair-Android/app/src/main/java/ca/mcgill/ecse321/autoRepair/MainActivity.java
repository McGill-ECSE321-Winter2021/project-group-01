package ca.mcgill.ecse321.autoRepair;

import android.content.Context;
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
import androidx.appcompat.app.AlertDialog;
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
import android.graphics.Color;

public class MainActivity extends AppCompatActivity{
    private String error = null;
    private String customerUsername = null;
    private String userType = null;
    private Context context = null;
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


        HttpUtils.post("register_customer/", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
        try {
            if (firstName==null||lastName==null||phoneNumber==null||address==null||zipCode==null||username==null
            ||password==null||model==null||carTransmissionNew==null||email==null){
                error = "Missing sign up information";
            }
            else {
                new SweetAlertDialog(MainActivity.this)
                        .setTitleText("Registration Successful, Login please!")
                        .show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Login()).commit();
            }
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
        });
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
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText("Login Successful!")
                                .show();
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
    public void goToLogin(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Login()).commit();
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
        final TextView cars = (TextView) findViewById(R.id.cars);

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
                    String carsString = "";
                    for(int i=0; i<customerCars.length(); i++){
                        JSONObject car = customerCars.getJSONObject(i);
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

    public void makeAppointment(View v){
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
        String minute =  "" + startTime.getCurrentMinute();

        String startTimeString = hour+":"+minute;

        requestParams.put("appointmentTime",startTimeString);

        HttpUtils.post("make_appointment/"+customerUsername, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
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
        });

    }

    public void updateAppointment(View v){
        final Spinner appointment = findViewById(R.id.appointmentUpdateAppointment);
        final Spinner newService = findViewById(R.id.newServiceUpdateAppointment);
        final DatePicker newStartDate = findViewById(R.id.newDateUpdateAppointment);
        final TimePicker newStartTime = findViewById(R.id.newTimeUpdateAppointment);


        String day = "" + newStartDate.getDayOfMonth();
        String month = "" + (newStartDate.getMonth() + 1);
        String year = "" + newStartDate.getYear();

        String newStartDateString = year + "-" + month + "-" + day;

        RequestParams requestParams = new RequestParams();
        requestParams.put("newServiceName", newService.getSelectedItem().toString());
        requestParams.put("newAppointmentDate", newStartDateString);

        String hour = "" + newStartTime.getCurrentHour();
        String minute =  "" + newStartTime.getCurrentMinute();

        String newStartTimeString = hour+":"+minute;

        requestParams.put("newAppointmentTime",newStartTimeString);

        HttpUtils.patch("update_appointment/"+customerUsername, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
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
        });

    }

    public void cancelAppointment(View v){
        final Spinner appointment = findViewById(R.id.appointmentCancelAppointment);

        RequestParams requestParams = new RequestParams();


        HttpUtils.delete("cancel_appointment/"+customerUsername, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {

                try {
                    JSONObject serverResp = new JSONObject(response.toString());
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
        });

    }

    public void getAppointmentsOfCustomer(View v){
        error="";
        final TextView appointments = (TextView) findViewById(R.id.myAppointments);

        HttpUtils.get("appointmentsOf/"+customerUsername, new RequestParams() ,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {

                try {
                    Spinner updateAppointmentSpinner = findViewById(R.id.appointmentUpdateAppointment);
                    Spinner cancelAppointmentSpinner = findViewById(R.id.appointmentCancelAppointment);
                    String appointmentString = "";
                    String appointmentsArray[] = new String[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject appointment = response.getJSONObject(i);
                        appointmentString += appointment.getString("chosenService") + "; "
                                + appointment.getJSONObject("timeSlot").getString("startDate") + "; " +
                                appointment.getJSONObject("timeSlot").getString("startTime") + "\n";
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

    public void getServices(View v){
        error="";

        HttpUtils.get("view_all_services/", new RequestParams() ,new JsonHttpResponseHandler() {
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
                    serviceUpdateAppointmentSpinner.setAdapter(appointmentAdapter);
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