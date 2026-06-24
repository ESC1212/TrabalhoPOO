package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Conta;
import model.Desconto;
import model.GereciarArquivo;
import model.Movimentacao;
import model.Receita;
import model.CategoriaReceita;
import model.CategoriaDesconto;

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
	Conta conta = new Conta();
	private NovaMovimentacao mv = new NovaMovimentacao(this);
	DateTimeFormatter formtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final JTextField edSaldo = new JTextField();
	private final JButton btOrdenar = new JButton("Ordenar tabela");
	JComboBox cbOrdem = new JComboBox();
	private final JTextField edDataSaldo = new JTextField();
	private final JButton btEditar = new JButton("Editar movimentaçao");
	private GereciarArquivo ga = new GereciarArquivo();
	JComboBox cbFiltrar = new JComboBox();
	

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
		edDataSaldo.setBounds(188, 46, 159, 20);
		edDataSaldo.setColumns(10);
		atualizarSaldo();
		edSaldo.setEditable(false);
		edSaldo.setBounds(54, 8, 462, 20);
		edSaldo.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbSaldo.setBounds(10, 11, 46, 14);
		contentPane.add(lbSaldo);
		
		JButton btNovaMovimentacao = new JButton("Cadastrar movimentação");
		btNovaMovimentacao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btNovaMovimentacao.setBounds(10, 145, 159, 23);
		contentPane.add(btNovaMovimentacao);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 179, 506, 179);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		table.setModel(modeloTabela = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Tipo", "Categoria", "Valor", "Data", "Saldo"
			}
		));
		
		contentPane.add(edSaldo);
		btOrdenar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btOrdenar.setBounds(10, 77, 159, 23);
		
		contentPane.add(btOrdenar);
		
		cbOrdem.setModel(new DefaultComboBoxModel(new String[] {"Data", "Valor", "Tipo"}));
		cbOrdem.setBounds(188, 77, 159, 22);
		contentPane.add(cbOrdem);
		cbOrdem.setSelectedItem(-1);
		
		JButton btSaldoData = new JButton("Consultar saldo por data");
		btSaldoData.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btSaldoData.setBounds(10, 43, 159, 23);
		contentPane.add(btSaldoData);
		
		contentPane.add(edDataSaldo);
		
		JButton btDeletar = new JButton("Deletar movimentação");
		btDeletar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deletarRegistro();
			}
		});
		btDeletar.setBounds(188, 145, 159, 23);
		contentPane.add(btDeletar);
		btEditar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btEditar.setBounds(357, 145, 159, 23);
		contentPane.add(btEditar);
		
		JButton btFiltrar = new JButton("Filtrar por");
		btFiltrar.setBounds(10, 111, 159, 23);
		contentPane.add(btFiltrar);
		
		cbFiltrar.setModel(new DefaultComboBoxModel(new String[] {"Nenhum", "Receita", "Desconto"}));
		cbFiltrar.setBounds(188, 110, 159, 22);
		contentPane.add(cbFiltrar);
		
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	cbFiltrar.setSelectedIndex(0);
		    	filtrarTabela();
		    	ga.escreverArquivo(conta.getMovimentacoes());
		    }
		});
		
		btFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrarTabela();
			}
		});
		
		btEditar.addActionListener(new ActionListener() {
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
		
		btOrdenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				organizarTabela(cbOrdem.getSelectedIndex());				
			}
		});
		
		btNovaMovimentacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mv.limparCampos();
				mv.setModo(0);
				mv.setVisible(true);
				mv.getCbTipo().setEnabled(true);
			}
		});
		
		inicializar();
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Atualiza a tabela na tela principal 
	 * <br>
	 * este metodo é chamado quando um novo registro é adicionado
	 */
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
				movimentacao.getDataMovimentacao().format(formtData),
				conta.calcularSaldoData(movimentacao.getDataMovimentacao())
			});
		}
		
	}
	/**
	 * Cadastra uma nova movimentação no ArrayList de receita, ou o de desconto, dependendo do tipo selecionado
	 * <br>
	 * Este metodo é chamado toda fez que o botão de "Cadastrar" é precionado
	 */
	public void cadastrarMovimentacao() {
		
		if (mv.getTipoInt() == -1 || mv.getCbCategoria().getSelectedIndex() == -1 || mv.getEdData() == null || mv.getEdValor() <= 0)
			return;
		
		if(mv.getTipoInt() == 0) {
			conta.novaReceita(Float.valueOf(mv.getEdValor()), mv.getEdData(), (CategoriaReceita) mv.getCbCategoria().getSelectedItem());
		} else if(mv.getTipoInt() == 1) {
			conta.novoDesconto(Float.valueOf(mv.getEdValor()), mv.getEdData(), (CategoriaDesconto) mv.getCbCategoria().getSelectedItem());
		}
		edSaldo.setText(String.valueOf(conta.calcularSaldoAtual()));
		filtrarTabela();
		JOptionPane.showMessageDialog(null, "Movimentação cadastrada!");
		this.setEnabled(true);
		mv.setVisible(false);
		mv.limparCampos();
	}
	/**
	 * Atualiza o campo saldo na tela principal baseado na data atual
	 */
	public void atualizarSaldo() {
		conta.calcularSaldoAtual();
		edSaldo.setText(String.valueOf(conta.getSaldo()));
	}
	/**
	 * Organiza a tabela na tela principal baseado no tipo de organização
	 * @param Tipo Tipo de organização que vai ser utilizado para ordenar a tabela
	 */
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
	/**
	 * Deleta o registro atualmente selecionado na tabela
	 * <br>
	 * Caso nenhum registro for selecionado, nada acontece
	 */
	public void deletarRegistro() {
		int selecionado = table.getSelectedRow();
		
		if (selecionado == -1) {
			JOptionPane.showMessageDialog(this, "Nenhum registro selecionado!");
			return;
		}

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
	/**
	 * Edita o registro atualmente selecinado na tabela quando clicar no botão "Editar" na tela de Edição de registro
	 * <br>
	 * Caso nenhum registro estiver selecionado, este metodo não será chamado
	 */
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
			((Receita) mov.get(selecionado)).setTipo((CategoriaReceita) mv.cbCategoria.getSelectedItem());
		else
			((Desconto) mov.get(selecionado)).setTipo((CategoriaDesconto) mv.cbCategoria.getSelectedItem());
		
		edSaldo.setText(String.valueOf(conta.calcularSaldoAtual()));
		atualizarTabela();
		filtrarTabela();
		JOptionPane.showMessageDialog(null, "Movimentação editada!");
		this.setEnabled(true);
		mv.setVisible(false);
		mv.limparCampos();	
	}
	/**
	 * Filtra a tabela por receita ou desconto
	 */
	public void filtrarTabela() {
		ArrayList<Movimentacao> mov = new ArrayList<Movimentacao>();
		switch(cbFiltrar.getSelectedIndex()) {
		case 0:
			mov.addAll(conta.getReceitas());
			mov.addAll(conta.getDescontos());
			conta.setMovimentacoes(mov);
			organizarTabela(0);
			cbOrdem.setSelectedIndex(0);
			break;
		case 1:
			mov.addAll(conta.getReceitas());
			conta.setMovimentacoes(mov);
			conta.setMovimentacoes(mov);
			organizarTabela(0);
			cbOrdem.setSelectedIndex(0);
			break;
		case 2:
			mov.addAll(conta.getDescontos());
			conta.setMovimentacoes(mov);
			conta.setMovimentacoes(mov);
			organizarTabela(0);
			cbOrdem.setSelectedIndex(0);
			break;
		}
	}
	/**
	 * Inicializa a tela, setando o saldo, e atualizando a tabela
	 */
	public void inicializar() {
		ga.abrirOuCriarArquivo();
		conta.setMovimentacoes(ga.lerArquivo());
		ArrayList<Desconto> d = new ArrayList<Desconto>();
		ArrayList<Receita> r = new ArrayList<Receita>();
		for (Movimentacao m : conta.getMovimentacoes()) {
			if (m.getClass() == Receita.class)
				r.add((Receita) m);
			else
				d.add((Desconto)m);
		}
		conta.setReceitas(r);
		conta.setDescontos(d);
		atualizarSaldo();
		atualizarTabela();
	}
}
