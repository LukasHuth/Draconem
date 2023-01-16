package tk.lukashuth.draconem.utils;

import tk.lukashuth.draconem.menu.GameMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Game {
    private Playfield playfield;
    private long gameId=0;
    private ArrayList<Player> players;
    private int playerCount;
    public void setPlayerCount(int pc) { this.playerCount = pc; }
    public int getPlayerCount() { return this.playerCount; }
    public void addPlayer(String name){ this.players.add(new Player(name, this));}
    public boolean hasPlayer(String name)
    {
        boolean t = false;
        for(Player p : this.players)
        {
            if(p.getName().equalsIgnoreCase(name)) t= true;
        }
        return t;
    }
    public Playfield getPlayfield() { return this.playfield; }
    public ArrayList<Player> getPlayers() { return this.players; }
    public Player getPlayer(int index) { return this.players.get(index); }
    private GameMenu parent;
    public GameMenu getParent() { return this.parent; }
    public Game(GameMenu parent)
    {
        this.parent = parent;
        this.playerCount = 0;
        this.players = new ArrayList<>();
        this.gameId = System.currentTimeMillis();
        this.activePlayer = 0;
        this.neededInput = false;
        this.playfield = new Playfield(this);
        this.playfield.generateMap();
    }
    private int activePlayer;
    private boolean neededInput;
    public boolean execute()
    {
        boolean needsInput = false;
        Player p = this.getPlayer(activePlayer%this.playerCount);
        Field f = this.playfield.getField(p.getPosition());
        needsInput = f.execute(p);
        if(needsInput) return true;
        this.advancePlayer(p);
        return false;
    }
    private void advancePlayer(Player p)
    {
        activePlayer++;
        p.move(Math.round(Math.random()*6+1));
    }
    public boolean giveInput(int input)
    {
        Player p = this.getPlayer(activePlayer%this.playerCount);
        Field f = this.playfield.getField(p.getPosition());
        boolean neededInput = f.giveInput(input, p);
        if(neededInput) return true;
        this.advancePlayer(p);
        return false;
    }
    public boolean isFinished()
    {
        for(Player p : this.players)
        {
            if(!p.isFinished()) return false;
        }
        return true;
    }
    public void sortPlayers()
    {
        this.players.sort((o1, o2) -> o2.getMoney() - o1.getMoney());
    }
}
