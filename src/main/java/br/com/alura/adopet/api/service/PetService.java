package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public List<DadosDetalhesPet> listarTodosDisponiveis() {
        return petRepository
                .findAllByAdotadoFalse()
                .stream()
                .map(DadosDetalhesPet::new)
                .toList();
    }
}