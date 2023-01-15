package tk.lukashuth.draconem.utils;

import java.util.ArrayList;

public class Leaderboard {
    private ArrayList<Player> players;

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public ArrayList<Player> getPlayers() { return this.players; }
    public Leaderboard()
    {
        this.players = new ArrayList<>();
        this.players.add(new Player(0.0, "Lukas", 5000, 500));
        this.players.add(new Player(0.0, "Kira", 4500, 500));
        // TODO: load players from json
    }
    public void sort()
    {
        // TODO: sort players after money
        this.save();
    }
    public void save()
    {
        // TODO: save leaderboard
    }
}
