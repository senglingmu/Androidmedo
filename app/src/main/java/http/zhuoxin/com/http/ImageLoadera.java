package http.zhuoxin.com.http;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016.9.12.
 */
public class ImageLoadera {
    private ImageView imageView;
    private String lur;
    private LruCache<String,Bitmap> lruCache;
    public ImageLoadera(){
        int mixSize=(int)Runtime.getRuntime().maxMemory()/5;
        lruCache=new LruCache<String,Bitmap>(mixSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
}
