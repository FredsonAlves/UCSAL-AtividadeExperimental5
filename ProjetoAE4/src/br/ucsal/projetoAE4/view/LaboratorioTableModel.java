/**
 * 
 */
package br.ucsal.projetoAE4.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.ucsal.projetoAE4.modelo.Campus;
import br.ucsal.projetoAE4.modelo.Laboratorio;

/**
 * @author fredson
 *
 */
@SuppressWarnings("serial")
public class LaboratorioTableModel extends AbstractTableModel {
	private static final int COL_NOMELABORATORIO=0;	
	private static final int COL_NOMECAMPUS=1;
	private static final int COL_COMPVERT=2;
	private static final int COL_COMPHORIZ=3;
	private static final int COL_FAIXAREDE=4;
	private Boolean dadosFiltrados=false;
	
	private List<Laboratorio> lLaboratorio;
	
	private String[] rotulosColunas = { "Nome Laboratorio", "Campus", "Qt. Comp. Vertical", "Qt. Comp. Horizontal", "Endereço de Rede IPV4" };	
	
	public LaboratorioTableModel(List<Laboratorio> ListaLaboratorio) {
		this.lLaboratorio =  ListaLaboratorio;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return lLaboratorio.size();
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

		Laboratorio camp = lLaboratorio.get(row);

		if (column == COL_NOMELABORATORIO) {
			return camp.getNomeLaboratorio();
		} else if (column == COL_NOMECAMPUS) {
			if (camp.getCampus() != null) {
				return camp.getCampus().getNomeCampus();
			} else {
				return "Campus inválido";
			}
		} else if (column == COL_COMPVERT) {
			return camp.getQteComputadoresVertical();
		} else if (column == COL_COMPHORIZ) {
			return camp.getQteComputadoresHorizontal();
		} else if (column == COL_FAIXAREDE) {
			return camp.getFaixaRedeIPV4();
		//se for passado o número 5, pega o valor do id do Laboratorio
		} else if (column == 5) {
			return camp.getIdLaboratorio();
		}
		return "";
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Laboratorio camp = lLaboratorio.get(row);
		if (column == COL_NOMELABORATORIO) {
			camp.setNomeLaboratorio(aValue.toString());
		} else if (column == COL_NOMECAMPUS) {

			camp.setCampus((Campus) aValue);
		} else if (column == COL_COMPVERT) {
			camp.setQteComputadoresVertical(Integer.parseInt(aValue.toString()));
		} else if (column == COL_COMPHORIZ) {
			camp.setQteComputadoresHorizontal(Integer.parseInt(aValue.toString()));
		} else if (column == COL_FAIXAREDE) {
			camp.setFaixaRedeIPV4(aValue.toString());
		}
	}


	public Laboratorio getLaboratorio(int indiceLinha) {
		return lLaboratorio.get(indiceLinha);
	}

	public void addLaboratorio(Laboratorio laboratorio) {
		lLaboratorio.add(laboratorio);
		int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);

	}

	public void updateLaboratorio(int indiceLinha, Laboratorio cAlterado) {
		lLaboratorio.set(indiceLinha, cAlterado);
		fireTableRowsUpdated(indiceLinha, indiceLinha);

	}

	public void removeLaboratorio(int[] indicesLinhas) {
		int PrimeiraLinha = -1;
		int UltimaLinha = -1;
		int exclusoes = 0;
		
		for (int linha : indicesLinhas) {
			if (PrimeiraLinha==-1) {
				PrimeiraLinha=linha;
			}
			//linha atual menos as excluídas
			lLaboratorio.remove(linha-exclusoes);
			exclusoes++;
			UltimaLinha = linha;
		}
		
		fireTableRowsDeleted(PrimeiraLinha, UltimaLinha);
	}
	
	public void updateModelo(List<Laboratorio> listc) {
		this.lLaboratorio = listc;
		fireTableDataChanged();

	}

	public Boolean getDadosFiltrados() {
		return dadosFiltrados;
	}

	public void setDadosFiltrados(Boolean dadosFiltrados) {
		this.dadosFiltrados = dadosFiltrados;
	}
	
}