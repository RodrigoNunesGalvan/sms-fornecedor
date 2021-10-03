package br.com.unipix.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unipix.api.model.ParametroFornecedorSMS;
import br.com.unipix.api.repository.ParametroFornecedorSMSRepository;

@Service
public class ParametroFornecedorSMSService {

	@Autowired
	private ParametroFornecedorSMSRepository repositoryParametroFornecedorSMS;

	public String findByfornecedorSMSIDChave(Integer fornecedorSMSID, String chave) {
		return repositoryParametroFornecedorSMS.findByfornecedorSMSIDChave(fornecedorSMSID, chave);
	}
	
	public HashMap<String,String> findByfornecedorSMSID(Integer fornecedorSMSID) {
		HashMap<String,String> chaves = new HashMap<>();
		List<ParametroFornecedorSMS> parametros = repositoryParametroFornecedorSMS.findByfornecedorSMSID(fornecedorSMSID);
		for (ParametroFornecedorSMS fornecedorSMSParam : parametros) {
			chaves.put(fornecedorSMSParam.getChave(), fornecedorSMSParam.getValor());
		}
		return chaves;
	}

}
