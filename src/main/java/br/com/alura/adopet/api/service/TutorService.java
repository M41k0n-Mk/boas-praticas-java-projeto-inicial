package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.tutor.ValidacaoCadastrarTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private List<ValidacaoCadastrarTutor> validacoes;

    public void cadastrar(Tutor tutor) {
        validacoes.forEach(v -> v.validar(tutor));
        tutorRepository.save(tutor);
    }
}