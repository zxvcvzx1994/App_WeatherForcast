package com.cvcompany.vo.myapplication.View;

import android.support.v4.app.NavUtils;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cvcompany.vo.myapplication.Module.Volley.MyVolley;
import com.cvcompany.vo.myapplication.Module.Weather;
import com.cvcompany.vo.myapplication.R;
import com.cvcompany.vo.myapplication.View.Adapter.Myadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {
    private static final String  TAG = Main2Activity.class.getSimpleName();
    private String weather="";
    ArrayList<Weather> arrayList ;
    @BindView(R.id.txtCity)
    TextView txtCity;
    private Myadapter adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       weather = getIntent().getStringExtra("country");
        arrayList = new ArrayList<Weather>();
        Log.i(TAG, "onCreate: "+weather);
        txtCity.setText(""+weather);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        callWeather(weather);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return  true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void callWeather(final String data){
        String url ="http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&units=metric&cnt=7&appid=c0c4a4b4047b97ebc5948ac9c48c0559";
        Log.i(TAG, "callWeather: "+url);
        MyVolley.getSingleton(this).startVolley();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: "+response.toString());

                try {
                    //city
                    JSONObject jsonObject_City = response.getJSONObject("city");
                    txtCity.setText(""+jsonObject_City.getString("name"));
                    //list
                    JSONArray jsonArray_List =  response.getJSONArray("list");
                    for(int i  = 0 ; i< jsonArray_List.length(); i++){
                        JSONObject jsonObject_List = jsonArray_List.getJSONObject(i);
                        //get Date
                        String date = jsonObject_List.getString("dt");
                        // get temp MAX, MIN
                        JSONObject jsonObject_Temp = jsonObject_List.getJSONObject("temp");
                        String tempMax = jsonObject_Temp.getString("max");
                        String tempMin = jsonObject_Temp.getString("min");
                        // get description, icon
                        JSONArray jsonArray_Weather = jsonObject_List.getJSONArray("weather");
                        JSONObject jsonObject_Weather = jsonArray_Weather.getJSONObject(0);
                        String description = jsonObject_Weather.getString("description");
                        String icon = jsonObject_Weather.getString("icon");

                        arrayList.add(new Weather(date, description, tempMax, tempMin, icon));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter = new Myadapter(getApplicationContext(), arrayList);
                recyclerView.setAdapter(adapter);

                MyVolley.getSingleton(getApplicationContext()).stopVolley();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyVolley.getSingleton(getApplicationContext()).stopVolley();
            }
        });

        MyVolley.getSingleton(this).addRequest(jsonObjectRequest);
    }
}
