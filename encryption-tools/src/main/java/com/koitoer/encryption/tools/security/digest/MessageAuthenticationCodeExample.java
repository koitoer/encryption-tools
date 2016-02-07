package com.koitoer.encryption.tools.security.digest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * Created by mauricio.mena on 28/05/2015.
 */

public class MessageAuthenticationCodeExample {

	public static void main (String[] args) throws Exception {
		//
		// check args and get plaintext
		if (args.length !=1) {
			System.err.println
					("Usage: java MessageAuthenticationCodeExample text");
			System.exit(1);
		}
		byte[] plainText = args[0].getBytes("UTF8");
		//
		// get a key for the HmacMD5 algorithm
		System.out.println( "\nStart generating key" );
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
		SecretKey MD5key = keyGen.generateKey();
		System.out.println( "Finish generating key" );
		//
		// get a MAC object and update it with the plaintext
		// Message Authentication Code Object using HMAC
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(MD5key);
		mac.update(plainText);
		//
		// print out the provider used and the MAC
		System.out.println( "\n" + mac.getProvider().getInfo() );
		System.out.println( "\nMAC: " );
		System.out.println( new String( mac.doFinal(), "UTF8") );

	}
}