package models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Bloco {
    private final int id;
    private final String conteudo;
    private final String hashAnterior;
    private final String hash;
    private final long time;

    public Bloco(int id, String conteudo, String hashAnterior, String hash, long time) {
        this.id = id;
        this.conteudo = conteudo;
        this.hashAnterior = hashAnterior;
        this.hash = hash;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getHashAnterior() {
        return hashAnterior;
    }

    public String getHash() {
        return hash;
    }

    private String formatarData(long milissegundos){
        Instant instant = Instant.ofEpochMilli(milissegundos);
        LocalDateTime dataHora = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHora.format(formatador);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Conteúdo: " + conteudo + "\n" +
                "Hash Anterior: " + hashAnterior + "\n" +
                "Hash Atual: " + hash + "\n" +
                "Data/Hora: " + formatarData(time) + "\n" +
                "------------------------------------";
    }

}