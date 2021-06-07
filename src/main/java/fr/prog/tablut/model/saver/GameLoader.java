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

import org.json.JSONArray;
import org.json.JSONObject;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.controller.game.ia.AIRandom;
import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.player.PlayerEnum;

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

			if((lineNumber == 0 && !loadParameters(lineContent))
			|| (lineNumber != 0 && !loadBoard(lineContent)))
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

			setDefender(new JSONObject(array.getString(0)).getString("defender"));
			setAttacker(new JSONObject(array.getString(1)).getString("attacker"));
			
			setWinner(new JSONObject(array.getString(2)).getString("winner"));
			setPlayingPlayer(new JSONObject(array.getString(3)).getString("playingPlayer"));
			setPlays(new JSONObject(array.getString(4)).getString("plays"));

		}
		catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean loadBoard(String line) {
		String board;

	 	try {
			board = new JSONObject(line).getString("board");
		}
		catch(NoSuchElementException | ParseException e) {
			e.printStackTrace();
			return false;
		}



    	int index = 0;
    	int currColumn = 0;
    	int currLine = 0;
    	String val = "";

    	while(index < board.length()) {
    		if(board.charAt(index) == ' ') {
    			if(val.matches(SaverConstants.ATTACK_TOWER))
    				game.setContent(CellContent.ATTACK_TOWER, currLine, currColumn);
    			else if(val.matches(SaverConstants.DEFENSE_TOWER))
    				game.setContent(CellContent.DEFENSE_TOWER, currLine, currColumn);
    			else if(val.matches(SaverConstants.GATE))
    				game.setContent(CellContent.GATE, currLine, currColumn);
    			else if(val.matches(SaverConstants.KING))
    				game.setContent(CellContent.KING, currLine, currColumn);
    			else if(val.matches(SaverConstants.KINGPLACE))
    				game.setContent(CellContent.KINGPLACE, currLine, currColumn);

    			val = "";
    			currColumn++;
    		}
    		else if(board.charAt(index) == '\n'){
    			currColumn = 0;
    			currLine++;
    		}
    		else {
    			val += board.charAt(index);
    		}

    		index++;
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

	public void setAttacker(String attacker) {
		switch(attacker) {
			case "AIRandom": 	game.setAttacker(new AIRandom(PlayerEnum.ATTACKER)); break;
			case "HumanPlayer": game.setDefender(new HumanPlayer(PlayerEnum.DEFENDER)); break;
			default: break;
		}
	}

	public void setDefender(String defender) {
		switch(defender) {
			case "AIRandom": 	game.setAttacker(new AIRandom(PlayerEnum.ATTACKER)); break;
			case "HumanPlayer": game.setDefender(new HumanPlayer(PlayerEnum.DEFENDER)); break;
			default: break;
		}
	}

	public void setPlays(String playsString) {
		String toOrFrom = SaverConstants.NEXT_LINE;
		String lOrC = "";
        int index = 0;
        Movement movement = new Movement();

    	while(index < playsString.length()) {
    		switch(playsString.charAt(index)) {
    			case ' ':
    				toOrFrom = SaverConstants.BLANK;
    				break;
    			case '\n':
    				game.getPlays().movements().add(new Play(movement));
    				toOrFrom = SaverConstants.NEXT_LINE;
    				break;
    			case '(':
    				toOrFrom = SaverConstants.BR_LEFT;
    				break;
    			case ',':
    				toOrFrom = SaverConstants.COMMA;
    				break;
    			case ')':
    				break;
    			default:
    				if(toOrFrom == SaverConstants.NEXT_LINE && lOrC == SaverConstants.BR_LEFT)
    	    			movement.setFromL(playsString.charAt(index));
    	    		else if(toOrFrom == SaverConstants.NEXT_LINE && lOrC == SaverConstants.COMMA)
    	    			movement.setFromC(playsString.charAt(index));
    	    		if(toOrFrom == SaverConstants.BLANK && lOrC == SaverConstants.BR_LEFT)
    	    			movement.setToL(playsString.charAt(index));
    	    		else if(toOrFrom == SaverConstants.BLANK && lOrC == SaverConstants.COMMA)
    	    			movement.setToC(playsString.charAt(index));
    				break;
    		}

    		index++;
    	}
	}

	public String getCurrentSavePath() {
		return this.currentSavePath;
	}
}