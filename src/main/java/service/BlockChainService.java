package service;

import models.Bloco;
import repository.ArmazenadorRepository;

import java.util.ArrayList;
import java.util.List;

public class BlockChainService {

    private final List<Bloco> chain = new ArrayList<>();
    private final ArmazenadorRepository armazenadorRepository;
    private final AlgoritmoHash algoritmoHash;

    public BlockChainService(ArmazenadorRepository armazenadorRepository, AlgoritmoHash algoritmoHash) {
        this.armazenadorRepository = armazenadorRepository;
        this.algoritmoHash = algoritmoHash;
    }

    public void carregarArquivo(){
        chain.clear();
        chain.addAll(armazenadorRepository.carregar());
    }

    public void novoBloco(String conteudo) {
        String hashAnterior  = chain.isEmpty() ? "0" : chain.getLast().getHash();
        int proximoId = chain.size() + 1;
        long time = System.currentTimeMillis();

        String dadosParaHash = proximoId + conteudo + hashAnterior + time;
        String hashGerado = algoritmoHash.gerar(dadosParaHash);

        Bloco novoBloco = new Bloco(proximoId, conteudo, hashAnterior, hashGerado, time);
        chain.add(novoBloco);
    }

    public void verificarIntegridadeGeral(){
        boolean redeIntegra = true;

        for (int i = 0; i < chain.size(); i++) {
            redeIntegra = true;
            Bloco atual = chain.get(i);

            String dadosParaVerificar = atual.getId() + atual.getConteudo() + atual.getHashAnterior() + atual.getTime();
            String hashRecalculado = algoritmoHash.gerar(dadosParaVerificar);

            if(!atual.getHash().equals(hashRecalculado)){
                System.out.println("FALHA DETECTADA: O Bloco ID [" + atual.getId() + "] foi violado diretamente na base de dados!");
                redeIntegra = false;
                continue;
            }

            if (i > 0) {
                Bloco anterior = chain.get(i - 1);
                if (!atual.getHashAnterior().equals(anterior.getHash())) {
                    System.out.println("QUEBRA DE CORRENTE: O Bloco ID [" + atual.getId() + "] quebrou o link com o Bloco [" + anterior.getId() + "].");
                    redeIntegra = false;
                }
            }

            if (redeIntegra) {
                System.out.println("Bloco 100% integro.");
            }

        }
    }

    public void salvarProgresso(){
        this.armazenadorRepository.salvar(this.chain);
    }

    public List<Bloco> obterCorrente() { return this.chain; }
}
