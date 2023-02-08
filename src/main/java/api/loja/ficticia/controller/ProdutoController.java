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

import api.loja.ficticia.domain.produto.Produto;
import api.loja.ficticia.domain.produto.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Validated Produto produto, UriComponentsBuilder uriBuilder){
		produto = repository.save(produto);
		URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(produto);
	}
	
	@GetMapping
	public List<Produto> listarProdutos(){
		List<Produto> produtos = repository.findAll();
		return produtos;
	}
	
	@GetMapping("/{id}") 
	public Produto listarUmProduto(@PathVariable Long id){
		Optional<Produto> produto = repository.findById(id);
		return produto.get(); 
	}	

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarProduto(@PathVariable Long id) throws Exception {
		repository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado!"));
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto detalhesProduto) throws Exception{
		Produto atualizarProduto = repository.findById(id).orElseThrow(() -> new Exception("Produto não encontrado!"));
		atualizarProduto.setCodigo(detalhesProduto.getCodigo());
		atualizarProduto.setDescricao(detalhesProduto.getDescricao());
		repository.save(atualizarProduto);
		return ResponseEntity.ok(atualizarProduto);
	}
}
