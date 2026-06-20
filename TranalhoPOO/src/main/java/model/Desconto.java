package model;

import java.time.LocalDate;

public class Desconto extends Movimentacao{

	private TiposDescontos Tipo;
	
	public Desconto (float saldoAtual, float valor, LocalDate dataMovimentacao, TiposDescontos Tipo) {
		super(saldoAtual, valor, dataMovimentacao);
		setTipo(Tipo);
	}

	public TiposDescontos getTipo() {
		return Tipo;
	}

	public void setTipo(TiposDescontos tipo) {
		if (tipo == null)
			throw new IllegalArgumentException("Tipo inválido!");
		Tipo = tipo;
	}

}