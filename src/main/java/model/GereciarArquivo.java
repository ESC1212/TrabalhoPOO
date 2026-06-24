package model;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GereciarArquivo {
	
	File arquivo = new File("Arquivos/dados.csv");
	ArrayList<Movimentacao> movimentacoes = new ArrayList();
	DateTimeFormatter formtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/**
	 * Abri o arquivo caso ele exista, caso não exista, cria um novo
	 */
	public void abrirOuCriarArquivo() {
		if (!arquivo.exists()) {
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * Grava os dados do programa no arquivo
	 * @param mov lista de movimentações para gravar
	 */
	public void escreverArquivo(ArrayList<Movimentacao> mov) {
		try {
			FileOutputStream fos = new FileOutputStream(arquivo);
			PrintWriter arquivoCSV = new PrintWriter(fos);
			for (Movimentacao m : mov) {
				String s;
				if (m.getClass() == Receita.class)
					s = "Receita,";
				else
					s = "Desconto,";
				
				s = s + String.valueOf(m.getValor()) + ",";
				s = s + String.valueOf(m.getDataMovimentacao()) + ",";
				s = s+String.valueOf(m.getTipo());
				arquivoCSV.println(s);
			}	
			arquivoCSV.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * le o arquivo .csv para obter as informações das movimentações
	 * @return lista de movimentações
	 */
	public ArrayList<Movimentacao> lerArquivo() {
		FileReader reader;
		try {
			reader = new FileReader(arquivo);
			BufferedReader buffer = new BufferedReader(reader);
			String linha = buffer.readLine();
			while (linha != null) {
				processaLinha(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			return movimentacoes;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * Transforma uma string em um objeto Desconto ou Receita e adiciona ele a lista de movimentações
	 * @param linha linha a ser processada
	 * @throws IOException
	 */
	public void processaLinha(String linha) throws IOException {
		String[] campos = linha.split(",");
		try {
			Float valor = Float.parseFloat(campos[1]);
			LocalDate data = LocalDate.parse(campos[2]);
			
			if (campos[0].equals("Receita")) {
				Receita r = new Receita(valor, data, CategoriaReceita.valueOf(campos[3]));
				movimentacoes.add(r);
			} else {
				Desconto d = new Desconto(valor, data, CategoriaDesconto.valueOf(campos[3]));
				movimentacoes.add(d);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Caso o arquivo esteja cheio de merda
			JOptionPane.showMessageDialog(null, "Arquivo corrompido\nCriando novo arquivo...");
			arquivo.delete();
			arquivo.createNewFile();
			
		}
		
	}
}
