//  Project name: FwiCore
//  File name   : FwiFormParam.java
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

package com.fiision.lib.core.request.params;


import java.io.*;
import java.net.*;


public class FwiFormParam implements Serializable {
    
    
    // <editor-fold defaultstate="collapsed" desc="Class's static constructors">
    static public FwiFormParam param(String key, String value) {
        return new FwiFormParam(key, value);
    }
    // </editor-fold>
    
    
    // Global variables
    private String _key   = null;
    private String _value = null;
    
    
    // Class's constructors
    public FwiFormParam(String key, String value) {
        this._key   = key;
        this._value = value;
    }
    
    
    // Class's properties
    public String getKey() {
        return _key;
    }
    public String getValue() {
        return _value;
    }
    
    
    // Class's override methods
    @Override
    public boolean equals(Object o) {
        /* Condition validation */
        if (o == null || !(o instanceof FwiFormParam)) {
            return false;
        }
        else {
            return this.hashCode() == o.hashCode();
        }
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this._key != null ? this._key.hashCode() : 0);
        hash = 97 * hash + (this._value != null ? this._value.hashCode() : 0);
        
        return hash;
    }
    
    @Override
    public String toString() {
        try {
            return String.format("%s=%s", _key, URLEncoder.encode(_value, "UTF-8"));
        }
        catch(Exception ex) {
            return String.format("%s=", _key);
        }
    }
}
