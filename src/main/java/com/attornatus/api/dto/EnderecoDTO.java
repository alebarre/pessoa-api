package com.attornatus.api.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
	
	private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private boolean principal;

}
