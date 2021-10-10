package br.com.unipix.api.fornecedor.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.unipix.api.dto.fornecedor.BestVoiceRequest;
import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.fornecedor.ConversorSMS;

@Component
public class ConversorSMSBestVoiceImpl implements ConversorSMS {

	@Override
	public String converterFormato(List<SMSRequest> lista, Long fornecedorId) throws JsonProcessingException {
		StringBuilder payload = new StringBuilder();
		if (lista.size() > 1) {
			payload.append("{\"bulk\":[");
			for (SMSRequest sms : lista) {
				payload.append(criarRequest(sms) + ",");
			}
			payload.replace(payload.length() - 1, payload.length(), "");
			payload.append("]}");
		} else {
			payload.append(criarRequest(lista.get(0)));
		}
		return payload.toString();
	}

	private String criarRequest(SMSRequest request) throws JsonProcessingException {
		BestVoiceRequest bestVoiceRequest = new BestVoiceRequest();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		bestVoiceRequest.setParceiroId(request.getSmsId());
		bestVoiceRequest.setMensagem(request.getMensagem());
		bestVoiceRequest.setCelular(request.getNumero());
		String json = ow.writeValueAsString(bestVoiceRequest);
		return json;
	}

}
