package tk.lukashuth.draconem.fields;

import tk.lukashuth.draconem.utils.Field;
import tk.lukashuth.draconem.utils.Player;
import tk.lukashuth.draconem.utils.Playfield;

public class MoneyConField implements Field {
    private int amount;
    private Playfield parent;
    public MoneyConField(int amount, Playfield parent)
    {
        this.parent = parent;
        this.amount = amount;
    }
    @Override
    public boolean execute(Player p) {
        double scale = 1.0;
        int money =  (int)(this.amount*scale);
        p.addMoney(-money);
        this.parent.getParent().getParent().addOutput(
                p.getName() + " got robbed and lost " + money + "g"
        );
        return false;
    }

    @Override
    public boolean giveInput(int input, Player p) {return false;}
}
