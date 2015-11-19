package com.fiision.lib.core.services;


import java.io.*;
import java.net.*;
import java.util.*;


public class FwiService {


	// Global variables
	protected com.fiision.lib.core.request.FwiRequest mRequest = null;
    protected int mStatusCode = -1;


	// Class's constructors
	public FwiService(com.fiision.lib.core.request.FwiRequest request) {
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
//        connection.setUseCaches(false);
//        connection.setDoOutput(true);

        if (mRequest.getMethod() != com.fiision.lib.core.FwiCore.FwiHttpMethod.kGet) connection.setRequestMethod(mRequest.getMethod().method);
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
                content.append(newLine);
            }
        } while (newLine != null);

        reader.close();
        connection.disconnect();
        return content.toString();
	}
}