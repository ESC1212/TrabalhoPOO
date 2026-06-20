package model;

import java.io.PrintStream;
import java.sql.Date;
import java.time.LocalDate;

import javax.xml.crypto.Data;

public class Movimentacao {
	
	private float saldoAtual;
	private float valor;
	private LocalDate dataCadastro;
	private LocalDate dataMovimentacao;
	
	public Movimentacao (float saldoAtual, float valor, LocalDate dataMovimentacao) {
		setSaldoAtual(saldoAtual);
		setValor(valor);
		setDataCadastro(LocalDate.now());
		setDataMovimentacao(dataMovimentacao);
	}
	
	public float getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(float saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		if (valor <= 0)
			throw new IllegalArgumentException("Valor inválido");
		this.valor = valor;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		if (dataCadastro == null)
			throw new IllegalArgumentException("Data Cadastro invalida");
		this.dataCadastro = dataCadastro;
	}
	public LocalDate getDataMovimentacao() {
		return dataMovimentacao;
	}
	public void setDataMovimentacao(LocalDate dataMovimentacao) {
		if (dataMovimentacao == null)
			throw new IllegalArgumentException("Data Cadastro invalida");
		this.dataMovimentacao = dataMovimentacao;
	}
}
