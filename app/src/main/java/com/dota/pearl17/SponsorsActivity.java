package com.dota.pearl17;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SponsorsActivity extends AppCompatActivity {

    private String base_spons_url="";
    RecyclerView sponsorRecycler;
    private SponsAdapter mAdapter;
    private ArrayList<String> sponsor_url = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        Picasso.with(this)
                .load(R.drawable.sponsors_frame)
                .fit()
                .centerCrop()
                .into((ImageView)findViewById(R.id.bg_sponsors));

        sponsorRecycler = (RecyclerView) findViewById(R.id.sponsor_recycler);
        mAdapter = new SponsAdapter(this);
        sponsorRecycler.setAdapter(mAdapter);
        sponsorRecycler.setLayoutManager(new LinearLayoutManager(this));

        updateSponsorURLs();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    void updateSponsorURLs(){

            StringRequest request = new StringRequest(Request.Method.POST, ControllerConstant.url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                    try {
                        JSONObject object = new JSONObject(s);
                        if(object.getInt("success")==1){
                            Toast.makeText(SponsorsActivity.this,"Loaded successfully",Toast.LENGTH_SHORT).show();
                            //update all events

                            try {
                                JSONArray array = new JSONObject(s).getJSONArray("data");
                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject obj = array.getJSONObject(j);
                                    // {"name":"ACT","title":"Title","image":"http:\/\/bits-arena.com\/sponsors\/img\/act.png"}
                                    sponsor_url.add(obj.getString("image"));
                                    mAdapter.notifyItemInserted(sponsor_url.size()-1);
                                }
                                Log.v("Sponsors",s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(SponsorsActivity.this,"Unable to load due to bad internet",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //internet problem
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("tag", "getSponsors");
                    //Log.e("Sent", params.toString());
                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(request);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageButton;

        MyViewHolder(View itemView) {
            super(itemView);
            imageButton= (ImageView) itemView.findViewById(R.id.main_events);
        }
    }

    LayoutInflater inflater;

    private class  SponsAdapter extends RecyclerView.Adapter<MyViewHolder>{

        Context context;

        SponsAdapter(Context context) {
            this.context = context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_main_row,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.imageButton.requestLayout();
            Picasso.with(context)
                    .load(sponsor_url.get(position)) //This is the image url
                    .fit()
                    .centerInside()
                    .into(holder.imageButton);
        }

        @Override
        public int getItemCount() {
            return sponsor_url.size();
        }
    }
}

