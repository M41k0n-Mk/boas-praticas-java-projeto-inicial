package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoDadosJaCadastradosOutroAbrigo implements ValidacaoAbrigo {
    @Autowired
    AbrigoRepository abrigoRepository;

    @Override
    public void validar(Abrigo abrigo) {
        boolean nomeJaCadastrado = abrigoRepository.existsByNome(abrigo.getNome());
        boolean telefoneJaCadastrado = abrigoRepository.existsByTelefone(abrigo.getTelefone());
        boolean emailJaCadastrado = abrigoRepository.existsByEmail(abrigo.getEmail());

        if (nomeJaCadastrado || telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }
    }
}