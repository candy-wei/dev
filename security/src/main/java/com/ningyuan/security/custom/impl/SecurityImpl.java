package com.ningyuan.security.custom.impl;

import com.ningyuan.security.ISecurity;
import com.ningyuan.security.utils.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityImpl implements ISecurity {

    @Override
    public Object getCurrentUser() {
        return SecurityUtils.getCurrentUser();
    }

    @Override
    public String crypto(String text) {
        return new BCryptPasswordEncoder().encode(text);
    }

}
