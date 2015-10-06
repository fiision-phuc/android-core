//  Project name: FwiCore
//  File name   : FwiDataParam.java
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

package com.fiision.lib.request.params;


import com.fiision.lib.codec.*;
import com.fiision.lib.foundation.*;

import java.io.*;


public class FwiDataParam implements Serializable {

    
    // <editor-fold defaultstate="collapsed" desc="Class's static constructors">
    static public FwiDataParam paramWithString(String string) {
        /* Condition validation */
        if (string == null || string.length() == 0) {
            return null;
        }
        else {
            return FwiDataParam.paramWithData(FwiCodec.convertStringToData(string), "application/json; charset=UTF-8");
        }
    }
    static public FwiDataParam paramWithData(FwiData data, String contentType) {
        /* Condition validation */
        if (data == null || data.length() == 0 || contentType == null || contentType.length() == 0) {
            return null;
        }
        return new FwiDataParam(data, contentType);
    }
    // </editor-fold>
    
    
    // Global variables
    private String _contentType = null;
    private FwiData _data = null;
    
    
    // Class's constructors
    public FwiDataParam(FwiData data, String contentType) {
        this._contentType = contentType;
        this._data = data;
    }
    
    
    // Class's properties
    public FwiData getData() {
        return _data;
    }
    public String getContentType() {
        return _contentType;
    }
}
