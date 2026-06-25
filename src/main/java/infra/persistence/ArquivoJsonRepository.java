package infra.persistence;

import models.Bloco;
import repository.ArmazenadorRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ArquivoJsonRepository implements ArmazenadorRepository {

    private final String nomeArquivo;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ArquivoJsonRepository(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public void salvar(List<Bloco> chain) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            gson.toJson(chain, writer);

            System.out.println("[Infraestrutura] Blockchain salva com sucesso via Gson!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo JSON: " + e.getMessage());
        }
    }

    @Override
    public List<Bloco> carregar() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Bloco>>(){}.getType();

            List<Bloco> correnteCarregada = gson.fromJson(reader, tipoLista);

            return correnteCarregada != null ? correnteCarregada : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}