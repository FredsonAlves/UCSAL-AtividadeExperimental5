package br.ucsal.projetoAE4.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.ucsal.projetoAE4.dao.CampusDao;
import br.ucsal.projetoAE4.dao.EstadoDao;
import br.ucsal.projetoAE4.modelo.Campus;
import br.ucsal.projetoAE4.modelo.Estado;
import br.ucsal.projetoAE4.uteis.Uteis;

public class FormCampus extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel painelCamposTexto = new JPanel();
	JPanel painelContainerCamposTexto = new JPanel();
	JPanel painelBotoes = new JPanel(new BorderLayout());
	JPanel subPainelBotoes = new JPanel();
	private JTextField txtNomeCampus = new JTextField(20);
	private JTextField txtLogradouro = new JTextField(20);
	private JTextField txtComplemento = new JTextField(20);
	private JTextField txtNumero = new JTextField(5);
	private JTextField txtBairro = new JTextField(10);
	private JTextField txtCidade = new JTextField(10);
	private JComboBox<Estado> txtEstado = new JComboBox<Estado>();
	private JFormattedTextField txtCep;
	Campus CampusParaEdicao = null;
	private CampusTableModel modelo;
	private int linhaSelecionada;
	
	public JButton getBotSalvar() {
		return botSalvar;
	}

	public void setBotOk(JButton botSalvar) {
		this.botSalvar = botSalvar;
	}

	JButton botCancelar = new JButton("Cancelar");
	JButton botSalvar = new JButton("Salvar");

	public FormCampus(String title) {
		// super.setTitle(title);
		this(null, -1, title);
	}

	public FormCampus(Campus objCampus, int linhaSel, String title) {
		// super(title, resizable, closable, maximizable, iconifiable);
		super.setTitle(title);
		this.setModal(true);

		if (objCampus != null) {
			this.CampusParaEdicao = objCampus;
			txtNomeCampus.setText(objCampus.getNomeCampus());
			txtLogradouro.setText(objCampus.getLogradouro());
			txtComplemento.setText(objCampus.getComplemento());
			txtNumero.setText(objCampus.getNumero());
			txtBairro.setText(objCampus.getBairro());
			txtCidade.setText(objCampus.getCidade());
			txtNomeCampus.grabFocus();
			this.linhaSelecionada = linhaSel;
		}

		painelContainerCamposTexto.setLayout(new BoxLayout(
				painelContainerCamposTexto, BoxLayout.Y_AXIS));

		// instancia a classe EstadoDao
		EstadoDao uf = new EstadoDao();
		// Cria Lista com os estado, originados da tabela estado
		Vector<Estado> vt = new Vector<Estado>(uf.getLista());
		// Ordena os estados por Sigla - A classe estado implementa Comparable
		Collections.sort(vt);

		// cria ComboBox para a escolha do estado...
		for (Estado itemVt : vt) {
			// se foi passado um objeto Campus, verifica seta o objeto com mesmo
			// nome de estado do objeto selecionado

			txtEstado.addItem(itemVt);
			if (this.CampusParaEdicao != null) {
				if (this.CampusParaEdicao.getEstado() != null) {
					if (this.CampusParaEdicao.getEstado().equals(itemVt.getSigla())) {
						txtEstado.setSelectedItem(itemVt);
					}
				}
			}
		}

		Image image = Toolkit.getDefaultToolkit().getImage("tray2.png");

		// ImageIcon ico = new ImageIcon(image);
		// this.setIconImage(image);
		this.setIconImage(image);

		// termina de formar a máscara para o CEP
		MaskFormatter maskCep = null;
		try {
			maskCep = new MaskFormatter("#####-###");
			//maskCep.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		txtCep = new JFormattedTextField(maskCep);
		txtCep.setToolTipText("Digite o CEP");
		
		if (CampusParaEdicao != null) {
			txtCep.setText(objCampus.getCep().replace("-", ""));
		}

		painelCamposTexto.setLayout(new GridLayout(8, 2, 0, 5));
		painelCamposTexto.setSize(200, 200);

		// Cria Labels
		JLabel labelNomeCampus = new JLabel("Nome do Campus: ");
		JLabel labelLogradouro = new JLabel("Logradouro: ");
		JLabel labelComplemento = new JLabel("Complemento: ");
		JLabel labelNumero = new JLabel("Numero: ");
		JLabel labelBairro = new JLabel("Bairro: ");
		JLabel labelCidade = new JLabel("Cidade: ");
		JLabel labelEstado = new JLabel("Estado: ");
		JLabel labelCep = new JLabel("CEP: ");

		JPanel fieldset = new JPanel();
		fieldset.setBorder(BorderFactory
				.createTitledBorder("Preencha todos os campos"));
		fieldset.setSize(400, 400);

		// adicionar Labels e campos de texto ao Panel
		painelCamposTexto.add(labelNomeCampus);
		painelCamposTexto.add(txtNomeCampus);
		painelCamposTexto.add(labelLogradouro);
		painelCamposTexto.add(txtLogradouro);
		painelCamposTexto.add(labelComplemento);
		painelCamposTexto.add(txtComplemento);
		painelCamposTexto.add(labelNumero);
		painelCamposTexto.add(txtNumero);
		painelCamposTexto.add(labelBairro);
		painelCamposTexto.add(txtBairro);
		painelCamposTexto.add(labelCidade);
		painelCamposTexto.add(txtCidade);
		painelCamposTexto.add(labelEstado);
		painelCamposTexto.add(txtEstado);
		painelCamposTexto.add(labelCep);
		painelCamposTexto.add(txtCep);

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
				FormCampus.this.dispose();
			}
		});

		botSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtNomeCampus.getText().equals("")) {
					br.ucsal.projetoAE4.uteis.Uteis.janelaErro(
							"Digite um nome de Campus Válido",
							"Erro no nome de Campus");
					txtNomeCampus.grabFocus();
				//} else if (!Uteis.ValidaCampo(txtNumero)) {
				} else if (txtNumero.getText().equals("")) {
					Uteis.janelaErro("Digite um número para o Campus",
							"Erro no numero");
					txtNumero.grabFocus();
				} else {

					Campus campus;
					if (CampusParaEdicao != null) {
						campus = CampusParaEdicao;
					} else {
						campus = new Campus();
					}

					campus.setNomeCampus(txtNomeCampus.getText());
					campus.setLogradouro(txtLogradouro.getText());
					campus.setNumero(txtNumero.getText());
					campus.setComplemento(txtComplemento.getText());
					campus.setBairro(txtBairro.getText());
					campus.setCidade(txtCidade.getText());
					// retorna a sigla do objeto estado selecionado na ComboBox
					campus.setEstado(((Estado) txtEstado.getSelectedItem())
							.getSigla());
					campus.setCep(txtCep.getText());
					// utiliza o DAO para adiconar o objeto na base
					CampusDao cpDao = new CampusDao();
					
					if (CampusParaEdicao == null) {
					
					cpDao.adiciona(campus);

					txtNomeCampus.setText("");
					txtLogradouro.setText("");
					txtComplemento.setText("");
					txtNumero.setText("");
					txtBairro.setText("");
					txtCidade.setText("");
					txtEstado.setSelectedIndex(0);
					txtCep.setText("");
					txtNomeCampus.grabFocus();
					
					modelo.addCampus(cpDao.UltimoRegistroInserido());
					Uteis.janelaInfo("Campus incluído com com sucesso!",
							"Sucesso");
					} else {
						cpDao.altera(campus);
						modelo.updateCampus(FormCampus.this.linhaSelecionada, campus);
						Uteis.janelaInfo("Campus atualizado com com sucesso!",
								"Sucesso");
					}
					
					//FormBuscaCampus.criaModelo(modelo);
				}

			}
		});

		// this.botOk.setFocusable(isActive());
		// this.setDefaultButton(botOk);
		// coloca o botão ok como padrão quando o usuário digitar a senha...
		this.getRootPane().setDefaultButton(botSalvar);

		//seta operação padrão para fechamento, retira o form da memória, sem fechar o sistema
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

	public CampusTableModel getModelo() {
		return modelo;
	}

	public void setModelo(CampusTableModel model) {
		this.modelo = model;
	}

}
