package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//migration com flyway
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    //vai falar qual verbo do protocolo http vai ser chamado (nesse caso: POST)
    @PostMapping
    //Insert no sql
    @Transactional
    public String cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
        return "medico cadastrado com sucesso!";
    }

    //vai falar qual verbo do protocolo http vai ser chamado (nesse caso: GET)
    @GetMapping
    public List<DadosListagemMedico> listarMedicos() {
        //repository.findAll() [encontra e retorna lista de medicos
        //stream().map(DadosListagemMedico::new) [converte a lista de medico para dadosListagemMedico]
        //.toList() [converte para lista]
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    }
}
