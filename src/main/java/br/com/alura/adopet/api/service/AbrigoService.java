package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validacoes.abrigo.ValidacaoAbrigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {
    @Autowired
    AbrigoRepository abrigoRepository;
    @Autowired
    List<ValidacaoAbrigo> validacoes;
    @Autowired
    private PetRepository petRepository;

    public void cadastrar(CadastroAbrigoDto cadastroAbrigoDto) {
        validacoes.forEach(v -> v.validar(cadastroAbrigoDto));
        abrigoRepository.save(new Abrigo(cadastroAbrigoDto));
    }

    public List<PetDto> listarPetsDoAbrigo(String idOuNome) {
        Abrigo abrigo = encontrarAbrigo(idOuNome);
        return petRepository
                .findByAbrigo(abrigo)
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public void cadastrarPetNoAbrigo(String idOuNome, CadastroPetDto cadastroPetDto) {
        Abrigo abrigo = encontrarAbrigo(idOuNome);
        petRepository.save(new Pet(cadastroPetDto, abrigo));
    }

    private Abrigo encontrarAbrigo(String idOuNome) {
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        } catch (NumberFormatException e) {
            optional = abrigoRepository.findByNome(idOuNome);


        }
        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }
}