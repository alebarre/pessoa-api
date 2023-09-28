package com.attornatus.api.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PessoaDTO {
	
	private Long id;
    private String nome;
    private LocalDate dataDeNascimento;
    private List<EnderecoDTO> enderecos = new ArrayList<>();

}
