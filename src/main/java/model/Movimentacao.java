package model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Movimentacao {
	
	private float valor;
	private LocalDate dataMovimentacao;
	
	public Movimentacao ( float valor, LocalDate dataMovimentacao) {
		setValor(valor);
		setDataMovimentacao(dataMovimentacao);
	}

	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		if (valor <= 0)
			throw new IllegalArgumentException("Valor inválido");
		this.valor = valor;
	}
	public LocalDate getDataMovimentacao() {
		return dataMovimentacao;
	}
	public void setDataMovimentacao(LocalDate dataMovimentacao) {
		if (dataMovimentacao == null)
			throw new IllegalArgumentException("Data inválida!");
		this.dataMovimentacao = dataMovimentacao;
	}

	public abstract Object getTipo();


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimentacao other = (Movimentacao) obj;
		return Objects.equals(dataMovimentacao, other.dataMovimentacao)
				&& Float.floatToIntBits(valor) == Float.floatToIntBits(other.valor);
	}
}
