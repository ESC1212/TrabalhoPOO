package model;

import java.time.LocalDate;

public class Receita extends Movimentacao{
	
	private TipoReceita Tipo;
	
	public Receita (float saldoAtual, float valor, LocalDate dataMovimentacao, TipoReceita Tipo) {
		super(saldoAtual, valor, dataMovimentacao);
		setTipo(Tipo);
	}

	public TipoReceita getTipo() {
		return Tipo;
	}

	public void setTipo(TipoReceita tipo) {
		if (tipo == null)
			throw new IllegalArgumentException("Tipo inválido!");
		Tipo = tipo;
	}

}
