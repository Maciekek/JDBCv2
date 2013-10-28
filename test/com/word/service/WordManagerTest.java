package com.word.service;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import com.word.domain.PartOfSpeech;
import com.word.domain.Word;

public class WordManagerTest {

	private String mianownik1 = "Rower";
	private String dopelniacz1 = "Rowerem";
	private String wolacz1 = "ROWER!";
	private String rzeczownik1 = "Rower";
	private String przymiotnik1 = "duzy_rower";
	private String czasownik1 = "jade_Rowerem";
	private String mianownik2 = "Motor";
	private String dopelniacz2 = "Motoru";
	private String wolacz2 = "O motor";
	private String rzeczownik2 = "Motor";
	private String przymiotnik2 = "Bialy_motor";
	private String czasownik2 = "Jade_motorem";
	private int NEW_ID = 100;
	private int witchRecord = 1;

	WordManager wordManager = new WordManager();

	
	@Test
	@After
	public void afterInputDate() {
		PartOfSpeech pos = new PartOfSpeech(1, rzeczownik1, przymiotnik1,
				czasownik1);
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);

		assertEquals(1, wordManager.addWord(word));
		assertEquals(1, wordManager.addPartOfSpeech(pos));
		wordManager.clearTableWord();
		wordManager.clearTablePartOfSpeech();

	}
	@Test
	public void checkConnection() {
		assertNotNull(wordManager.getConnection());
	}

	@Test
	public void checkAddWord() {
		// given
		// when
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);

		// then
		assertEquals(1, wordManager.addWord(word));
		wordManager.clearTableWord();
	}

	@Test
	public void checkAddPartOdSpeech() {
		PartOfSpeech partOfSpeech = new PartOfSpeech(1, rzeczownik1,
				przymiotnik1, czasownik1);

		assertEquals(1, wordManager.addPartOfSpeech(partOfSpeech));
		wordManager.clearTablePartOfSpeech();
	}

	@Test
	public void checkClearTableWord() {
		wordManager.clearTableWord();

		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);

		assertEquals(1, wordManager.addWord(word));
		assertEquals(1, wordManager.clearTableWord());

	}

	@Test
	public void checkClearTablePartOfSpeech() {
		wordManager.clearTablePartOfSpeech();

		PartOfSpeech partOfSpeech = new PartOfSpeech(1, rzeczownik1,
				przymiotnik1, czasownik1);

		assertEquals(1, wordManager.addPartOfSpeech(partOfSpeech));
		assertEquals(1, wordManager.clearTablePartOfSpeech());
		wordManager.clearTablePartOfSpeech();

	}

	@Test
	public void checkGetAllWord() {
		// given
		// when
		wordManager.clearTableWord();
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);
		wordManager.addWord(word);
		List<Word> words = wordManager.getAllWord();
		Word wordRetrieved = words.get(0);

		// then
		assertEquals(1, wordRetrieved.getId());
		assertEquals(1, wordRetrieved.getId_pos());
		assertEquals(mianownik1, wordRetrieved.getMianownik());
		assertEquals(dopelniacz1, wordRetrieved.getDopelniacz());
		assertEquals(wolacz1, wordRetrieved.getWolacz());
		wordManager.clearTableWord();
	}

	@Test
	public void checkJoin() {
		// given
		wordManager.clearTablePartOfSpeech();
		PartOfSpeech pos = new PartOfSpeech(1, rzeczownik1, przymiotnik1,
				czasownik1);
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);

		// when
		wordManager.addWord(word);
		wordManager.addPartOfSpeech(pos);

		List<PartOfSpeech> poses = wordManager.getJoin();
		PartOfSpeech partRetrieved = poses.get(0);

		// then
		assertEquals(rzeczownik1, partRetrieved.getRzeczownik());
		assertEquals(przymiotnik1, partRetrieved.getPrzymiotnik());
		assertEquals(czasownik1, partRetrieved.getCzasownik());
		assertEquals(mianownik1, partRetrieved.getMianoswnik());
		assertEquals(dopelniacz1, partRetrieved.getDopelniacz());
		assertEquals(wolacz1, partRetrieved.getWolacz());
		wordManager.clearTableWord();
		wordManager.clearTablePartOfSpeech();
	}
	
	@Test @After
	public void checkJoinVer2() {
		wordManager.clearTablePartOfSpeech();
		wordManager.clearTableWord();
		
		Word word = new Word(2,2, mianownik2,dopelniacz2,wolacz2);
		PartOfSpeech pos = new PartOfSpeech(2, rzeczownik2, przymiotnik2, czasownik2);
		
		wordManager.addPartOfSpeech(pos);
		wordManager.addWord(word);
		
		List<PartOfSpeech> poses = wordManager.getJoin();
		PartOfSpeech partRetrieved = poses.get(0);
		
		assertEquals(rzeczownik2, partRetrieved.getRzeczownik());
		assertEquals(przymiotnik2, partRetrieved.getPrzymiotnik());
		assertEquals(czasownik2, partRetrieved.getCzasownik());
		assertEquals(mianownik2, partRetrieved.getMianoswnik());
		assertEquals(dopelniacz2, partRetrieved.getDopelniacz());
		assertEquals(wolacz2, partRetrieved.getWolacz());
		
		wordManager.clearTablePartOfSpeech();
		wordManager.clearTableWord();
		
	}

	@Test
	public void checkGetAllPartOfSpeech() {
		wordManager.clearTablePartOfSpeech();
		PartOfSpeech pos = new PartOfSpeech(2, rzeczownik1, przymiotnik1,
				czasownik1);
		wordManager.addPartOfSpeech(pos);

		List<PartOfSpeech> poses = wordManager.getAllPartOfSpeech();
		PartOfSpeech posRetrieved = poses.get(0);

		assertEquals(2, posRetrieved.getId());
		assertEquals(rzeczownik1, posRetrieved.getRzeczownik());
		assertEquals(przymiotnik1, posRetrieved.getPrzymiotnik());
		assertEquals(czasownik1, posRetrieved.getCzasownik());

		wordManager.clearTablePartOfSpeech();

	}
	
	@Test 
	public void checkUpdateID() {
		wordManager.clearTableWord();
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);
		wordManager.addWord(word);
		assertEquals(1, wordManager.updateId_pos(NEW_ID, witchRecord));
		
		List<Word> words = wordManager.getAllWord();
		Word wordRetrieved = words.get(0);
		
		assertEquals(1, wordRetrieved.getId());
		assertEquals(NEW_ID, wordRetrieved.getId_pos());
		assertEquals(mianownik1, wordRetrieved.getMianownik());
		assertEquals(dopelniacz1, wordRetrieved.getDopelniacz());
		assertEquals(wolacz1, wordRetrieved.getWolacz());
		
		
		
		
		
		
	}
	
	@Test
	public void checkNewJoin() {
		wordManager.clearTablePartOfSpeech();
		wordManager.clearTableWord();
		Word word = new Word(2, 2, mianownik2, dopelniacz2, wolacz2);
		PartOfSpeech pos = new PartOfSpeech(33, rzeczownik2, przymiotnik2, czasownik2);
		
		wordManager.addPartOfSpeech(pos);
		wordManager.addWord(word);
		wordManager.updateId_pos(33, 2);
		
		List<PartOfSpeech> poses = wordManager.getJoin();
		PartOfSpeech partRetrieved = poses.get(0);
		
		assertEquals(33, partRetrieved.getId());
		assertEquals(33, partRetrieved.getId_pos());
		assertEquals(rzeczownik2, partRetrieved.getRzeczownik());
		assertEquals(przymiotnik2, partRetrieved.getPrzymiotnik());
		assertEquals(czasownik2, partRetrieved.getCzasownik());
		assertEquals(mianownik2, partRetrieved.getMianoswnik());
		assertEquals(dopelniacz2, partRetrieved.getDopelniacz());
		assertEquals(wolacz2, partRetrieved.getWolacz());
		
		
	
	
	}
	
	@Test
	public void checkDeleteJoin() {
		wordManager.clearTablePartOfSpeech();
		wordManager.clearTableWord();
		Word word = new Word(2, 2, mianownik2, dopelniacz2, wolacz2);
		PartOfSpeech pos = new PartOfSpeech(2, rzeczownik2, przymiotnik2, czasownik2);
		
		wordManager.addPartOfSpeech(pos);
		wordManager.addWord(word);
		wordManager.updateId_pos(33, 2);
		
		List<PartOfSpeech> poses = wordManager.getJoin();
		PartOfSpeech partRetrieved = poses.get(0);
		
		assertEquals(0, partRetrieved.getId());
		assertEquals(0, partRetrieved.getId_pos());
		assertEquals(rzeczownik2, partRetrieved.getRzeczownik());
		assertEquals(przymiotnik2, partRetrieved.getPrzymiotnik());
		assertEquals(czasownik2, partRetrieved.getCzasownik());
		assertEquals(null, partRetrieved.getMianoswnik());
		assertEquals(null, partRetrieved.getDopelniacz());
		assertEquals(null, partRetrieved.getWolacz());
		
		
	
	
	}
	
	
	
	@Test
	public void CheckDeleteFromWord() {
		//given
		wordManager.clearTablePartOfSpeech();
		wordManager.clearTableWord();
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);
		Word word2 = new Word(2,2, mianownik2, dopelniacz2, wolacz2);
		
		//when
		wordManager.addWord(word);
		wordManager.addWord(word2);
		//then
		assertEquals(1,wordManager.deleteRecordById(2));
		
	}
	
	@Test
	public void ShouldDeleteOnlyOneRecord() {
		wordManager.clearTablePartOfSpeech();
		wordManager.clearTableWord();
		
		Word word = new Word(1, 1, mianownik1, dopelniacz1, wolacz1);
		Word word2 = new Word(2,2, mianownik2, dopelniacz2, wolacz2);
		wordManager.addWord(word);
		wordManager.addWord(word2);
		
		wordManager.deleteRecordById(2);
		
		List<Word> words = wordManager.getAllWord();
		Word wordRetrieved = words.get(0);
		
		assertEquals(1, wordRetrieved.getId());
		assertEquals(1, wordRetrieved.getId_pos());
		assertEquals(mianownik1, wordRetrieved.getMianownik());
		assertEquals(dopelniacz1, wordRetrieved.getDopelniacz());
		assertEquals(wolacz1, wordRetrieved.getWolacz());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	

}
