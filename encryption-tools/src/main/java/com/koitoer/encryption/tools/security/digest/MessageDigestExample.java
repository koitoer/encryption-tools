package com.koitoer.encryption.tools.security.digest;

import java.security.MessageDigest;

/**
 * Created by mauricio.mena on 28/05/2015.
 */

public class MessageDigestExample {

	public static void main (String[] args) throws Exception {
		// check args and get plaintext
		if (args.length !=1) {
			System.err.println("Usage: java MessageDigestExample text");
			System.exit(1);
		}
		byte[] plainText = args[0].getBytes("UTF8");

		// get a message digest object using the MD5 algorithm
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		// print out the provider used
		System.out.println( "\n" + messageDigest.getProvider().getInfo() );
		System.out.println( "\n" + messageDigest.getAlgorithm().toString() );

		// calculate the digest and print it out
		messageDigest.update( plainText);
		System.out.println( "\nDigest: " );
		System.out.println( new String( messageDigest.digest(), "UTF8") );
	}
}