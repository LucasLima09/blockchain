package terminal;

import models.Bloco;
import service.BlockChainService;

import java.util.Scanner;

public class BlockChainConsole {

    private final BlockChainService service;
    private final Scanner scanner;

    public BlockChainConsole(BlockChainService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void iniciar(){
        service.carregarArquivo();
        Scanner input = this.scanner != null ? this.scanner : new Scanner(System.in);
        boolean closeLocalScanner = this.scanner == null;

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n===== MENU BLOCKCHAIN =====");
            System.out.println("1. Adicionar Novo Bloco");
            System.out.println("2. Visualizar Blockchain");
            System.out.println("3. Verificar Integridade da Rede");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                if (!input.hasNextLine()) {
                    System.out.println("Entrada encerrada. Saindo.");
                    break;
                }

                opcao = Integer.parseInt(input.nextLine());
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Digite o conteudo/transacao do bloco: ");
                        String conteudo = input.hasNextLine() ? input.nextLine() : "";
                        service.novoBloco(conteudo);
                        service.salvarProgresso();
                        System.out.println("Bloco adicionado e salvo com sucesso!");
                    }
                    case 2 -> {
                        System.out.println("\n=== EXIBINDO CORRENTE ATUAL ===");
                        service.carregarArquivo();
                        for (Bloco b : service.obterCorrente()) {
                            System.out.println(b);
                        }
                    }
                    case 3 -> {
                        System.out.println("\n=== CHECANDO SEGURANCA ===");
                        service.carregarArquivo();
                        service.verificarIntegridadeGeral();
                    }
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opcao invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um numero valido.");
            }
        }

        if (closeLocalScanner) {
            try {
                input.close();
            } catch (Exception ignore) {}
        }
    }


}