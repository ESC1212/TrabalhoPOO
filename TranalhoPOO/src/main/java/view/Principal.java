package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Conta;
import model.Desconto;
import model.Movimentacao;
import model.Receita;
import model.TipoReceita;
import model.TiposDescontos;

import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabExpander;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JLabel lbSaldo = new JLabel("Saldo: ");
	private JTable table = new JTable();
	private DefaultTableModel modeloTabela;
	Conta conta = new Conta("Eduardo", 123123, 0);
	private NovaMovimentacao mv = new NovaMovimentacao(this);
	DateTimeFormatter formtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final JTextField edSaldo = new JTextField();
	private final JButton btOrdenar = new JButton("Ordenar");
	JComboBox cbOrdem = new JComboBox();
	private final JTextField edDataSaldo = new JTextField();
	private final JButton btnNewButton = new JButton("Editar movimentaçao");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		edDataSaldo.setBounds(184, 62, 185, 20);
		edDataSaldo.setColumns(10);
		atualizarSaldo();
		edSaldo.setEditable(false);
		edSaldo.setBounds(49, 8, 415, 20);
		edSaldo.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbSaldo.setBounds(10, 11, 46, 14);
		contentPane.add(lbSaldo);
		
		JButton btNovaMovimentacao = new JButton("Cadastrar movimentação");
		btNovaMovimentacao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btNovaMovimentacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mv.limparCampos();
				mv.setModo(0);
				mv.setVisible(true);
				mv.getCbTipo().setEnabled(true);
			}
		});
		btNovaMovimentacao.setBounds(0, 129, 159, 23);
		contentPane.add(btNovaMovimentacao);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 163, 474, 190);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		table.setModel(modeloTabela = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Tipo", "Categoria", "Valor", "Data"
			}
		));
		
		contentPane.add(edSaldo);
		btOrdenar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btOrdenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				organizarTabela(cbOrdem.getSelectedIndex());				
			}
		});
		btOrdenar.setBounds(10, 95, 149, 23);
		
		contentPane.add(btOrdenar);
		
		cbOrdem.setModel(new DefaultComboBoxModel(new String[] {"Data", "Valor", "Tipo"}));
		cbOrdem.setBounds(184, 95, 185, 22);
		contentPane.add(cbOrdem);
		cbOrdem.setSelectedItem(-1);
		
		JButton btSaldoData = new JButton("Consultar saldo em data");
		btSaldoData.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btSaldoData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (edDataSaldo.getText().isEmpty()) {
					edSaldo.setText(String.valueOf(conta.calcularSaldoAtual()));
					return;
				}
				try {
					 edSaldo.setText(String.valueOf(conta.calcularSaldoData(LocalDate.parse(edDataSaldo.getText(), formtData))));
				} catch (DateTimeParseException e) {
					JOptionPane.showMessageDialog(null, "Formato da data digitado incorretamente!\nuse o formato \"dd/mm/aaaa\"");
				}
			}
		});
		btSaldoData.setBounds(10, 61, 149, 23);
		contentPane.add(btSaldoData);
		
		contentPane.add(edDataSaldo);
		
		JButton btDeletar = new JButton("Deletar movimentação");
		btDeletar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deletarRegistro();
			}
		});
		// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
		btDeletar.setBounds(157, 129, 159, 23);
		contentPane.add(btDeletar);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					mv.preencherCampos(conta.getMovimentacoes().get(table.getSelectedRow()));
					mv.setVisible(true);
					mv.getCbTipo().setEnabled(false);
					mv.setModo(1);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um registro!");
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(315, 129, 159, 23);
		
		contentPane.add(btnNewButton);
	}
	public void atualizarTabela() {
		modeloTabela.setRowCount(0);
		for (Movimentacao movimentacao: conta.getMovimentacoes()) {
			String tipo;
			if (movimentacao.getClass() == Receita.class)
				tipo = "Receita";
			else
				tipo = "Desconto";
			
			modeloTabela.addRow(new Object[] {
				tipo,
				movimentacao.getTipo(),
				movimentacao.getValor(),
				movimentacao.getDataMovimentacao().format(formtData)
			});
		}
	}
	public void cadastrarMovimentacao() {
		
		if (mv.getTipoInt() == -1 || mv.getCbCategoria().getSelectedIndex() == -1 || mv.getEdData() == null || mv.getEdValor() <= 0)
			return;
		
		if(mv.getTipoInt() == 0) {
			conta.novaReceita(Float.valueOf(mv.getEdValor()), mv.getEdData(), (TipoReceita) mv.getCbCategoria().getSelectedItem());
		} else if(mv.getTipoInt() == 1) {
			conta.novoDesconto(Float.valueOf(mv.getEdValor()), mv.getEdData(), (TiposDescontos) mv.getCbCategoria().getSelectedItem());
		}
		edSaldo.setText(String.valueOf(conta.calcularSaldoAtual()));
		atualizarTabela();
		JOptionPane.showMessageDialog(null, "Movimentação cadastrada!");
		mv.setVisible(false);
		mv.limparCampos();
	}

	public void atualizarSaldo() {
		edSaldo.setText(String.valueOf(conta.getSaldo() + conta.calcularSaldoAtual()));
	}
	
	public void organizarTabela(int Tipo){
		ArrayList<Movimentacao> movimentacoes = conta.getMovimentacoes();
		switch(Tipo) {
			case 0:
				movimentacoes.sort((m1, m2) -> m1.getDataMovimentacao().compareTo(m2.getDataMovimentacao()));
				break;
			case 1:
				movimentacoes.sort((m1, m2) -> Float.compare(m1.getValor(), m2.getValor()));
				break;
			case 2:
				movimentacoes.clear();
				movimentacoes.addAll(conta.getReceitas());
				movimentacoes.addAll(conta.getDescontos());
				break;
				
		}
		conta.setMovimentacoes(movimentacoes);
		atualizarTabela();
	}
	
	public void deletarRegistro() {
		int selecionado = table.getSelectedRow();
		
		if (selecionado == -1) 
			return;

		ArrayList<Movimentacao> m = conta.getMovimentacoes();
		ArrayList<Receita> R = conta.getReceitas();
		ArrayList<Desconto> D = conta.getDescontos();
		
		if (m.get(selecionado).getClass() == Receita.class) {
			R.remove(m.get(selecionado));
			conta.setReceitas(R);
		} else {
			D.remove(m.get(selecionado));
			conta.setDescontos(D);
		}
		
		m.remove(selecionado);
		conta.setMovimentacoes(m);
		modeloTabela.removeRow(selecionado);
		atualizarTabela();
	}
	
	public void editarRegistro() {
		int selecionado = table.getSelectedRow();
		
		ArrayList<Movimentacao> m = conta.getMovimentacoes();
		ArrayList<Receita> R = conta.getReceitas();
		ArrayList<Desconto> D = conta.getDescontos();
		
		if (mv.getTipoInt() == -1 || mv.getCbCategoria().getSelectedIndex() == -1 || mv.getEdData() == null || mv.getEdValor() <= 0)
			return;
		
		ArrayList<Movimentacao> mov = conta.getMovimentacoes();
		
		mov.get(selecionado).setValor(mv.getEdValor());
		mov.get(selecionado).setDataMovimentacao(mv.getEdData());
		
		if (mov.get(selecionado).getClass() == Receita.class)
			((Receita) mov.get(selecionado)).setTipo((TipoReceita) mv.cbCategoria.getSelectedItem());
		else
			((Desconto) mov.get(selecionado)).setTipo((TiposDescontos) mv.cbCategoria.getSelectedItem());
		
		edSaldo.setText(String.valueOf(conta.calcularSaldoAtual()));
		atualizarTabela();
		JOptionPane.showMessageDialog(null, "Movimentação editada!");
		mv.setVisible(false);
		mv.limparCampos();
		
		
	}
	
}
