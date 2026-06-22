package model;

import java.time.LocalDate;
import java.util.Objects;

public class Receita extends Movimentacao{
	
	private TipoReceita Tipo;
	
	public Receita (float valor, LocalDate dataMovimentacao, TipoReceita Tipo) {
		super(valor, dataMovimentacao);
		setTipo(Tipo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(Tipo);
		return result;
	}

	@Override
	public TipoReceita getTipo() {
		return Tipo;
	}

	public void setTipo(TipoReceita tipo) {
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
		Receita other = (Receita) obj;
		return Tipo == other.Tipo;
	}


}
