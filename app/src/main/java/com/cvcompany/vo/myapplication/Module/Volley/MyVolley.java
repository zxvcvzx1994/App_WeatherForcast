package com.cvcompany.vo.myapplication.Module.Volley;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by vinh on 8/29/2017.
 */

public class MyVolley {
    private  Context context;
    private static  MyVolley myVolley;
    private RequestQueue requestQueue;

    private MyVolley(Context context){
        this.context = context;
    }

    public static  synchronized MyVolley getSingleton(Context context){
        if(myVolley==null){
            myVolley = new MyVolley(context);
        }
        return myVolley;
    }

    public void startVolley(){
        Network network = new BasicNetwork(new HurlStack());
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024*1024);
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }

    public void stopVolley(){
        if(requestQueue!=null)
            requestQueue.stop();
    }

    public<T> void addRequest(Request<T> request){
        requestQueue.add(request);
    }
}
