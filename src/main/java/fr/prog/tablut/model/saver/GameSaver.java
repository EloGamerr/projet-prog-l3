package fr.prog.tablut.model.saver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;

import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;



public class GameSaver {
	private static final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private static final String savePrefix = "save-";
	private static final String saveSuffix = ".sv";
	private final Game game;
	private String currentSavePath = "";
	private final List<Game> saves = new ArrayList<>();

	////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////

	public GameSaver(Game game) {
		this.game = game;
	}

	 public GameSaver(Game game, String currentSavePath) {
		this.game = game;
		this.currentSavePath = currentSavePath;
	}

	////////////////////////////////////////////////////
	// Main Functions
	////////////////////////////////////////////////////

	public void save() {
		if(game.getCurrentSavePath().matches(""))
            newSave();
        else
			save_core(Paths.get(game.getCurrentSavePath()));
	}

	private void writeInFile(Path path, List<String> items, OpenOption... options) {
		try {
			Files.write(path, items, StandardCharsets.UTF_8, options);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void writeInFile(Path path, String content, OpenOption... options) {
		writeInFile(path, Collections.singletonList(content), options);
	}

	public void newSave() {
		save_core(Paths.get(newSaveName()));
	}

	public void save_core(Path path) {
		JSONObject jsonBoard = generateJSONBoard();
		JSONArray jsonArrayParameters = generateJSONParameters();
		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put("parameters", jsonArrayParameters);

		try {
			Files.createDirectories(path.getParent());

			if(!Files.exists(path)) {
				writeInFile(path, jsonParameters.toString(), StandardOpenOption.CREATE);
				writeInFile(path, jsonBoard.toString(), StandardOpenOption.APPEND);
			}
			else {
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			    lines.set(0, jsonParameters.toString());
			    lines.set(1, jsonBoard.toString());
			    writeInFile(path, lines);
			}

			setCurrentSavePath(path.toString());
			game.setCurrentSavePath(path.toString());

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	 ////////////////////////////////////////////////////
	 // JSON generators
	 ////////////////////////////////////////////////////

	public JSONObject generateJSONBoard() {
		JSONObject jsonBoard = new JSONObject();
		jsonBoard.put("board", saveBoard().toString());
		return jsonBoard;
	}

	public JSONArray generateJSONParameters() {
		JSONArray jsonParameters = new JSONArray();

		JSONObject jsonWinner = new JSONObject();
		JSONObject jsonPlayingPlayer = new JSONObject();
		JSONObject jsonPlays = new JSONObject();
		JSONObject jsonDefender = new JSONObject();
		JSONObject jsonDefenderName = new JSONObject();
		JSONObject jsonAttacker = new JSONObject();
		JSONObject jsonAttackerName = new JSONObject();

		jsonDefender.put("defender", PlayerTypeEnum.getFromPlayer(game.getDefender()).ordinal());
		jsonDefenderName.put("defenderName", game.getDefenderName());
		jsonAttacker.put("attacker", PlayerTypeEnum.getFromPlayer(game.getAttacker()).ordinal());
		jsonAttackerName.put("attackerName", game.getAttackerName());
		jsonWinner.put("winner",this.game.getWinner().toString());
		jsonPlayingPlayer.put("playingPlayer", game.getPlayingPlayerEnum().toString());
		jsonPlays.put("plays", savePlays().toString());

		jsonParameters.put(jsonDefender);
		jsonParameters.put(jsonDefenderName);
		jsonParameters.put(jsonAttacker);
		jsonParameters.put(jsonAttackerName);
		jsonParameters.put(jsonWinner);
		jsonParameters.put(jsonPlayingPlayer);
		jsonParameters.put(jsonPlays);

		return jsonParameters;
	}

	 ////////////////////////////////////////////////////
	 // StringBuilder functions
	 ////////////////////////////////////////////////////

	public StringBuilder saveBoard() {
		StringBuilder builder = new StringBuilder();
		CellContent cell = null;

		for(int l = 0; l < game.getRowAmout();l++) {
			for(int c = 0; c < game.getColAmout();c++) {
				cell = game.getCellContent(l, c);

				switch(cell) {
					case EMPTY: 		builder.append(SaverConstants.EMPTY); break;
					case ATTACK_TOWER: 	builder.append(SaverConstants.ATTACK_TOWER); break;
					case DEFENSE_TOWER: builder.append(SaverConstants.DEFENSE_TOWER); break;
					case KING: 			builder.append(SaverConstants.KING); break;
					case GATE: 			builder.append(SaverConstants.GATE); break;
					case KINGPLACE: 	builder.append(SaverConstants.KINGPLACE); break;
					default: break;
				}

				builder.append(SaverConstants.BLANK);
			}

			builder.append(SaverConstants.NEXT_LINE);
		}

		return builder;
	}


	public StringBuilder savePlays() {
		StringBuilder builder = new StringBuilder();

		for(Play move : game.getPlays().movements()) {
			builder.append(SaverConstants.BR_LEFT);
			builder.append(move.getMovement().fromL);
			builder.append(SaverConstants.COMMA);
			builder.append(move.getMovement().fromC);
			builder.append(SaverConstants.BR_RIGHT);

			builder.append(SaverConstants.BLANK);

			builder.append(SaverConstants.BR_LEFT);
			builder.append(move.getMovement().toC);
			builder.append(SaverConstants.COMMA);
			builder.append(move.getMovement().toL);
			builder.append(SaverConstants.BR_RIGHT);

			builder.append(SaverConstants.BR_RIGHT);
			builder.append(move.getMovement().fromL);
			builder.append(SaverConstants.COMMA);
			builder.append(move.getMovement().fromC);
			builder.append(SaverConstants.BR_LEFT);

			builder.append(SaverConstants.BR_RIGHT);
			builder.append(move.getMovement().toC);
			builder.append(SaverConstants.COMMA);
			builder.append(move.getMovement().toL);
			builder.append(SaverConstants.BR_LEFT);

			builder.append(SaverConstants.NEXT_LINE);
		}

		return builder;
	}

	private String newSaveName() {
		int index = 1;
		String savePath = Paths.get(savesPath, savePrefix + index + saveSuffix).toString();
		File f = new File(savePath);

		while(f.isFile()) {
			
			index++;
		    savePath = Paths.get(savesPath, savePrefix + index + saveSuffix).toString();
		    f = new File(savePath);
		   
		}
		System.out.println(savePath);
		return savePath;
	}

	////////////////////////////////////////////////////
	// Reload / Delete / overwrite saves
	////////////////////////////////////////////////////

	public void overwriteSave() {
		save_core(Paths.get(currentSavePath));
	}

	public Game reload(int index_game) {
		return saves.get(index_game);
	}

	public void delete(int index_game) {
		saves.remove(index_game);
	}

	////////////////////////////////////////////////////
	// Getter and Setters functions
	////////////////////////////////////////////////////

	public String getCurrentSavePath() {
		return currentSavePath;
	}

	public void setCurrentSavePath(String path) {
		currentSavePath = path;
	}

	public List<Game> getSaves() {
		return saves;
	}
}
