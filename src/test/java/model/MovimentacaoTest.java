package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MovimentacaoTest {
	Receita r = new Receita(1000, LocalDate.now(), CategoriaReceita.SALARIO);
	@Test
	void testeSetSaldo() {
		assertThrows(IllegalArgumentException.class, () -> r.setValor(0),"Valor não pode ser menor ou igual a zero!");
	}
	
	@Test
	void testeSetData() {
		assertThrows(IllegalArgumentException.class, () -> r.setDataMovimentacao(null),"Valor não pode ser menor ou igual a zero!");
	}
}
