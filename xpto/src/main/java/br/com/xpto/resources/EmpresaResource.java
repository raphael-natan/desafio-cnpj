package br.com.xpto.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.xpto.services.EmpresaService;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaResource {

	@Autowired
	private EmpresaService service;

	@GetMapping(value = "/{cnpj}")
	public ResponseEntity<?> findByCnpj(@PathVariable String cnpj) {
		try {
			return ResponseEntity.ok(service.findByCnpj(cnpj));
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getRawStatusCode()).body(e.getReason());
		}
	}
}
