package model;

import java.time.LocalDate;
import java.util.Objects;

public class Desconto extends Movimentacao{

	private CategoriaDesconto Tipo;
	
	public Desconto (float valor, LocalDate dataMovimentacao, CategoriaDesconto Tipo) {
		super(valor, dataMovimentacao);
		setTipo(Tipo);
	}

	@Override
	public CategoriaDesconto getTipo() {
		return Tipo;
	}

	public void setTipo(CategoriaDesconto tipo) {
		if (tipo == null)
			throw new IllegalArgumentException("Tipo inválido!");
		Tipo = tipo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Desconto other = (Desconto) obj;
		return Tipo == other.Tipo;
	}

}