package com.attornatus.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.api.dto.PessoaDTO;
import com.attornatus.api.model.ApiResponse;
import com.attornatus.api.model.Endereco;
import com.attornatus.api.model.Pessoa;
import com.attornatus.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa pessoaCriada = pessoaService.criarPessoa(pessoa);
        return new ResponseEntity<>(pessoaCriada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> editarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Pessoa pessoaEditada = pessoaService.editarPessoa(id, pessoa);
        return new ResponseEntity<>(pessoaEditada, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable Long id) {
        PessoaDTO pessoa = pessoaService.consultarPessoa(id);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @PostMapping("/{pessoaId}")
    public ResponseEntity<ApiResponse<Endereco>> criarEnderecoParaPessoa(
            @PathVariable Long pessoaId, @RequestBody Endereco endereco) {
        try {
            Endereco enderecoSalvo = pessoaService.salvarEndereco(pessoaId, endereco);
            return new ResponseEntity<>(new ApiResponse<>(enderecoSalvo, HttpStatus.CREATED), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{pessoaId}/enderecos")
    public ResponseEntity<ApiResponse<List<Endereco>>> listarEnderecosDePessoa(@PathVariable Long pessoaId) {
        try {
            List<Endereco> enderecos = pessoaService.listarEnderecosPorPessoa(pessoaId);
            return new ResponseEntity<>(new ApiResponse<>(enderecos, HttpStatus.OK), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idPessoa}/enderecos/{idEndereco}/principal")
    public ResponseEntity<Endereco> marcarEnderecoPrincipal(@PathVariable Long idPessoa, @PathVariable Long idEndereco) {
        Endereco endereco = pessoaService.marcarEnderecoPrincipal(idPessoa, idEndereco);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        pessoaService.deletarPessoa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
