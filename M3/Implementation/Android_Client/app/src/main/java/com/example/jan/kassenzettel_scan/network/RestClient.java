package com.example.jan.kassenzettel_scan.network;

import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.HttpEntity;

/*
Rest Client for handling http network requests to our server.
https://github.com/loopj/android-async-http
http://loopj.com/android-async-http/
*/

public class RestClient {

    private static final String BASE_URL = "http://192.168.0.171:1337/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String urlEndpoint, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(urlEndpoint), params, responseHandler);
    }

    public static void post(Context context, String urlEndpoint, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(urlEndpoint), entity, null, responseHandler);
    }

    public static void patch(String urlEndpoint, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.patch(getAbsoluteUrl(urlEndpoint), params, responseHandler);
    }

    public static void delete(String urlEndpoint, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(urlEndpoint), params, responseHandler);
    }

    private static String getAbsoluteUrl(String endpoint) {
        return BASE_URL + endpoint;
    }
}
