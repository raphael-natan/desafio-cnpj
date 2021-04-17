package br.com.xpto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.xpto.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {

	Empresa findByCnpj(String cnpj);

}
