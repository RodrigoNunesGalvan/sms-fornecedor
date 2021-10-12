package br.com.unipix.api.fornecedor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.dto.response.SMSBestVoiceResponse;
import br.com.unipix.api.dto.response.SMSResponse;
import br.com.unipix.api.fornecedor.ConversorSMS;
import br.com.unipix.api.fornecedor.ConversorSMSFactory;
import br.com.unipix.api.fornecedor.EnviadorSMS;
import br.com.unipix.api.service.ParametroFornecedorService;

@Component
public class EnviadorSMSBestVoiceImpl implements EnviadorSMS {

	@Autowired
	private ParametroFornecedorService parametroFornecedorService;
	
	@Autowired
	private ConversorSMSFactory converterSMSFactory;
	
	@Override
	public List<SMSResponse> prepararEnviar(List<SMSRequest> request, Long fornecedorId) throws JsonProcessingException {
		HashMap<String, String> chaves = parametroFornecedorService.findByfornecedorSMSID(fornecedorId);
		ConversorSMS conversorSMS = converterSMSFactory.getConversorFornecedor(fornecedorId);
		String payload = conversorSMS.converterFormato(request, fornecedorId);
		String response =  enviar(chaves, request, payload);
		List<SMSBestVoiceResponse> retornos = obterRetornoEnvioFornecedor(response, request.size());
		return obterRespostaEnvio(retornos, request);
	}
	
	private List<SMSResponse> obterRespostaEnvio(List<SMSBestVoiceResponse>  retornos, List<SMSRequest> request) {
		List<SMSResponse> responses = new ArrayList<>();
		return responses;
	}

	private List<SMSBestVoiceResponse> obterRetornoEnvioFornecedor(String response, Integer quantidadeSMS) {
		List<SMSBestVoiceResponse> smsZenviaResponses = new ArrayList<>();
		return smsZenviaResponses;
	}

	private String obterEndPointEnvio(HashMap<String, String> chaves, Integer quantidadeMensagens) {
		if (quantidadeMensagens == 1) {
			return chaves.get("endpointSendSingleSMS");
		} else {
			return chaves.get("endpointSendMultipleSMS");
		}
	}
	
	private String enviar(HashMap<String, String> chaves, List<SMSRequest> lista, String payload) {
		String usuario = chaves.get("usuario");
		String senha = chaves.get("senha");
		String urlfornecedorSMS = obterEndPointEnvio(chaves, lista.size());
		
		RestTemplate rest = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Accept", "application/json");
    	headers.add("Content-Type", "application/json");
    	headers.add("usuario", usuario);
    	headers.add("chave", senha);
    	HttpEntity<?> request = new HttpEntity<Object>(payload, headers);
    	ResponseEntity<String> response = rest.exchange(urlfornecedorSMS, HttpMethod.POST, request, String.class);
       	return response.getBody();
	}
}
