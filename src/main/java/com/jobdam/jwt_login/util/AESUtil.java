package com.jobdam.jwt_login.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@Slf4j
public class AESUtil {
    @Value("${security.aes.secret-key}")
    private String aesSecretKey;

    private SecretKeySpec secretKeySpec;
    @PostConstruct
    public void init() {
        byte[] keyBytes = aesSecretKey.getBytes(StandardCharsets.UTF_8);
        secretKeySpec = new SecretKeySpec(keyBytes, "AES");
    }

    //IV 생성자
    private String generateIV(){
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        String resultIV =Base64.getEncoder().encodeToString(iv);
        log.info("iv값 확인 {}",resultIV);
        return resultIV;
    }
    // 암호화
    public String encoding(String planText){
        try {
            String iv = generateIV();
            IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));

            Cipher cipher =Cipher.getInstance("AES/CBC/PKCSSPadding");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivSpec);
            byte[] encrypted = cipher.doFinal(planText.getBytes(StandardCharsets.UTF_8));

            String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
            return iv+"|"+encryptedBase64;
        }catch (Exception e) {}
        return null;
    }
    // 복화화
    public String decoding(String planText){
        return null;
    }

}
