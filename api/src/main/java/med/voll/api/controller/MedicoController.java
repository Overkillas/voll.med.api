package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    //@PageableDefault substitui padrão da paginação (opcional)
    public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        //repository.findAll() [encontra e retorna lista de medicos
        //stream().map(DadosListagemMedico::new) [converte a lista de medico para dadosListagemMedico]
        //########################################################
        //################Codigo sem paginação:###################
        //.toList() [converte para lista]
        // return repository.findAll().stream().map(DadosListagemMedico::new).toList();
        //########################################################
        //findAll(paginacao) serve para aparecer X registros por vez
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        //OBS: PARA TROCAR O TAMANHO DE REGISTROS MOSTRADOS: http://localhost:8080/medicos?size=1
        //OBS: PARA TROCAR A PAGINA: http://localhost:8080/medicos?size=1&page=1
        //OBS: PARA ORDENAR A  (CRESCENTE): http://localhost:8080/medicos?sort=atributoASerOrdenado
        //OBS: PARA ORDENAR A PAGINA (DECRESCENTE: http://localhost:8080/medicos?sort=crm,desc (sendo crm o atributo)
        //TUDO JUNTO:  http://localhost:8080/medicos?sort=crm,desc&size=2&page=1
    }

    @PutMapping
    @Transactional // pra escrever no banco de dados (ele ja faz a alteração no bd ao mudar o próprio médico
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        // repository acessa banco de dados
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }

    //API VAI EXCLUIR POR MEIO DA URL (ONDE VAI SER medicos/id a ser deletado
    @DeleteMapping("/{id}")
    @Transactional
    //@PathVariable diz que é um parâmetro dinâmico (vem por url)
    public void excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
