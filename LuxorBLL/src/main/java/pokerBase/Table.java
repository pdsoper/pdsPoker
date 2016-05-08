package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Table implements Serializable {
	
	@XmlElement
	private UUID TableID;
	
	@XmlElement
	private HashMap<UUID, Player> HashMapPlayer = new HashMap<UUID, Player>();
	
	public Table()
	{
		TableID = UUID.randomUUID();
	}
	
	
	public UUID getTableID() {
		return TableID;
	}


	public Table AddPlayerToTable(Player p)
	{
		HashMapPlayer.put(p.getPlayerID(), p);
		return this;
	}
	public Table RemovePlayerFromTable(Player p)
	{
		HashMapPlayer.remove(p.getPlayerID());
		return this;	
	}
	public HashMap getHashPlayers() {
		return HashMapPlayer;
	}
	
	public Player getPlayerByPosition(int iPlayerPosition)
	{
		Player pl = null;
		
		Iterator it = getHashPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player)pair.getValue();
			if (p.getiPlayerPosition() == iPlayerPosition)
				pl = p;
		}
		
		return pl;
	}
	
	public Player PickRandomPlayerAtTable()
	{
		List<Player> listPlayers = new ArrayList<Player>(getHashPlayers().values());
		Collections.shuffle(listPlayers);
		return listPlayers.get(0);
		
	}
	public static Table CloneTable(Table t)
	{
		Table t1 = new Table();
		t1.TableID = t.TableID;
		Iterator it = t.getHashPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			t1.AddPlayerToTable((Player)pair.getValue());
		}
		
		return t1;
	}
	public static void StateOfTable(Table t)
	{
		System.out.println("----------------------");
		System.out.println("Table : " + t.TableID);
		System.out.println("Table Nbr of Players: " + t.getHashPlayers().size());
		Iterator it = t.getHashPlayers().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player)pair.getValue();
			
			System.out.println("Player ID: " + p.getPlayerID().toString());
			System.out.println("Player Position: " + p.getiPlayerPosition());
			System.out.println("Player Name: " + p.getPlayerName());
			System.out.println("----------------------");
		}
		
		System.out.println("----------------------");
		System.out.println(" ");
	}
}
