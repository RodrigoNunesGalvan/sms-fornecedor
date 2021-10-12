package br.com.unipix.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.dto.request.SMSResponse;
import br.com.unipix.api.fornecedor.EnviadorSMS;
import br.com.unipix.api.fornecedor.EnviadorSMSFactory;

@Service
public class EnviarSMSService {
	
	@Autowired
	private EnviadorSMSFactory enviadorSMSFactory;

	public List<SMSResponse> enviarSMS(SMSRequest sms) throws JsonProcessingException {
		List<SMSRequest> request = new ArrayList<>();
		request.add(sms);
		EnviadorSMS enviador = enviadorSMSFactory.obterFornecedor(sms.getFornecedorId());
		return enviador.prepararEnviar(request, sms.getFornecedorId());
	}
}
