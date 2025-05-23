package com.jobdam.jwt_login.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AESUtilTest {

    @Autowired
    private AESUtil aesUtil;

    @Test
     void init() {
        assertThat(aesUtil).isNotNull();
    }

    @Test
    void encoding() {
        //given
        String planText =  "test01";
        //when
        String encodingText = aesUtil.encoding(planText);
        System.out.println(encodingText);
    }

    @Test
    void decoding() {
    }
}