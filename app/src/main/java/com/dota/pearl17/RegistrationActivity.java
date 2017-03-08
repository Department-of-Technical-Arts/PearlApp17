package com.dota.pearl17;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, EventConfirmDialog.confirm, PickDOBDialog.DOB {

    private EditText name, email, phone, college, dob;
    private RadioGroup gender;
    private String _name, _email, _phone, _college, _gender;;
    private Button register;
    private RadioButton male,female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.name);
        dob = (EditText) findViewById(R.id.dob);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        college = (EditText) findViewById(R.id.college);
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
        register.setTypeface(custom_font_bold);
        male.setTypeface(custom_font);
        female.setTypeface(custom_font);

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

        EventConfirmDialog dialog = new EventConfirmDialog(RegistrationActivity.this);
        dialog.show(getSupportFragmentManager(),"TAG");


    }

    @Override
    public void confirm_events() {
        //Send to API
        Toast.makeText(this,"API ko bhej",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDOB(int year, int month, int day) {

        dob.setText(day + "." + month + "." + year);
        Toast.makeText(this,day + "." + month + "." + year,Toast.LENGTH_SHORT).show();
    }
}
