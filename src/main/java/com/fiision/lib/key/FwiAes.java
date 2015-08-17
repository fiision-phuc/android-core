//  Project name: FwiCore
//  File name   : FwiAes.java
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

package com.fiision.lib.key;


import com.fiision.lib.foundation.*;
import com.fiision.lib.key.base.*;

import java.io.*;

import javax.crypto.*;
import javax.crypto.spec.*;


public final class FwiAes extends FwiKey implements Serializable {
	static private final long serialVersionUID = 1L;


	// Global variables
    private int mKeySize = 0;
    private String mKeyTag = null;
	private SecretKeySpec mAesKey = null;


    // Class's constructors
    public FwiAes(String keyName, byte[] keyData) {
        super(keyName, keyData.length << 3);
        this.mAesKey = new SecretKeySpec(keyData, "AES");
    }


    // <editor-fold defaultstate="collapsed" desc="Class's properties">
	/** Return raw key. */
    public SecretKey secretkey() {
        return mAesKey;
    }
    // </editor-fold>


	// <editor-fold defaultstate="collapsed" desc="Class's public methods">
	/** Convert this key to data. */
	@Override
    public FwiData encode() {
        return (this.mAesKey != null ? new FwiData(this.mAesKey.getEncoded()) : null);
	}

	/** Decrypt data. */
	public FwiData decryptData(byte[] data) {
		/* Condition validation */
		if (this.mAesKey == null) return null;

		// Decrypt data
		FwiData decrypted = null;
		try {
			Cipher cipher = this._getCipher();

			cipher.init(Cipher.DECRYPT_MODE, this.mAesKey);
			byte[] decryptData = cipher.doFinal(data);
			decrypted = new FwiData(decryptData);
		}
		catch (Exception ex) {
			decrypted = null;
		}
		finally {
			return decrypted;
		}
	}
	public FwiData decryptData(FwiData data) {
		/* Condition validation */
		if (this.mAesKey == null || data == null || data.length() == 0) return null;
		return this.decryptData(data.bytes());
	}

	/** Encrypt data. */
	public FwiData encryptData(byte[] data) {
		/* Condition validation */
		if (this.mAesKey == null) return null;

		// Encrypt data
		FwiData encrypted = null;
		try {
			Cipher cipher = this._getCipher();

			cipher.init(Cipher.ENCRYPT_MODE, this.mAesKey);
			byte[] encryptData = cipher.doFinal(data);
			encrypted = new FwiData(encryptData);
		}
		catch (Exception ex) {
			encrypted = null;
		}
		finally {
			return encrypted;
		}
	}
	public FwiData encryptData(FwiData data) {
		/* Condition validation */
        if (this.mAesKey == null || data == null || data.length() == 0) return null;
        return this.encryptData(data.bytes());
	}
	// </editor-fold>


	// Class's private methods
	private Cipher _getCipher() {
        Cipher cipher = null;
		try {
            cipher = Cipher.getInstance("AES");
		}
		catch (Exception ex) {
            cipher = null;
		}
        finally {
            return cipher;
        }
	}
}
