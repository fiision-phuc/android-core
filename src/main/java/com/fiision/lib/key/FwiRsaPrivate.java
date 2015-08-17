//  Project name: FwiCore
//  File name   : FwiRsaPrivate.java
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


public final class FwiRsaPrivate extends FwiRsa implements RSAPrivateKey, Serializable {


	// Global variables
	private RSAPrivateKey mPrivateKey = null;


	// Class's constructors
	public FwiRsaPrivate(String keyName, RSAPrivateKey privateKey) {
        super(keyName, privateKey.getModulus().bitLength());
		this.mPrivateKey = privateKey;
	}


    // <editor-fold defaultstate="collapsed" desc="RSAPrivateKey's members">
    @Override
    public BigInteger getModulus() {
        return mPrivateKey.getModulus();
    }
    @Override
    public BigInteger getPrivateExponent() {
        return mPrivateKey.getPrivateExponent();
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="FwiKey's members">
    @Override
    public FwiData encode() {
        return new FwiData(this.mPrivateKey.getEncoded());
    }
    // </editor-fold>


    // Class's public methods
    @Override
    public boolean equals(Object other) {
        /* Condition validation */
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof FwiRsaPrivate)) return false;
        return this.mPrivateKey.equals(((FwiRsaPrivate) other).mPrivateKey);
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.mPrivateKey != null ? this.mPrivateKey.hashCode() : 0);
        return hash;
    }

	public FwiData decryptData(FwiData data) {
        /* Condition validation: data's block size */
		if (data == null || data.length() != mBlocksize) return null;

		FwiData decrypted = null;
		try {
			Cipher cipher = super._getCipher();
            
            if (cipher != null) {
                cipher.init(Cipher.DECRYPT_MODE, this.mPrivateKey, super._getSecureRandom());
                byte[] decryptedData = cipher.doFinal(data.bytes());
                decrypted = new FwiData(decryptedData);
            }
		}
		catch (Exception ex) {
			decrypted = null;
		}
		finally {
            return decrypted;
		}
	}
	public FwiData signData(FwiCore.FwiSignature digest, FwiData data) {
        /* Condition validation */
		if (data == null || data.length() == 0) return null;

        /* Condition validation: apply default signature algorithm if neccessary. */
        if (digest == null) digest = FwiCore.FwiSignature.kSignature1;

		FwiData signature = null;
		try {
			Signature signer = Signature.getInstance(digest.algorithm);
            
            if (signer != null) {
                signer.initSign(this.mPrivateKey, super._getSecureRandom());
                signer.update(data.bytes());

                byte[] signatureData = signer.sign();
                signature = new FwiData(signatureData);
            }
		}
		catch (Exception ex) {
			signature = null;
		}
		finally {
            return signature;
		}
	}
}
