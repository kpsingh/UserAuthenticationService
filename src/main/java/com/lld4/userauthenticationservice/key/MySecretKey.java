package com.lld4.userauthenticationservice.key;

import javax.crypto.SecretKey;

public class MySecretKey implements SecretKey {
    @Override
    public String getAlgorithm() {
        return "HS256";
    }

    @Override
    public String getFormat() {
        return "";
    }

    @Override
    public byte[] getEncoded() {
        return new byte[0];
    }
}
