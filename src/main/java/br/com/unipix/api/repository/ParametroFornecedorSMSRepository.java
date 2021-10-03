package br.com.unipix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unipix.api.model.ParametroFornecedorSMS;

public interface ParametroFornecedorSMSRepository extends JpaRepository<ParametroFornecedorSMS, Integer> {

	@Query(value="select f.valor from SMSHub.ParametroFornecedorSMS f where fornecedorSMSID = ?1 and chave = ?2",nativeQuery=true)
	public String findByfornecedorSMSIDChave( Integer fornecedorSMSID, String chave);
	
	@Query(value="select f.* from SMSHub.ParametroFornecedorSMS f where fornecedorSMSID = ?1",nativeQuery=true)
	public List<ParametroFornecedorSMS> findByfornecedorSMSID(Integer fornecedorSMSID);
}