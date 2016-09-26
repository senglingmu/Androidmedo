package http.zhuoxin.com.http;

/**
 * Created by Administrator on 2016.9.12.
 */
public class JsonBean {

////    Bean newBean = new Bean();
//    public void getNewListgson(String json){
//        Gson gson=new GsonBuilder().
//                setPrettyPrinting().create();
//        NewsInfo newsInfo=new NewsInfo(""+10,System.currentTimeMillis()+"","url"+10);
//        List<NewsInfo> newsInfos=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            NewsInfo newsInfo1=new NewsInfo("title"+i,System.currentTimeMillis()+"","url"+10);
//            newsInfos.add(newsInfo1);
//        }
//    }
//    public List<NewsInfo> getNewsListFromJSON(String json){
//        List<NewsInfo> newsInfos=new ArrayList<>();
//        Gson gson=new Gson();
//        try {
//            JSONObject jsonObject=new JSONObject(json);
//            if (jsonObject.getInt("code")==200){
//                JSONArray jsonArray=jsonObject.getJSONArray("newslist");
//                JSONObject indexObject;
//                NewsInfo indexInfo;
//                for (int i=0;i<jsonArray.length();i++){
//                    indexObject=jsonArray.getJSONObject(i);
//                    indexInfo=gson.fromJson(indexObject.toString(),NewsInfo.class);
//                    newsInfos.add(indexInfo);
//
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}
