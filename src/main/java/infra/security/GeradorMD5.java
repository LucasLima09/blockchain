package infra.security;

import org.apache.commons.codec.digest.DigestUtils;
import service.AlgoritmoHash;

public class GeradorMD5 implements AlgoritmoHash {
    @Override
    public String gerar(String input) {
        return DigestUtils.md5Hex(input);
    }
}
