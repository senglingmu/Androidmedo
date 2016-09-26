package http.zhuoxin.com.http.VolleyHoder;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2016.9.13.
 */
public interface VolleyHttpCallBack {
    void onSuccess(String response);
    void onError(VolleyError volleyError);
}
