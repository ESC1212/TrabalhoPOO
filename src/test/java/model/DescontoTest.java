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
}
