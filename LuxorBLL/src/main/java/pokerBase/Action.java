package pokerBase;

import pokerEnums.ActionOption;
import pokerEnums.BettingOption;
import pokerEnums.GameOption;
import pokerEnums.JokerOption;
import pokerEnums.WildCardOption;

public class Action {

	private ActionOption actOpt;
	private Player player;
	private int position;
	private GameOption gameOpt;
	private JokerOption jokerOpt;
	private WildCardOption wildOpt;
	private BettingOption betOpt;
	
	public Action()	{
	}
	
	public Action(ActionOption actOpt, int position) {
		super();
		this.actOpt = actOpt;
		this.position = position;
	}

	public ActionOption getActionOption() {
		return actOpt;
	}
	
	public void setActionOption(ActionOption actOpt) {
		this.actOpt = actOpt;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	public GameOption getGameOpt() {
		return gameOpt;
	}

	public void setGameOpt(GameOption gameOpt) {
		this.gameOpt = gameOpt;
	}

	public JokerOption getJokerOpt() {
		return jokerOpt;
	}

	public void setJokerOpt(JokerOption jokerOpt) {
		this.jokerOpt = jokerOpt;
	}

	public WildCardOption getWildOpt() {
		return wildOpt;
	}

	public void setWildOpt(WildCardOption wildOpt) {
		this.wildOpt = wildOpt;
	}

	public BettingOption getBetOpt() {
		return betOpt;
	}

	public void setBetOpt(BettingOption betOpt) {
		this.betOpt = betOpt;
	}
}
