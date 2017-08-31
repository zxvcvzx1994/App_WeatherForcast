package com.cvcompany.vo.myapplication.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvcompany.vo.myapplication.MainActivity;
import com.cvcompany.vo.myapplication.Module.Volley.MyVolley;
import com.cvcompany.vo.myapplication.Module.Weather;
import com.cvcompany.vo.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinh on 8/30/2017.
 */

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{
    private ArrayList<Weather> arrayList;
    private Context context;
    public Myadapter(Context context, ArrayList<Weather> arrayList){
        this.context=context;
        this.arrayList= arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Weather weather = arrayList.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy");
        long d =Long.valueOf( weather.getDate());
                Date date = new Date(d*1000L);

        holder.txtDate.setText(""+simpleDateFormat.format(date));
        holder.txtDescription.setText(""+weather.getDescription());
        int  tempFrom  = (int) Float.parseFloat(weather.getTempFrom());
        int tempTo  = (int) Float.parseFloat(weather.getTempTo());
        holder.txtTemFrom.setText(""+tempFrom+  "\u00B0"+"C");
        holder.txtTemTo.setText(""+tempTo+  "\u00B0"+"C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.getImg()+".png").into(holder.img);
    }

    @Override
    public int getItemCount() {
        return (arrayList==null)?0:arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtDescription)
        TextView txtDescription;
        @BindView(R.id.txtTempFrom)
        TextView txtTemFrom;
        @BindView(R.id.txtTempTo)
        TextView txtTemTo;
        @BindView(R.id.img)
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
