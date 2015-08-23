/**
 * 
 */
package br.ucsal.projetoAE4.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.ucsal.projetoAE4.net.Cliente;
import br.ucsal.projetoAE4.net.Mensagem;
import br.ucsal.projetoAE4.uteis.LeitorXML;
import br.ucsal.projetoAE4.uteis.LinhadeComando;
import br.ucsal.projetoAE4.uteis.Uteis;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * @author fredson
 *
 */
@SuppressWarnings("serial")
public class FormChat extends JFrame {
	JPanel BoxExterno;
	JPanel containerConteudo;
	JTextArea txtMsgRecebidas;
	JTextArea txtMsgaEnviar;
	JButton btEnviar;
	JButton btFechar;
	JScrollPane PaneScrol;
	static int clique = 1;
	Cliente con;
	OutputStream saidaMsg; 
	private String userChat; 
	String enderecoConfigurado; 
	int portaConfigurada; 
	LeitorXML lt;
	boolean ErroConexao=false;
	//MODIFICADO POR FREDSON EM 01/06 - IMPLEMENTADA A OPÇÃO PARA OBTER O USUÁRIO DA REDE
	//String nomeUserTemp = JOptionPane.showInputDialog("Digite seu nome: ");  
	
	public FormChat(String usuarioChat) {
		//super("Central de Mensagens");
		this.setTitle("Central de Mensagens");
		//torna a janela não redimensionável
		this.setResizable(false);
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new GridLayout(1,1));
		
		//setando o nome do usuário do chat - 01/06/2015
		this.userChat = usuarioChat;
		
		BoxExterno = new JPanel();
		
		Font fontLabel = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		
		
		BoxExterno.setPreferredSize(new Dimension(280, 380));
		containerConteudo = new JPanel();
		containerConteudo.setLayout(new BoxLayout(containerConteudo, BoxLayout.Y_AXIS));
		JLabel rotMsgRecebidas = new JLabel("Mensagens Recebidas");
		rotMsgRecebidas.setFont(fontLabel);
		JLabel rotMsgaEnviar= new JLabel("Digite abaixo sua mensagem");
		rotMsgaEnviar.setPreferredSize(new Dimension(220, 15));
		rotMsgaEnviar.setFont(fontLabel);
		txtMsgaEnviar = new JTextArea(2,1);
		txtMsgRecebidas = new JTextArea(12,1);
		txtMsgRecebidas.setEditable(false);
		 
		JPanel painelBotoes = new JPanel(new BorderLayout());
		JPanel subPainelBotoes = new JPanel();
		
		btFechar = new JButton("Fechar");
		btEnviar = new JButton("Enviar");	
		subPainelBotoes.add(btFechar);
		subPainelBotoes.add(btEnviar);
		//subPainelBotoes.setBackground(Color.yellow);
		painelBotoes.add(subPainelBotoes, BorderLayout.EAST);
		//painelBotoes.setBackground(Color.cyan);
		painelBotoes.setPreferredSize(new Dimension(60, 40));

		PaneScrol = new JScrollPane(txtMsgRecebidas);

		JScrollPane PaneScrol2 = new JScrollPane(txtMsgaEnviar);
		PaneScrol2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//garante a quebra de linha no textarea quando atingir a borda da direita
		txtMsgRecebidas.setLineWrap(true);
		txtMsgaEnviar.setLineWrap(true);
		//garante a quebra de linha no textarea quando atingir a borda da direita, apenas de palavras completas		
		txtMsgRecebidas.setWrapStyleWord(true);
		txtMsgaEnviar.setWrapStyleWord(true);		

