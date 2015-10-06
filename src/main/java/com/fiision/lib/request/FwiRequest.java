//  Project name: FwiCore
//  File name   : FwiRequest.java
//
//  Author      : Phuc, Tran Huu
//  Created date: 8/17/15
//  Version     : 1.00
//  --------------------------------------------------------------
//  Copyright (C) 2012, 2015 Fiision Studio.
//  All Rights Reserved.
//  --------------------------------------------------------------
//
//  Permission is hereby granted, free of charge, to any person obtaining  a  copy
//  of this software and associated documentation files (the "Software"), to  deal
//  in the Software without restriction, including without limitation  the  rights
//  to use, copy, modify, merge,  publish,  distribute,  sublicense,  and/or  sell
//  copies of the Software,  and  to  permit  persons  to  whom  the  Software  is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF  ANY  KIND,  EXPRESS  OR
//  IMPLIED, INCLUDING BUT NOT  LIMITED  TO  THE  WARRANTIES  OF  MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO  EVENT  SHALL  THE
//  AUTHORS OR COPYRIGHT HOLDERS  BE  LIABLE  FOR  ANY  CLAIM,  DAMAGES  OR  OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING  FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN  THE
//  SOFTWARE.
//
//
//  Disclaimer
//  __________
//  Although reasonable care has been taken to  ensure  the  correctness  of  this
//  software, this software should never be used in any application without proper
//  testing. Fiision Studio disclaim  all  liability  and  responsibility  to  any
//  person or entity with respect to any loss or damage caused, or alleged  to  be
//  caused, directly or indirectly, by the use of this software.

package com.fiision.lib.request;


import android.text.*;

import com.fiision.lib.FwiCore.*;
import com.fiision.lib.codec.*;
import com.fiision.lib.foundation.*;
import com.fiision.lib.request.params.*;

import java.net.*;
import java.util.*;


public class FwiRequest {
    
    
    // <editor-fold defaultstate="collapsed" desc="Class's static constructors">
    static public FwiRequest requestWithURL(FwiHttpMethod requestType, String url) {
        try {
            return new FwiRequest(requestType, url);
        }
        catch (Exception ex) {
            return null;
        }
    }
    // </editor-fold>
    
    
    // Global variables
    private URL mUrl = null;
    private FwiHttpMethod mMethod = null;
    private TreeMap<String, String> mHeaders = null;

    // Raw request
    FwiDataParam mRaw = null;
    // Form request
    ArrayList<FwiFormParam> mForm = null;
    ArrayList<FwiMultipartParam> mUpload = null;
    
    
    // Class's constructors
    public FwiRequest(FwiHttpMethod type, final String url) throws Exception {
        mMethod  = type;
        mUrl     = new URL(url);
        mHeaders = new TreeMap<>();
    }

    
    // Class's properties
    public URL getUrl() {
        return mUrl;
    }
    public FwiHttpMethod getMethod() {
        return mMethod;
    }
    public TreeMap<String, String> getHeaders() {
        return mHeaders;
    }
    public FwiData getBody() {
        if (mRaw != null) {
            return mRaw.getData();
        }
        else {
            return null;
        }
    }


