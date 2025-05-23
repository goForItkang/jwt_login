package com.jobdam.jwt_login.util;

import com.jobdam.jwt_login.excetion.CustomException;
import com.jobdam.jwt_login.excetion.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
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

            Cipher cipher =Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivSpec);
            byte[] encrypted = cipher.doFinal(planText.getBytes(StandardCharsets.UTF_8));

            String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
            return iv+encryptedBase64;
        }catch (Exception e) {}
        throw new CustomException(ErrorCode.ENCODING_ERROR);
    }
    // 복화화
    public String decoding(String encodingText){
        try{
            String ivBase64 = encodingText.substring(0,24);
            String cipherBase64 = encodingText.substring(24);

            IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(ivBase64));
            byte[] encrypted = Base64.getDecoder().decode(cipherBase64);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivSpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        }catch (Exception e) {}
        throw new CustomException(ErrorCode.DECODING_ERROR);
    }

}
