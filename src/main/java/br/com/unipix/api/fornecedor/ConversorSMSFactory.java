package br.com.unipix.api.fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.unipix.api.fornecedor.impl.ConversorSMSBestVoiceImpl;
import br.com.unipix.api.fornecedor.impl.ConversorSMSZenviaImpl;

@Component
public class ConversorSMSFactory {

	@Autowired
	private ConversorSMSZenviaImpl converterSMSZenvia;

	@Autowired
	private ConversorSMSBestVoiceImpl converterSMSBestVoice;

	public ConversorSMS getConversorFornecedor(Integer fornecedorId) {

		switch (fornecedorId) {
		case 1:
			return converterSMSZenvia; 
		case 2:
			return converterSMSZenvia; 
		case 3:
			return converterSMSBestVoice; 
		case 4:
			return converterSMSBestVoice; 
		case 5:
			return converterSMSBestVoice; 
		default:
			return null;
		}

	}
}
