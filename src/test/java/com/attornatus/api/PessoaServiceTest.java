package com.attornatus.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.attornatus.api.model.Pessoa;
import com.attornatus.api.respository.EnderecoRepository;
import com.attornatus.api.respository.PessoaRepository;
import com.attornatus.api.service.PessoaService;

@SpringBootTest
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCriarPessoa() {
        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
        
        Pessoa created = pessoaService.criarPessoa(pessoa);
        
        assertNotNull(created);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void testEditarPessoa() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa updated = pessoaService.editarPessoa(id, pessoa);

        assertNotNull(updated);
        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void testListarPessoas() {
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.listarPessoas();

        assertEquals(pessoas.size(), result.size());
        verify(pessoaRepository).findAll();
    }
}

