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

public class GereciarArquivo {
	
	File arquivo = new File("Arquivos/dados.csv");
	ArrayList<Movimentacao> movimentacoes = new ArrayList();
	DateTimeFormatter formtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
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
				System.out.println(s);
				arquivoCSV.println(s);
			}	
			arquivoCSV.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	public void processaLinha(String linha) {
		String[] campos = linha.split(",");
		Float valor = Float.parseFloat(campos[1]);
		LocalDate data = LocalDate.parse(campos[2]);
			
		if (campos[0].equals("Receita")) {
			Receita r = new Receita(valor, data, TipoReceita.valueOf(campos[3]));
			movimentacoes.add(r);
		} else {
			Desconto d = new Desconto(valor, data, TiposDescontos.valueOf(campos[3]));
			movimentacoes.add(d);
		}
	}
}
