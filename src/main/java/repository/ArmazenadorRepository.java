package repository;

import models.Bloco;

import java.util.List;

public interface  ArmazenadorRepository {
    void salvar(List<Bloco> corrente);
    List<Bloco> carregar();
}

