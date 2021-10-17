package com.mechashadow.javajsrsa;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

public class AppRSA {
    private static final String ALGORITHM = "RSA";
    private static final String ALGORITHM_OPTIONS = "RSA/ECB/PKCS1Padding";

    public static final String TESTING_MESSAGE = "My name is Mustafa Remember It :)";

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    private static BigInteger publicKeyModulus;
    private static BigInteger publicKeyExponent;

    static {
        try {
            generateKeys();
            setModulusAndExponents();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    private static void setModulusAndExponents() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

        publicKeyModulus = rsaPublicKeySpec.getModulus();
        publicKeyExponent = rsaPublicKeySpec.getPublicExponent();
    }

    public static byte[] encrypt(byte[] text) throws Exception {
        Cipher encryptCipher = Cipher.getInstance(ALGORITHM_OPTIONS);
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return encryptCipher.doFinal(text);
    }

    public static byte[] decrypt(byte[] cipher) throws Exception {
        Cipher decryptCipher = Cipher.getInstance(ALGORITHM_OPTIONS);
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return decryptCipher.doFinal(cipher);
    }

    public static void test() {
        try {
            byte[] encrypted = encrypt(TESTING_MESSAGE.getBytes(StandardCharsets.UTF_8));
            byte[] decrypted = decrypt(encrypted);
            if ((new String(decrypted)).equals(TESTING_MESSAGE)) {
                System.out.println("AppKey is WORKING!!!");
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("AppKey is NOT WORKING???");
    }

    public static Map<String, String> getPublicKeyInfo() {
        Map<String, String> publicKeyInfo = new HashMap<>();

        publicKeyInfo.put("Mod", publicKeyModulus.toString());
        publicKeyInfo.put("Exp", publicKeyExponent.toString());

        return publicKeyInfo;
    }
}
