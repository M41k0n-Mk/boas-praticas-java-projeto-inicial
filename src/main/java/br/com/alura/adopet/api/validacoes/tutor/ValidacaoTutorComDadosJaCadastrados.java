package br.com.alura.adopet.api.validacoes.tutor;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoTutorComDadosJaCadastrados implements ValidacaoCadastrarTutor {
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validar(CadastroTutorDto cadastroTutorDto) {
        boolean jaCadastrado = tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        }
    }
}