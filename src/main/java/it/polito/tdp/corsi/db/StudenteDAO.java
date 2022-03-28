package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.Divisione;
import it.polito.tdp.corsi.model.Studente;

public class StudenteDAO {
	
	public List<Studente> getStudentiByCorso(String codins) {
		
		String sql = "select s.matricola, s.cognome, s.nome, s.CDS "
				+ "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> result = new ArrayList<Studente>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);     //Posizione del punto interrogativo
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				result.add(s);
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
	
	public List<Divisione> getDivisioneStudenti(String codins) {
		
		String sql = "select s.CDS, COUNT(*) as n "
				+ "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola AND i.codins = ? AND s.CDS <> '' "
				+ "group by s.CDS";
		
		List<Divisione> result = new ArrayList<Divisione>();
		
        try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);     //Posizione del punto interrogativo
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Divisione d = new Divisione(rs.getString("CDS"), rs.getInt("n"));
				result.add(d);
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
