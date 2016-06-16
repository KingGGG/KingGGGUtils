package com.KinFourGUtils.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 作者：KingGGG on 16/4/7 10:42
 * 描述：
 */
public class PostRequest extends Request<String> {
    private Map<String,String> mParams;
    private ResponseListener mListener ;

    public PostRequest(String url, Map<String, String> params, ResponseListener listener) {
        super(Method.POST,url, listener);
        this.mParams = params ;
        this.mListener = listener ;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError()) ;
        }
    }

    @Override
    protected void deliverResponse(String response) {
        this.mListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.mParams;
    }
}
