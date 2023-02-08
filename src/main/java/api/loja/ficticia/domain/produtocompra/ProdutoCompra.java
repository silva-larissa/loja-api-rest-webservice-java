package api.loja.ficticia.domain.produtocompra;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import api.loja.ficticia.domain.notafiscal.NotaFiscal;
import api.loja.ficticia.domain.produto.Produto;

@Entity
@Table(name = "tb_produtocompra")
public class ProdutoCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produtocompra")
	private Long id;

	@Column(nullable = false)
	private String codigo;

	@ManyToOne
	private NotaFiscal notaFiscal;

	@ManyToOne
	private Produto produto;

	@Column(nullable = false)
	private BigDecimal quantidade;

	@Column(name = "valor_parcial")
	private BigDecimal valorParcial;

	
	// Getters e Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorParcial() {
		return valorParcial;
	}

	public void setValorParcial() {
		valorParcial = quantidade.multiply(produto.getValorUnitario());
	}

}
