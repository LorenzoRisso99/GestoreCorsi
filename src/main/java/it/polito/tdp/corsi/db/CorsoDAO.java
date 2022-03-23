package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	public List<Corso> getCorsoByPeriodo(int periodo) {
		
		String sql = "select * "
				+ "from corso "
				+ "where pd = ?" ;
		
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);     //Posizione del punto interrogativo
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				result.add(c);
			}
			
			rs.close();
			st.close();
			conn.close();
			return result;
			
			
		} catch (SQLException e) {
			System.out.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
		
		
		
	}

}
