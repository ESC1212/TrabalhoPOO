package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DescontoTest {
	Desconto d = new Desconto(1000,LocalDate.now(),CategoriaDesconto.ALIMENTACAO);

	@Test
	void testeSetCategoria() {
		assertThrows(IllegalArgumentException.class, () -> d.setTipo(null),"Validação de categoria errada!");
	}
	
	@Test
	void testeEquals() {
		Desconto d1 = new Desconto(2000, LocalDate.now(), CategoriaDesconto.ALIMENTACAO);
		Desconto d2 = new Desconto(2000, LocalDate.now(), CategoriaDesconto.ALIMENTACAO);
		Desconto d3 = new Desconto(1000, LocalDate.now(), CategoriaDesconto.ALIMENTACAO);
		
		assertTrue(d1.equals(d2), "Descontos iguais retornando diferentes!");
		assertFalse(d1.equals(d3), "Descontos diferentes retornando iguais!");
	}
}
