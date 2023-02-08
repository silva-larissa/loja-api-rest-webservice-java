package api.loja.ficticia.domain.notafiscal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import api.loja.ficticia.domain.cliente.Cliente;
import api.loja.ficticia.domain.produtocompra.ProdutoCompra;

@Entity
@Table(name = "tb_notasfiscais")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NotaFiscal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nota")
	private Long id;

	@Column(name = "numero_nota", nullable = false)
	private String numeroNota;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_emissao", nullable = false)
	private Date dataEmissao;

	@ManyToOne
	@JoinColumn(name = "tb_clientes")
	private Cliente cliente;

	@OneToMany(mappedBy = "notaFiscal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ProdutoCompra> produtosCompra;

	@Column(name = "valor_nota")
	private BigDecimal valorNotaFiscal;

	
	// Getters e Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ProdutoCompra> getProdutosCompra() {
		return produtosCompra;
	}

	public void setProdutosCompra(List<ProdutoCompra> produtosCompra) {
		this.produtosCompra = produtosCompra;
	}

	public BigDecimal getValorNotaFiscal() {
		return valorNotaFiscal;
	}

	public void setValorNotaFiscal(BigDecimal valorNotaFiscal) {
		this.valorNotaFiscal = valorNotaFiscal;
	}
	




}
