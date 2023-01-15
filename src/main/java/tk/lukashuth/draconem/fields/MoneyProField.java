package tk.lukashuth.draconem.fields;

import tk.lukashuth.draconem.utils.Field;
import tk.lukashuth.draconem.utils.Player;
import tk.lukashuth.draconem.utils.Playfield;

public class MoneyProField implements Field {
    private int amount;
    private Playfield parent;
    public MoneyProField(int amount, Playfield parent)
    {
        this.parent = parent;
        this.amount = amount;
    }
    @Override
    public boolean execute(Player p) {
        double scale = p.calcMoneyScale();
        int money =  (int)(this.amount*scale);
        p.addMoney(money);
        this.parent.getParent().getParent().addOutput(
                p.getName() + " found " + money + "g in a corpse"
        );
        return false;
    }

    @Override
    public boolean giveInput(int input, Player p) {return false;}
}
