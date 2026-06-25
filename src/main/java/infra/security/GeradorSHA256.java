package infra.security;

import org.apache.commons.codec.digest.DigestUtils;
import service.AlgoritmoHash;

public class GeradorSHA256 implements AlgoritmoHash {
    @Override
    public String gerar(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
