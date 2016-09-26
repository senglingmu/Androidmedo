package http.zhuoxin.com.http;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Administrator on 2016.9.11.
 */
public class ImageLoader {
    private ImageView imageView;
    private String urlStr;
    // 缓存类
    private LruCache<String, Bitmap> lruCache;
    public ImageLoader() {
        // 创建出 缓存区大小
        int maxSize = (int) Runtime.getRuntime().maxMemory()/5;
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            // 用于计算每一个需要缓存的对象的大小的 一定要重写到这个
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    //添加Bitmap到缓冲中
    public void addBitmapToCache(String urlStr, Bitmap bitmap) {
        if (getBitmapFromCache(urlStr) == null)
        lruCache.put(urlStr, bitmap);
        Log.i("asd",lruCache.get(urlStr).toString());
    }
     //从缓存中获取
    public Bitmap getBitmapFromCache(String urlStr) {
        return lruCache.get(urlStr);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (((String) imageView.getTag()).equals(urlStr)) {
                imageView.setImageBitmap((Bitmap) msg.obj);
                Log.i("==", "true");
            }
        }
    };
    // 获取图片 从HttpUrl当中
    private Bitmap getHttpBitmapFromURL(final String urlStr){
        InputStream inputStream = null;
        Bitmap bitmap = getBitmapFromCache(urlStr);
        try {
//            Log.i("test","http操作"+urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            inputStream = new BufferedInputStream(connection.getInputStream());
            // 生成bitmap
            bitmap = BitmapFactory.decodeStream(inputStream);
            addBitmapToCache(urlStr, bitmap);
            //Thread.sleep(1000);
            // 不要在这里直接设置我们的ImageViewBitmap
            connection.disconnect();
            inputStream.close();
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } finally {
        }
        return bitmap;
    }
    //通过多线程的方法进行请求图片 并且缓存
    public void showHttpBitmapFromURLByThread(final String urlStr, ImageView imageView) {
        this.urlStr = urlStr;
        this.imageView = imageView;
        new Thread() {
            @Override
            public void run() {
                super.run();
                // Message的复用
                Bitmap bitmap = getBitmapFromCache(urlStr);
                if(bitmap == null){
                    bitmap = getHttpBitmapFromURL(urlStr);
                    addBitmapToCache(urlStr,bitmap);
                }
                Message message = Message.obtain();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }
    // 通过异步任务加载我们的图片
    public void showHttpBitmapFromURLByAsyncTask(String urlStr,ImageView imageView){
        new HttpBitmapAsyncTask(imageView).execute(urlStr);
    }
    //异步处理类
    private class HttpBitmapAsyncTask extends AsyncTask<String,Integer,Bitmap> {

        private ImageView imageView;
        public HttpBitmapAsyncTask(ImageView imageView){
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String bitmapUrl = params[0];
            Log.i("asd",bitmapUrl);
            Bitmap bitmap = getBitmapFromCache(bitmapUrl);
            if(bitmap == null){
                Log.i("asd","下载了图片");
                bitmap = getHttpBitmapFromURL(bitmapUrl);
                addBitmapToCache(bitmapUrl,bitmap);
            }else{
                Log.i("asd","没有下载图片");
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
