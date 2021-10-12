package br.com.unipix.api.fornecedor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.dto.request.SMSResponse;

@Component
public interface EnviadorSMS {

	public List<SMSResponse> prepararEnviar(List<SMSRequest> request, Long fornecedorId) throws JsonProcessingException;

}

