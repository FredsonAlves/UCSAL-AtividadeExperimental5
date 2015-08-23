/**
 * 
 */
package br.ucsal.projetoAE4.view;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ucsal.projetoAE4.dao.ComputadorDao;
import br.ucsal.projetoAE4.dao.LaboratorioDao;
import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.modelo.Laboratorio;
import br.ucsal.projetoAE4.uteis.Uteis;

public class FormComputador extends JDialog {
	private static final long serialVersionUID = 1L;
	JPanel painelCamposTexto = new JPanel();
	JPanel painelContainerCamposTexto = new JPanel();
	JPanel painelBotoes = new JPanel(new BorderLayout());
	JPanel subPainelBotoes = new JPanel();
	private JTextField txtNomeMaquina = new JTextField(20);
	private JComboBox<Laboratorio> txtLaboratorio = new JComboBox<Laboratorio>();
	private JTextField txtPosicaoVertical = new JTextField(5);
	private JTextField txtPosicaoHorizontal = new JTextField(5);
	JButton botCancelar = new JButton("Cancelar");
	JButton botSalvar = new JButton("Salvar");
	private ComputadorTableModel modelo;
	Computador ComputadorParaEdicao = null;
	private int linhaSelecionada;
	
	public JButton getBotSalvar() {
		return botSalvar;
	}

	public void setBotOk(JButton botSalvar) {
		this.botSalvar = botSalvar;
	}

	public FormComputador(String title) {
		// super.setTitle(title);
		this(null, -1, title);
	}

