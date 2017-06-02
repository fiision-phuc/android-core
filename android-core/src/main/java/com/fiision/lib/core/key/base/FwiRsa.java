//  Project name: FwiCore
//  File name   : FwiRsa.java
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

import javax.crypto.*;


public abstract class FwiRsa extends FwiKey implements SecretKey, Serializable {


    // Global variables
    protected int mBlocksize = 0;


	// Class's constructors
	public FwiRsa(String keyName, int keysize) {
		super(keyName, keysize);
        this.mBlocksize = (keysize >> 3);
	}


	// <editor-fold defaultstate="collapsed" desc="Class's properties">
	public String getFormat() {
		return "RAW";
	}
    public byte[] getEncoded() {
        com.fiision.lib.core.foundation.FwiData data = this.encode();

        if (data == null) return null;
        else return data.bytes();
    }
	public String getAlgorithm() {
        return "RSA";
	}

	public int blocksize() {
		return this.mBlocksize;
	}
	public String objectIdentifier() {
		return "1.2.840.113549.1.1.1";
	}
	// </editor-fold>


	// Class's protected methods
	protected Cipher _getCipher() {
        Cipher cipher = null;
		try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		}
		catch (Exception ex) {
            cipher = null;
		}
        finally {
            return cipher;
        }
	}
}
