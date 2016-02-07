package com.koitoer.encryption.tools.security.cert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by mauricio.mena on 07/01/2016.
 */
public class CertificateEncryption {

	public static void main(String[] args)
			throws IOException, CertificateException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException,
			NoSuchProviderException {
		FileInputStream fis = new FileInputStream("server.crt");
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		X509Certificate crt = (X509Certificate) cf.generateCertificate(fis);
		PublicKey publicKey = crt.getPublicKey();
		System.out.println(publicKey.getAlgorithm());

		Cipher cipher = Cipher.getInstance("RSA");
		System.out.println("\n" + cipher.getProvider().getInfo());

		final byte[] plainText = "koitoer".getBytes();

		// encrypt using the key and the plaintext
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));

		// Loading private key file
		String keyFile = "server.k9key";
		FileInputStream inStream = new FileInputStream(keyFile);
		byte[] encKey = new byte[inStream.available()];
		inStream.read(encKey);
		inStream.close();

		// Read the private key from file
		System.out.println("RSA PrivateKeyInfo: " + encKey.length + " bytes\n");
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		System.out.println("KeyFactory Object Info:");
		System.out.println("Algorithm = " + keyFactory.getAlgorithm());
		System.out.println("Provider = " + keyFactory.getProvider());
		PrivateKey priv = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
		System.out.println("Loaded " + priv.getAlgorithm() + " " + priv.getFormat() + " private key.");


		// encrypt using the key and the plaintext
		System.out.println("\nStart decryption");
		cipher.init(Cipher.DECRYPT_MODE, priv);
		byte[] newPlainText = cipher.doFinal(cipherText);
		System.out.println("Finish decryption: ");
		System.out.println(new String(newPlainText, "UTF8"));


		// Initialize the cipher for encryption
		//cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom);

		// Encrypt the message
		//ciphertextBytes = cipher.doFinal(messageBytes);
		//System.out.println("Message encrypted with certificate file public key:\n" + new String(ciphertextBytes) + "\n");




	}
}
