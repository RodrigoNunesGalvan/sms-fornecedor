package br.com.unipix.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unipix.api.dto.request.SMSRequest;
import br.com.unipix.api.fornecedor.EnviadorSMS;
import br.com.unipix.api.fornecedor.EnviadorSMSFactory;

@Service
public class ReceberSMSService {
	
	@Autowired
	private EnviadorSMSFactory enviadorSMSFactory;

	public void enviarSMS(SMSRequest sms) throws JsonProcessingException {
		List<SMSRequest> request = new ArrayList<>();
		request.add(sms);
		EnviadorSMS enviador = enviadorSMSFactory.obterFornecedor(sms.getFornecedorId());
		enviador.prepararEnviar(request, sms.getFornecedorId());
	}
}
