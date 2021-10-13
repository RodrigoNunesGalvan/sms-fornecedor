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
import br.com.unipix.api.dto.response.SMSBestVoiceResponse;
import br.com.unipix.api.dto.response.SMSResponse;
import br.com.unipix.api.fornecedor.ConversorSMS;
import br.com.unipix.api.fornecedor.ConversorSMSFactory;
import br.com.unipix.api.fornecedor.EnviadorSMS;
import br.com.unipix.api.service.ParametroFornecedorService;

@Component
public class EnviadorSMSBestVoiceImpl implements EnviadorSMS {

	@Autowired
	private Gson jsonConverter;

	@Autowired
	private ParametroFornecedorService parametroFornecedorService;
	
	@Autowired
	private ConversorSMSFactory converterSMSFactory;
	
	@Override
	public List<SMSResponse> prepararEnviar(List<SMSRequest> request, Long fornecedorId) throws JsonProcessingException {
		HashMap<String, String> chaves = parametroFornecedorService.findByfornecedorSMSID(fornecedorId);
		ConversorSMS conversorSMS = converterSMSFactory.getConversorFornecedor(fornecedorId);
		String payload = conversorSMS.converterFormato(request, fornecedorId);
		try {
			String response =  enviar(chaves, request, payload);
			if (enviadoComSucesso(response)) {
				List<SMSBestVoiceResponse> retornos = obterRetornoEnvioFornecedor(response, request.size());
				return definirRespostaEnvio(retornos, request);
			} else {
				JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
				String statusDetalhe = jsonObject.get("statusDetalhe").getAsString();
				return definirStatusErroEnvio(request,statusDetalhe);
			}
		} catch (Exception e) {
			return definirStatusErroEnvio(request,e.getMessage());
		}
	}
	
	private boolean enviadoComSucesso(String response) {
		JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
		return jsonObject.get("status") == null;
	}
	
	private List<SMSResponse> definirStatusErroEnvio(List<SMSRequest> request, String mensagemFornecedor) {
		List<SMSResponse> responses = new ArrayList<>();
		for (SMSRequest smsRequest : request) {
			SMSResponse smsResponse = new SMSResponse();
			BeanUtils.copyProperties(smsRequest, smsResponse);
			smsResponse.setStatus("nao_enviado");
			smsResponse.setMensagemFornecedor(mensagemFornecedor);
			responses.add(smsResponse);
		}
		return responses;
	}

	private List<SMSResponse> definirRespostaEnvio(List<SMSBestVoiceResponse>  retornos, List<SMSRequest> request) {
		List<SMSResponse> responses = new ArrayList<>();
		for (SMSRequest smsRequest : request) {
			SMSResponse smsResponse = new SMSResponse();
			smsRequest.setMensagemFornecedor("Message Sent");
			BeanUtils.copyProperties(smsRequest, smsResponse);
			smsResponse.setStatus("enviado");
			responses.add(smsResponse);
		}
		return responses;
	}

	private List<SMSBestVoiceResponse> obterRetornoEnvioFornecedor(String response, Integer quantidadeSMS) {
		List<SMSBestVoiceResponse> smsZenviaResponses = new ArrayList<>();
		JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
		if (quantidadeSMS == 1) {
			JsonElement jsonId = jsonObject.get("id");
			JsonElement jsonParceiroId = jsonObject.get("parceiroId");
			String id = jsonId.toString();
			String parceiroId = jsonParceiroId.toString();
			SMSBestVoiceResponse smsResponse = SMSBestVoiceResponse.builder().id(id).parceiroId(parceiroId).build();
			smsZenviaResponses.add(smsResponse);
		} else {
			JsonObject element = jsonObject.getAsJsonObject();
			JsonArray list = element.getAsJsonArray("bulk");
			Type userListType = new TypeToken<ArrayList<SMSBestVoiceResponse>>(){}.getType();
			smsZenviaResponses = jsonConverter.fromJson(list, userListType);  
		}
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
