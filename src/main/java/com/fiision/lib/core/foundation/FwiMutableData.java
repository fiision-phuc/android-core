//  Project name: FwiCore
//  File name   : FwiMutableData.java
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

package com.fiision.lib.core.foundation;


import java.io.*;


public final class FwiMutableData extends FwiData {


	// Class's constructors
	private ByteArrayOutputStream mBuffer;


	// Class's constructors
	public FwiMutableData() {
		super();
		mBuffer = new ByteArrayOutputStream();
	}
	public FwiMutableData(byte[] bytes) {
		this();

		if (bytes != null && bytes.length > 0) {
			try {
				mBuffer.write(bytes);
			}
			catch (IOException ex) {
				// Ignore the error
			}
		}
	}
	public FwiMutableData(FwiData data) {
		this();

		if (data != null && data.length() > 0) {
			try {
				mBuffer.write(data.bytes());
			}
			catch (IOException ex) {
				// Ignore the error
			}
		}
	}


	// <editor-fold defaultstate="collapsed" desc="Class's Properties">
    @Override
	public int length() {
        return mBuffer.size();
	}
	@Override
	public byte[] bytes() {
		return mBuffer.toByteArray();
	}
    // </editor-fold>
    
    
    // Class's override methods
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FwiMutableData other = (FwiMutableData) obj;
		return !(this.mBuffer != other.mBuffer && (this.mBuffer == null || !this.mBuffer.equals(other.mBuffer)));
	}
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.mBuffer != null ? this.mBuffer.hashCode() : 0);
        return hash;
    }
	@Override
	public String toString() {
		/* Condition validation */
		if (this.mBuffer == null || this.mBuffer.size() == 0) return "<>";

		StringBuilder s = new StringBuilder("<");
		byte[] b = this.mBuffer.toByteArray();
		int index = 0;

		for (int i = 0; i < b.length; i++) {
			String text = Integer.toString((b[i] & 0xff), 16);
			s.append(String.format("%s%s", (text.length() == 1 ? "0" : ""), text));

			if (index > 0 && index < (this.length() - 1) && ((index + 1) % 4) == 0) { s.append(" "); }
			index++;
		}
		s.append(">");
		return s.toString();
	}
    
    
	// Class's public methods
	public void append(byte value) {
		byte[] bytes = { value };

		try {
			mBuffer.write(bytes);
		}
		catch (IOException ex) {
			// Ignore the error
		}
	}
 	public void append(byte... bytes) {
		/* Condition validation */
		if (bytes == null || bytes.length == 0) { return; }

		try {
			mBuffer.write(bytes);
		}
		catch (IOException ex) {
			// Ignore the error
		}
	}

 	public void append(FwiData data) {
		/* Condition validation */
		if (data == null) { return; }
		this.append(data.bytes());
	}
	public void append(FwiMutableData data) {
		/* Condition validation */
		if (data == null) { return; }
		this.append(data.bytes());
	}
}
