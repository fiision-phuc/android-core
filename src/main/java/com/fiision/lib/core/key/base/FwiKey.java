//  Project name: FwiCore
//  File name   : FwiKey.java
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

package com.fiision.lib.core.key.base;


import java.io.*;
import java.security.*;


public abstract class FwiKey implements Serializable {
	static private final long serialVersionUID = 1L;


	// Global variables
	protected int mKeySize = 0;
	protected String mKeyName = null;


	// Class's constructors
	public FwiKey(String keyName) {
		this.mKeySize = 0;
		this.mKeyName = keyName;
	}
	public FwiKey(String keyName, int keySize) {
		this.mKeyName = keyName;
		this.mKeySize = keySize;
	}


	// <editor-fold defaultstate="collapsed" desc="Class's properties">
	/** Key's size. */
	public int keysize() {
		return this.mKeySize;
	}
	/** Name of the key, will be used when stored inside keychain. */
	public String keyName() {
		return this.mKeyName;
	}
    // </editor-fold>


	// Class's public abstract methods
	public abstract com.fiision.lib.core.foundation.FwiData encode();


	// Class's public methods
	public com.fiision.lib.core.foundation.FwiData encodeBase64Data() {
		return com.fiision.lib.core.codec.FwiBase64.encodeBase64Data(this.encode());
	}
	public String  encodeBase64String() {
		return com.fiision.lib.core.codec.FwiCodec.convertDataToString(this.encodeBase64Data());
	}


	// Class's protected methods
	protected void _keysize(int keysize) {
		/* Condition validation */
		if (keysize < 0) return;
		this.mKeySize = keysize;
	}
    protected SecureRandom _getSecureRandom() {
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (Exception ex) {
            secureRandom = null;
        }
        finally {
            return secureRandom;
        }
    }
}
