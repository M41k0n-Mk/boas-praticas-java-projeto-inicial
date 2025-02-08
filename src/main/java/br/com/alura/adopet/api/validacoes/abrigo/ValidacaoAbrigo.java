package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;

public interface ValidacaoAbrigo {
    void validar(CadastroAbrigoDto cadastroAbrigoDto);
}