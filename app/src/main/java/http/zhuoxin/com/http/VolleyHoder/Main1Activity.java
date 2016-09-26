package http.zhuoxin.com.http.VolleyHoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import http.zhuoxin.com.http.R;
import http.zhuoxin.com.http.RecyclerViewAdapter;
/**
 * Created by Administrator on 2016.9.13.
 */
public class Main1Activity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("qwe", "q");
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        VolleyDoHttp volleyDoHttp = VolleyDoHttp.getInstance();
        volleyDoHttp.strongRequestByGETMethod(GetURL.HTTPURL + "?key=05636e8c14ea716cdb30c1469d258c00&num=30");
        volleyDoHttp.setCallBack(
                new VolleyHttpCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Main1Activity.this, LinearLayoutManager.VERTICAL, false);
                        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(Main1Activity.this, getNewsInfoJSON(response));
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        Log.i("qwe", "onSuccess");
                    }
                    @Override
                    public void onError(VolleyError volleyError) {
                        Log.i("qwe", "没有下载数据");
                    }
                });
    }
    private List<VelloyHoder.NewslistBean> getNewsInfoJSON(String json) {
        List<VelloyHoder.NewslistBean> keys = new ArrayList<>();
        Gson gson = new Gson();
        VelloyHoder velloyHoder = gson.fromJson(json, VelloyHoder.class);
        if (velloyHoder.getCode() == 200) {
            keys = velloyHoder.getNewslist();
        }
            return keys;
        }
}
