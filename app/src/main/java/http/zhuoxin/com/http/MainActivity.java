package http.zhuoxin.com.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<NewsInfo> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)this.findViewById(R.id.rv_main);
//        new NewsAsync().execute("http://apis.baidu.com/txapi/keji/keji?key=05636e8c14ea716cdb30c1469d258c00&num=30");
    }
    private String getJSONStr(String urlStr){
        String json="";
        StringBuffer stringBuffer=new StringBuffer();
        try {
            URL url=new URL(urlStr);
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestProperty("apikey","05636e8c14ea716cdb30c1469d258c00");
            InputStream inputStream=urlConnection.getInputStream();/////////
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String readLine="";
            while((readLine=bufferedReader.readLine())!=null){
                stringBuffer.append(readLine);
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){

        }
        json=stringBuffer.toString();
        return json;
    }
//    private class NewsAsync extends AsyncTask<String,Integer,List<NewsInfo>>{///////
//
//        @Override
//        protected void onPostExecute(List<NewsInfo> newsInfos) {
//            super.onPostExecute(newsInfos);
//            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
//            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,newsInfos);
//            recyclerView.setAdapter(recyclerViewAdapter);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            Log.i("qwe","1");
//        }
//
//        @Override
//        protected List<NewsInfo> doInBackground(String... params) {
//            return getNewsInfoJSON(getJSONStr(params[0]));////////
//        }
//    }
    private List<NewsInfo> getNewsInfoJSON(String json){
//        List<NewsInfo> keys = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            if (jsonObject.getInt("code") == 200) {
//                JSONArray jsonArray = jsonObject.getJSONArray("newslist");
//                JSONObject indexObject;
//                NewsInfo indexInfo;
//                for (int index = 0; index < jsonArray.length(); index++) {
//                    indexObject = jsonArray.getJSONObject(index);
//                    // 根据indexObject 生成相应的实体类的对象
//                    Log.i("qwe",indexObject.toString());
//                    indexInfo = new NewsInfo(indexObject.getString("title"), indexObject.getString("ctime"), indexObject.getString("picUrl"));
//                    keys.add(indexInfo);
//                }
//            }
//        } catch (JSONException e) {
//        }
        List<NewsInfo> keys=new ArrayList<>();
        Gson gson=new Gson();
        try {
            JSONObject jsonObject=new JSONObject(json);
            Log.i("qwe",json);
            if (jsonObject.getInt("code")==200){
                JSONArray jsonArray=jsonObject.getJSONArray("newslist");
                JSONObject indexObject;
                NewsInfo indexInfo;
                for (int i=0;i<jsonArray.length();i++){
                    indexObject=jsonArray.getJSONObject(i);
                    indexInfo=gson.fromJson(indexObject.toString(),NewsInfo.class);
                    keys.add(indexInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return keys;
    }
}
