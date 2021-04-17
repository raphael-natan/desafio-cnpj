package br.com.xpto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.server.ResponseStatusException;

import br.com.xpto.entities.Empresa;
import br.com.xpto.repositories.EmpresaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpresaService {	
	@Value("${receitaWS.cnpj}")
	public String receitaWSCnpj;

	@Autowired
	private EmpresaRepository repository;
	
	@Autowired
	private WebClient webClient;

	public Empresa findByCnpj(String cnpj) {
		Empresa empresa = repository.findByCnpj(cnpj);
		if (empresa == null) {
			log.error("Cnpj não encontrado: " + cnpj);

			try {
				var emp = getEmpresaByCnpjInReceitaWS(cnpj);
				if (emp.getCnpj()==null) throw new IllegalArgumentException("Cnpj rejeitado ou inválido");
				return repository.save(emp);
			} catch (WebClientException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Empresa não encontrada", e);				
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(), e);
			}
		}
		log.info("Cnpj encontrado: " + cnpj);
		return empresa;
	}
	
	public Empresa getEmpresaByCnpjInReceitaWS(String cnpj) {
		   return webClient
		           .method(HttpMethod.GET)
		           .uri(receitaWSCnpj, cnpj)
		           .retrieve()
		           .bodyToMono(Empresa.class)
		           .block();
		}	
}
