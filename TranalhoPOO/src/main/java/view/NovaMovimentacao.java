package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Movimentacao;
import model.Receita;
import model.TipoReceita;
import model.TiposDescontos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NovaMovimentacao extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pnCadastro;
	private JTextField edValor;
	private JTextField edData;
	JComboBox cbCategoria = new JComboBox();
	

	JButton btCadastrar = new JButton("Cadastrar");
	JComboBox cbTipo = new JComboBox();
	private Principal p;
	DateTimeFormatter formtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private int modo = 0;
	
	public NovaMovimentacao(Principal p) {
		this();
		this.p = p;
	}

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NovaMovimentacao frame = new NovaMovimentacao();
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
	public NovaMovimentacao() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pnCadastro = new JPanel();
		pnCadastro.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnCadastro);
		pnCadastro.setLayout(null);
		
		JLabel lbNovaMovimentacao = new JLabel("Cadastrar nova movimentação");
		lbNovaMovimentacao.setBounds(132, 11, 152, 14);
		pnCadastro.add(lbNovaMovimentacao);
		
		cbTipo.setModel(new DefaultComboBoxModel(new String[] {"Receita", "Desconto"}));
		cbTipo.setSelectedIndex(-1);
		cbTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbTipo.getSelectedIndex() == 0){
					cbCategoria.setEnabled(true);
					edValor.setEnabled(true);
					edData.setEnabled(true);
					cbCategoria.setModel(new DefaultComboBoxModel<TipoReceita>(TipoReceita.values()));
				} else if (cbTipo.getSelectedIndex() == 1) {
					cbCategoria.setEnabled(true);
					edValor.setEnabled(true);
					edData.setEnabled(true);
					cbCategoria.setModel(new DefaultComboBoxModel<TiposDescontos>(TiposDescontos.values()));
				} else {
					cbCategoria.setEnabled(false);
					edValor.setEnabled(false);
					edData.setEnabled(false);
				}
				cbCategoria.setSelectedIndex(-1);
			}
		});
		cbTipo.setBounds(132, 38, 163, 22);
		pnCadastro.add(cbTipo);
		
		edValor = new JTextField();
		edValor.setEnabled(false);
		edValor.setBounds(132, 130, 163, 20);
		pnCadastro.add(edValor);
		edValor.setColumns(10);
		cbCategoria.setEnabled(false);
		
		cbCategoria.setBounds(132, 66, 163, 22);
		pnCadastro.add(cbCategoria);
		
		edData = new JTextField();
		edData.setEnabled(false);
		edData.setBounds(132, 99, 163, 20);
		pnCadastro.add(edData);
		edData.setColumns(10);
		
		JLabel lbTipo = new JLabel("Tipo");
		lbTipo.setBounds(43, 42, 46, 14);
		pnCadastro.add(lbTipo);
		
		JLabel lbCategoria = new JLabel("Categoria");
		lbCategoria.setBounds(43, 70, 56, 14);
		pnCadastro.add(lbCategoria);
		
		JLabel lbData = new JLabel("Data");
		lbData.setBounds(43, 102, 46, 14);
		pnCadastro.add(lbData);
		
		JLabel lbValor = new JLabel("Valor");
		lbValor.setBounds(43, 133, 46, 14);
		pnCadastro.add(lbValor);
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modo == 0)
					p.cadastrarMovimentacao();
				else 
					p.editarRegistro();
			}
		});
		
		btCadastrar.setBounds(132, 169, 163, 23);
		pnCadastro.add(btCadastrar);

	}
	
	public int getTipoInt() {
		if (cbTipo.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Selecione um tipo!");
		}
		return cbTipo.getSelectedIndex();
	}

	public JComboBox getCbTipo() {
		return cbTipo;
	}

	public void setCbTipo(JComboBox cbTipo) {
		this.cbTipo = cbTipo;
	}

	public JComboBox getCbCategoria() {
		if (cbCategoria.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Selecione uma categoria!");
		}
		return cbCategoria;
	}
	
	public LocalDate getEdData() {
		try {
			return LocalDate.parse(edData.getText(), formtData);
		} catch (DateTimeParseException e){
			JOptionPane.showMessageDialog(null, "Formato da data digitado incorretamente!\nuse o formato \"dd/mm/aaaa\"");
		}
		return null;
	}
	
	public float getEdValor() {
		try {
			return Float.valueOf(edValor.getText());
		} catch (IllegalArgumentException e){
			JOptionPane.showMessageDialog(null, "Valor inserido incorretamente!");
		}
		return 0;
	}

	public JButton getBtCadastrar() {
		return btCadastrar;
	}
	
	public void setModo(int modo) {
		this.modo = modo;
	}

	public void limparCampos() {
		cbTipo.setSelectedIndex(-1);
		cbCategoria.removeAll();
		edData.setText(null);
		edValor.setText(null);
	}
	
	public void preencherCampos(Movimentacao m) {
		
		if (m.getClass() == Receita.class)
			cbTipo.setSelectedIndex(0);
		else
			cbTipo.setSelectedIndex(1);
		
		cbCategoria.setSelectedItem(m.getTipo());
		edData.setText(String.valueOf(m.getDataMovimentacao().format(formtData)));
		edValor.setText(String.valueOf(m.getValor()));
	}

}
