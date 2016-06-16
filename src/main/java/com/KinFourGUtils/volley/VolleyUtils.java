package com.KinFourGUtils.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.KinFourGUtils.json.PreferencesCookieStore;
import com.KinFourGUtils.utils.ToastUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：KingGGG on 15/12/7 11:05
 * 描述：Volley
 */
public class VolleyUtils {
    String LINE_END = System.getProperty("line.separator");
    String TWO_HYPHENS = "--";
    private static RequestQueue mRequestQueue;

    public static void initialize(Context context) {
        if (mRequestQueue == null) {
            synchronized (VolleyUtils.class) {
                if (mRequestQueue == null) {
                    DefaultHttpClient httpclient = new DefaultHttpClient();
                    CookieStore cookieStore = new PreferencesCookieStore(context);
                    httpclient.setCookieStore(cookieStore);
                    HttpStack httpStack = new HttpClientStack(httpclient);
                    mRequestQueue = Volley.newRequestQueue(context, httpStack);
                }
            }
        }
        mRequestQueue.start();
    }


    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return mRequestQueue;
    }

    public static JsonObjectRequest newJSONObjectRequest(String url, JSONObject params, Response.Listener<JSONObject> listener,
                                                         Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(url, params, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "android");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return request;
    }


    public static void startObjectRequest(final Context mContext, String url, Map<String, Object> map, Response.Listener<JSONObject> listener) {
        JSONObject params = new JSONObject(map);
//        Log.e("test",params.toString());

        JsonObjectRequest request = newJSONObjectRequest(url, params, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showLong(VolleyErrorHelper.getMessage(error, mContext), mContext);
            }
        });
        getRequestQueue().add(request);
    }

    public static void startRequest(final Context mContext, String url, Map<String, String> map, Response.Listener<JSONObject> listener) {

        JSONObject params = new JSONObject(map);
        JsonObjectRequest request = newJSONObjectRequest(url, params, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showLong(VolleyErrorHelper.getMessage(error, mContext), mContext);
            }
        });
        getRequestQueue().add(request);
    }


    public static void startPostFileFormRequest(final Context mContext, String urlPath, FormFile[] files, Map<String, String> params, ResponseListener listener) {
        PostFileRequest mPostFileRequest = new PostFileRequest(urlPath, files, params, listener);
        mPostFileRequest.setShouldCache(false);
        mPostFileRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(mPostFileRequest);
    }

}