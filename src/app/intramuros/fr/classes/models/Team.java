package app.intramuros.fr.classes.models;

import java.io.Serializable;
import java.util.HashMap;

public class Team implements Serializable {
	private static final long serialVersionUID = -5325782059242634848L;
	protected String id;
	protected Integer indice;
	protected String name;
	protected Integer score;
	
	public Team(String id, Integer indice, String name, Integer score) {
		this.id = id;
		this.indice = indice;
		this.name = name;
		this.score = score;
	}
	
	public Team(HashMap<String, Object> attributes) {
		this.id = (String) attributes.get("id");
		this.indice = (Integer) attributes.get("indice");
		this.name = (String) attributes.get("name");
		this.score = (Integer) attributes.get("score");
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setIndice(Integer indice) {
		this.indice = indice;
	}
	
	public Integer getIndice() {
		return this.indice;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getScore() {
		return this.score;
	}
}
