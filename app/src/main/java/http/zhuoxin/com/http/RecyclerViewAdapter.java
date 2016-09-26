package http.zhuoxin.com.http;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import http.zhuoxin.com.http.VolleyHoder.VelloyHoder;
import http.zhuoxin.com.http.VolleyHoder.VolleyDoHttp;

/**
 * Created by Administrator on 2016.9.11.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter{
    Context context;
    List<VelloyHoder.NewslistBean> data;
    ImageLoader imageLoader;
    VolleyDoHttp volleyDoHttp;
    public RecyclerViewAdapter(Context context,List<VelloyHoder.NewslistBean> data){
        super();
        imageLoader = new ImageLoader();
        this.context=context;
        this.data=data;
        volleyDoHttp=VolleyDoHttp.getInstance();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder)holder).tv_time.setText(data.get(position).getCtime());
        ((MyHolder)holder).tv_title.setText(data.get(position).getTitle());
        ((MyHolder)holder).imageView.setImageResource(R.mipmap.ic_launcher);
//        viewHolder.icon.setImageResource(R.mipmap.ic_launcher);
//        imageLoader.showHttpBitmapFromURLByThread(info.picUrl,viewHolder.icon);
        Log.i("qwe",data.get(position).getPicUrl());
//        imageLoader.showHttpBitmapFromURLByAsyncTask(data.get(position).picUrl,((MyHolder)holder).imageView);
//        volleyDoHttp.imageRequestByGetMethod(data.get(position).picUrl,((MyHolder)holder).imageView);
        volleyDoHttp.imageLoaderByLruCache(data.get(position).getPicUrl(),((MyHolder)holder).imageView);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
}
class MyHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView tv_title;
    public TextView tv_time;
    public MyHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.recycler);
        tv_title=(TextView) itemView.findViewById(R.id.title);
        tv_time=(TextView)itemView.findViewById(R.id.time);
    }
}
