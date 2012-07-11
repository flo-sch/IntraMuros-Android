package app.intramuros.fr.classes.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;
import app.intramuros.fr.vendors.IMDateFormatter;

public class User implements Serializable {
	private static final long serialVersionUID = 6816218199982487747L;
	private static final String TAG = "IM-Model-User";
	protected String id;
	protected Calendar registredAt;
	protected String username;
	protected String email;
	protected Integer score;
	protected Integer bonus;
	protected Integer sessionsOpened;
	protected Integer sessionsWon;
	protected Team team;
	protected String avatar;
	
	public User() {
		Log.i(TAG, "Empty __construct()");
	}
	
	public User(String id, Calendar registredAt, String username, String email, Integer score, Integer bonus, Integer sessionsOpened, Integer sessionsWon, Team team, String avatar) {
		Log.i(TAG, "values __construct()");
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
		Log.i(TAG, "JSON __construct()");
	}
	
	@SuppressWarnings("unchecked")
	public User(HashMap<String, Object> attributes) {
		Log.i(TAG, "HashMap __construct()");
		this.id = (String) attributes.get("id");
		
		HashMap <String, Object> registredAt = (HashMap<String, Object>) attributes.get("registredAt");
		IMDateFormatter formatter = new IMDateFormatter();
		this.registredAt = formatter.getCalendarFromJson((String) registredAt.get("date"));
		
		
		this.username = (String) attributes.get("username");
		this.email = (String) attributes.get("email");
		this.score = (Integer) attributes.get("score");
		this.bonus = (Integer) attributes.get("bonus");
		this.sessionsOpened = (Integer) attributes.get("sessionsOpened");
		this.sessionsWon = (Integer) attributes.get("sessionsWon");
		
		this.team = new Team((HashMap<String, Object>) attributes.get("team"));
		this.avatar = (String) attributes.get("avatar");
	}
	
	public void initWithAttributes(HashMap<String, Object> attributes) {
		Log.i(TAG, "HashMap initWithAttributes");
		this.id = (String) attributes.get("id");
		this.registredAt = (Calendar) attributes.get("registredAt");
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
	
	public Calendar getRegistredAt() {
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
