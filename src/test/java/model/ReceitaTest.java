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

}
