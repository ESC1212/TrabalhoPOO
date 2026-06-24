package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Conta {
	
	private float saldo;
	private ArrayList<Desconto> descontos = new ArrayList<Desconto>();
	private ArrayList<Receita> receitas = new ArrayList<Receita>();
	private ArrayList<Movimentacao> movimentacoes = new ArrayList();

	public Conta() {
		setSaldo(0);
	}

	public void setMovimentacoes(ArrayList<Movimentacao> movimentacoes) {
		if (movimentacoes == null)
			throw new IllegalArgumentException("Lista vazia!");
		this.movimentacoes = movimentacoes;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public void setDescontos(ArrayList<Desconto> descontos) {
		if (descontos == null)
			throw new IllegalArgumentException("Lista vazia!");
		this.descontos = descontos;
	}

	public void setReceitas(ArrayList<Receita> receitas) {
		if (receitas == null)
			throw new IllegalArgumentException("Lista vazia!");
		this.receitas = receitas;
	}

	public ArrayList<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}
	
	public ArrayList<Desconto> getDescontos() {
		return descontos;
	}

	public ArrayList<Receita> getReceitas() {
		return receitas;
	}

	/**
	 * Metodo para cadastrar uma nova receita
	 * @param valor valor da receita
	 * @param dataMovimentacao data da receita
	 * @param Tipo categoria da receita 
	 */
	public void novaReceita(float valor, LocalDate dataMovimentacao, CategoriaReceita Tipo) {
		if (!dataMovimentacao.isAfter(LocalDate.now()))
			setSaldo(saldo + valor);
		Receita r = new Receita(valor, dataMovimentacao, Tipo);	
		receitas.add(r);
		movimentacoes.add(r);
		calcularSaldoAtual();
	}
	/**
	 * Metodo para cadastrar um novo desconto
	 * @param valor valor do desconto
	 * @param dataMovimentacao data do desconto
	 * @param Tipo categoria do desconto
	 */
	public void novoDesconto(float valor, LocalDate dataMovimentacao, CategoriaDesconto Tipo) {
		Desconto d = new Desconto(valor, dataMovimentacao, Tipo);	
		descontos.add(d);
		movimentacoes.add(d);
		calcularSaldoAtual();
	}
	/**
	 * Calcula o saldo baseado na data atual
	 * @return saldo atual
	 */
	public float calcularSaldoAtual() {
		float totalReceitas = 0;
		float totalDescontos = 0;
		for (int i = 0; i < receitas.size(); i++) {
			if (!receitas.get(i).getDataMovimentacao().isAfter(LocalDate.now())) {
				totalReceitas += receitas.get(i).getValor();
			}
		}
		for (int i = 0; i < descontos.size(); i++) {
			if (!descontos.get(i).getDataMovimentacao().isAfter(LocalDate.now())) {
				totalDescontos += descontos.get(i).getValor();
			}
		}
		saldo = totalReceitas - totalDescontos;
		return saldo;					
	}
	/**
	 * Calcula o saldo baseado em uma data informada
	 * @param data data para o salculo do saldo
	 * @return o saldo calculado para a data informada
	 */
	public float calcularSaldoData(LocalDate data) {
		float totalReceitas = 0;
		float totalDescontos = 0;
		for (int i = 0; i < receitas.size(); i++) {
			if (!receitas.get(i).getDataMovimentacao().isAfter(data)) {
				totalReceitas += receitas.get(i).getValor();
			}
		}
		for (int p = 0; p < descontos.size(); p++) {
			if (!descontos.get(p).getDataMovimentacao().isAfter(data)) {
				totalDescontos += descontos.get(p).getValor();
			}
		}
		return totalReceitas - totalDescontos;				
	}
	
}
