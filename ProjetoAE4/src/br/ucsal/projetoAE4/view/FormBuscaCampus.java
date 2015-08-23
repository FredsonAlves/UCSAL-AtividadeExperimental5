package br.ucsal.projetoAE4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.ucsal.projetoAE4.dao.CampusDao;
import br.ucsal.projetoAE4.main.Mdi;
import br.ucsal.projetoAE4.modelo.Campus;
import br.ucsal.projetoAE4.uteis.Uteis;

@SuppressWarnings("serial")
public class FormBuscaCampus extends JInternalFrame {
	JPanel painelTable = new JPanel();
	JPanel painelContainer = new JPanel();
	JPanel painelBotoes = new JPanel(new BorderLayout());
	JPanel painelFiltro = new JPanel();

	JPanel subPainelFiltro1 = new JPanel();
	JPanel subPainelFiltro2 = new JPanel();
	JLabel rotFiltroNomeCampus = new JLabel("Nome do Campus: ");
	JTextField txtFiltroNomeCampus = new JTextField(25);
	JLabel rotFiltroCidadeCampus = new JLabel("Cidade: ");
	JLabel rotFiltroBairroCampus = new JLabel("Bairro: ");
	JTextField txtFiltroCidadeCampus = new JTextField(10);
	JTextField txtFiltroBairroCampus = new JTextField(10);
	final CampusDao dao = new CampusDao();
	private CampusTableModel model;
	private JTable datagrid;

