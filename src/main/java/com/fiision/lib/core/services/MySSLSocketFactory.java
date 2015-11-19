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
//import org.apache.http.conn.ssl.SSLSocketFactory;
//
//import java.io.*;
//import java.net.*;
//import java.security.*;
//import java.security.cert.*;
//
//import javax.net.ssl.*;
//
//
//public class MySSLSocketFactory extends SSLSocketFactory {
//    SSLContext sslContext = SSLContext.getInstance("TLS");
//
//    public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//        super(truststore);
//
//        TrustManager tm = new X509TrustManager() {
//            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            }
//
//            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//            }
//
//            public X509Certificate[] getAcceptedIssuers() {
//                return null;
//            }
//        };
//
//        sslContext.init(null, new TrustManager[] { tm }, null);
//    }
//
//    @Override
//    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
//        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
//    }
//
//    @Override
//    public Socket createSocket() throws IOException {
//        return sslContext.getSocketFactory().createSocket();
//    }
//}
