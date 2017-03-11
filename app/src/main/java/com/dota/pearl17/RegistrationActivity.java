package com.dota.pearl17;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, PickDOBDialog.DOB {

    private EditText name, email, phone, college, dob, city;
    private RadioGroup gender;
    private String _name, _email, _phone, _college, _gender, _city;
    private Button register;
    private RadioButton male, female;
    private String _dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Picasso.with(this)
                .load(R.drawable.registration_frame)
                .fit()
                .into((ImageView) findViewById(R.id.bg_register));

        name = (EditText) findViewById(R.id.name);
        dob = (EditText) findViewById(R.id.dob);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        college = (EditText) findViewById(R.id.college);
        city = (EditText) findViewById(R.id.city);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        register = (Button) findViewById(R.id.register);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condmedium.otf");
        Typeface custom_font_bold = Typeface.createFromAsset(getAssets(), "fonts/goodpro_condblack.otf");

        name.setTypeface(custom_font);
        dob.setTypeface(custom_font);
        email.setTypeface(custom_font);
        phone.setTypeface(custom_font);
        college.setTypeface(custom_font);
        city.setTypeface(custom_font);
        register.setTypeface(custom_font_bold);
        male.setTypeface(custom_font);
        female.setTypeface(custom_font);


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //send volley request and fill others
//                    Log.v("ASD", "Focus change detected");

                    StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

//                            Log.v("response", s);
                            try {
                                JSONObject Object = new JSONObject(s);
                                if (Object.getInt("success") == 1) {

                                    JSONObject object = Object.getJSONObject("data");
                                    name.setText(object.getString("name"));
                                    phone.setText(object.getString("phone"));
                                    college.setText(object.getString("college"));
                                    city.setText(object.getString("city"));
                                    if (object.getString("gender").equals("male")) {
                                        male.setChecked(true);
                                    } else {
                                        female.setChecked(true);
                                    }
                                    dob.setText(object.getString("dob"));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //internet problem, cannot upload Group member
//                            Log.v("ASD", "Internet problem");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("tag", "getDetailsUser");
                            params.put("email", email.getText().toString());
                            //Log.e("Sent", params.toString());
                            return params;
                        }
                    };


                    AppController.getInstance().addToRequestQueue(request);
                }
            }
        });

        register.setOnClickListener(this);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PickDOBDialog dialog = new PickDOBDialog(RegistrationActivity.this);
                dialog.show(getSupportFragmentManager(), "TAG");

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
        i.putExtra("scrollTo",5);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {

//        Log.v("Details", "Details: " + name.getText().toString() + "email: " + email.getText().toString() + phone.getText().toString() + city.getText().toString() + college.getText().toString());

        if (name.getText().toString().trim().equals("") || email.getText().toString().trim().equals("") || phone.getText().toString().trim().equals("") || city.getText().toString().trim().equals("") || college.getText().toString().trim().equals("") || dob.getText().toString().trim().equals("")) {

            Toast.makeText(RegistrationActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;

        }

        _name = name.getText().toString();
        _email = email.getText().toString();
        _phone = phone.getText().toString();
        _college = college.getText().toString();
        _city = city.getText().toString();
        _dob = dob.getText().toString();
        _gender = getSelectedGender();

        //Send to API
//        Toast.makeText(this, "API ko bhej", Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("success") == 1) {
                        Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                        // Clear fields

                        name.setText("");
                        dob.setText("");
                        email.setText("");
                        phone.setText("");
                        college.setText("");
                        city.setText("");
                        male.setSelected(true);
                        female.setSelected(false);

                    } else {
//                        Log.i("success0",object.toString());
                        Toast.makeText(RegistrationActivity.this, object.getString("error_msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegistrationActivity.this, "Unknown error. Please retry later", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //internet problem, cannot upload Group member
//                Log.i("volley error",volleyError.getMessage());
                Toast.makeText(RegistrationActivity.this, "Cannot register due to internet issues", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "registerNewUser");
                params.put("email", _email);
                params.put("name", _name);
                params.put("dob", _dob);
                params.put("college", _college);
                params.put("city", _city);
//                Log.i("gender",_gender);
                params.put("gender", _gender);
                params.put("phone", _phone);
                params.put("event_ids",""); //since we removed the feature later on
                //Log.e("Sent", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request);


    }

    String getSelectedGender(){
        if(male.isChecked()){return "Male";}
        else{return "Female";}
    }

    @Override
    public void setDOB(int year, int month, int day) {

        dob.setText(day + "/" + (month + 1) + "/" + year);
//        Toast.makeText(this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
    }
}
