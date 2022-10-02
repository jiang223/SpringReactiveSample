package com.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Md5 {
    @Test
    public  void aaa(){
        System.out.println(new BCryptPasswordEncoder().encode("1"));
    }
}
