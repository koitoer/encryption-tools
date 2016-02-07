package com.koitoer.encryption.tools.security.digital;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by mauricio.mena on 28/05/2015.
 */
public class DigitalSignature2Example {
	public static void main (String[] args) throws Exception {
		//
		// check args and get plaintext
		if (args.length !=1) {
			System.err.println("Usage: java DigitalSignature1Example text");
			System.exit(1);
		}
		byte[] plainText = args[0].getBytes("UTF8");
		//
		// generate an RSA keypair
		System.out.println( "\nStart generating RSA key" );
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);

		KeyPair key = keyGen.generateKeyPair();
		System.out.println( "Finish generating RSA key" );
		//
		// get a signature object using the MD5 and RSA combo
		// and sign the plaintext with the private key,
		// listing the provider along the way

		Signature sig = Signature.getInstance("MD5WithRSA");
		sig.initSign(key.getPrivate());
		sig.update(plainText);
		byte[] signature = sig.sign();
		System.out.println( sig.getProvider().getInfo() );
		System.out.println( "\nSignature:" );
		System.out.println( new String(signature, "UTF8") );
		//
		// verify the signature with the public key
		System.out.println( "\nStart signature verification" );
		sig.initVerify(key.getPublic());
		sig.update(plainText);
		try {
			if (sig.verify(signature)) {
				System.out.println( "Signature verified" );
			} else System.out.println( "Signature failed" );
		} catch (SignatureException se) {
			System.out.println( "Signature failed" );
		}
	}

}
