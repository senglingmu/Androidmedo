package http.zhuoxin.com.http.VolleyHoder;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016.9.13.
 */
public class MainActivity1 extends Application{
    private static RequestQueue queue;
    private static Gson gson;
    public static Gson getGson(){return gson;}
    public static RequestQueue getRequestQueue(){return queue;}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("qwe","w");
        //创建出请求列表
        queue= Volley.newRequestQueue(getApplicationContext());
        gson=new Gson();
    }
}
