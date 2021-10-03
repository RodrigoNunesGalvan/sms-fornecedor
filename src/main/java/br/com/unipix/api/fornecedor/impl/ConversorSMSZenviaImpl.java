package br.com.unipix.api.fornecedor.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.unipix.api.dto.fornecedor.ZenviaRequest;
import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.fornecedor.ConversorSMS;

@Component
public class ConversorSMSZenviaImpl implements ConversorSMS {

	@Override
	public String converterFormato(List<SMSRequest> lista, String tipoFormatoRequisicao) throws JsonProcessingException {
		StringBuilder payload = new StringBuilder();
		if (lista.size() > 1) {
			payload.append("{\"sendSmsMultiRequest\":{");
			payload.append("\"sendSmsRequestList\":[");
			for (SMSRequest sms : lista) {
				payload.append(criarRequest(sms) + ",");
			}
			payload.replace(payload.length() - 1, payload.length(), "");
			payload.append("]}}");
		} else {
			payload.append("{\"sendSmsRequest\":");
			for (SMSRequest sms : lista) {
				payload.append(criarRequest(sms) + ",");
			}
			payload.replace(payload.length() - 1, payload.length(), "");
			payload.append("}");
		}
		return payload.toString();
	}

	private String criarRequest(SMSRequest request) throws JsonProcessingException {
		ZenviaRequest zenviaRequest = new ZenviaRequest();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		zenviaRequest.setAggregateId(null);
		zenviaRequest.setCallbackOption("ALL");
		zenviaRequest.setDataCoding(null);
		zenviaRequest.setFlashSms(false);
		zenviaRequest.setFrom(null);
		zenviaRequest.setId(request.getId());
		zenviaRequest.setMsg(request.getMensagem());
		zenviaRequest.setSchedule(null);
		zenviaRequest.setTo(request.getNumero());
		String json = ow.writeValueAsString(zenviaRequest);
		return json;
	}

}
