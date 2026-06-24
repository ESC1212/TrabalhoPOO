package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ReceitaTest {

	Receita r = new Receita(1000,LocalDate.now(), CategoriaReceita.SALARIO);

	@Test
	void testeSetCategoria() {
		assertThrows(IllegalArgumentException.class, () -> r.setTipo(null),"Validação de categoria errada!");
	}
	
	@Test
	void testeEquals() {
		Receita r1 = new Receita(2000, LocalDate.now(), CategoriaReceita.SALARIO);
		Receita r2 = new Receita(2000, LocalDate.now(), CategoriaReceita.SALARIO);
		Receita r3 = new Receita(1000, LocalDate.now(), CategoriaReceita.SALARIO);
		
		assertTrue(r1.equals(r2), "Receitas iguais retornando diferentes!");
		assertFalse(r1.equals(r3), "Receitas diferentes retornando iguais!");
	}

}
