package br.ucsal.projetoAE4.dao;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class DAOException extends  RuntimeException {
    
	public DAOException(SQLException e) {
		// TODO Auto-generated constructor stub
		super(e);
	}

	public void getException(Exception e) {  
        System.out.println(e);  
    } 			
	
}
