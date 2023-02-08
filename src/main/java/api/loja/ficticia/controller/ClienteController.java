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

import api.loja.ficticia.domain.cliente.Cliente;
import api.loja.ficticia.domain.cliente.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
		
	@PostMapping
	@Transactional
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Validated Cliente cliente, UriComponentsBuilder uriBuilder){
		cliente = repository.save(cliente);
		URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(cliente);
	}
	
	@GetMapping
	public List<Cliente> listarClientes(){
		List<Cliente> clientes = repository.findAll();
		return clientes;
	}
	
	@GetMapping("/{id}")
	public Cliente listarUmCliente(@PathVariable Long id){
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.get();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarCliente(@PathVariable Long id) throws Exception {
		repository.findById(id).orElseThrow(() -> new Exception("Cliente não encontrado!"));
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente detalhesCliente) throws Exception{
		Cliente atualizarCliente = repository.findById(id).orElseThrow(() -> new Exception("Cliente não encontrado!"));
		atualizarCliente.setNome(detalhesCliente.getNome());
		atualizarCliente.setCpf(detalhesCliente.getCpf());
		repository.save(atualizarCliente);
		return ResponseEntity.ok(atualizarCliente);
	}
	
}
