package api.loja.ficticia.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import api.loja.ficticia.domain.notafiscal.NotaFiscal;
import api.loja.ficticia.domain.notafiscal.NotaFiscalRepository;
import api.loja.ficticia.domain.produtocompra.ProdutoCompra;

@RestController
@RequestMapping("/notafiscal")
public class NotaFiscalController {

	@Autowired
	NotaFiscalRepository repository;
		
	@PostMapping
	@Transactional
	public ResponseEntity<NotaFiscal> cadastrarNotaFiscal(@RequestBody @Validated NotaFiscal notaFiscal, UriComponentsBuilder uriBuilder){
		
		BigDecimal valorNota = BigDecimal.ZERO;
		for (ProdutoCompra produtoCompra : notaFiscal.getProdutosCompra()) { //ForEach para incluir o(s) produto(s) da compra na nota fiscal
			produtoCompra.setNotaFiscal(notaFiscal);
			valorNota = valorNota.add(produtoCompra.getValorParcial());
			notaFiscal.setValorNotaFiscal(valorNota);
		}
			
		notaFiscal = repository.save(notaFiscal);
		URI uri = uriBuilder.path("/notafiscal/{id}").buildAndExpand(notaFiscal.getId()).toUri();
		return ResponseEntity.created(uri).body(notaFiscal);
	}
	
	@GetMapping
	public List<NotaFiscal> listarNotasFiscais(){
		List<NotaFiscal> notasFiscais = repository.findAll();
		return notasFiscais;
	}
	
	@GetMapping("/{id}")
	public Optional<NotaFiscal> listarUmaNotaFiscal(@PathVariable Long id) {
		Optional<NotaFiscal> notaFiscal = repository.findById(id);
		return notaFiscal;
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarNotaFiscal(@PathVariable Long id) throws Exception {
		repository.findById(id).orElseThrow(() -> new Exception("Nota Fiscal não encontrada!"));
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<NotaFiscal> atualizarNotaFiscal(@PathVariable Long id, @RequestBody NotaFiscal detalhesNotaFiscal) throws Exception {
		NotaFiscal atualizarNotaFiscal = repository.findById(id).orElseThrow(() -> new Exception("Nota Fiscal não encontrada!"));
		repository.save(atualizarNotaFiscal);
		return ResponseEntity.ok(atualizarNotaFiscal);
	}
}