    /** Build the request. */
    public long prepare() {
//        mHeaders.put("Accept-Encoding", "gzip, deflate");
        mHeaders.put("Connection", "close");

        /* Condition validation */
        if (mRaw == null && (mForm == null || mForm.size() == 0) && (mUpload == null || mUpload.size() == 0)) return 0;
        long length = 0;

        switch (mMethod) {
            case kDelete: {
                // Do nothing
                break;
            }
            case kGet: {
                String finalURL = String.format("%s?%s", mUrl.toString(), TextUtils.join("&", mForm.toArray()));
                try {
                    URL url = new URL(finalURL);
                    mUrl = url;
                }
                catch (Exception ex) {
                    // Ignore the exception, use original
                }
                break;
            }
            case kPatch:
            case kPost:
            case kPut: {
                if (mForm != null && mUpload == null) {
                    mRaw = new FwiDataParam(FwiCodec.convertStringToData(TextUtils.join("&", mForm.toArray())), "application/x-www-form-urlencoded");
                }
                else if (mForm != null || mUpload != null) {
                    // Define boundary
                    String boundary = String.format("----------%d", (new Date()).getTime());
                    String contentType = String.format("multipart/form-data; boundary=%s", boundary);

                    // Define body
                    String boundaryData = String.format("\r\n--%s\r\n", boundary);
                    FwiMutableData body = new FwiMutableData();

                    if (mUpload != null && mUpload.size() > 0) {
                        for (int i = 0; i < mUpload.size(); i++) {
                            FwiMultipartParam part = mUpload.get(i);

                            body.append(FwiCodec.convertStringToData(boundaryData));
                            body.append(FwiCodec.convertStringToData(String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n", part.getName(), part.getFilename())));
                            body.append(FwiCodec.convertStringToData(String.format("Content-Type: %s\r\n\r\n", part.getContentType())));
                            body.append(part.getData());
                        }
                    }

                    if (mForm != null && mForm.size() > 0) {
                        for (int i = 0; i < mForm.size(); i++) {
                            FwiFormParam pair = mForm.get(i);

                            body.append(FwiCodec.convertStringToData(boundaryData));
                            body.append(FwiCodec.convertStringToData(String.format("Content-Disposition: form-data; name=\"%s\"\r\n\r\n", pair.getKey())));
                            body.append(FwiCodec.convertStringToData(pair.getValue()));
                        }
                    }
                    body.append(FwiCodec.convertStringToData(String.format("\r\n--%s--\r\n", boundary)));

                    mRaw = new FwiDataParam(body, contentType);
                }
                break;
            }
            default: {
                break;
            }
        }

        if (mRaw != null) {
            // Define content type header
            mHeaders.put("Content-Type", mRaw.getContentType());
            length = mRaw.getData().length();
        }
        return length;
    }

    public void setHeader(String header, String value) {
        mHeaders.put(header, value);
    }

    
    // <editor-fold defaultstate="collapsed" desc="FwiForm">
    /** Add key-value. */
    public void addFormParams(FwiFormParam... params) {
        if (mForm == null) this._initializeForm();
        mRaw = null;

        for (FwiFormParam param : params) {
            mForm.add(param);
        }
    }
    /** Like add parameters but will reset the collection. */
    public void setFormParams(FwiFormParam... params) {
        if (mForm == null) {
            this._initializeForm();
        }
        else {
            mForm.clear();
        }

        mRaw = null;
        this.addFormParams(params);
    }

    /** Add multipart data. */
    public void addMultipartParams(FwiMultipartParam... params) {
        if (mUpload == null) this._initializeUpload();
        mRaw = null;

        for (FwiMultipartParam param : params) {
            mUpload.add(param);
        }
    }
    /** Like add multipart data but will reset the collection. */
    public void setMultipartParams(FwiMultipartParam... params) {
        if (mUpload == null) {
            this._initializeUpload();
        }
        else {
            mUpload.clear();
        }

        mRaw = null;
        this.addMultipartParams(params);
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="FwiRaw">
    public void setDataParam(FwiDataParam param) {
        /* Condition validation: Validate method type */
        if (!(mMethod == FwiHttpMethod.kPost || mMethod == FwiHttpMethod.kPatch || mMethod == FwiHttpMethod.kPut)) return;

        /* Condition validation: Validate parameter type */
        if (param == null) return;

        mUpload = null;
        mForm   = null;
        mRaw    = param;
    }
    // </editor-fold>

    
    // Class's private methods
    public void _initializeForm() {
        /* Condition validation */
        if (mForm != null) return;
        mForm = new ArrayList<>(9);
    }
    public void _initializeUpload() {
        /* Condition validation */
        if (mUpload != null) return;
        mUpload = new ArrayList<>(1);
    }
}


//public class HttpRequest {
//    public static final String HEADER_CONTENT_TYPE = "Content-Type";
//    public static final String HEADER_PROJECT_ID = "project_id";
//    public static final String HEADER_AUTHORIZATION = "Authorization";
//
//    public static final String CONTENT_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded";
//    public static final String CONTENT_TYPE_JSON = "application/json";
//
//    private SimpleArrayMap<String, String> mHeaders = new SimpleArrayMap<>();
//    private int responseCode;
//    private String responseBody;
//
//    /**
//     * Add a request header
//     * @param name the header's name
//     * @param value the header's value
//     */
//    public void setHeader(String name, String value) {
//        this.mHeaders.put(name, value);
//    }
//
//    /**
//     * @return this request's response code
//     */
//    public int getResponseCode() {
//        return responseCode;
//    }
//
//    /**
//     *
//     * @return this request's response body
//     */
//    public String getResponseBody() {
//        return responseBody;
//    }
//
//    /**
//     * Post the request
//     * @param url where to post to
//     * @param requestBody the body of the request
//     * @throws IOException
//     */
//    public void doPost(String url, String requestBody) throws IOException {
//        Log.i(LoggingService.LOG_TAG, "HTTP request. body: " + requestBody);
//
//        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//        conn.setDoOutput(true);
//        conn.setUseCaches(false);
//        conn.setFixedLengthStreamingMode(requestBody.getBytes().length);
//        conn.setRequestMethod("POST");
//        for (int i = 0; i < mHeaders.size(); i++) {
//            conn.setRequestProperty(mHeaders.keyAt(i), mHeaders.valueAt(i));
//        }
//        OutputStream out = null;
//        try {
//            out = conn.getOutputStream();
//            out.write(requestBody.getBytes());
//        } finally {
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    // Ignore.
//                }
//            }
//        }
//
//        responseCode = conn.getResponseCode();
//
//        InputStream inputStream = null;
//        try {
//            if (responseCode == 200) {
//                inputStream = conn.getInputStream();
//            } else {
//                inputStream = conn.getErrorStream();
//            }
//            responseBody = getString(inputStream);
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    // Ignore.
//                }
//            }
//        }
//
//        Log.i(LoggingService.LOG_TAG, "HTTP response. body: " + responseBody);
//
//        conn.disconnect();
//    }
//
//    /**
//     * Convenience method to convert an InputStream to a String.
//     * <p/>
//     * If the stream ends in a newline character, it will be stripped.
//     * <p/>
//     * If the stream is {@literal null}, returns an empty string.
//     */
//    private String getString(InputStream stream) throws IOException {
//        if (stream == null) {
//            return "";
//        }
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(stream));
//        StringBuilder content = new StringBuilder();
//        String newLine;
//        do {
//            newLine = reader.readLine();
//            if (newLine != null) {
//                content.append(newLine).append('\n');
//            }
//        } while (newLine != null);
//        if (content.length() > 0) {
//            // strip last newline
//            content.setLength(content.length() - 1);
//        }
//        return content.toString();
//    }
//}
