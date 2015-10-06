package com.fiision.lib.services;


import com.fiision.lib.request.*;

import java.io.*;
import java.net.*;
import java.util.*;


public class FwiService {


	// Global variables
	protected FwiRequest mRequest = null;
    protected int mStatusCode = -1;
    
	// Class's constructors
	public FwiService(FwiRequest request) {
        mRequest = request;
	}

	
    // Class's properties
    public int status() {
        return mStatusCode;
    }


	// Class's protected methods
	public String execute() throws Exception {
        mRequest.prepare();

        HttpURLConnection connection = (HttpURLConnection) mRequest.getUrl().openConnection();
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        connection.setRequestMethod(mRequest.getMethod().method);
        if (mRequest.getBody() != null && mRequest.getBody().length() > 0) connection.setFixedLengthStreamingMode(mRequest.getBody().length());

        TreeMap<String, String> headers = mRequest.getHeaders();
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            String header = entry.getKey();
            String value  = entry.getValue();
            connection.setRequestProperty(header, value);
        }

        // Send request
        if (mRequest.getBody() != null && mRequest.getBody().length() > 0) {
            OutputStream out = connection.getOutputStream();
            out.write(mRequest.getBody().bytes());
            out.flush();
            out.close();
        }

        // Read response
        mStatusCode = connection.getResponseCode();
        InputStream inputStream = (mStatusCode == 200 ? connection.getInputStream() : connection.getErrorStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder content = new StringBuilder();
        String newLine;
        do {
            newLine = reader.readLine();
            if (newLine != null) {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);

        if (content.length() > 0) {
            // strip last newline
            content.setLength(content.length() - 1);
        }

//        try {
//
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
        connection.disconnect();
        return content.toString();
//        Log.i(LoggingService.LOG_TAG, "HTTP response. body: " + responseBody);



//		/* Condition validation */
//		if (this.mRequest == null) return;
//
//		try {
//            mRequest.prepare();
//			_res = _con.execute(mRequest);
//		}
//		catch (Exception ex) {
//			mRequest.abort();
//		}
	}
}
