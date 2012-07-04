package app.intramuros.fr.classes.models;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class User {
	protected String id;
	protected GregorianCalendar registredAt;
	protected String username;
	protected String email;
	protected Integer score;
	protected Integer bonus;
	protected Integer sessionsOpened;
	protected Integer sessionsWon;
	protected Team team;
	protected String avatar;
	
	public User(String id, GregorianCalendar registredAt, String username, String email, Integer score, Integer bonus, Integer sessionsOpened, Integer sessionsWon, Team team, String avatar) {
		this.id = id;
		this.registredAt = registredAt;
		this.username = username;
		this.email = email;
		this.score = score;
		this.bonus = bonus;
		this.sessionsOpened = sessionsOpened;
		this.sessionsWon = sessionsWon;
		this.team = team;
		this.avatar = avatar;
	}
	
	public User(JSONObject attributes) {
		
	}
	
	public User(HashMap<String, Object> attributes) {
		this.id = (String) attributes.get("id");
		this.registredAt = (GregorianCalendar) attributes.get("registredAt");
		this.username = (String) attributes.get("username");
		this.email = (String) attributes.get("email");
		this.score = (Integer) attributes.get("score");
		this.bonus = (Integer) attributes.get("bonus");
		this.sessionsOpened = (Integer) attributes.get("sessionsOpened");
		this.sessionsWon = (Integer) attributes.get("sessionsWon");
		this.team = (Team) attributes.get("team");
		this.avatar = (String) attributes.get("avatar");
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setRegistredAt(GregorianCalendar registredAt) {
		this.registredAt = registredAt;
	}
	
	public GregorianCalendar getRegistredAt() {
		return this.registredAt;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getScore() {
		return this.score;
	}
	
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	
	public Integer getBonus() {
		return this.bonus;
	}

	public void setSessionsOpened(Integer sessionsOpened) {
		this.sessionsOpened = sessionsOpened;
	}
	
	public Integer SessionsOpened() {
		return this.sessionsOpened;
	}
	
	public void setSessionsWon(Integer sessionsWon) {
		this.sessionsWon = sessionsWon;
	}
	
	public Integer getSessionsWon() {
		return this.sessionsWon;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getAvatar() {
		return this.avatar;
	}
}
