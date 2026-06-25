import infra.persistence.ArquivoJsonRepository;
import repository.ArmazenadorRepository;
import service.BlockChainService;
import terminal.BlockChainConsole;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ArmazenadorRepository repository = new ArquivoTxtRepository("blockchain.txt");
        ArmazenadorRepository repository = new ArquivoJsonRepository("blockchain.json");

        BlockChainService service = new BlockChainService(repository);

        try (Scanner scanner = new Scanner(System.in)) {
            BlockChainConsole console = new BlockChainConsole(service, scanner);
            console.iniciar();
        }
    }
}