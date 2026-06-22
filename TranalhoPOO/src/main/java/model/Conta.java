package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Conta {
	
	private String nomeTitulas;
	private int numeroConta;
	private float saldo;
	private ArrayList<Desconto> descontos = new ArrayList<Desconto>();
	private ArrayList<Receita> receitas = new ArrayList<Receita>();
	private ArrayList<Movimentacao> movimentacoes = new ArrayList();
	
	public void setMovimentacoes(ArrayList<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Conta(String nomeTitutal, int numeroConta, float saldo) {
		setNomeTitulas(nomeTitutal);
		setNumeroConta(numeroConta);
		setSaldo(saldo);
	}

	public String getNomeTitulas() {
		return nomeTitulas;
	}

	public void setNomeTitulas(String nomeTitulas) {
		if (nomeTitulas.isEmpty())
			throw new IllegalArgumentException("Nome inválido!");
		this.nomeTitulas = nomeTitulas;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		if (numeroConta <= 0)
			throw new IllegalArgumentException("Numero conta inválido");
		this.numeroConta = numeroConta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	
	public void novaReceita(float valor, LocalDate dataMovimentacao, TipoReceita Tipo) {
		if (!dataMovimentacao.isAfter(LocalDate.now()))
			setSaldo(saldo + valor);
		Receita r = new Receita(valor, dataMovimentacao, Tipo);	
		receitas.add(r);
		movimentacoes.add(r);
		calcularSaldoAtual();
	}
	
	public void novoDesconto(float valor, LocalDate dataMovimentacao, TiposDescontos Tipo) {
		Desconto d = new Desconto(valor, dataMovimentacao, Tipo);	
		descontos.add(d);
		movimentacoes.add(d);
		calcularSaldoAtual();
	}
	
	public void setDescontos(ArrayList<Desconto> descontos) {
		this.descontos = descontos;
	}

	public void setReceitas(ArrayList<Receita> receitas) {
		this.receitas = receitas;
	}

	public ArrayList<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

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
		return totalReceitas - totalDescontos;					
	}
	
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

	public ArrayList<Desconto> getDescontos() {
		return descontos;
	}

	public ArrayList<Receita> getReceitas() {
		return receitas;
	}
	
}
