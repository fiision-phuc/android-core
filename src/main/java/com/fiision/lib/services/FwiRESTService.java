//  Project name: FwiCore
//  File name   : FwiRESTService.java
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

package com.fiision.lib.services;


import com.fiision.lib.codec.*;
import com.fiision.lib.foundation.*;

import org.apache.http.*;

import java.io.*;


public class FwiRESTService extends FwiService {


    // Class's constructors
    public FwiRESTService(com.fiision.lib.request.FwiRequest request) {
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
