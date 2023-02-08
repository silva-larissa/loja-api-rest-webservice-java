package api.loja.ficticia.controller;

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

import api.loja.ficticia.domain.produtocompra.ProdutoCompra;
import api.loja.ficticia.domain.produtocompra.ProdutoCompraRepository;

@RestController
@RequestMapping("/produtocompra")
public class ProdutoCompraController {
	
	@Autowired
	ProdutoCompraRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity<ProdutoCompra> cadastrarProdutoDaCompra(@RequestBody @Validated ProdutoCompra produtoCompra, UriComponentsBuilder uriBuilder){
		produtoCompra = repository.save(produtoCompra);
		URI uri = uriBuilder.path("/produtocompra/{id}").buildAndExpand(produtoCompra.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoCompra);
	}
	
	@GetMapping
	public List<ProdutoCompra> listarProdutosDaCompra(){
		List<ProdutoCompra> produtosCompra = repository.findAll();
		return produtosCompra;
	}
	
	@GetMapping("/{id}") 
	public ProdutoCompra listarUmProdutoDaCompra(@PathVariable Long id){
		Optional<ProdutoCompra> produtoCompra = repository.findById(id);
		return produtoCompra.get(); 
	}	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarProdutoDaCompra(@PathVariable Long id) throws Exception {
		repository.findById(id).orElseThrow(() -> new Exception("Item não encontrado na lista de produtos da compra!"));
		repository.deleteById(id);
		return ResponseEntity.noContent().build();	
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProdutoCompra> atualizarProdutoDaCompra(@PathVariable Long id, @RequestBody ProdutoCompra produtoCompra) throws Exception {
		ProdutoCompra atualizarProdutoDaCompra = repository.findById(id).orElseThrow(() -> new Exception("Item não encontrado na lista de produtos da compra!"));
		atualizarProdutoDaCompra.setQuantidade(produtoCompra.getQuantidade()); //O único campo atualizável é a quantidade, os demais deveriam implicar na exclusão do produto da Nota Fiscal
		repository.save(atualizarProdutoDaCompra);
		return ResponseEntity.ok(atualizarProdutoDaCompra);
	}
}
