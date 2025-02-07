package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validacoes.abrigo.ValidacaoAbrigo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {
    @Autowired
    AbrigoRepository abrigoRepository;
    @Autowired
    List<ValidacaoAbrigo> validacoes;

    public void cadastrar(Abrigo abrigo) {
        validacoes.forEach(v -> v.validar(abrigo));
        abrigoRepository.save(abrigo);
    }

    public List<Pet> listarPets(String idOuNome) {
        Abrigo abrigo = encontrarAbrigo(idOuNome);
        return abrigo.getPets();
    }

    public void cadastrarPetNoAbrigo(String idOuNome, Pet pet) {
        Abrigo abrigo = encontrarAbrigo(idOuNome);
        pet = new Pet(abrigo, false);
        abrigo.getPets().add(pet);
        abrigoRepository.save(abrigo);
    }

    private Abrigo encontrarAbrigo(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            return abrigoRepository.getReferenceById(id);
        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException("Abrigo não foi encontrado");
        } catch (NumberFormatException e) {
            try {
                return abrigoRepository.findByNome(idOuNome);
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Abrigo não foi encontrado");
            }
        }
    }
}