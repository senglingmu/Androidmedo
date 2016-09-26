package http.zhuoxin.com.http.VolleyHoder;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import http.zhuoxin.com.http.R;

/**
 * Created by Administrator on 2016.9.13.
 */
public class VolleyDoHttp {
    private VolleyDoHttp(){}
    private static VolleyDoHttp http;
    public static VolleyDoHttp getInstance(){
        if (http==null)
            http=new VolleyDoHttp();
        return http;
    }
    private VolleyHttpCallBack callBack;
    public VolleyHttpCallBack getCallBack(){return callBack;}
    public void setCallBack(VolleyHttpCallBack  callBack){this.callBack=callBack;}
    public void imageRequestByGetMethod(String picUrl, final ImageView imageView){//直接显示到UI，没有缓存
        RequestQueue queue=MainActivity1.getRequestQueue();
        ImageRequest  imageRequest=new ImageRequest(picUrl,new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap bitmap) {//UI
                imageView.setImageBitmap(bitmap);
            }
        },100,100,Bitmap.Config.RGB_565,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {//请求错误的情况下 可以在这里设置
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
        });
        imageRequest.setTag(picUrl);
        queue.add(imageRequest);
    }
private class MyImageCache implements ImageLoader.ImageCache{
    private LruCache<String,Bitmap> lruCache;
        public MyImageCache(){
            int maxCache=20*1024*1024;//20m
            this.lruCache=new LruCache<String,Bitmap>(maxCache){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };
        }
    @Override
    public Bitmap getBitmap(String key) {
        Log.i("qwe","从内存获取的图片");
        return lruCache.get(key);

    }
    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        if (key!=null&&bitmap!=null){
        lruCache.put(key,bitmap);
            Log.i("qwe","存到内存成功");
        }
    }
}
public void imageLoaderByLruCache(String picUrl,ImageView imageView){
    ImageLoader imageLoader=new ImageLoader(MainActivity1.getRequestQueue(),new MyImageCache());
    ImageLoader.ImageListener imageListener =ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
    imageLoader.get(picUrl,imageListener);
}
public void strongRequestByGETMethod(String url){
 final Gson gson=MainActivity1.getGson();
    //拿到请求队列
    RequestQueue queue=MainActivity1.getRequestQueue();
    //实例化一个StringRequest
    //1，请求方式 2，请求地址 3，请求成功回调 4，请求失败回调
    Log.i("qwe","qwe1");
    StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //请求成功调用
            //Liview。setAdapter
            if (callBack!=null)
                callBack.onSuccess(response);
            Log.i("qwe","qwe2");
        }
    }, new Response.ErrorListener() {
        //请求失败调用
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            callBack.onError(volleyError);
            Log.i("qwe","qwe3");
        }
    }){
        @Override
        public Map<String,String> getHeaders()throws AuthFailureError{
            Map<String,String> apikey=new HashMap<>();
            apikey.put("apikey",GetURL.APIKEY);
            Log.i("qwe","qwe4");
            return apikey;
        }
    };
    stringRequest.setTag(url);
    queue.add(stringRequest);
}
}

