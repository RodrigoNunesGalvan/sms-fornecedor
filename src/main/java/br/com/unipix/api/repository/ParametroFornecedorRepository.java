package br.com.unipix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unipix.api.model.ParametroFornecedor;

public interface ParametroFornecedorRepository extends JpaRepository<ParametroFornecedor, Integer> {

	@Query("select f from ParametroFornecedor f where f.fornecedorSMSID = ?1")
	public List<ParametroFornecedor> findByfornecedorSMSID(Integer fornecedorSMSID);
}