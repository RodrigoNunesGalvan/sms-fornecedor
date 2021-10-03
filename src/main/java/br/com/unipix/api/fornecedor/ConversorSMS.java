package br.com.unipix.api.fornecedor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;

@Component
public interface ConversorSMS {

	public String converterFormato(List<SMSRequest> lista, Integer fornecedorId) throws JsonProcessingException;

}
