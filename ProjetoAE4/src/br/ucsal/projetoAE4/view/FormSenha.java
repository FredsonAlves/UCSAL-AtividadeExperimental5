package br.ucsal.projetoAE4.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JTextField;

import br.ucsal.projetoAE4.main.Mdi;

import br.ucsal.projetoAE4.net.LoginLDAP;

public class FormSenha extends JPanel {
	private Mdi windowPai;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel painelCamposTexto = new JPanel();
	JPanel painelContainerCamposTexto = new JPanel();
	JPanel painelBotoes = new JPanel();
	private JTextField txtUsuario = new JTextField(15);

	public Mdi getWindowPai() {
		return windowPai;
	}

	public void setWindowPai(Mdi windowPai) {
		this.windowPai = windowPai;
	}

	public JButton getBotOk() {
		return botOk;
	}

	public void setBotOk(JButton botOk) {
		this.botOk = botOk;
	}

	private JPasswordField txtSenha = new JPasswordField(15);
	JButton botCancelar = new JButton("Cancelar");
	JButton botOk = new JButton("Ok");

	public FormSenha() {
		// super(title, resizable, closable, maximizable, iconifiable);
		painelContainerCamposTexto.setLayout(new BoxLayout(
				painelContainerCamposTexto, BoxLayout.Y_AXIS));

		painelCamposTexto.setLayout(new GridLayout(2, 2));
		painelCamposTexto.setSize(200, 200);
		JLabel labelUsuario = new JLabel("Usuário: ");
		JLabel labelSenha = new JLabel("Senha: ");

		painelCamposTexto.add(labelUsuario);
		painelCamposTexto.add(txtUsuario);
		painelCamposTexto.add(labelSenha);
		painelCamposTexto.add(txtSenha);

		painelBotoes.add(botCancelar);
		painelBotoes.add(botOk);

		painelContainerCamposTexto.add(painelCamposTexto);
		painelContainerCamposTexto.add(painelBotoes);
		this.add(painelContainerCamposTexto, BorderLayout.CENTER);
		// this.add(painelBotoes, BorderLayout.CENTER);

		botCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Mdi md = (Mdi)
				// windowPai.getParent().getParent().getParent().getParent();
				// md.dispose();
				windowPai.dispose();
			}
		});

		botOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtUsuario.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe um usuário",
							"Erro no Usuário", JOptionPane.ERROR_MESSAGE);
					txtUsuario.grabFocus();
				} else if (new String(txtSenha.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null,

					"Informe uma senha válida...",
							"Erro no preenchimento da Senha",
							JOptionPane.ERROR_MESSAGE);
					txtSenha.grabFocus();

				} else {
					// LdapContext ad =
					// ActiveDirectory.getConnection(txtUsuario.getText(),
					// txtSenha.getText(),"ae4.local", "192.168.0.200");

					if (LoginLDAP.login(txtUsuario.getText(), new String(
							txtSenha.getPassword())) == true) {
						// User[] fasArr = ActiveDirectory.getUsers(ad);
						// JLayeredPane
						// JRootPane
						JDialog dialog = (JDialog) FormSenha.this.getParent()
								.getParent().getParent().getParent();
						JOptionPane.showMessageDialog(null,
								"Login efetuado com sucesso",
								"Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						
						dialog.dispose();
						// FormSenha.this.getParent().dispose();
					} else {
						JOptionPane.showMessageDialog(null,
								"Usuário ou Senha inválidos",
								"Erro na Autenticação",
								JOptionPane.ERROR_MESSAGE);
						txtSenha.grabFocus();
					}

					// e1.printStackTrace();
				}

			}
		});

		// this.botOk.setFocusable(isActive());
		// this.setDefaultButton(botOk);
		// coloca o botão ok como padrão quando o usuário digitar a senha...
		// this.getRootPane().setDefaultButton(botOk);
		this.setSize(400, 100);
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

}
