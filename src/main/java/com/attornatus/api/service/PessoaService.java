package com.attornatus.api.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attornatus.api.dto.EnderecoDTO;
import com.attornatus.api.dto.PessoaDTO;
import com.attornatus.api.model.Endereco;
import com.attornatus.api.model.Pessoa;
import com.attornatus.api.respository.EnderecoRepository;
import com.attornatus.api.respository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa editarPessoa(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setDataDeNascimento(pessoaAtualizada.getDataDeNascimento());
        return pessoaRepository.save(pessoa);
    }

    public PessoaDTO consultarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoa.getId());
        
        PessoaDTO pessoaDTO = toPessoDTO(pessoa);
        List<EnderecoDTO> enderecosDTO = enderecos.stream().map(e -> toEnderecoDTO(e)).toList();
        pessoaDTO.setEnderecos(enderecosDTO);
        
        
        return pessoaDTO;
    }
    
    public PessoaDTO toPessoDTO(Pessoa pessoa){
    	PessoaDTO pessoaDTO = new PessoaDTO();
    	
    	pessoaDTO.setId(pessoa.getId());
    	pessoaDTO.setNome(pessoa.getNome());
    	pessoaDTO.setDataDeNascimento(pessoa.getDataDeNascimento());
    	
    	return pessoaDTO;
    }
    
    public EnderecoDTO toEnderecoDTO(Endereco endereco) {
    	EnderecoDTO enderecoDTO  = new EnderecoDTO();
    	
    	enderecoDTO.setCep(endereco.getCep());
    	enderecoDTO.setCidade(endereco.getCidade());
    	enderecoDTO.setId(endereco.getId());
    	enderecoDTO.setLogradouro(endereco.getLogradouro());
    	enderecoDTO.setNumero(endereco.getNumero());
    	enderecoDTO.setPrincipal(endereco.isPrincipal());
    	
    	return enderecoDTO;
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Endereco salvarEndereco(Long pessoaId, Endereco endereco) throws Exception {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new Exception("Pessoa não encontrada com ID: " + pessoaId));
        
        endereco.setPessoa(pessoa);
        return enderecoRepository.save(endereco);
    }

    @Transactional
    public List<Endereco> listarEnderecosPorPessoa(Long pessoaId) throws Exception {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new Exception("Pessoa não encontrada com ID: " + pessoaId));
        
        return enderecoRepository.findByPessoaId(pessoa.getId());
    }

    @Transactional
    public Endereco marcarEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoa.getId());

        for (Endereco endereco : enderecos) {
            endereco.setPrincipal(endereco.getId().equals(enderecoId));
        }

        enderecoRepository.saveAll(enderecos);
        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }
    
    @Transactional
    public void deletarPessoa(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }
}

