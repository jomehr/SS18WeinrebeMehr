package com.example.jan.kassenzettel_scan.network;

import com.loopj.android.http.*;


public class RestClient {

    private static final String BASE_URL = "http://192.168.0.172:8081/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String urlEndpoint, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(urlEndpoint), params, responseHandler);
    }

    public static void post(String urlEndpoint, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(urlEndpoint), params, responseHandler);
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
