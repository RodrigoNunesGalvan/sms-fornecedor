package br.com.unipix.api.fornecedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.unipix.api.fornecedor.impl.EnviadorSMSBestVoiceImpl;
import br.com.unipix.api.fornecedor.impl.EnviadorSMSZenviaImpl;

@Component
public class EnviadorSMSFactory {

	@Autowired
	private EnviadorSMSZenviaImpl enviadorSMSZenvia;

	@Autowired
	private EnviadorSMSBestVoiceImpl enviadorSMSBestVoice;

	public EnviadorSMS obterFornecedor(Integer fornecedorID) {
		switch (fornecedorID) {
		case 1:
			return enviadorSMSZenvia; // short
		case 2:
			return enviadorSMSZenvia; // flash
		case 3:
			return enviadorSMSBestVoice; // short
		case 4:
			return enviadorSMSBestVoice; // short premium
		case 5:
			return enviadorSMSBestVoice; // long
		default:
			return null;
		}
	}

}
