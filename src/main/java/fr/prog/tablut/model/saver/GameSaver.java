package fr.prog.tablut.model.saver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import fr.prog.tablut.model.CellContent;
import fr.prog.tablut.model.Game;
import fr.prog.tablut.model.Movement;


public class GameSaver {
	private static final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private static final String savePrefix = "save-";
	private static final String saveSuffix = ".sv";
	private final Game game;
	private String currentSavePath;
	
	private static int saves_limit = 10;
	private final List<Game> saves = new ArrayList<>();

	 public GameSaver(Game game){
		 this.game = game; 
	}
	 
	 public GameSaver(Game game, String currentSavePath){
		 this.game = game; 
		 this.currentSavePath = currentSavePath;
	}
	 
	public void saveToFile() {
		save(Paths.get(this.generateSaveName()));
	}
	
	private void save(Path path) {
		JSONObject jsonBoard = this.generateJSONBoard();
		JSONObject jsonParameters = this.generateJSONParameters();
		
		try {
			Files.createDirectories(path.getParent());
			
			if (!Files.exists(path)) {
				
				Files.write(path, Collections.singletonList(jsonBoard.toString()), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
				Files.write(path, Collections.singletonList(jsonParameters.toString()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			} else {
				
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			    lines.set(0, jsonBoard.toString());
			    lines.set(jsonBoard.toString().length(), jsonParameters.toString());
			    Files.write(path, lines, StandardCharsets.UTF_8);
			}	
			
			this.setCurrentSavePath(path.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private JSONObject generateJSONBoard() {
		JSONObject jsonBoard = new JSONObject();
		jsonBoard.put("board", saveBoard().toString());
		
		return jsonBoard;
	}
	
	public StringBuilder saveBoard() {
		StringBuilder builder = new StringBuilder();
		CellContent cell = null;
		for(int l = 0; l < game.getRowAmout();l++) {
			for(int c = 0; c < game.getColAmout();c++) {
				cell = game.getCellContent(l, c);
				
				switch(cell) {
					case EMPTY:
							builder.append(SaverConstants.EMPTY);
						break;
						
					case ATTACK_TOWER:
							builder.append(SaverConstants.ATTACK_TOWER);
						break;
						
					case DEFENSE_TOWER:
							builder.append(SaverConstants.DEFENSE_TOWER);
						break;
						
					case KING:
							builder.append(SaverConstants.KING);
						break;
						
					case GATE:
							builder.append(SaverConstants.GATE);
						break;
				}
				builder.append(SaverConstants.BLANK);
			}
			builder.append(SaverConstants.NEXT_LINE);
		}
		return builder;
	}
	
	
	public StringBuilder savePlays() {
		StringBuilder builder = new StringBuilder();
		for(Movement move : game.getPlays().movements()) {
			
			builder.append(SaverConstants.BR_RIGHT);
			builder.append(move.fromL);
			builder.append(SaverConstants.COMMA);
			builder.append(move.fromC);
			builder.append(SaverConstants.BR_LEFT);
			
			builder.append(SaverConstants.BR_RIGHT);
			builder.append(move.toC);
			builder.append(SaverConstants.COMMA);
			builder.append(move.toL);
			builder.append(SaverConstants.BR_LEFT);
			
			builder.append(SaverConstants.BLANK);
		}
		return builder;
	}
	
	private JSONObject generateJSONParameters() {
		JSONObject jsonParameters = new JSONObject();
		JSONObject winner = new JSONObject();
		JSONObject playingPlayer = new JSONObject();
		JSONObject plays = new JSONObject();
		
		winner.put("Winner",this.game.getWinner());
		playingPlayer.put("playingPlayer", game.getPlayingPlayer());
		plays.put("plays", savePlays());
		
		
		return jsonParameters;
	}
	

	private String generateSaveName() {
		int index = 1;
		String savePath = Paths.get(savesPath, savePrefix + index + saveSuffix).toString();
		File f = new File(savePath);
		
		while (f.isFile()) { 
		    savePath = Paths.get(savesPath, savePrefix + ++index + saveSuffix).toString();
		    f = new File(savePath);
		}
		
		return savePath;
	}
	
	public void overwriteSave() {
		save(Paths.get(currentSavePath));
	}
	
	
	public String getCurrentSavePath() {
		return this.currentSavePath;
	}
	
	public void setCurrentSavePath(String path) {
		this.currentSavePath = path;
	}
	
	public Game reload(int index_game) {
		return saves.get(index_game);
	}
	
	
	public void delete(int index_game) {
		saves.remove(index_game);
	}
	
	public List<Game> getSaves() {
		return saves;
	}
}
