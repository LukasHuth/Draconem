package tk.lukashuth.draconem.utils;

import tk.lukashuth.draconem.fields.*;

import java.util.HashMap;

public class Playfield {
    private HashMap<Double, Field> playfield;
    private Game parent;
    public Field getField(double pos) { return this.playfield.get(pos); };
    public Playfield(Game parent){
        this.playfield = new HashMap<>();
        this.parent = parent;
    }
    private static final double boardSize = 25;
    public void generateMap()
    {
        // TODO: generate map dynamically
        // TODO: split field
        // TODO: class fields
        // TODO: class specific texts
        for(int i = 0;i < boardSize; i++)
        {
            if(i%4 == 0)
            {
                this.playfield.put(i+0.1, new MoneyConField((int)(Math.random()*200+100), this));
                this.playfield.put(i+0.0, new MoneyConField((int)(Math.random()*200+100), this));
            } else if(i%4 == 1)
            {
                this.playfield.put(i+0.1, new ActionMissionField((int)(Math.random()*200+100), this));
                this.playfield.put(i+0.0, new ActionMissionField((int)(Math.random()*200+100), this));
            } else
            {
                this.playfield.put(i+0.1, new MoneyProField((int)(Math.random()*200+100), this));
                this.playfield.put(i+0.0, new MoneyProField((int)(Math.random()*200+100), this));
            }
        }
        this.playfield.put(boardSize, new FinishField(this));
        this.playfield.put(0.0, new StartField(this));
    }
    public Game getParent() { return this.parent; }
    public double toNextPause(double pos)
    {
        return boardSize-pos;
    }
}
