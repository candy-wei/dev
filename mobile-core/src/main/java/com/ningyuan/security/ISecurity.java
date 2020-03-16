package com.ningyuan.security;

public interface ISecurity {

    Object getCurrentUser();

    String crypto(String text);

}
