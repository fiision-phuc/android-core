////  Project name: FwiCore
////  File name   : FwiDownloadService.java
////
////  Author      : Phuc, Tran Huu
////  Created date: 8/17/15
////  Version     : 1.00
////  --------------------------------------------------------------
////  Copyright (C) 2012, 2015 Fiision Studio.
////  All Rights Reserved.
////  --------------------------------------------------------------
////
////  Permission is hereby granted, free of charge, to any person obtaining  a  copy
////  of this software and associated documentation files (the "Software"), to  deal
////  in the Software without restriction, including without limitation  the  rights
////  to use, copy, modify, merge,  publish,  distribute,  sublicense,  and/or  sell
////  copies of the Software,  and  to  permit  persons  to  whom  the  Software  is
////  furnished to do so, subject to the following conditions:
////
////  The above copyright notice and this permission notice shall be included in all
////  copies or substantial portions of the Software.
////
////  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF  ANY  KIND,  EXPRESS  OR
////  IMPLIED, INCLUDING BUT NOT  LIMITED  TO  THE  WARRANTIES  OF  MERCHANTABILITY,
////  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO  EVENT  SHALL  THE
////  AUTHORS OR COPYRIGHT HOLDERS  BE  LIABLE  FOR  ANY  CLAIM,  DAMAGES  OR  OTHER
////  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING  FROM,
////  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN  THE
////  SOFTWARE.
////
////
////  Disclaimer
////  __________
////  Although reasonable care has been taken to  ensure  the  correctness  of  this
////  software, this software should never be used in any application without proper
////  testing. Fiision Studio disclaim  all  liability  and  responsibility  to  any
////  person or entity with respect to any loss or damage caused, or alleged  to  be
////  caused, directly or indirectly, by the use of this software.
//
//package com.fiision.lib.services;
//
//
//import org.apache.http.*;
//
//import java.io.*;
//
//
//public class FwiDownloadService extends FwiService {
//
//
//    private String _path;
//
//
//    // Class's constructors
//    public FwiDownloadService(FwiRequest request, String path) {
//        super(request);
//        this._path = path;
//    }
//
//
//    // Class's public methods
//    @Override
//    public String getResource() {
//        super.execute();
//
//        if (_res != null) {
//            HttpEntity entity   = _res.getEntity();
//            InputStream  input  = null;
//            OutputStream output = null;
//            String filename     = null;
//
//            // Download data
//            try {
//                byte[] bytes = new byte[512];
//                input  = entity.getContent();
//                output = new FileOutputStream(_path);
//
//                // Download Data
//                int length = 0;
//                while ((length = input.read(bytes)) > 0) {
//                    output.write(bytes, 0, length);
//                }
//
//                // Close connection
//                input.close();
//                output.close();
//
//                filename = mRequest.getURI().toString();
//            }
//            catch (Exception ex) {
//                mRequest.abort();
//
//                // Close input
//                if (input != null) {
//                    input.close();
//                }
//
//                // Close output
//                if (output != null) {
//                    output.close();
//                }
//
//                // Reset cacheFile
//                filename = null;
//            }
//            finally {
//                return filename;
//            }
//        }
//        else {
//            return mRequest.getURI().toString();
//        }
//    }
//}
