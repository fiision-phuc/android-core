//  Project name: FwiCore
//  File name   : FwiBase64.java
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

package com.fiision.lib.codec;


import com.fiision.lib.foundation.*;

import org.apache.commons.codec.binary.*;


public final class FwiBase64 {
    
    
    /** Validate base64. */
    static public boolean isBase64(FwiData base64Data) {
        return !(base64Data == null || base64Data.length() == 0) && FwiBase64.isBase64(base64Data.bytes());
    }
	static public boolean isBase64(String  base64Data) {
        return !(base64Data == null || base64Data.length() == 0) && FwiBase64.isBase64(FwiCodec.convertStringToData(base64Data));
	}
    static public boolean isBase64(byte[]  base64Data) {
        return !(base64Data == null || base64Data.length == 0 || (base64Data.length % 4) != 0) && Base64.isArrayByteBase64(base64Data);
    }
    
    /** Decode base64. */
    static public FwiData decodeBase64Data(FwiData base64Data) {
        /* Condition validation */
		if (base64Data == null || base64Data.length() == 0) return null;
        return FwiBase64.decodeBase64Data(base64Data.bytes());
    }
    static public FwiData decodeBase64Data(String  base64Data) {
		/* Condition validation */
		if (base64Data == null || base64Data.length() == 0) return null;
        return FwiBase64.decodeBase64Data(FwiCodec.convertStringToData(base64Data));
	}
    static public FwiData decodeBase64Data(byte[]  base64Data) {
        /* Condition validation */
        if (!FwiBase64.isBase64(base64Data)) return null;
        return new FwiData(Base64.decodeBase64(base64Data));
    }
    static public String decodeBase64String(FwiData base64Data) {
        return FwiCodec.convertDataToString(FwiBase64.decodeBase64Data(base64Data));
    }
    static public String decodeBase64String(String  base64Data) {
		return FwiCodec.convertDataToString(FwiBase64.decodeBase64Data(base64Data));
	}
    static public String decodeBase64String(byte[]  base64Data) {
        return FwiCodec.convertDataToString(FwiBase64.decodeBase64Data(base64Data));
    }
    
    /** Encode base64. */
    static public FwiData encodeBase64Data(FwiData data) {
        /* Condition validation */
        if (data == null || data.length() == 0) return null;
        return FwiBase64.encodeBase64Data(data.bytes());
    }
	static public FwiData encodeBase64Data(String  data) {
        /* Condition validation */
        if (data == null || data.length() == 0) return null;
        return FwiBase64.encodeBase64Data(FwiCodec.convertStringToData(data));
    }
    static public FwiData encodeBase64Data(byte[]  data) {
        /* Condition validation */
        if (data == null || data.length == 0) return null;
        return new FwiData(Base64.encodeBase64(data));
    }

    static public String encodeBase64String(FwiData data) {
        return FwiBase64.encodeBase64String(data.bytes());
    }
    static public String encodeBase64String(String  data) {
        return FwiBase64.encodeBase64String(FwiCodec.convertStringToData(data));
    }
    static public String encodeBase64String(byte[]  data) {
        return FwiCodec.convertDataToString(FwiBase64.encodeBase64Data(data));
    }
}