	public FormBuscaCampus(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		painelFiltro.setLayout(new BoxLayout(painelFiltro, BoxLayout.Y_AXIS));
		subPainelFiltro1.setLayout(new BoxLayout(subPainelFiltro1,
				BoxLayout.X_AXIS));
		painelContainer.setLayout(new BoxLayout(painelContainer,
				BoxLayout.Y_AXIS));

		Image image = Toolkit.getDefaultToolkit().getImage("tray2.png");

		ImageIcon ico = new ImageIcon(image);
		// this.setIconImage(image);
		this.setFrameIcon(ico);
		/*
		 * painelFiltro.setPreferredSize(new Dimension(850, 80));
		 * painelTable.setPreferredSize(new Dimension(850, 100));
		 * painelBotoes.setPreferredSize(new Dimension(850, 100));
		 */

		// painelFiltro.setSize(new Dimension(400, 300));
		// painelFiltro.setSize(new Dimension(400, 200));

		JLabel Mensagem = new JLabel(
				"Caso deseje filtrar os registros, preencha os campos desejados e clique em Filtrar...");

		Mensagem.setForeground(Color.BLUE);
		Font font = new Font("SansSerif", Font.BOLD, 15);
		Mensagem.setFont(font);
		subPainelFiltro1.add(Mensagem);

		subPainelFiltro2.add(rotFiltroNomeCampus);
		subPainelFiltro2.add(txtFiltroNomeCampus);
		subPainelFiltro2.add(rotFiltroBairroCampus);
		subPainelFiltro2.add(txtFiltroBairroCampus);
		subPainelFiltro2.add(rotFiltroCidadeCampus);
		subPainelFiltro2.add(txtFiltroCidadeCampus);
		JButton btFiltrar = new JButton("Filtrar");

		btFiltrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (txtFiltroNomeCampus.getText().equals("")
						&& txtFiltroCidadeCampus.getText().equals("")
						&& txtFiltroBairroCampus.getText().equals("")
						&& model.getDadosFiltrados() == false) {
					Uteis.janelaErro(
							"Preencha pelo menos uma opção para filtrar",
							"Filtro");
				} else if (txtFiltroNomeCampus.getText().equals("")
						&& txtFiltroCidadeCampus.getText().equals("")
						&& txtFiltroBairroCampus.getText().equals("")
						&& model.getDadosFiltrados() == true) {
					List<Campus> novaLista = dao.getLista();
					model.updateModelo(novaLista);
					model.setDadosFiltrados(false);
				} else {
					Campus c = new Campus();
					c.setNomeCampus(txtFiltroNomeCampus.getText());
					c.setCidade(txtFiltroCidadeCampus.getText());
					c.setBairro(txtFiltroBairroCampus.getText());
					List<Campus> novaLista = dao.getListaFiltrada(c);
					model.updateModelo(novaLista);
					model.setDadosFiltrados(true);
				}
			}
		});

		subPainelFiltro2.add(btFiltrar);

		painelFiltro.add(subPainelFiltro1);
		painelFiltro.add(subPainelFiltro2);

		/** Criando o data grid para exibir os registros do banco de dados **/
		// cria o modelo utiliando o default e sobrepõe método para tornar as
		// colunas não editáveis
		datagrid = new JTable(model);
		criaModelo();
		// utiliza o método estátivo criaModelo para criar o modelo em si,
		// buscando os dados da base via DAO

		// remove coluna ID do datagrid, porém a mesma permanece no modelo
		// datagrid.removeColumn(datagrid.getColumn("ID"));
		/** Fim da criação do datagrid **/

		datagrid.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					editarRegistro();
				}
			}

		});

		JScrollPane scrol = new JScrollPane(datagrid);
		datagrid.setLocation(5, 80);
		datagrid.setPreferredScrollableViewportSize(new Dimension(400, 101));
		datagrid.setFillsViewportHeight(true);

		// adiciona o datagrid ao painel da tabela (central)
		painelTable.add(scrol);

		JPanel subPainelBotoes = new JPanel();

		JButton botExcluir = new JButton("Excluir");
		botExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] linhas;
				linhas = datagrid.getSelectedRows();

				if (linhas.length == 0) {
					Uteis.janelaErro(
							"Escolha pelo menos um campus na lista para excluir...",
							"Não seleciou Campus");
				} else {
					try {
						for (int linha : linhas) {
							Campus c = model.getCampus(linha);
							dao.remove(c);
						}
						model.removeCampus(linhas);
					} catch (RuntimeException rtError) {
						rtError.printStackTrace();
						Uteis.janelaErro(
								"Ocorreu um erro na exclusão do Campus.",
								"Exclusão não efetuada");
					}
				}

			}
		});

		// adiciona evento em resposta ao clique no botão Editar
		JButton botEditar = new JButton("Editar");

		botEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editarRegistro();
			}
		});

		JButton botNovo = new JButton("Novo Registro");
		// adiciona evento em resposta ao clique no botão NovoRegistro
		botNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormCampus frmCampusCad = new FormCampus(
						"Cadastramento de Campus");

				// opções para centralização do formulário no MDI
				Mdi Pai = (Mdi) FormBuscaCampus.this.getParent().getParent()
						.getParent().getParent().getParent();
				frmCampusCad.setLocation(
						(Pai.getWidth() / 2 - frmCampusCad.getWidth() / 2),
						(Pai.getHeight() / 2 - frmCampusCad.getHeight() / 2));

				frmCampusCad.setModelo(model);
				frmCampusCad.setVisible(true);
			}
		});

		/*
		 * JPanel painelEspacador = new JPanel();
		 * painelEspacador.setPreferredSize(new Dimension(0,20));
		 */

		subPainelBotoes.add(botExcluir);
		subPainelBotoes.add(botEditar);
		subPainelBotoes.add(botNovo);

		// painelBotoes.add(painelEspacador, BorderLayout.NORTH);
		painelBotoes.add(subPainelBotoes, BorderLayout.EAST);

		// adiciona espaço vertival entre um painel e outro
		painelContainer.add(Box.createVerticalStrut(10));
		painelContainer.add(painelFiltro);
		// adiciona espaço vertival entre um painel e outro
		painelContainer.add(Box.createVerticalStrut(20));
		painelContainer.add(scrol);
		// adiciona espaço vertival entre um painel e outro
		painelContainer.add(Box.createVerticalStrut(10));
		painelContainer.add(painelBotoes);
		// adiciona espaço vertival entre um painel e outro
		painelContainer.add(Box.createVerticalStrut(10));

		this.add(painelContainer, BorderLayout.CENTER);

		this.getRootPane().setDefaultButton(botNovo);
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	private void editarRegistro() {
		FormCampus frmCampusCad;
		// CampusDao daoC = new CampusDao();
		int[] linhas = datagrid.getSelectedRows();

		if (linhas.length > 1) {
			Uteis.janelaErro("Escolha apenas um campus na lista para editar",
					"Seleções Múltipas");
		} else if (datagrid.getSelectedRow() == -1) {
			Uteis.janelaErro("Escolha um campus na lista",
					"Não selecionou Campus");
		} else {
			Campus campusObtido = dao.find((long) model.getValueAt(
					datagrid.getSelectedRow(), 4));
			frmCampusCad = new FormCampus(campusObtido,
					datagrid.getSelectedRow(), "Cadastramento de Campus");

			// opções para centralização do formulário no MDI
			Mdi Pai = (Mdi) FormBuscaCampus.this.getParent().getParent()
					.getParent().getParent().getParent();
			frmCampusCad.setLocation(
					(Pai.getWidth() / 2 - frmCampusCad.getWidth() / 2),
					(Pai.getHeight() / 2 - frmCampusCad.getHeight() / 2));

			// seta o modelo no atributo interno do formulário FormCampus
			frmCampusCad.setModelo(model);
			// torna FormCampus visível
			frmCampusCad.setVisible(true);
		}
	}

	public void criaModelo() {
		CampusDao daoc = new CampusDao();
		List<Campus> ListaCampus = daoc.getLista();
		/*
		 * for (Campus c : daoc.getLista()) { model.addCampus((new Object[] {
		 * c.getNomeCampus(), c.getLogradouro(), c.getCidade(), c.getEstado(),
		 * c.getIdCampus() }); }
		 */
		model = new CampusTableModel(ListaCampus);
		datagrid.setModel(model);
	}

}
