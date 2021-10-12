package br.com.unipix.api.fornecedor.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.dto.request.SMSResponse;
import br.com.unipix.api.dto.response.SMSZenviaResponse;
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

	@Autowired
	private Gson jsonConverter;

	@Override
	public List<SMSResponse> prepararEnviar(List<SMSRequest> request, Long fornecedorId) throws JsonProcessingException {
		HashMap<String, String> chaves = parametroFornecedorSMSService.findByfornecedorSMSID(fornecedorId);
		ConversorSMS conversorSMS = converterSMSFactory.getConversorFornecedor(fornecedorId);
		String payload = conversorSMS.converterFormato(request, fornecedorId);
		String response = enviar(chaves, request, payload);
		List<SMSZenviaResponse> retornos = obterRetornoEnvioFornecedor(response, request.size());
		return obterStatusEnvio(retornos, request);
	}
	
	private List<SMSResponse> obterStatusEnvio(List<SMSZenviaResponse>  retornos, List<SMSRequest> request) {
		int index = 0;
		List<SMSResponse> responses = new ArrayList<>();
		for (SMSZenviaResponse smsZenviaResponse : retornos) {
			SMSResponse smsResponse = new SMSResponse();
			SMSRequest smsRequest = request.get(index);
			smsRequest.setStatusCode(smsZenviaResponse.getStatusCode());
			smsRequest.setStatusDescription(smsZenviaResponse.getStatusDescription());
			smsRequest.setDetailDescription(smsZenviaResponse.getDetailDescription());
			request.set(index, smsRequest);
			BeanUtils.copyProperties(smsRequest, smsResponse);
			smsResponse.setStatus("nao_enviado");
			if (smsZenviaResponse.getStatusCode().equals("00") || smsZenviaResponse.getStatusCode().equals("01")) {
				smsResponse.setStatus("enviado");
			}
			responses.add(smsResponse);
		}
		return responses;
	}
	
	private String obterEndPointEnvio(HashMap<String, String> chaves, Integer quantidadeMensagens) {
		if (quantidadeMensagens == 1) {
			return chaves.get("endpointSendSingleSMS");
		} else {
			return chaves.get("endpointSendMultipleSMS");
		}
	}
	
	private List<SMSZenviaResponse> obterRetornoEnvioFornecedor(String response, Integer quantidadeSMS) {
		List<SMSZenviaResponse> smsZenviaResponses = new ArrayList<>();
		JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
		if (quantidadeSMS == 1) {
			JsonElement jsonElement = jsonObject.get("sendSmsResponse");
			String element = jsonElement.toString();
			SMSZenviaResponse smsZenviaResponse = jsonConverter.fromJson(element, SMSZenviaResponse.class);  
			smsZenviaResponses.add(smsZenviaResponse);
		} else {
			JsonElement jsonElement = jsonObject.get("sendSmsMultiResponse");
			JsonObject element = jsonElement.getAsJsonObject();
			JsonArray list = element.getAsJsonArray("sendSmsResponseList");
			Type userListType = new TypeToken<ArrayList<SMSZenviaResponse>>(){}.getType();
			smsZenviaResponses = jsonConverter.fromJson(list, userListType);  
		}
		return smsZenviaResponses;
	}
	
	private String enviar(HashMap<String, String> chaves, List<SMSRequest> lista, String payload) {
		String token = chaves.get("tokenShort");
		String urlfornecedorSMS = obterEndPointEnvio(chaves, lista.size());
		RestTemplate rest = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Accept", "application/json");
    	headers.add("Content-Type", "application/json");
    	headers.add("Authorization", "Basic " + token);
    	HttpEntity<?> request = new HttpEntity<Object>(payload, headers);
    	ResponseEntity<String> response = rest.exchange(urlfornecedorSMS, HttpMethod.POST, request, String.class);
    	return response.getBody();
	}
}
