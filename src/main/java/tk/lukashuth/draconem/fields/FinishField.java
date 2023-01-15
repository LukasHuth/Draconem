package tk.lukashuth.draconem.fields;

import tk.lukashuth.draconem.utils.Field;
import tk.lukashuth.draconem.utils.Player;
import tk.lukashuth.draconem.utils.Playfield;

public class FinishField implements Field {
    private Playfield parent;
    public FinishField(Playfield parent)
    {
        this.parent = parent;
    }
    @Override
    public boolean execute(Player p) {
        if(p.isFinished())
        {
            this.parent.getParent().getParent().addOutput(
                    p.getName() + " has already finished the game"
            );
            return false;
        }
        this.parent.getParent().getParent().addOutput(
                p.getName() + " has Reached Mount Celestia"
        );
        p.setFinished(true);
        return false;
    }

    @Override
    public boolean giveInput(int input, Player p) {return false;}
}
