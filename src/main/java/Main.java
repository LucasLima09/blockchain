import infra.persistence.ArquivoJsonRepository;
import infra.security.GeradorSHA256;
import repository.ArmazenadorRepository;
import service.AlgoritmoHash;
import service.BlockChainService;
import terminal.BlockChainConsole;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ArmazenadorRepository repository = new ArquivoTxtRepository("blockchain.txt");
        ArmazenadorRepository repository = new ArquivoJsonRepository("blockchain.json");
        AlgoritmoHash SHA256 = new GeradorSHA256();

        BlockChainService service = new BlockChainService(repository, SHA256);

        try (Scanner scanner = new Scanner(System.in)) {
            BlockChainConsole console = new BlockChainConsole(service, scanner);
            console.iniciar();
        }
    }
}