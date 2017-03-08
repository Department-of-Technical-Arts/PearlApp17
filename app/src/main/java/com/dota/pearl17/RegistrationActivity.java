package com.dota.pearl17;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, EventConfirmDialog.confirm, PickDOBDialog.DOB {

    private EditText name, email, phone, college, dob, city;
    private RadioGroup gender;
    private String _name, _email, _phone, _college, _gender, _city;
    private Button register;
    private RadioButton male,female;
    private String _dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Picasso.with(this)
                .load(R.drawable.registration_frame)
                .fit()
                .into((ImageView)findViewById(R.id.bg_register));

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
                if(!hasFocus){
                    //send volley request and fill others

                    StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                            try {
                                JSONObject object = new JSONObject(s);
                                if(object.getInt("success")==1){

                                    name.setText(object.getString("name"));
                                    phone.setText(object.getString("phone"));
                                    college.setText(object.getString("college"));
                                    city.setText(object.getString("city"));
                                    if(object.getInt("gender")==1){
                                        male.setChecked(true);
                                    }else{
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
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("tag", "check_email");
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
                dialog.show(getSupportFragmentManager(),"TAG");

            }
        });

    }

    @Override
    public void onClick(View v) {

        _name = name.getText().toString();
        _email = email.getText().toString();
        _phone = phone.getText().toString();
        _college = college.getText().toString();
        _city = city.getText().toString();
        _dob = dob.getText().toString();

        EventConfirmDialog dialog = new EventConfirmDialog(RegistrationActivity.this);
        dialog.show(getSupportFragmentManager(),"TAG");


    }

    @Override
    public void confirm_events(ArrayList<String> events) {

        final JSONArray finalEvents = new JSONArray();
        for (int i = 0; i < events.size(); i++) {
                finalEvents.put(events.get(i));
        }
        //Send to API
        Toast.makeText(this,"API ko bhej",Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if(object.getInt("success")==1){
                        Toast.makeText(RegistrationActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegistrationActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //internet problem, cannot upload Group member
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "register");
                params.put("email", _email);
                params.put("name", _name);
                params.put("dob", _dob);
                params.put("college", _college);
                params.put("city", _city);
                params.put("events", finalEvents.toString());

                //Log.e("Sent", params.toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);

    }

    @Override
    public void setDOB(int year, int month, int day) {

        dob.setText(day + "." + month + "." + year);
        Toast.makeText(this,day + "." + month + "." + year,Toast.LENGTH_SHORT).show();
    }
}
