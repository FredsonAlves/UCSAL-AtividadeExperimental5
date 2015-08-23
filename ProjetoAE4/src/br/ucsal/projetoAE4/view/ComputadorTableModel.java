/**
 * 
 */
package br.ucsal.projetoAE4.view;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.ucsal.projetoAE4.modelo.Computador;
import br.ucsal.projetoAE4.modelo.Laboratorio;

/**
 * @author 000841064
 *
 */
@SuppressWarnings("serial")
public class ComputadorTableModel extends AbstractTableModel {

	private static final int COL_NOMEMAQUINA = 0;
	private static final int COL_LABORATORIO = 1;
	private static final int COL_POSICAOHORIZONTAL = 2;
	private static final int COL_POSICAOVERTICAL = 3;
	private static final int COL_ULTIMACOMUNICACAO = 4;
	private Boolean dadosFiltrados = false;

	private List<Computador> lComputador;

	private String[] rotulosColunas = { "Nome Computador", "Laboratorio",
			"Posição Horizontal", "Posição Vertical", "Última Comunicação" };

	public ComputadorTableModel(List<Computador> ListaComputador) {
		this.lComputador = ListaComputador;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return lComputador.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return rotulosColunas.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return rotulosColunas[columnIndex];
	}

	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 4) {
			return Integer.class;
		}
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {

		Computador cmp = lComputador.get(row);

		if (column == COL_NOMEMAQUINA) {
			return cmp.getNomeComputador();
		} else if (column == COL_LABORATORIO) {
			if (cmp.getLaboratorio() != null) {
				return cmp.getLaboratorio().getNomeLaboratorio();
			} else {
				return "Laboratório inválido";
			}
		} else if (column == COL_POSICAOHORIZONTAL) {
			return cmp.getPosicaoHorizontal();
		} else if (column == COL_POSICAOVERTICAL) {
			return cmp.getPosicaoHorizontal();
		} else if (column == COL_ULTIMACOMUNICACAO) {
			if (cmp.getUltimaComunicacao() != null) {
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				return df.format(cmp.getUltimaComunicacao().getTimeInMillis());
			} else {
				return "";
			}
		//se for passado o número 5, pega o valor do id do Computador
		} else if (column == 5) {
			return cmp.getIdComputador();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Computador cmp = lComputador.get(row);

		if (column == COL_NOMEMAQUINA) {
			cmp.setNomeComputador(aValue.toString());
		} else if (column == COL_LABORATORIO) {
			cmp.setLaboratorio((Laboratorio) aValue);
		} else if (column == COL_POSICAOHORIZONTAL) {
			cmp.setPosicaoHorizontal(Integer.parseInt(aValue.toString()));
		} else if (column == COL_POSICAOVERTICAL) {
			cmp.setPosicaoVertical(Integer.parseInt(aValue.toString()));
		} else if (column == COL_ULTIMACOMUNICACAO) {
			cmp.setUltimaComunicacao(null);
		}
	}

	public Computador getComputador(int indiceLinha) {
		return lComputador.get(indiceLinha);
	}

	public void addComputador(Computador computador) {
		lComputador.add(computador);
		int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);

	}

	public void updateComputador(int indiceLinha, Computador cAlterado) {
		lComputador.set(indiceLinha, cAlterado);
		fireTableRowsUpdated(indiceLinha, indiceLinha);

	}

	public void removeComputador(int[] indicesLinhas) {
		int PrimeiraLinha = -1;
		int UltimaLinha = -1;
		int exclusoes = 0;

		for (int linha : indicesLinhas) {
			if (PrimeiraLinha == -1) {
				PrimeiraLinha = linha;
			}
			// linha atual menos as excluídas
			lComputador.remove(linha - exclusoes);
			exclusoes++;
			UltimaLinha = linha;
		}

		fireTableRowsDeleted(PrimeiraLinha, UltimaLinha);
	}

	public void updateModelo(List<Computador> listc) {
		this.lComputador = listc;
		fireTableDataChanged();

	}

	public Boolean getDadosFiltrados() {
		return dadosFiltrados;
	}

	public void setDadosFiltrados(Boolean dadosFiltrados) {
		this.dadosFiltrados = dadosFiltrados;
	}
}
