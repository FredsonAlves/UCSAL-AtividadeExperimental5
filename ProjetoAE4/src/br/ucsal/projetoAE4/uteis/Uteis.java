package br.ucsal.projetoAE4.uteis;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

final public class Uteis {

	private Uteis() {
	};

	public static void janelaErro(String mensagem, String titulo) {
		JOptionPane.showMessageDialog(null, mensagem, titulo,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void janelaErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, null,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void janelaInfo(String mensagem, String titulo) {
		JOptionPane.showMessageDialog(null, mensagem, titulo,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void janelaInfo(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, null,
				JOptionPane.INFORMATION_MESSAGE);
	}

	// origem: http://www.guj.com.br/java/12992-validar-ip-com-java/2 -
	// Corrigido/melhorado por Fredson
	// valida um ip IPV4/IPV6 -
	public static boolean isIpValido(String ip, int IPV) {
		String[] ipSplit = { "" };
		switch (IPV) {
		case 4:
			// Minimo 1.1.1.1 Máximo 255.255.255.255
			if (ip.length() < 7 || ip.length() > 15)
				return false;
			ipSplit = ip.split("\\.");
			if (ipSplit.length != 4)
				return false;
			break;
		case 6:
			// Minimo 1.1.1.1.1.1 Máximo 255.255.255.255.255.255
			if (ip.length() < 11 || ip.length() > 23)
				return false;
			ipSplit = ip.split("\\.");
			if (ipSplit.length != 6)
				return false;
			break;
		}

		try {
			int count = 1;
			for (String ipContext : ipSplit) {
				if ((count == 1)
						&& (Integer.parseInt(ipContext) <= 0 || Integer
								.parseInt(ipContext) > 255)) {
					return false;
				} else if ((Integer.parseInt(ipContext) < 0 || Integer
						.parseInt(ipContext) > 255)) {
					return false;
				}
				count++;
			}
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	// para IPV4
	public static boolean isEnderecoRede(String ip) {

		if (isIpValido(ip, 4)) {
			String[] ipSplit = { "" };
			// Minimo 1.1.1.1 Máximo 255.255.255.255
			if (ip.length() < 7 || ip.length() > 15)
				return false;
			ipSplit = ip.split("\\.");
			if (ipSplit.length != 4)
				return false;

			try {
				int count = 1;
				for (String ipContext : ipSplit) {
					if ((count == 4) && (Integer.parseInt(ipContext) != 0)) {
						return false;
					}
					count++;
				}
			} catch (NumberFormatException ex) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static boolean ValidaCampo(JTextField campo) {

		// long resultado;

		// retorna uma cópia da string retirando os espaços do edit(JTextField
		String codigo = campo.getText().trim();

		try {

			@SuppressWarnings("unused")
			long resultado = Long.parseLong(codigo);

			return true;

		} catch (Exception ex) {

			return false;

		}

	}// Fim do método

	public static String obterDataHoraAtual() {
		Calendar dt = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return df.format(dt.getTimeInMillis());
	}

	public static String obterHoraAtual() {
		Calendar dt = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(dt.getTimeInMillis());
	}

	public static String obterDataAtual() {
		Calendar dt = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(dt.getTimeInMillis());
	}

	public static String obterNomeCompletoUsuario() {
		// obtém o nome de usuário através de script vbscript localizado na
		// pasta ModuloClienteAE4
		// Há um pequeno retardo
		LinhadeComando lc = new LinhadeComando();
		lc.execute("cscript //NoLogo c:\\ModuloClienteAE4\\buscadados_usuario.vbs");
		
		if (lc.getStdinData().trim().contains("Administra")) {
			return "Administrador";
		} else {
			return lc.getStdinData().trim();
		}
	}

}
