package com.word.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.word.domain.PartOfSpeech;
import com.word.domain.Word;

public class WordManager {
	/*
	 * PartOfSpeech jest w realicji jeden do wielu z tabela Word Jeden
	 * PartOfSpeech moze miec wiele Word
	 */
	private Connection connection;
	private Statement statement;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	private String createTableWord = ("CREATE TABLE WORD (id_word BIGINT,id_pos BIGINT, mianownik VARCHAR(20), dopelniacz VARCHAR(20), wolacz VARCHAR(20))");
	private String createTablePartOfSpeech = ("CREATE TABLE PARTOFSPEECH (id_pos BIGINT, rzeczownik VARCHAR(20), przymiotnik VARCHAR(20), czasownik VARCHAR(20)) ");
	private PreparedStatement addWordStmt;
	private PreparedStatement getAllWord;
	private PreparedStatement clearTableWord;
	private PreparedStatement addPartOfSpeechStmt;
	private PreparedStatement clearPartOfSpeechStmt;
	private PreparedStatement getJoinAllStmt;
	private PreparedStatement getPartOfSpeechStmt;
	private PreparedStatement setId_pos;
	private PreparedStatement deleteRecordByIdStmt;
	private PreparedStatement getXfromYStmt;
	private PreparedStatement getYfromXStmt;
	private PreparedStatement getRecordsByIdStmt;

	/*
	 * e = get ... e.pole = "...." update(e)
	 */

