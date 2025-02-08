package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoDadosJaCadastradosOutroAbrigo implements ValidacaoAbrigo {
    @Autowired
    AbrigoRepository abrigoRepository;

    @Override
    public void validar(CadastroAbrigoDto cadastroAbrigoDto) {
        boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(cadastroAbrigoDto.nome(),
                cadastroAbrigoDto.telefone(), cadastroAbrigoDto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }
    }
}