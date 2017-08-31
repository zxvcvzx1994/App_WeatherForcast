package com.cvcompany.vo.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cvcompany.vo.myapplication.Module.Volley.MyVolley;
import com.cvcompany.vo.myapplication.View.Main2Activity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName() ;
    @BindView(R.id.imgCloud)
    ImageView imgCloud;
    @BindView(R.id.imgRain)
    ImageView imgRain;
    @BindView(R.id.imgWeather)
    ImageView imgWeather;
    @BindView(R.id.imgWind)
    ImageView imgWind;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.btnNextDay)
    Button btnNextDay;
    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.txtCity)
    TextView txtCity;
    @BindView(R.id.txtCountry)
    TextView txtCountry;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTemp)
    TextView txtTemp;
    @BindView(R.id.txtHumidity)
    TextView txtHumidity;
    @BindView(R.id.txtWind)
    TextView txtWind;
    @BindView(R.id.txtCloud)
    TextView txtCloud;
    private String data="";
    private String all="";
    private String speed="";
    private String country="";
    private String humidity="";
    private String temp="";
    private String picture="";
    private String city="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(savedInstanceState!=null){
            data = savedInstanceState.getString("et");
             city= savedInstanceState.getString("city");
            country = savedInstanceState.getString("country");
            picture = savedInstanceState.getString("icon");
            temp = savedInstanceState.getString("temp");
            humidity = savedInstanceState.getString("hum");
            speed = savedInstanceState.getString("wind");
            all = savedInstanceState.getString("all");

            etCity.setText(data);
            txtCity.setText(city);
            txtCountry.setText(country);
            Picasso.with(MainActivity.this).load(picture).into(imgWeather);
            txtTemp.setText(temp);
            txtHumidity.setText(humidity);
            txtWind.setText(speed);
            txtCloud.setText(all);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("et",etCity.getText().toString());
        outState.putString("city",txtCity.getText().toString());
        outState.putString("country",txtCountry.getText().toString());
        outState.putString("icon",picture);
        outState.putString("temp",txtTemp.getText().toString());
        outState.putString("hum",txtHumidity.getText().toString());
        outState.putString("wind",txtWind.getText().toString());
        outState.putString("all",txtCloud.getText().toString());
    }

    @OnClick(R.id.btnNextDay)
    public void btnNextDay(){
        if (data.length()>0) {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("country", data);
            startActivity(intent);
        }else{
            Toast.makeText(this, ""+getResources().getString(R.string.nulldata), Toast.LENGTH_SHORT).show();
        }
    }
    @OnTextChanged(R.id.etCity)
    public void etCity(){
        etCity.setBackgroundColor(Color.TRANSPARENT);
    }
    @OnClick(R.id.btnOk)
    public void btnOk(){
        data = etCity.getText().toString().trim();

        if(data.length()==0){
            etCity.setBackgroundColor(Color.RED);
            Toast.makeText(this, ""+getResources().getString(R.string.nulldata), Toast.LENGTH_SHORT).show();
        }else
            getCurrentWeather(data);

    }


    public void getCurrentWeather(String data){
        String url= "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=c2ac1e5fc041e4f885de21be122aecee";

        MyVolley.getSingleton(this).startVolley();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: "+response.toString());
                try {
                    long dt=Long.valueOf(  response.getString("dt"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy hh:mm:ss");
                    Date date = new Date(dt*1000L);
                    String day = simpleDateFormat.format(date);
                    txtDate.setText(day);
                    city = response.getString("name");
                    txtCity.setText(city);
                    JSONObject jsonObject_Main =  response.getJSONObject("main");
                    JSONArray jsonArray_Weather =  response.getJSONArray("weather");
                    JSONObject jsonObject_Wind = response.getJSONObject("wind");
                    JSONObject jsonObject_Cloud  =response.getJSONObject("clouds");
                    JSONObject jsonObject_Sys = response.getJSONObject("sys");
                    JSONObject jsonObject_Weather = jsonArray_Weather.getJSONObject(0);
                    String icon = jsonObject_Weather.getString("icon");
                    picture = "http://openweathermap.org/img/w/"+ icon +".png";
                    Picasso.with(MainActivity.this).load(picture).into(imgWeather);
                    temp = jsonObject_Main.getString("temp");
                    txtTemp.setText(temp +  "\u00B0"+"C");
                    humidity = jsonObject_Main.getString("humidity");
                    txtHumidity.setText(humidity +"%");
                    speed = jsonObject_Wind.getString("speed");
                    txtWind.setText(speed +"m/s");
                    all = jsonObject_Cloud.getString("all");
                    txtCloud.setText(all +"%");
                    country = jsonObject_Sys.getString("country");
                    txtCountry.setText(""+ country);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                MyVolley.getSingleton(MainActivity.this).stopVolley();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyVolley.getSingleton(MainActivity.this).stopVolley();
            }
        });

        MyVolley.getSingleton(this).addRequest(jsonObjectRequest);

    }
}
