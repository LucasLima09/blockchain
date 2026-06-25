package service;

import org.apache.commons.codec.digest.DigestUtils;

public class GeradorHash {
    public static String aplicarSHA256(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
