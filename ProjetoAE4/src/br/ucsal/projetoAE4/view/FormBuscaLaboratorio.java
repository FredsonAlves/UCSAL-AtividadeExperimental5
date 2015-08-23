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
import br.ucsal.projetoAE4.dao.LaboratorioDao;
import br.ucsal.projetoAE4.main.Mdi;
import br.ucsal.projetoAE4.modelo.Campus;
import br.ucsal.projetoAE4.modelo.Laboratorio;
import br.ucsal.projetoAE4.uteis.Uteis;

@SuppressWarnings("serial")
public class FormBuscaLaboratorio extends JInternalFrame {
	JPanel painelTable = new JPanel();
	JPanel painelContainer = new JPanel();
	JPanel painelBotoes = new JPanel(new BorderLayout());
	JPanel painelFiltro = new JPanel();

	JPanel subPainelFiltro1 = new JPanel();
	JPanel subPainelFiltro2 = new JPanel();
	JLabel rotFiltroNomeLaboratorio = new JLabel("Nome do Laboratorio: ");
	JLabel rotFiltroCampus = new JLabel("Nome do Campus: ");
	JTextField txtFiltroNomeLaboratorio = new JTextField(25);
	JTextField txtFiltroCampus = new JTextField(10);
	final LaboratorioDao dao = new LaboratorioDao();
	private LaboratorioTableModel model;
	private JTable datagrid;

	public FormBuscaLaboratorio(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
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

		JLabel Mensagem = new JLabel(
				"Caso deseje filtrar os registros, preencha os campos desejados e clique em Filtrar...");

		Mensagem.setForeground(Color.BLUE);
		Font font = new Font("SansSerif", Font.BOLD, 15);
		Mensagem.setFont(font);
		subPainelFiltro1.add(Mensagem);

		subPainelFiltro2.add(rotFiltroNomeLaboratorio);
		subPainelFiltro2.add(txtFiltroNomeLaboratorio);
		subPainelFiltro2.add(rotFiltroCampus);
		subPainelFiltro2.add(txtFiltroCampus);

		JButton btFiltrar = new JButton("Filtrar");

		btFiltrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (txtFiltroNomeLaboratorio.getText().equals("")
						&& txtFiltroCampus.getText().equals("")
						&& model.getDadosFiltrados() == false) {
					Uteis.janelaErro(
							"Preencha pelo menos uma opção para filtrar",
							"Filtro");
				} else if (txtFiltroNomeLaboratorio.getText().equals("")
						&& txtFiltroCampus.getText().equals("")
						&& model.getDadosFiltrados() == true) {
					List<Laboratorio> novaLista = dao.getLista();
					model.updateModelo(novaLista);
					model.setDadosFiltrados(false);
				} else {
					Laboratorio c = new Laboratorio();
					c.setNomeLaboratorio(txtFiltroNomeLaboratorio.getText());
					CampusDao campusDao = new CampusDao();
					Campus camp = campusDao.findByName(txtFiltroCampus
							.getText());
					c.setCampus(camp);
					List<Laboratorio> novaLista = dao.getListaFiltrada(c);
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
							"Escolha pelo menos um laboratório na lista para excluir...",
							"Não seleciou Laboratório");
				} else {
					try {
						for (int linha : linhas) {
							Laboratorio c = model.getLaboratorio(linha);
							dao.remove(c);
						}
						model.removeLaboratorio(linhas);
					} catch (RuntimeException rtError) {
						rtError.printStackTrace();
						Uteis.janelaErro(
								"Ocorreu um erro na exclusão do registro.",
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
				FormLaboratorio frmLaboratorioCad = new FormLaboratorio(
						"Cadastramento de Laboratorio");

				// opções para centralização do formulário no MDI
				Mdi Pai = (Mdi) FormBuscaLaboratorio.this.getParent()
						.getParent().getParent().getParent().getParent();
				frmLaboratorioCad.setLocation(
						(Pai.getWidth() / 2 - frmLaboratorioCad.getWidth() / 2),
						(Pai.getHeight() / 2 - frmLaboratorioCad.getHeight() / 2));

				frmLaboratorioCad.setModelo(model);
				frmLaboratorioCad.setVisible(true);
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
		FormLaboratorio frmLaboratorioCad;
		// LaboratorioDao daoC = new LaboratorioDao();
		int[] linhas = datagrid.getSelectedRows();

		if (linhas.length > 1) {
			Uteis.janelaErro(
					"Escolha apenas um laboratorio na lista para editar",
					"Seleções Múltipas");
		} else if (datagrid.getSelectedRow() == -1) {
			Uteis.janelaErro("Escolha um laboratorio na lista",
					"Não selecionou Laboratorio");
		} else {
			Laboratorio laboratorioObtido = dao.find((int) model.getValueAt(
					datagrid.getSelectedRow(), 5));
			frmLaboratorioCad = new FormLaboratorio(laboratorioObtido,
					datagrid.getSelectedRow(), "Cadastramento de Laboratorio");

			// opções para centralização do formulário no MDI
			Mdi Pai = (Mdi) FormBuscaLaboratorio.this.getParent().getParent()
					.getParent().getParent().getParent();
			frmLaboratorioCad.setLocation(
					(Pai.getWidth() / 2 - frmLaboratorioCad.getWidth() / 2),
					(Pai.getHeight() / 2 - frmLaboratorioCad.getHeight() / 2));

			// seta o modelo no atributo interno do formulário FormLaboratorio
			frmLaboratorioCad.setModelo(model);
			// torna FormLaboratorio visível
			frmLaboratorioCad.setVisible(true);
		}
	}

	public void criaModelo() {
		LaboratorioDao daoc = new LaboratorioDao();
		List<Laboratorio> ListaLaboratorio = daoc.getLista();
		/*
		 * for (Laboratorio c : daoc.getLista()) { model.addLaboratorio((new
		 * Object[] { c.getNomeLaboratorio(), c.getLogradouro(), c.getCidade(),
		 * c.getEstado(), c.getIdLaboratorio() }); }
		 */
		model = new LaboratorioTableModel(ListaLaboratorio);
		datagrid.setModel(model);
	}

}
