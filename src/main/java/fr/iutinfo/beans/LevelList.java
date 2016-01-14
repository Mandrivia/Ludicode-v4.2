package fr.iutinfo.beans;

import java.util.List;

public class LevelList {
	private int id;
	private String name;
	private int levelCount;
	private List<LevelListAssociation> levelsAssociation;
	private List<Level> levels;
	private int idAuthor;
	private int idView;
	
	public int getIdView() {
		return idView;
	}

	public void setIdView(int idView) {
		this.idView = idView;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public List<LevelListAssociation> getLevelsAssociation() {
		return levelsAssociation;
	}

	public void setLevelsAssociation(List<LevelListAssociation> levelsAssociation) {
		this.levelsAssociation = levelsAssociation;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}

	public int getIdAuthor() {
		return idAuthor;
	}

	public void setIdAuthor(int idAuthor) {
		this.idAuthor = idAuthor;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
	public String toString() {
		return this.name + ";" + this.id + ";" + this.idAuthor + ";" + this.idView + ";" + this.levelCount + ";" + this.levels + ";" + this.levelsAssociation;
	}
	
}
