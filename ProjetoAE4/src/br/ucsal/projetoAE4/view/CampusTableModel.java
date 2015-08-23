/**
 * 
 */
package br.ucsal.projetoAE4.view;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import br.ucsal.projetoAE4.modelo.Campus;

/**
 * @author fredson
 *
 */
@SuppressWarnings("serial")
public class CampusTableModel extends AbstractTableModel {
	private static final int COL_NOMECAMPUS=0;
	private static final int COL_LOGRAD=1;
	private static final int COL_CIDADE=2;
	private static final int COL_UF=3;
	private Boolean dadosFiltrados=false;
	
	private List<Campus> lCampus;
	
	private String[] rotulosColunas = { "Nome Campus", "Logradouro", "Cidade", "Estado" };	
	
	public CampusTableModel(List<Campus> ListaCampus) {
		this.lCampus =  ListaCampus;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return lCampus.size();
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

		Campus camp = lCampus.get(row);

		if (column == COL_NOMECAMPUS) {
			return camp.getNomeCampus();
		} else if (column == COL_LOGRAD) {
			return camp.getLogradouro();
		} else if (column == COL_CIDADE) {
			return camp.getCidade();
		} else if (column == COL_UF) {
			return camp.getEstado();
		//se for passado o número 4, pega o valor do id do Campus
		} else if (column == 4) {
			return camp.getIdCampus();
		}
		return "";
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Campus camp = lCampus.get(row);
		if (column == COL_NOMECAMPUS) {
			camp.setNomeCampus(aValue.toString());
		} else if (column == COL_LOGRAD) {
			camp.setNomeCampus(aValue.toString());
		} else if (column == COL_CIDADE) {
			camp.setCidade(aValue.toString());
		} else if (column == COL_UF) {
			camp.setEstado(aValue.toString());
		}
	}


	public Campus getCampus(int indiceLinha) {
		return lCampus.get(indiceLinha);
	}

	public void addCampus(Campus campus) {
		lCampus.add(campus);
		int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);

	}

	public void updateCampus(int indiceLinha, Campus cAlterado) {
		lCampus.set(indiceLinha, cAlterado);
		fireTableRowsUpdated(indiceLinha, indiceLinha);

	}

	public void removeCampus(int[] indicesLinhas) {
		int PrimeiraLinha = -1;
		int UltimaLinha = -1;
		int exclusoes = 0;
		
		for (int linha : indicesLinhas) {
			if (PrimeiraLinha==-1) {
				PrimeiraLinha=linha;
			}
			//linha atual menos as excluídas
			lCampus.remove(linha-exclusoes);
			exclusoes++;
			UltimaLinha = linha;
		}
		
		fireTableRowsDeleted(PrimeiraLinha, UltimaLinha);
	}
	
	public void updateModelo(List<Campus> listc) {
		this.lCampus = listc;
		fireTableDataChanged();

	}

	public Boolean getDadosFiltrados() {
		return dadosFiltrados;
	}

	public void setDadosFiltrados(Boolean dadosFiltrados) {
		this.dadosFiltrados = dadosFiltrados;
	}
	
}