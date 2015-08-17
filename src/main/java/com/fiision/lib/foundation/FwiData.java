//  Project name: FwiCore
//  File name   : FwiData.java
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

package com.fiision.lib.foundation;


import java.io.*;
import java.util.*;


public class FwiData implements Serializable {


	// Global variables
	private byte[] _bytes = null;


	// Class's constructors
	public FwiData() {
		this._bytes = null;
	}
	public FwiData(FwiData data) {
		if (data != null && data.length() > 0) {
			this._bytes = new byte[data.bytes().length];
			System.arraycopy(data.bytes(), 0, this._bytes, 0, data._bytes.length);
		}
	}
    public FwiData(byte[]  data) {
		if (data != null && data.length > 0) {
            this._bytes = data;
        }
	}


	// <editor-fold defaultstate="collapsed" desc="Class's Properties">
    public int length() {
		if (this._bytes == null || this._bytes.length == 0) {
            return 0;
        }
		else {
            return this._bytes.length;
        }
	}
	public byte[] bytes() {
		if (this._bytes == null || this._bytes.length == 0) {
            return null;
        }
		else {
            return this._bytes;
        }
	}
    // </editor-fold>


    // Class's override methods
	@Override
	public boolean equals(Object o) {
		/* Condition validation */
		if (o == null) return false;

		/* Condition validation: Must be instance of FwiData or FwiMutableData */
		if (!(o instanceof FwiData || o instanceof FwiMutableData)) { return false; }

		return Arrays.equals(this._bytes, ((FwiData) o)._bytes);
	}
    
    @Override
    public int hashCode() {
        int hash = 3;

        hash = 67 * hash + Arrays.hashCode(this._bytes);
        return hash;
    }
	@Override
	public String toString() {
		/* Condition validation */
		if (this._bytes == null || this._bytes.length == 0) return "<>";
        
        char[] _hexTable = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        // Calculate length
        int c = this._bytes.length * 2;
        int r = this._bytes.length % 4;
        int a = (this._bytes.length - r) >> 2;
		StringBuilder builder = new StringBuilder(c + a + 2);

        builder.append('<');
		for (int i = 0; i < this._bytes.length; i++) {
            int b = this._bytes[i] & 0xff;
            builder.append(_hexTable[(b >> 4)]);
            builder.append(_hexTable[(b & 0x0f)]);

			if (i > 0 && i < (this._bytes.length - 1) && ((i + 1) % 4) == 0) {
                builder.append(' ');
            }
		}
		builder.append('>');
		return builder.toString();
	}
    
    
	// Class's public methods
	public void clean() {
		/* Condition validation */
		if (_bytes == null || _bytes.length == 0) return;
		for (int i = 0; i < _bytes.length; i++) _bytes[i] = (byte)0x00;
	}
}
