package com.koitoer.encryption.tools.security.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;

import java.nio.ByteBuffer;

/**
 * Created by koitoer on 3/19/16.
 */
public class KMSEncryption {

    String accessKey = "";
    String secretKey = "";

    public KMSEncryption(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public static void main(String[] args) {

        String password = "koitoer";
        KMSEncryption kmsEncryption =  new KMSEncryption("", "");
        System.out.println("Encrypted password ");
        System.out.println(kmsEncryption.encrypt(password));
        System.out.println("Decrypted password ");
        System.out.println(kmsEncryption.decrypt(kmsEncryption.encrypt(password)));
    }

    public String encrypt(String password) {
        final AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);

        AWSKMSClient kms = new AWSKMSClient(creds);
        kms.setEndpoint("https://kms.us-east-1.amazonaws.com");

        ByteBuffer plaintext = ByteBuffer.wrap(password.getBytes());

        EncryptRequest req = new EncryptRequest().withPlaintext(plaintext);
        req.setKeyId("arn:aws:kms:us-east-1:757639574144:key/f6dfd466-fc3c-4d08-83f7-4af2faab2232");
        ByteBuffer encryptedKey = kms.encrypt(req).getCiphertextBlob();

        byte[] key = new byte[encryptedKey.remaining()];
        encryptedKey.get(key);

        return Base64.encode(key);
    }

    public String decrypt(String encryptedPassword) {

        final AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);

        AWSKMSClient kms = new AWSKMSClient(creds);
        kms.setEndpoint("https://kms.us-east-1.amazonaws.com");

        try {
            byte[] encryptedBytes = Base64.decode(encryptedPassword);
            ByteBuffer encryptedKey = ByteBuffer.wrap(encryptedBytes);

            DecryptRequest req = new DecryptRequest().withCiphertextBlob(encryptedKey);
            ByteBuffer plaintextKey = kms.decrypt(req).getPlaintext();

            byte[] key = new byte[plaintextKey.remaining()];
            plaintextKey.get(key);

            return new String(key);
        } catch (Base64DecodingException ex) {
            return null;
        }
    }

}