	public FormComputador(Computador objComputador, int linhaSel, String title) {
		// super(title, resizable, closable, maximizable, iconifiable);
		super.setTitle(title);
		this.setModal(true);

		if (objComputador != null) {
			this.ComputadorParaEdicao = objComputador;
			txtNomeMaquina.setText(objComputador.getNomeComputador());
			Integer vert = new Integer(objComputador.getPosicaoVertical());
			txtPosicaoVertical.setText(vert.toString());
			Integer horiz = new Integer(objComputador.getPosicaoHorizontal());
			txtPosicaoHorizontal.setText(horiz.toString());
			this.linhaSelecionada = linhaSel;
		}
		painelContainerCamposTexto.setLayout(new BoxLayout(
				painelContainerCamposTexto, BoxLayout.Y_AXIS));
		
		// instancia a classe CampusDao
		LaboratorioDao lLab = new LaboratorioDao();
		// Cria Lista com os estado, originados da tabela estado
		Vector<Laboratorio> vt = new Vector<Laboratorio>(lLab.getLista());
		// Ordena os estados por Sigla - A classe estado implementa Comparable
		Collections.sort(vt);

		// cria ComboBox para a escolha do estado...
		for (Laboratorio itemVt : vt) {
			// se foi passado um objeto Laboratorio, verifica seta o objeto com mesmo
			// nome de estado do objeto selecionado

			txtLaboratorio.addItem(itemVt);
			if (this.ComputadorParaEdicao != null) {
				if (this.ComputadorParaEdicao.getLaboratorio().getIdLaboratorio() != 0) {
					if (this.ComputadorParaEdicao.getLaboratorio().getIdLaboratorio() == itemVt.getIdLaboratorio()) {
						txtLaboratorio.setSelectedItem(itemVt);
					}
				}
			}
		}
		
		Image image = Toolkit.getDefaultToolkit().getImage("tray2.png");
		this.setIconImage(image);
		painelCamposTexto.setLayout(new GridLayout(8, 2, 0, 5));
		painelCamposTexto.setSize(200, 200);
		JLabel labelNomeMaquina = new JLabel("Nome: ");
		JLabel labelLaboratorio = new JLabel("Laboratorio: ");
		JLabel labelPosicaoVertical = new JLabel("Pos. Vertical: ");
		JLabel labelPosicaoHorizontal = new JLabel("Pos. Horizontal: ");

		JPanel fieldset = new JPanel();
		fieldset.setBorder(BorderFactory
				.createTitledBorder("Preencha todos os campos"));
		fieldset.setSize(400, 300);


		painelCamposTexto.add(labelNomeMaquina);
		painelCamposTexto.add(txtNomeMaquina);
		painelCamposTexto.add(labelLaboratorio);
		painelCamposTexto.add(txtLaboratorio);
		painelCamposTexto.add(labelPosicaoVertical);
		painelCamposTexto.add(txtPosicaoVertical);
		painelCamposTexto.add(labelPosicaoHorizontal);
		painelCamposTexto.add(txtPosicaoHorizontal);
		subPainelBotoes.add(botCancelar);
		subPainelBotoes.add(botSalvar);
		painelBotoes.add(subPainelBotoes, BorderLayout.EAST);
		// adiciona espaço vertical entre os painéis
		painelContainerCamposTexto.add(Box.createVerticalStrut(10));
		painelContainerCamposTexto.add(painelCamposTexto);
		// adiciona espaço vertical entre os painéis
		painelContainerCamposTexto.add(Box.createVerticalStrut(20));
		painelContainerCamposTexto.add(painelBotoes);

		fieldset.add(painelContainerCamposTexto);
		this.add(fieldset, BorderLayout.CENTER);

		botCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormComputador.this.dispose();
			}
		});

		botSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtNomeMaquina.getText().equals("")) {
					br.ucsal.projetoAE4.uteis.Uteis.janelaErro("Digite um nome válido de computador");
					txtNomeMaquina.grabFocus();
				} else if (txtLaboratorio.getSelectedIndex() == 0) {
					br.ucsal.projetoAE4.uteis.Uteis.janelaErro("Escolha um Laboratorio");
				} else if (!Uteis.ValidaCampo(txtPosicaoHorizontal)) {
						br.ucsal.projetoAE4.uteis.Uteis.janelaErro("Informe uma posição Horizontal Válida");
						txtPosicaoHorizontal.grabFocus();
				} else if (!Uteis.ValidaCampo(txtPosicaoVertical)) {
							br.ucsal.projetoAE4.uteis.Uteis.janelaErro("Informe uma posição Vertical Válida");
							txtPosicaoVertical.grabFocus();
				} else {
					Computador computador;
					if (ComputadorParaEdicao != null) {
						computador = ComputadorParaEdicao;
					} else {
						computador = new Computador();
					}

					computador.setNomeComputador(txtNomeMaquina.getText());
					computador.setLaboratorio(((Laboratorio) txtLaboratorio.getSelectedItem()));
					computador.setPosicaoVertical(Integer.parseInt(txtPosicaoVertical.getText()));
					computador.setPosicaoHorizontal(Integer.parseInt(txtPosicaoHorizontal.getText()));

					ComputadorDao cmpDao = new ComputadorDao();
					if (ComputadorParaEdicao == null) {
						
						cmpDao.adiciona(computador);

						txtNomeMaquina.setText("");
						txtLaboratorio.setSelectedIndex(0);						
						txtPosicaoVertical.setText("");
						txtPosicaoHorizontal.setText("");
												
						modelo.addComputador(cmpDao.UltimoRegistroInserido());
						Uteis.janelaInfo("Computador incluído com com sucesso!",
								"Sucesso");
						} else {
							cmpDao.altera(computador);
							modelo.updateComputador(FormComputador.this.linhaSelecionada, computador);
							Uteis.janelaInfo("Computador atualizado com com sucesso!",
									"Sucesso");
						}
						
				}

			}
		});

	

	this.getRootPane().setDefaultButton(botSalvar);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setSize(400, 340);
	
	}
	
	public ComputadorTableModel getModelo() {
		return modelo;
	}

	public void setModelo(ComputadorTableModel model) {
		this.modelo = model;
	}

}