		/*sequência para criar o serviço de mensagens propriamente dito */
		//abre conexão com o servidor de mensagens Porta 12345
		//con = new Cliente("192.168.1.4", 12345);
		try {
			lt = new LeitorXML("c:\\ModuloClienteAE4\\configModuloCliente.xml");
			enderecoConfigurado = lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidorChat"), "enderecoIPChat", 1);
			portaConfigurada = Integer.parseInt(lt.getTextElementNameporPosicao(lt.obterListaNodes("conexaoServidorChat"), "portaServidorChat", 1));
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		con = new Cliente(enderecoConfigurado, portaConfigurada);
		
		try {
			con.executa();
			saidaMsg = con.getSaida();
			//executa thread passando o objeto Recebedor de mensagens criado...
			con.executaRecebedor(con.criaRecebedor(txtMsgRecebidas));
			ErroConexao = false;
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Uteis.janelaErro("Não conseguiu conectar ao servidor de Chat...\nServidor: " + enderecoConfigurado +" -> Porta: "+portaConfigurada);
			ErroConexao = true;
		} catch (ConnectException e) {
			e.printStackTrace();
			
			Uteis.janelaErro("Conexão recusada pelo servidor de Chat...\nServidor: " + enderecoConfigurado +" -> Porta: "+portaConfigurada);
			FormChat.this.dispose();
			ErroConexao = true;
			//System.exit(0);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ErroConexao = true;
		}
		/*fim da sequência para criação do serviço de mensagem */
		
		
		
		btFechar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FormChat.this.dispose();
			}
		});
		
		
		con.getSaida();
		btEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> dest = new ArrayList<String>();
				dest.add(userChat);
				//Mensagem msg = new Mensagem(dest,User.getUserLogado(), txtMsgaEnviar.getText());
				Mensagem msg = new Mensagem(dest,userChat, txtMsgaEnviar.getText());
				ObjectOutputStream objEnviar;
				try {
					objEnviar = new ObjectOutputStream(saidaMsg);
					objEnviar.writeObject(msg);
					objEnviar.flush();
					txtMsgaEnviar.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//saidaMsg.println(txtMsgaEnviar.getText());
				
				
				
			}
		});
		
		
		txtMsgaEnviar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					//faz com que o enter não seja sensibilizado no JTextArea
					e.consume();
					
					// TODO Auto-generated method stub
					ArrayList<String> dest = new ArrayList<String>();
					
					
					dest.add(userChat);
					//Mensagem msg = new Mensagem(dest,User.getUserLogado(), txtMsgaEnviar.getText());
					Mensagem msg = new Mensagem(dest,userChat, txtMsgaEnviar.getText());
					
					ObjectOutputStream objEnviar;
					try {
						objEnviar = new ObjectOutputStream(saidaMsg);
						objEnviar.writeObject(msg);
						objEnviar.flush();
						txtMsgaEnviar.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					                     
					/*if (doc.getLength() > 0) {
                    	 doc.
                         try {
							doc.remove(doc.getLength() - 1, 1);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                     }*/	
                     				
					
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				//fecha a conexão no fechamento da janela...
				con.closeConexaoSocket();
			}
			
		});
		
		if (!ErroConexao) {
			
			containerConteudo.add(Box.createVerticalStrut(10));
			containerConteudo.add(rotMsgRecebidas);
			containerConteudo.add(PaneScrol);
			containerConteudo.add(Box.createVerticalStrut(20));
			containerConteudo.add(rotMsgaEnviar);
			containerConteudo.add(PaneScrol2);
			containerConteudo.add(Box.createVerticalStrut(10));
			containerConteudo.add(painelBotoes);
			
			BoxExterno.add(containerConteudo);
			this.add(BoxExterno);
			
			this.pack();
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setVisible(true);		
		} else {
			this.dispose();
		}
	}

	
	
	
	
	/**
	 * @author fredson
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinhadeComando lc = new LinhadeComando();
		lc.execute("cscript //NoLogo c:\\ModuloClienteAE4\\buscadados_usuario.vbs");
		String UsuarioRetornado = lc.getStdinData();		
		int posicao = UsuarioRetornado.trim().indexOf(" ");
		new FormChat(UsuarioRetornado.trim().substring(0, posicao) + " ("+User.getUserLogado()+")");
	}

}
