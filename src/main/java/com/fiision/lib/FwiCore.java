//  Project name: FwiCore
//  File name   : FwiCore.java
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

package com.fiision.lib;


public final class FwiCore {

    /** AES Key size supported. */
    static public enum FwiAesSize {
        k128(128),
        k192(192),
        k256(256);


        static public FwiAesSize getKey(int length) {
            switch (length) {
                case 192: return k192;
                case 256: return k256;
                default : return k128;
            }
        }


        public int length;
        private FwiAesSize(int length) { this.length = length; }
    }

    /** RSA Key size supported. */
    static public enum FwiRsaSize {
        k1024(1024),
        k2048(2048),
        k4096(4096);

        static public FwiRsaSize getSize(int length) {
            switch (length) {
                case 2048: return k2048;
                case 4096: return k4096;
                default	 : return k1024;
            }
        }


        public int block;
        public int length;
        private FwiRsaSize(int length) { this.block = (length >> 3); this.length = length; }

        @Override
        public String toString() { return String.format("RSA -> Key-size: %i bits, Block-size: %i bytes, Content-size: %i", length, block, (block - 12)); }
    }
    /** Signature supported. */
    static public enum FwiSignature {
        kSignature1  ((byte)20, "1.2.840.113549.1.1.5" , "SHA1withRSA"  ),
        kSignature256((byte)32, "1.2.840.113549.1.1.11", "SHA256withRSA"),
        kSignature384((byte)48, "1.2.840.113549.1.1.12", "SHA384withRSA"),
        kSignature512((byte)64, "1.2.840.113549.1.1.13", "SHA512withRSA");

        static public FwiSignature getSignature(int length) {
            switch (length) {
                case 32: return kSignature256;
                case 48: return kSignature384;
                case 64: return kSignature512;
                default: return kSignature1;
            }
        }
        static public FwiSignature getSignature(String signatureOID) {
            /* Condition validation */
            if (signatureOID == null || signatureOID.length() == 0) return kSignature1;

            if (signatureOID.compareTo(kSignature256.oid) == 0) {
                return kSignature256;
            }
            else if (signatureOID.compareTo(kSignature384.oid) == 0) {
                return kSignature384;
            }
            else if (signatureOID.compareTo(kSignature512.oid) == 0) {
                return kSignature512;
            }
            else {
                return kSignature1;
            }
        }


        public byte length		= 0x00;
        public String oid		= null;
        public String algorithm = null;
        private FwiSignature(byte length, String oid, String algorithm) { this.length = length; this.oid = oid; this.algorithm = algorithm; }

        @Override
        public String toString() { return String.format("%s (%s)", algorithm, oid); }
    }

    /** Json supported. */
    static public enum FwiJsonValue {
        kNull	 ((byte)0x00, "Null"   ), // Primitive
        kBoolean ((byte)0x01, "Boolean"), // Primitive
        kDouble	 ((byte)0x02, "Double" ), // Primitive
        kInteger ((byte)0x03, "Integer"), // Primitive
        kString	 ((byte)0x04, "String" ), // Primitive
        kArray	 ((byte)0x05, "Array"  ), // Constructed
        kObject	 ((byte)0x06, "Object" ); // Constructed

        public byte value = 0x00;
        public String description = null;
        private FwiJsonValue(byte value, String description) { this.value = value; this.description = description; }

        @Override
        public String toString() { return description; }
    }

    /** HTTP request method supported. */
    static public enum FwiHttpMethod {
        kCopy   ("COPY"),
        kDelete ("DELETE"),     // Delete data
        kGet    ("GET" ),       // Load data
        kHead   ("HEAD"),
        kLink   ("LINK"),
        kOptions("OPTIONS"),
        kPatch  ("PATCH"),
        kPost   ("POST"),       // Create data
        kPurge  ("PURGE"),
        kPut    ("PUT"),        // Update data
        kUnlink ("UNLINK");


        public String method = null;
        private FwiHttpMethod(String method) { this.method = method; }

        @Override
        public String toString() { return String.format("HTTP %s", this.method); }
    }
}
