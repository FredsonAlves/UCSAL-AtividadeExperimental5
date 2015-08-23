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

import br.ucsal.projetoAE4.dao.CampusDao;
import br.ucsal.projetoAE4.dao.LaboratorioDao;
import br.ucsal.projetoAE4.modelo.Campus;
import br.ucsal.projetoAE4.modelo.Laboratorio;
import br.ucsal.projetoAE4.uteis.Uteis;

public class FormLaboratorio extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel painelCamposTexto = new JPanel();
	JPanel painelContainerCamposTexto = new JPanel();
	JPanel painelBotoes = new JPanel(new BorderLayout());
	JPanel subPainelBotoes = new JPanel();
	private JTextField txtNomeLaboratorio = new JTextField(20);
	private JComboBox<Campus> txtCampus = new JComboBox<Campus>();
	private JTextField txtQteComputadoresVertical = new JTextField(20);
	private JTextField txtQteComputadoresHorizontal = new JTextField(5);
	private JTextField txtFaixaRede = new JTextField(10);
	Laboratorio LaboratorioParaEdicao = null;
	private LaboratorioTableModel modelo;
	private int linhaSelecionada;

	public JButton getBotSalvar() {
		return botSalvar;
	}

	public void setBotOk(JButton botSalvar) {
		this.botSalvar = botSalvar;
	}

	JButton botCancelar = new JButton("Cancelar");
	JButton botSalvar = new JButton("Salvar");

	public FormLaboratorio(String title) {
		// super.setTitle(title);
		this(null, -1, title);
	}

	public FormLaboratorio(Laboratorio objLaboratorio, int linhaSel,
			String title) {
		// super(title, resizable, closable, maximizable, iconifiable);
		super.setTitle(title);
		this.setModal(true);

		if (objLaboratorio != null) {
			this.LaboratorioParaEdicao = objLaboratorio;
			txtNomeLaboratorio.setText(objLaboratorio.getNomeLaboratorio());
			// txtCampus.setText(objLaboratorio.getCampus().getIdCampus());
			Integer vert = new Integer(
					objLaboratorio.getQteComputadoresVertical());
			txtQteComputadoresVertical.setText(vert.toString());
			Integer Horiz = new Integer(
					objLaboratorio.getQteComputadoresHorizontal());
			txtQteComputadoresHorizontal.setText(Horiz.toString());
			txtFaixaRede.setText(objLaboratorio.getFaixaRedeIPV4());
			txtNomeLaboratorio.grabFocus();
			this.linhaSelecionada = linhaSel;
		}

		painelContainerCamposTexto.setLayout(new BoxLayout(
				painelContainerCamposTexto, BoxLayout.Y_AXIS));

		// instancia a classe CampusDao
		CampusDao lCamp = new CampusDao();
		// Cria Lista com os estado, originados da tabela estado
		Vector<Campus> vt = new Vector<Campus>(lCamp.getLista());
		// Ordena os estados por Sigla - A classe estado implementa Comparable
		Collections.sort(vt);

		// cria ComboBox para a escolha do estado...
		for (Campus itemVt : vt) {
			// se foi passado um objeto Laboratorio, verifica seta o objeto com
			// mesmo
			// nome de estado do objeto selecionado

			txtCampus.addItem(itemVt);
			if (this.LaboratorioParaEdicao != null) {
				if (this.LaboratorioParaEdicao.getCampus().getIdCampus() != null) {
					if (this.LaboratorioParaEdicao.getCampus().getIdCampus() == itemVt
							.getIdCampus()) {
						txtCampus.setSelectedItem(itemVt);
					}
				}
			}
		}

		Image image = Toolkit.getDefaultToolkit().getImage("tray2.png");

		// ImageIcon ico = new ImageIcon(image);
		// this.setIconImage(image);
		this.setIconImage(image);

		painelCamposTexto.setLayout(new GridLayout(8, 2, 0, 5));
		painelCamposTexto.setSize(200, 200);

		// Cria Labels
		JLabel labelNomeLaboratorio = new JLabel("Nome do Laboratorio: ");
		JLabel labelCampus = new JLabel("Selecione o Campus: ");
		JLabel labelQteCompVertical = new JLabel(
				"Qte. Computadores na Vertical: ");
		JLabel labelQteCompHorizontal = new JLabel(
				"Qte. Computadores na Horizontal: ");
		JLabel labelFaixaRede = new JLabel("Faixa/Endereço de Rede IPV4: ");

		JPanel fieldset = new JPanel();
		fieldset.setBorder(BorderFactory
				.createTitledBorder("Preencha todos os campos"));
		fieldset.setSize(400, 400);

		// adicionar Labels e campos de texto ao Panel
		painelCamposTexto.add(labelNomeLaboratorio);
		painelCamposTexto.add(txtNomeLaboratorio);
		painelCamposTexto.add(labelCampus);
		painelCamposTexto.add(txtCampus);
		painelCamposTexto.add(labelQteCompVertical);
		painelCamposTexto.add(txtQteComputadoresVertical);
		painelCamposTexto.add(labelQteCompHorizontal);
		painelCamposTexto.add(txtQteComputadoresHorizontal);
		painelCamposTexto.add(labelFaixaRede);
		painelCamposTexto.add(txtFaixaRede);

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
		// this.add(painelBotoes, BorderLayout.CENTER);

		botCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormLaboratorio.this.dispose();
			}
		});

		botSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtNomeLaboratorio.getText().equals("")) {
					br.ucsal.projetoAE4.uteis.Uteis.janelaErro(
							"Digite um nome de Laboratorio Válido",
							"Erro no nome de Laboratorio");
					txtNomeLaboratorio.grabFocus();
				} else if (!Uteis.ValidaCampo(txtQteComputadoresVertical)) {
					Uteis.janelaErro(
							"Digite uma quantidade de computadores na fila vertical válida",
							"Erro na quantidade Vertical");
					txtQteComputadoresVertical.grabFocus();
				} else if (!Uteis.ValidaCampo(txtQteComputadoresHorizontal)) {
					Uteis.janelaErro(
							"Digite uma quantidade de computadores na fila horizontal válida",
							"Erro na quantidade Horizontal");
					txtQteComputadoresHorizontal.grabFocus();
				} else if (!Uteis.isEnderecoRede(txtFaixaRede.getText())) {
					Uteis.janelaErro(
							"Digite uma Endereço de Rede válido. Ex. 192.168.1.0 - Tem que terminar com zero",
							"Erro na Faixa IP");
					txtFaixaRede.grabFocus();

				} else {
					Laboratorio laboratorio;
					if (LaboratorioParaEdicao != null) {
						laboratorio = LaboratorioParaEdicao;
					} else {
						laboratorio = new Laboratorio();
					}

					laboratorio.setNomeLaboratorio(txtNomeLaboratorio.getText());
					laboratorio.setQteComputadoresVertical(Integer
							.parseInt(txtQteComputadoresVertical.getText()));
					laboratorio.setQteComputadoresHorizontal(Integer
							.parseInt(txtQteComputadoresHorizontal.getText()));
					laboratorio.setFaixaRedeIPV4(txtFaixaRede.getText());
					// retorna a sigla do objeto estado selecionado na ComboBox
					laboratorio.setCampus(((Campus) txtCampus.getSelectedItem()));
					// utiliza o DAO para adiconar o objeto na base
					LaboratorioDao cpDao = new LaboratorioDao();

					if (LaboratorioParaEdicao == null) {

						cpDao.adiciona(laboratorio);

						txtNomeLaboratorio.setText("");
						txtQteComputadoresVertical.setText("");
						txtQteComputadoresHorizontal.setText("");
						txtFaixaRede.setText("");
						txtCampus.setSelectedIndex(0);
						txtNomeLaboratorio.grabFocus();

						modelo.addLaboratorio(cpDao.UltimoRegistroInserido());
						Uteis.janelaInfo(
								"Laboratorio incluído com com sucesso!",
								"Sucesso");
					} else {
						cpDao.altera(laboratorio);
						modelo.updateLaboratorio(
								FormLaboratorio.this.linhaSelecionada,
								laboratorio);
						Uteis.janelaInfo(
								"Laboratorio atualizado com com sucesso!",
								"Sucesso");
					}

					// FormBuscaLaboratorio.criaModelo(modelo);
				}

			}
		});

		// this.botOk.setFocusable(isActive());
		// this.setDefaultButton(botOk);
		// coloca o botão ok como padrão quando o usuário digitar a senha...
		this.getRootPane().setDefaultButton(botSalvar);

		// seta operação padrão para fechamento, retira o form da memória, sem
		// fechar o sistema
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// this.setVisible(true);

		// this.pack();
		this.setSize(400, 340);
		// Border bd = new BevelBorder(2);

		// Border loweredetched =
		// BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		// this.setBorder(loweredetched);

		// this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// ((BasicInternalFrameUI)frame.getUI()).getNorthPane().setPreferredSize(
		// new Dimension(0,0) );

	}

	public LaboratorioTableModel getModelo() {
		return modelo;
	}

	public void setModelo(LaboratorioTableModel model) {
		this.modelo = model;
	}

}
