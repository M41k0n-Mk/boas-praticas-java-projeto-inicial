package br.com.alura.adopet.api.validacoes.tutor;

import br.com.alura.adopet.api.dto.CadastroTutorDto;

public interface ValidacaoCadastrarTutor {
    void validar(CadastroTutorDto cadastroTutorDto);
}