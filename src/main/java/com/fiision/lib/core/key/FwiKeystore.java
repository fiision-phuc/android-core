//  Project name: FwiCore
//  File name   : FwiKeystore.java
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

package com.fiision.lib.core.key;


import android.content.*;

import java.io.*;
import java.security.*;


public final class FwiKeystore {
	static private File mKeystoreDir = null;
    static private File mKeystoreFile = null;
    static private KeyStore mKeystore = null;
    static private char[] mKeystoreAuthentication = null;
	
	
	/** Initialize keystore. */
	static public synchronized void initialize(Context context, String authentication) {
        mKeystoreAuthentication = authentication.toCharArray();
		mKeystoreDir = context.getDir("Keystore", Context.MODE_PRIVATE);

		// Load keystore
		mKeystoreFile = new File(String.format("%s%sKeystore.jks", mKeystoreDir.getAbsolutePath(), File.separator));
		try {
			mKeystore = KeyStore.getInstance("UBER");
			
			if (mKeystoreFile.exists()) {
				InputStream input = new FileInputStream(mKeystoreFile);
				mKeystore.load(input, mKeystoreAuthentication);
				input.close();
			}
			else {
				mKeystore.load(null);

				FileOutputStream output = new FileOutputStream(mKeystoreFile);
				mKeystore.store(output, mKeystoreAuthentication);
				output.close();
			}
		}
		catch (Exception ex)  {
            mKeystoreAuthentication = null;
			mKeystoreFile = null;
			mKeystoreDir = null;
            mKeystore = null;
		}
	}
	
	/** Load/Save/Delete AES key. */
    static public FwiAes retrieveAesKey(String keyName) {
        FwiAes aesKey = null;

        // Retrieve key data
        try {
            Key entry = mKeystore.getKey(keyName, mKeystoreAuthentication);
            if (entry != null) {
                aesKey = new FwiAes(keyName, entry.getEncoded());
            }
        }
        catch (Exception ex) {
            aesKey = null;
        }
        finally {
            return aesKey;
        }
    }
    static public synchronized boolean deleteAesKey(FwiAes aesKey) {
		boolean isSuccess = true;

		// Delete key data
		try {
			mKeystore.deleteEntry(aesKey.keyName());

			// Update keystore
			FileOutputStream output = new FileOutputStream(mKeystoreFile);
			mKeystore.store(output, mKeystoreAuthentication);
			output.close();
		}
		catch (Exception ex) {
			isSuccess = false;
		}
        finally {
            return isSuccess;
        }
	}
	static public synchronized boolean insertAesKey(FwiAes aesKey) {
		boolean isSuccess = true;

		// Insert key data
		try {
			KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(aesKey.secretkey());
			mKeystore.setEntry(aesKey.keyName(), entry, new KeyStore.PasswordProtection(mKeystoreAuthentication));

			// Update keystore
			FileOutputStream output = new FileOutputStream(mKeystoreFile);
			mKeystore.store(output, mKeystoreAuthentication);
			output.close();
		}
		catch (Exception ex) {
			isSuccess = false;
		}
        finally {
            return isSuccess;
        }
	}
}
