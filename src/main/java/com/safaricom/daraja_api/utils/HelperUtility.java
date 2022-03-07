package com.safaricom.daraja_api.utils;

import org.bson.internal.Base64;

import java.nio.charset.StandardCharsets;

public class HelperUtility {
    public static String toBase64String(String value) {
        byte[] data = value.getBytes(StandardCharsets.ISO_8859_1);
        return Base64.encode(data);
    }
}
