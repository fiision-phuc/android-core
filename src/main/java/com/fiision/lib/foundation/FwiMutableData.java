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

package com.fiision.lib.foundation;


import java.util.*;


public final class FwiMutableData extends FwiData {


	// Class's constructors
	private ArrayList<byte[]> _list = null;


	// Class's constructors
	public FwiMutableData() {
		super();
		this._list = new ArrayList<byte[]>(0);
	}
	public FwiMutableData(byte[] bytes) {
		this();
		if (bytes != null && bytes.length > 0) { this._list.add(bytes); }
	}
	public FwiMutableData(int capacity) {
		this();
		// Do nothing after this
	}
	public FwiMutableData(FwiData data) {
		this();
		if (data != null && data.length() > 0) { this._list.add(data.bytes()); }
	}


	// <editor-fold defaultstate="collapsed" desc="Class's Properties">
    @Override
	public int length() {
        int total = 0;

		if (this._list != null && this._list.size() > 0) {
			for (int i = 0; i < this._list.size(); i++) {
                total += this._list.get(i).length;
            }
		}
        return total;
	}
	@Override
	public byte[] bytes() {
		/* Condition validation */
		if (this._list == null || this._list.isEmpty()) { return null; }

		// Validate bytes and create new bytes collection
		byte[] bytes = new byte[this.length()];

		// Copy value over
		int index = 0;
		for (int i = 0; i < this._list.size(); i++) {
			byte[] b = this._list.get(i);

			System.arraycopy(b, 0, bytes, index, b.length);
			index += b.length;
		}
		return bytes;
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
		return !(this._list != other._list && (this._list == null || !this._list.equals(other._list)));
	}
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this._list != null ? this._list.hashCode() : 0);
        return hash;
    }
	@Override
	public String toString() {
		/* Condition validation */
		if (this._list == null || this._list.size() == 0) return "<>";

        int index = 0;
		StringBuilder s = new StringBuilder("<");
		for (int i = 0; i < this._list.size(); i++) {
			byte[] b = this._list.get(i);

			for (int j = 0; j < b.length; j++) {
				String text = Integer.toString((b[j] & 0xff), 16);
				s.append(String.format("%s%s", (text.length() == 1 ? "0" : ""), text));

				if (index > 0 && index < (this.length() - 1) && ((index + 1) % 4) == 0) { s.append(" "); }
				index++;
			}
		}
		s.append(">");
		return s.toString();
	}
    
    
	// Class's public methods
	public void append(byte value) {
		byte[] bytes = { value };
		this.append(bytes);
	}
 	public void append(byte... bytes) {
		/* Condition validation */
		if (bytes == null || bytes.length == 0) { return; }
		this._list.add(bytes);
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