	public WordManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);

			boolean isTableExist = false;
			while (rs.next()) {
				if ("Word".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					isTableExist = true;
					break;
				}

			}

			if (!isTableExist) {
				statement.executeQuery(createTableWord);
				statement.executeQuery(createTablePartOfSpeech);
			}

			addWordStmt = connection
					.prepareStatement("INSERT INTO WORD VALUES (?,?,?,?,?)");
			clearTableWord = connection.prepareStatement("DELETE FROM WORD");
			getAllWord = connection
					.prepareStatement("SELECT id_word, id_pos, mianownik, dopelniacz, wolacz FROM WORD");
			addPartOfSpeechStmt = connection
					.prepareStatement("INSERT INTO PARTOFSPEECH VALUES (?,?,?,?)");

			clearPartOfSpeechStmt = connection
					.prepareStatement("DELETE FROM PARTOFSPEECH");
			getJoinAllStmt = connection
					.prepareStatement("SELECT * FROM WORD RIGHT JOIN PARTOFSPEECH ON WORD.ID_POS = PARTOFSPEECH.ID_POS");
			getPartOfSpeechStmt = connection
					.prepareStatement("SELECT * FROM PARTOFSPEECH");
			setId_pos = connection
					.prepareStatement("UPDATE WORD SET id_pos = ? where id_word = ?");
			deleteRecordByIdStmt = connection
					.prepareStatement("DELETE FROM word WHERE id_word = ?");
			getXfromYStmt = connection
					.prepareStatement("SELECT id_word,id_pos, mianownik, dopelniacz, wolacz FROM WORD RIGHT JOIN PARTOFSPEECH ON WORD.ID_POS = PARTOFSPEECH.ID_POS");
			getYfromXStmt = connection
					.prepareStatement("SELECT id_word,id_pos, rzeczownik, przymiotnik, czasownik FROM WORD RIGHT JOIN PARTOFSPEECH ON WORD.ID_POS = PARTOFSPEECH.ID_POS");
			getRecordsByIdStmt = connection
					.prepareStatement("SELECT id_word, id_pos, mianownik, dopelniacz,wolacz, rzeczownik,przymiotnik, czasownik FROM WORD RIGHT JOIN PARTOFSPEECH ON WORD.ID_POS = ? AND WORD.ID_POS = PARTOFSPEECH.ID_POS WHERE WORD.ID_POS > 0 ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	public int addWord(Word word) {
		int count = 0;

		try {
			addWordStmt.setInt(1, word.getId());
			addWordStmt.setInt(2, word.getId_pos());
			addWordStmt.setString(3, word.getMianownik());
			addWordStmt.setString(4, word.getDopelniacz());
			addWordStmt.setString(5, word.getWolacz());

			count = addWordStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	public int clearTableWord() {
		int count = 0;

		try {
			count = clearTableWord.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	public int deleteRecordById(int Id) {
		int count = 0;
		try {
			deleteRecordByIdStmt.setInt(1, Id);

			count = deleteRecordByIdStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public int clearTablePartOfSpeech() {
		int count = 0;
		try {
			count = clearPartOfSpeechStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int addPartOfSpeech(PartOfSpeech partOfSpeech) {
		int count = 0;

		try {
			addPartOfSpeechStmt.setInt(1, partOfSpeech.getId());
			addPartOfSpeechStmt.setString(2, partOfSpeech.getRzeczownik());
			addPartOfSpeechStmt.setString(3, partOfSpeech.getPrzymiotnik());
			addPartOfSpeechStmt.setString(4, partOfSpeech.getCzasownik());
			count = addPartOfSpeechStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public List<Word> getAllWord() {
		List<Word> words = new ArrayList<Word>();

		try {
			ResultSet rs = getAllWord.executeQuery();

			while (rs.next()) {
				Word w = new Word();

				w.setId(rs.getInt("id_word"));
				w.setId_pos(rs.getInt("id_pos"));
				w.setMianownik(rs.getString("mianownik"));
				w.setDopelniacz(rs.getString("dopelniacz"));
				w.setWolacz(rs.getString("wolacz"));
				words.add(w);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return words;

	}

	public List<PartOfSpeech> getJoin() {
		List<PartOfSpeech> parts = new ArrayList<PartOfSpeech>();
		ResultSet rs;
		try {
			// getJoinStmt.setString(1,columnName);
			rs = getJoinAllStmt.executeQuery();

			while (rs.next()) {
				PartOfSpeech pos = new PartOfSpeech();

				pos.setId(rs.getInt("id_word"));
				pos.setId_pos(rs.getInt("id_pos"));
				pos.setRzeczownik(rs.getString("rzeczownik"));
				pos.setPrzymiotnik(rs.getString("przymiotnik"));
				pos.setCzasownik(rs.getString("czasownik"));
				pos.setMianoswnik(rs.getString("mianownik"));
				pos.setDopelniacz(rs.getString("dopelniacz"));
				pos.setWolacz(rs.getString("wolacz"));

				parts.add(pos);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parts;

	}

	public List<PartOfSpeech> getXfromY() {
		List<PartOfSpeech> parts = new ArrayList<PartOfSpeech>();

		try {
			ResultSet rs = getXfromYStmt.executeQuery();
			while (rs.next()) {
				PartOfSpeech pos = new PartOfSpeech();

				pos.setId(rs.getInt("id_word"));
				pos.setId_pos(rs.getInt("id_pos"));
				pos.setMianoswnik(rs.getString("mianownik"));
				pos.setDopelniacz(rs.getString("dopelniacz"));
				pos.setWolacz(rs.getString("wolacz"));

				parts.add(pos);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parts;

	}

	public List<PartOfSpeech> getYfromX() {
		List<PartOfSpeech> parts = new ArrayList<PartOfSpeech>();

		try {
			ResultSet rs = getYfromXStmt.executeQuery();
			while (rs.next()) {
				PartOfSpeech pos = new PartOfSpeech();

				pos.setId(rs.getInt("id_word"));
				pos.setRzeczownik(rs.getString("rzeczownik"));
				pos.setPrzymiotnik(rs.getString("przymiotnik"));
				pos.setCzasownik(rs.getString("czasownik"));

				parts.add(pos);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parts;
	}

	public List<PartOfSpeech> getAllPartOfSpeech() {
		List<PartOfSpeech> parts = new ArrayList<PartOfSpeech>();

		try {
			ResultSet rs = getPartOfSpeechStmt.executeQuery();

			while (rs.next()) {
				PartOfSpeech pos = new PartOfSpeech();

				pos.setId(rs.getInt("id_pos"));
				pos.setRzeczownik(rs.getString("rzeczownik"));
				pos.setPrzymiotnik(rs.getString("przymiotnik"));
				pos.setCzasownik(rs.getString("czasownik"));

				parts.add(pos);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parts;
	}

	public int updateId_pos(int newId, int whichRecord) {
		int count = 0;

		try {
			setId_pos.setInt(1, newId);
			setId_pos.setInt(2, whichRecord);

			count = setId_pos.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}
	
	
	public List<PartOfSpeech> getRecordsById(int indexColumn) {
		List<PartOfSpeech> parts = new ArrayList<PartOfSpeech>();
		
		try {
			
			getRecordsByIdStmt.setInt(1, indexColumn);
			ResultSet rs = getRecordsByIdStmt.executeQuery();
			
			while(rs.next()) {
				PartOfSpeech pos = new PartOfSpeech();
				
				pos.setId_pos(rs.getInt("id_pos"));
				pos.setId(rs.getInt("id_word"));
				pos.setMianoswnik(rs.getString("mianownik"));
				pos.setDopelniacz(rs.getString("dopelniacz"));
				pos.setWolacz(rs.getString("wolacz"));
				pos.setRzeczownik(rs.getString("rzeczownik"));
				pos.setPrzymiotnik(rs.getString("przymiotnik"));
				pos.setCzasownik(rs.getString("czasownik"));
				
				parts.add(pos);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parts;
	}

}
