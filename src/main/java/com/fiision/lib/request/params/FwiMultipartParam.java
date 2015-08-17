//  Project name: FwiCore
//  File name   : FwiMultipartParam.java
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


import com.fiision.lib.foundation.*;

import java.io.*;


public class FwiMultipartParam implements Serializable {
    
    
    // <editor-fold defaultstate="collapsed" desc="Class's static constructors">
    static public FwiMultipartParam param(String name, String filename, FwiData data, String contentType) {
        return new FwiMultipartParam(name, filename, data, contentType);
    }
    // </editor-fold>
    
    
    // Global variables
    private String  _name        = null;
    private String  _filename    = null;
    private FwiData _data        = null;
    private String  _contentType = null;
    
    
    // Class's constructors
    public FwiMultipartParam(String name, String filename, FwiData data, String contentType) {
        this._name        = name;
        this._filename    = filename;
        this._data        = data;
        this._contentType = contentType;
    }
    
    
    // Class's properties
    public String getName() {
        return _name;
    }
    public String getFilename() {
        return _filename;
    }
    public FwiData getData() {
        return _data;
    }
    public String getContentType() {
        return _contentType;
    }
    
    
    // Class's override methods
    @Override
    public boolean equals(Object o) {
        /* Condition validation */
        if (o == null || !(o instanceof FwiMultipartParam)) {
            return false;
        }
        else {
            return this.hashCode() == o.hashCode();
        }
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this._name != null ? this._name.hashCode() : 0);
        hash = 47 * hash + (this._filename != null ? this._filename.hashCode() : 0);
        hash = 47 * hash + (this._data != null ? this._data.hashCode() : 0);
        hash = 47 * hash + (this._contentType != null ? this._contentType.hashCode() : 0);
        return hash;
    }
}
