package model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ContaTest {
	Conta conta = new Conta();
	@Test
	void testeNovaReceita() {
		conta.novaReceita(2000, LocalDate.parse("2025-12-12"), CategoriaReceita.FERIAS);
		Receita r = new Receita(2000, LocalDate.parse("2025-12-12"), CategoriaReceita.FERIAS);
		assertTrue(conta.getReceitas().get(0).equals(r), "Valores de receitas diferentes");
	}
	
	@Test
	void testeNovoDesconto() {
		conta.novoDesconto(2000, LocalDate.parse("2025-12-12"), CategoriaDesconto.ENTRETENIMENTO);
		Desconto d = new Desconto(2000, LocalDate.parse("2025-12-12"), CategoriaDesconto.ENTRETENIMENTO);
		assertTrue(conta.getDescontos().get(0).equals(d), "Valores de descontos diferentes");
	}
	
	@Test
	void testeCalcularSaldo() {
		conta.novaReceita(3000, LocalDate.now(), CategoriaReceita.SALARIO);
		conta.novoDesconto(300, LocalDate.now(), CategoriaDesconto.ALIMENTACAO);
		assertEquals(2700, conta.calcularSaldoAtual(), "Saldo calculado incorretamente");
	}
	
	@Test
	void testeCalcularSaldoData() {
		conta.novaReceita(3000, LocalDate.now(), CategoriaReceita.SALARIO);
		conta.novoDesconto(300, LocalDate.now(), CategoriaDesconto.ALIMENTACAO);
		conta.novaReceita(10000, LocalDate.parse("2077-01-01"), CategoriaReceita.SALARIO);
		conta.novoDesconto(8000, LocalDate.parse("2077-01-01"), CategoriaDesconto.ALIMENTACAO);
		assertEquals(0, conta.calcularSaldoData(LocalDate.parse("2000-01-01")), "Saldo calculado incorretamente");
		assertEquals(4700, conta.calcularSaldoData(LocalDate.parse("2080-01-01")), "Saldo calculado incorretamente");
	}
	
	@Test
	void testeSetDescontos() {
		assertThrows(IllegalArgumentException.class, () -> conta.setDescontos(null),"Validação de Descontos incorreta!");
	}
	
	@Test
	void testeSetReceitas() {
		assertThrows(IllegalArgumentException.class, () -> conta.setReceitas(null),"Validação de Receitas incorreta!");
	}
	
	@Test
	void testeSetMovimentecoes() {
		assertThrows(IllegalArgumentException.class, () -> conta.setMovimentacoes(null),"Validação de Receitas incorreta!");
	}
}
