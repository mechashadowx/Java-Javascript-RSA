package com.mechashadow.javajsrsa.controllers;

import com.mechashadow.javajsrsa.AppRSA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

@RestController
public class AppRestController {
    @GetMapping("/getPublicKeyInfo")
    public Map<String, String> getPublicKeyInfo() {
        return AppRSA.getPublicKeyInfo();
    }

    @GetMapping("/getMessage")
    public String getMessage() {
        return AppRSA.TESTING_MESSAGE;
    }

    @GetMapping("/checkMessage")
    public String checkMessage(@RequestParam(name = "encryptedMessage") String encryptedMessage) {
        try {
            byte[] decrypted = AppRSA.decrypt(Base64.getDecoder().decode(encryptedMessage.getBytes()));
            if ((new String(decrypted)).equals(AppRSA.TESTING_MESSAGE)) {
                return "Correct Message!!!";
            }
        } catch (Exception e) {
            return "Decryption Field :(";
        }
        return "Wrong Encryption!!!";
    }
}
