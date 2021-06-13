package fr.prog.tablut.model.saver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Collections;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.model.game.Game;

/**
 * Classe permettant la sauvegarde de la partie en cours dans un fichier en format JSON
 */
public class GameSaver {
    private static GameSaver gameSaver;
	private static final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private static final String savePrefix = "save-";
	private static final String saveSuffix = ".sv";
	private String currentSavePath = "";

	////////////////////////////////////////////////////
	// Constructors
	////////////////////////////////////////////////////
	/*
 	* Si c'est une nouvelle classe, le path de sauvegarde sera généré automatiquement
	* sinon le path est passé en paramètre
 	*/
	public GameSaver() {

	}
	 public GameSaver(String currentSavePath) {
		this.currentSavePath = currentSavePath;
	}

	////////////////////////////////////////////////////
	// Main Functions
	////////////////////////////////////////////////////
	/*
 	* Va chercher l'instance de GameSaver
 	*/
	public static GameSaver getInstance() {
		if(gameSaver == null) {
			gameSaver = new GameSaver();
		}

		return gameSaver;
	}

	/*
 	* Sauvegarde de la partie
 	*/
	public void save() {
		if(Game.getInstance().getCurrentSavePath().matches(""))
            newSave();
        else
			save_core(Paths.get(Game.getInstance().getCurrentSavePath()));
	}

    private void writeInFile(Path path, List<String> items, OpenOption... options) {
		try {
			Files.write(path, items, StandardCharsets.UTF_8, options);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/*
 	* Va appeller la fonction qui va écraser la sauvegarde du jeu courant
 	*/
	private void writeInFile(Path path, String content) {
		try {
			Files.write(path, Collections.singletonList(content), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/*
 	* Sauvegarde de la nouvelle partie
 	*/
	public void newSave() {
		save_core(Paths.get(newSaveName()));
	}

	/*
 	* Fonction de sauvegarde principale
 	*/
	public void save_core(Path path) {
		JSONArray jsonArrayParameters = generateJSONParameters();
		JSONObject jsonParameters = new JSONObject();

		jsonParameters.put("parameters", jsonArrayParameters);

		try {
			Files.createDirectories(path.getParent());

			if(!Files.exists(path)) {
				writeInFile(path, jsonParameters.toString());
			}
			else {
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			    lines.set(0, jsonParameters.toString());
			    writeInFile(path, lines);
			}

			setCurrentSavePath(path.toString());
			Game.getInstance().setCurrentSavePath(path.toString());

		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	 ////////////////////////////////////////////////////
	 // JSON generators
	 ////////////////////////////////////////////////////

	/*
 	* va générer les paramètres du jeu à sauvegarder en format JSON
 	*/
	public JSONArray generateJSONParameters() {
		JSONArray jsonParameters = new JSONArray();

		JSONObject jsonWinner = new JSONObject();
		JSONObject jsonPlayingPlayer = new JSONObject();
		JSONObject jsonPlays = new JSONObject();
		JSONObject jsonDefender = new JSONObject();
		JSONObject jsonDefenderName = new JSONObject();
		JSONObject jsonAttacker = new JSONObject();
		JSONObject jsonAttackerName = new JSONObject();

		jsonDefender.put("defender", Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender())).ordinal());
		jsonDefenderName.put("defenderName", Game.getInstance().getDefenderName());
		jsonAttacker.put("attacker", Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker())).ordinal());
		jsonAttackerName.put("attackerName", Game.getInstance().getAttackerName());
		jsonWinner.put("winner", Game.getInstance().getWinner().toString());
		jsonPlayingPlayer.put("playingPlayer", Game.getInstance().getPlayingPlayerEnum().toString());
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

	/*
 	* Construit la sauvegarde des coups joués dans la partie
 	*/
	public StringBuilder savePlays() {
		StringBuilder builder = new StringBuilder();

		for(Play move : Game.getInstance().getPlays().getPlays()) {
			builder.append(SaverConstants.BR_LEFT);
			builder.append(move.getMovement().getFromC());
			builder.append(SaverConstants.COMMA);
			builder.append(move.getMovement().getFromL());
			builder.append(SaverConstants.BR_RIGHT);

			builder.append(SaverConstants.BLANK);

			builder.append(SaverConstants.BR_LEFT);
			builder.append(move.getMovement().getToC());
			builder.append(SaverConstants.COMMA);
			builder.append(move.getMovement().getToL());
			builder.append(SaverConstants.BR_RIGHT);

			builder.append(SaverConstants.NEXT_LINE);
		}

		return builder;
	}
	/*
 	* Génère un nouveau nom de sauvegarde
 	*/
	private String newSaveName() {
		int index = 1;
		String savePath = Paths.get(savesPath, savePrefix + index + saveSuffix).toString();
		File f = new File(savePath);

		while(f.isFile()) {
			index++;
		    savePath = Paths.get(savesPath, savePrefix + index + saveSuffix).toString();
		    f = new File(savePath);
		}

		return savePath;
	}

	////////////////////////////////////////////////////
	// Delete / overwrite saves
	////////////////////////////////////////////////////


	public void overwriteSave() {
		save_core(Paths.get(currentSavePath));
	}

	public boolean delete(int index_game) {
        Path p = Paths.get(savesPath + "/" + savePrefix + index_game + saveSuffix);
        File f = p.toFile();

        if(!f.exists() ||!f.isFile()) {
            System.out.println("[Error] GameSaver::delete : File not found or not a file.");
            return false;
        }

        if(!f.canWrite()) {
            System.out.println("[Error] GameSaver::delete : Don't have rights on it.");
            return false;
        }

        try {
            if(!Files.deleteIfExists(p)) {
                System.out.println("[Error] GameSaver::delete : Failed to delete save (Surely the buffer that's not closed).");
                return false;
            }
        } catch(IOException e) {
            System.out.println("[Error] GameSaver::delete : Failed to delete save.");
            e.printStackTrace();
            return false;
        }

        return true;
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

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public ArrayList<Couple<String, Integer>> getSavesNames() {
        int i = 0;
		File folder = new File(savesPath);
		if(!folder.exists() || folder.isFile()) {
			folder.mkdir();
		}
        String[] files = folder.list();
        ArrayList<Couple<String, Integer>> saves = new ArrayList<>();

        // recover saves
		for(String f : Objects.requireNonNull(files)) {
            if(f.startsWith(savePrefix) && f.endsWith(saveSuffix)) {
                int fi = Integer.parseInt(f.replace(savePrefix, "").replace(saveSuffix, ""));
                saves.add(new Couple<>(generateSaveName(new File(savesPath + "/" + f), i++), fi));
            }
		}

        return saves;
	}


	private String generateSaveName(File f, int i) {
        Scanner scanner = null;

		try {
			scanner = new Scanner(f);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		if(scanner == null)
			throw new RuntimeException();

        if(scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            scanner.close();
            return getData(nextLine, f, i);
        }

        scanner.close();

		return "Erreur de lecture";
	}


	private String getData(String nextLine, File f, int i) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String date = "(" + sdf.format(f.lastModified()) + ")";

            JSONObject jsonParameters = new JSONObject(nextLine);
			JSONArray array = jsonParameters.getJSONArray("parameters");

			String defenderName = new JSONObject(array.getString(1)).getString("defenderName");
			String attackerName = new JSONObject(array.getString(3)).getString("attackerName");

			return "Partie " + (i+1) + " : " + date + "   " + attackerName + "  vs  " + defenderName;
		}
		catch (ParseException e) {
			e.printStackTrace();
			return "Erreur de lecture";
		}
	}
}
