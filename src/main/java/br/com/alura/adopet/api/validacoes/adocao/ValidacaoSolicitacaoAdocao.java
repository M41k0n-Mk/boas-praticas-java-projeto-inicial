package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;

public interface ValidacaoSolicitacaoAdocao {
    void validar(SolicitacaoAdocaoDto dto);
}