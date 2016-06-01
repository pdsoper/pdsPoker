package pokerBase;

import pokerEnums.ActionOption;

public class Action {

	private ActionOption actOpt;

	private Player ActPlayer;
	
	public Action()	{
	}
	
	public Action(ActionOption eAction, Player player) {
		super();
		this.actOpt = eAction;
		this.ActPlayer = player;
	}

	public ActionOption getActionOption() {
		return actOpt;
	}
	
	public void setActionOption(ActionOption actOpt) {
		actOpt = actOpt;
	}
	
	public Player getPlayer() {
		return ActPlayer;
	}
	
	public void setPlayer(Player player) {
		this.ActPlayer = player;
	}
}
