package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {
    @Autowired
    private AdocaoRepository repository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private List<ValidacaoSolicitacaoAdocao> validacoes;

    public void solicitar(SolicitacaoAdocaoDto solicitacaoAdocaoDto) {
        Pet pet = petRepository.getReferenceById(solicitacaoAdocaoDto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor());

        validacoes.forEach(v -> v.validar(solicitacaoAdocaoDto));

        Adocao adocao = new Adocao(tutor, pet, solicitacaoAdocaoDto.motivo());

        repository.save(adocao);

        emailService.enviarEmail(
                pet.getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " + pet.getAbrigo().getNome() +
                        "!\n\nUma solicitação de adoção foi registrada hoje para o pet: "
                        + pet.getNome() + ". \nFavor avaliar para aprovação ou reprovação.");
    }

    public void aprovar(AprovacaoAdocaoDto aprovacaoAdocaoDto) {
        Adocao adocao = repository.getReferenceById(aprovacaoAdocaoDto.idAdocao());
        adocao.marcarComoAprovada();

        //repository.save(adocao); //redundante?

        emailService.enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet "
                        + adocao.getPet().getNome() + ", solicitada em "
                        + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        + ", foi aprovada.\nFavor entrar em contato com o abrigo "
                        + adocao.getPet().getAbrigo().getNome()
                        + " para agendar a busca do seu pet.");
    }

    public void reprovar(ReprovacaoAdocaoDto reprovacaoAdocaoDto) {
        Adocao adocao = repository.getReferenceById(reprovacaoAdocaoDto.idAdocao());
        adocao.marcarComoReprovada(reprovacaoAdocaoDto.justificativa());

        //repository.save(reprovacaoAdocaoDto); //redundante??

        emailService.enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção reprovada",
                "Olá " + adocao.getTutor().getNome() +
                        "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() +
                        ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() +
                        " com a seguinte justificativa: " + adocao.getJustificativaStatus());
    }
}