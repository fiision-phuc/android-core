//  Project name: FwiCore
//  File name   : FwiRsaPublic.java
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


import com.fiision.lib.*;
import com.fiision.lib.foundation.*;
import com.fiision.lib.key.base.*;

import java.io.*;
import java.math.*;
import java.security.*;
import java.security.interfaces.*;

import javax.crypto.*;


public final class FwiRsaPublic extends FwiRsa implements RSAPublicKey, Serializable {


	// Global variables
	private RSAPublicKey mPublicKey = null;


	// Class's constructors
	public FwiRsaPublic(String keyTag, RSAPublicKey publicKey) {
		super(keyTag, publicKey.getModulus().bitLength());
		this.mPublicKey = publicKey;
	}


    // <editor-fold defaultstate="collapsed" desc="RSAPublicKey's members">
    @Override
    public BigInteger getModulus() {
        return mPublicKey.getModulus();
    }
    @Override
    public BigInteger getPublicExponent() {
        return mPublicKey.getPublicExponent();
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="FwiKey's members">
    @Override
    public FwiData encode() {
        return new FwiData(this.mPublicKey.getEncoded());
    }
    // </editor-fold>


    // Class's public methods
    @Override
    public boolean equals(Object other) {
        /* Condition validation */
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof FwiRsaPublic)) return false;
        return this.mPublicKey.equals(((FwiRsaPublic) other).mPublicKey);
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.mPublicKey != null ? this.mPublicKey.hashCode() : 0);
        return hash;
    }

	public FwiData encryptData(FwiData data) {
		/* Condition validation: data's block size */
        if (data == null || data.length() > (mBlocksize - 12)) return null;

		FwiData encrypted = null;
		try {
			Cipher cipher = super._getCipher();
            
            if (cipher != null) {
                cipher.init(Cipher.ENCRYPT_MODE, this.mPublicKey, super._getSecureRandom());
                byte[] encryptedData = cipher.doFinal(data.bytes());
                encrypted = new FwiData(encryptedData);
            }
		}
		catch (Exception ex) {
			encrypted = null;
		}
		finally {
            return encrypted;
		}
	}
	public boolean verifyData(FwiCore.FwiSignature digest, FwiData signature, FwiData data) {
		/* Condition validation: Validate signature size */
		if (signature == null || signature.length() != mBlocksize || data == null || data.length() == 0) return false;
		if (digest == null) digest = FwiCore.FwiSignature.kSignature1;

		boolean isVerified = false;
		try {
			Signature signer = Signature.getInstance(digest.algorithm);
            
            if (signer != null) {
                signer.initVerify(this.mPublicKey);
                signer.update(data.bytes());
                isVerified = signer.verify(signature.bytes());
            }
		}
		catch (Exception ex) {
			isVerified = false;
		}
		finally {
            return isVerified;
		}
	}
}
