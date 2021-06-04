package fr.prog.tablut.model.saver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;

public class GameLoader {
	private static final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private static final String savePrefix = "save-";
	private static final String saveSuffix = ".sv";
	private String currentSavePath;
	private Game game;

	////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////

	public GameLoader(Game game) {
		this.game = game;
	}

	////////////////////////////////////////////////////
	// Main function
	////////////////////////////////////////////////////

	public boolean loadData(int index_Save) {
		try {
			Path path = Paths.get(savesPath);
			Files.createDirectories(path);
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}

		String savePath = Paths.get(savesPath, savePrefix + index_Save + saveSuffix).toString();

		this.currentSavePath = savePath;
		game.setCurrentSavePath(currentSavePath);
		File save = new File(savePath);
        Scanner scanner = null;

		try {
			scanner = new Scanner(save);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		if(scanner == null)
			throw new RuntimeException();

		int lineNumber = 0;

		while(scanner.hasNextLine()) {
			String lineContent = scanner.nextLine();

			if(lineNumber == 0 && !loadParameters(lineContent))
				return false;
			
			lineNumber++;
		}

		return true;
	}
	
	

	////////////////////////////////////////////////////
	// Load Functions
	////////////////////////////////////////////////////
	public boolean loadParameters(String line) {
		try {
			JSONObject jsonParameters = new JSONObject(line);
			JSONArray array = jsonParameters.getJSONArray("parameters");
			
			PlayerTypeEnum defender = getDefender(new JSONObject(array.getString(0)).getInt("defender"));
			String defenderName = new JSONObject(array.getString(1)).getString("defenderName");
			PlayerTypeEnum attacker = getDefender(new JSONObject(array.getString(2)).getInt("attacker"));
			String attackerName = new JSONObject(array.getString(3)).getString("attackerName");
			game.start(attacker, defender, attackerName,defenderName);
			setPlays(new JSONObject(array.getString(6)).getString("plays"));

		}
		catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	

	////////////////////////////////////////////////////
	// Setter and Getters
	////////////////////////////////////////////////////
	public void setWinner(String winner) {
		if(winner.matches("Attacker"))
			game.setWinner(PlayerEnum.ATTACKER);
		if(winner.matches("Defender"))
			game.setWinner(PlayerEnum.DEFENDER);
		if(winner.matches("None"))
			game.setWinner(PlayerEnum.NONE);
	}

	public void setPlayingPlayer(String playingPlayer) {
		if(playingPlayer.matches("Attacker"))
			game.setPlayingPlayer(PlayerEnum.ATTACKER);
		if(playingPlayer.matches("Defender"))
			game.setPlayingPlayer(PlayerEnum.DEFENDER);
	}


	public PlayerTypeEnum getAttacker(int attacker) {
		PlayerTypeEnum[] values = PlayerTypeEnum.values();

		if(values.length == 0 || attacker < 0 || attacker >= values.length) {
			game.setAttacker(PlayerTypeEnum.getDefaultPlayer());
			return null;
		}

		return values[attacker];
	}

	public PlayerTypeEnum getDefender(int defender) {
		PlayerTypeEnum[] values = PlayerTypeEnum.values();

		if(values.length == 0 || defender < 0 || defender >= values.length) {
			game.setDefender(PlayerTypeEnum.getDefaultPlayer());
			return null;
		}

		return values[defender];
	}

	public void setPlays(String playsString) {
		String toOrFrom = SaverConstants.NEXT_LINE;
		String lOrC = SaverConstants.BR_LEFT;
        int index = 0;
        Movement movement = new Movement();

    	while(index < playsString.length()) {
    		switch(playsString.charAt(index)) {
    		
    			case ' ':
    				toOrFrom = SaverConstants.BLANK;
    				break;
    			case '\n':
    				game.move(movement.fromL, movement.fromC, movement.toL,movement.toC);
    				toOrFrom = SaverConstants.NEXT_LINE;
    				break;
    			case '(':
    				lOrC = SaverConstants.BR_LEFT;
    				break;
    			case ',':
    				lOrC = SaverConstants.COMMA;
    				break;
    			case ')':
    				break;
    			default:

    				if(toOrFrom.equals(SaverConstants.NEXT_LINE) && lOrC.equals(SaverConstants.BR_LEFT)) {
    	    			movement.setFromL(Character.getNumericValue(playsString.charAt(index)));
    				break;}
    	    		else if(toOrFrom == SaverConstants.NEXT_LINE && lOrC == SaverConstants.COMMA) {
    	    			movement.setFromC(Character.getNumericValue(playsString.charAt(index)));
    				break;}
    	    		else if(toOrFrom == SaverConstants.BLANK && lOrC == SaverConstants.BR_LEFT) {
    	    			movement.setToL(Character.getNumericValue(playsString.charAt(index)));
    				break;}
    	    		else if(toOrFrom == SaverConstants.BLANK && lOrC == SaverConstants.COMMA) {
    	    			movement.setToC(Character.getNumericValue(playsString.charAt(index)));
    				break;}
    				
    		}

    		index++;
    	}
	}
	
	public String getCurrentSavePath() {
		return this.currentSavePath;
	}
}