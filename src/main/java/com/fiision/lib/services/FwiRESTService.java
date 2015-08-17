package com.fiision.lib.services;


import com.fiision.lib.codec.*;
import com.fiision.lib.foundation.*;
import com.fiision.lib.services.request.*;
import org.apache.http.*;

import java.io.*;


public class FwiRESTService extends FwiService {


    // Class's constructors
    public FwiRESTService(FwiRequest request) {
        super(request);

        _req.addHeader("Accept", "application/json");
        _req.addHeader("Accept-Charset", "UTF-8");
    }


    // Class's public methods
    @Override
    public FwiJson getResource() {
        super.execute();

        FwiJson responseMessage = null;
        if (_res != null) {
            HttpEntity entity = _res.getEntity();

            // Download message
            try {
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                // Download response
                int capacity = (int) (entity.getContentLength() > 0 ? entity.getContentLength() : 4096);
                StringBuilder builder = new StringBuilder(capacity);

                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                // Close connection
                content.close();
                reader.close();

                // Convert to json object
                responseMessage = FwiCodec.convertDataToJson(builder.toString());
            } catch (Exception ex) {
                _req.abort();
                responseMessage = null;
            }
        }
        return responseMessage;
    }
}
