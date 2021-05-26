package fr.prog.tablut.model.saver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.json.JSONObject;

import fr.prog.tablut.model.Game;

public class GameLoader {
 
	private static final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private String currentSavePath;
	
	private final Game game;
	
	GameLoader(Game game){
		this.game = game;
		this.currentSavePath = null;
	}
	
	public boolean loadData() {
		int currLine = 0;
		
		try {
			Path path = Paths.get(savesPath);
			Files.createDirectories(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		this.currentSavePath = savesPath;
		
		
		File save = new File(savesPath);
	
        Scanner scanner = null;
        
		try {
			scanner = new Scanner(save);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (scanner == null) throw new RuntimeException();
		
		 while(scanner.hasNextLine()) {
	            String line = scanner.nextLine();

	            if (currLine == 0) {
	            	String board;
	            	
	            	try {
						board = new JSONObject(line).getString("board");
					} catch (NoSuchElementException | ParseException e) {
						e.printStackTrace();
						return false;
					}
		
	            	int index = 0;
	            	int currColumn = 0;
	            	int realLine = 0;
	            	String val = "";
	            
	            	while (index < board.length()) {
	            		if (board.charAt(index) == ' ') {
	            			
	            		}
	            	}   
	            }
	    }     
		return true;
	}
	
	
	public String getCurrentSavePath() {
		return this.currentSavePath;
	}
}
