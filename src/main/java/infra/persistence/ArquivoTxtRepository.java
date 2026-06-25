package infra.persistence;

import models.Bloco;
import repository.ArmazenadorRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoTxtRepository implements ArmazenadorRepository {

    private final String nomeArquivo;

    public ArquivoTxtRepository(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void salvar(List<Bloco> corrente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("====================================\n");
            writer.write("       BACKUP DA BLOCKCHAIN         \n");
            writer.write("====================================\n\n");

            for (Bloco bloco : corrente) {
                writer.write("ID: " + bloco.getId());
                writer.newLine();
                writer.write("CONTEÚDO: " + bloco.getConteudo());
                writer.newLine();
                writer.write("HASH ANTERIOR: " + bloco.getHashAnterior());
                writer.newLine();
                writer.write("HASH ATUAL: " + bloco.getHash());
                writer.newLine();
                writer.write("DATA/HORA: " + bloco.getTime());
                writer.newLine();
                writer.write("------------------------------------");
                writer.newLine();
            }

            System.out.println("[Infraestrutura] Blockchain salva em: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro critico ao salvar no Bloco de Notas: " + e.getMessage());
        }
    }

    @Override
    public List<Bloco> carregar() {
        List<Bloco> listaCarregada = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if(!arquivo.exists()){
            return listaCarregada;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            int id = 0;
            String conteudo = "";
            String hashAnterior = "";
            String hashAtual = "";
            long timestamp = 0;

            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                if (linha.equals("------------------------------------")) {
                    Bloco bloco = new Bloco(id, conteudo, hashAnterior, hashAtual, timestamp);
                    listaCarregada.add(bloco);
                    continue;
                }

                if (linha.contains(": ")) {
                    String[] partes = linha.split(": ", 2);
                    String chave = partes[0].toUpperCase();
                    String valor = partes[1];

                    switch (chave) {
                        case "ID" -> id = Integer.parseInt(valor);
                        case "CONTEÚDO" -> conteudo = valor;
                        case "HASH ANTERIOR" -> hashAnterior = valor;
                        case "HASH ATUAL" -> hashAtual = valor;
                        case "DATA/HORA" -> timestamp = Long.parseLong(valor);
                    }
                }
            }
            System.out.println("[Infraestrutura] Blockchain carregada com sucesso do arquivo. Total de blocos: " + listaCarregada.size());

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro critico ao ler e processar o arquivo TXT: " + e.getMessage());
        }

        return listaCarregada;
    }

}
