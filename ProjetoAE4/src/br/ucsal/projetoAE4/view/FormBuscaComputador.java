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

import br.ucsal.projetoAE4.dao.ComputadorDao;
import br.ucsal.projetoAE4.dao.LaboratorioDao;
import br.ucsal.projetoAE4.main.Mdi;
import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.modelo.Laboratorio;
import br.ucsal.projetoAE4.uteis.Uteis;

@SuppressWarnings("serial")
public class FormBuscaComputador extends JInternalFrame {
	JPanel painelTable = new JPanel();
	JPanel painelContainer = new JPanel();
	JPanel painelBotoes = new JPanel(new BorderLayout());
	JPanel painelFiltro = new JPanel();
	JPanel subPainelFiltro1 = new JPanel();
	JPanel subPainelFiltro2 = new JPanel();
	JLabel rotFiltroNomeComputador = new JLabel("Nome do Computador: ");
	JTextField txtFiltroNomeComputador = new JTextField(25);
	JLabel rotFiltroLaboratorioComputador = new JLabel("Laboratorio: ");
	JTextField txtFiltroLaboratorioComputador = new JTextField(10);

	final ComputadorDao dao = new ComputadorDao();
	private ComputadorTableModel model;
	private JTable datagrid;

	public FormBuscaComputador(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		painelFiltro.setLayout(new BoxLayout(painelFiltro, BoxLayout.Y_AXIS));
		subPainelFiltro1.setLayout(new BoxLayout(subPainelFiltro1,
				BoxLayout.X_AXIS));
		painelContainer.setLayout(new BoxLayout(painelContainer,
				BoxLayout.Y_AXIS));

		Image image = Toolkit.getDefaultToolkit().getImage("tray2.png");
		ImageIcon ico = new ImageIcon(image);
		this.setFrameIcon(ico);

		JLabel Mensagem = new JLabel(
				"Caso deseje filtrar os registros, preencha os campos desejados e clique em Filtrar...");

		Mensagem.setForeground(Color.BLUE);
		Font font = new Font("SansSerif", Font.BOLD, 15);
		Mensagem.setFont(font);
		subPainelFiltro1.add(Mensagem);

		subPainelFiltro2.add(rotFiltroLaboratorioComputador);
		subPainelFiltro2.add(txtFiltroLaboratorioComputador);
		subPainelFiltro2.add(rotFiltroNomeComputador);
		subPainelFiltro2.add(txtFiltroNomeComputador);
		JButton btFiltrar = new JButton("Filtrar");

		btFiltrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (txtFiltroNomeComputador.getText().equals("")
						&& txtFiltroLaboratorioComputador.getText().equals("")
						&& model.getDadosFiltrados() == false) {
					Uteis.janelaErro(
							"Preencha pelo menos uma opção para filtrar",
							"Filtro");
				} else if (txtFiltroNomeComputador.getText().equals("")
						&& txtFiltroLaboratorioComputador.getText().equals("")
						&& model.getDadosFiltrados() == true) {
					List<Computador> novaLista = dao.getLista();
					model.updateModelo(novaLista);
					model.setDadosFiltrados(false);
				} else {
					Computador c = new Computador();
					c.setNomeComputador(txtFiltroNomeComputador.getText());
					LaboratorioDao labDao = new LaboratorioDao();
					Laboratorio lab = labDao.findByName(txtFiltroLaboratorioComputador
							.getText());
					c.setLaboratorio(lab);
					
					List<Computador> novaLista = dao.getListaFiltrada(c);
					model.updateModelo(novaLista);
					model.setDadosFiltrados(true);
				}
			}
		});

		subPainelFiltro2.add(btFiltrar);

		painelFiltro.add(subPainelFiltro1);
		painelFiltro.add(subPainelFiltro2);
		datagrid = new JTable(model);
		//datagrid.removeColumn(datagrid.getColumn("ID"));
		criaModelo();

		datagrid.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					editarRegistro();
				}
			}

		});

		JScrollPane scrol = new JScrollPane(datagrid);
		datagrid.setLocation(3, 80);
		datagrid.setPreferredScrollableViewportSize(new Dimension(400, 101));
		datagrid.setFillsViewportHeight(true);
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
							"Escolha pelo menos um computador na lista para excluir...",
							"Não seleciou Campus");
				} else {
					for (int linha : linhas) {
						Computador c = model.getComputador(linha);
						dao.remove(c);
					}
					model.removeComputador(linhas);
				}

			}
		});

		JButton botEditar = new JButton("Editar");

		botEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editarRegistro();
			}
		});

		JButton botNovo = new JButton("Novo Registro");

		botNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormComputador frmComputadorCad = new FormComputador(
						"Cadastramento de Computador");

				// opções para centralização do formulário no MDI
				Mdi Pai = (Mdi) FormBuscaComputador.this.getParent()
						.getParent().getParent().getParent().getParent();
				frmComputadorCad.setLocation(
						(Pai.getWidth() / 2 - frmComputadorCad.getWidth() / 2),
						(Pai.getHeight() / 2 - frmComputadorCad.getHeight() / 2));

				frmComputadorCad.setModelo(model);
				frmComputadorCad.setVisible(true);
			}
		});

		
		subPainelBotoes.add(botExcluir);
		subPainelBotoes.add(botEditar);
		subPainelBotoes.add(botNovo);
		
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
		FormComputador frmComputadorCad;
		// CampusDao daoC = new CampusDao();
		int[] linhas = datagrid.getSelectedRows();

		if (linhas.length > 1) {
			Uteis.janelaErro("Escolha apenas um Computador na lista para editar",
					"Seleções Múltipas");
		} else if (datagrid.getSelectedRow() == -1) {
			Uteis.janelaErro("Escolha um Computador na lista",
					"Não selecionou Computador");
		} else {
			Computador computadorObtido = dao.find((int) model.getValueAt(
					datagrid.getSelectedRow(), 5));
			frmComputadorCad = new FormComputador(computadorObtido,
					datagrid.getSelectedRow(), "Cadastramento de Computador");
			
			//opções para centralização do formulário no MDI
			Mdi Pai = (Mdi) FormBuscaComputador.this.getParent().getParent().getParent().getParent().getParent();
			frmComputadorCad.setLocation((Pai.getWidth() / 2 - frmComputadorCad.getWidth() / 2),
					(Pai.getHeight() / 2 - frmComputadorCad.getHeight() / 2));
			
			//seta o modelo no atributo interno do formulário FormComputador
			frmComputadorCad.setModelo(model);
			//torna FormComputador visível
			frmComputadorCad.setVisible(true);
		}
	}

	public void criaModelo() {
		ComputadorDao daoc = new ComputadorDao();
		List<Computador> ListaComputador = daoc.getLista();
		/*
		 * for (Campus c : daoc.getLista()) { model.addCampus((new Object[] {
		 * c.getNomeCampus(), c.getLogradouro(), c.getCidade(), c.getEstado(),
		 * c.getIdCampus() }); }
		 */
		model = new ComputadorTableModel(ListaComputador);
		datagrid.setModel(model);
	}
}
