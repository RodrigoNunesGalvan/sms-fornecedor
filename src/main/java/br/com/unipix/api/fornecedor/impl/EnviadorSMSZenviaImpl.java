package br.com.unipix.api.fornecedor.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.fornecedor.ConversorSMS;
import br.com.unipix.api.fornecedor.ConversorSMSFactory;
import br.com.unipix.api.fornecedor.EnviadorSMS;
import br.com.unipix.api.service.ParametroFornecedorService;

@Component
public class EnviadorSMSZenviaImpl implements EnviadorSMS {

	@Autowired
	private ParametroFornecedorService parametroFornecedorSMSService;
	
	@Autowired
	private ConversorSMSFactory converterSMSFactory;
	
	@Override
	public void prepararEnviar(List<SMSRequest> request, Integer fornecedorId) throws JsonProcessingException {
		HashMap<String, String> chaves = parametroFornecedorSMSService.findByfornecedorSMSID(fornecedorId);
		ConversorSMS conversorSMS = converterSMSFactory.getConversorFornecedor(fornecedorId);
		String payload = conversorSMS.converterFormato(request, fornecedorId);
		enviar(chaves, request, payload);
	}
	
	private String obterEndPointEnvio(HashMap<String, String> chaves, Integer quantidadeMensagens) {
		if (quantidadeMensagens == 1) {
			return chaves.get("endpointSendSingleSMS");
		} else {
			return chaves.get("endpointSendMultipleSMS");
		}
	}
	
	private void enviar(HashMap<String, String> chaves, List<SMSRequest> lista, String payload) {
		String token = chaves.get("tokenShort");
		String urlfornecedorSMS = obterEndPointEnvio(chaves, lista.size());
		RestTemplate rest = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Accept", "application/json");
    	headers.add("Content-Type", "application/json");
    	headers.add("Authorization", "Basic " + token);
    	HttpEntity<?> request = new HttpEntity<Object>(payload, headers);
    	ResponseEntity<String> response = rest.exchange(urlfornecedorSMS, HttpMethod.POST, request, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			//enviarListaSucesso(lista);
		} else {
			//enviarListaFalha(lista);
		}
	}
}
