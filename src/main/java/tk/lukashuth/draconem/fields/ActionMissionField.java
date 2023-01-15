package tk.lukashuth.draconem.fields;

import tk.lukashuth.draconem.utils.Field;
import tk.lukashuth.draconem.utils.Player;
import tk.lukashuth.draconem.utils.Playfield;

public class ActionMissionField implements Field {
    private int amount;
    private Playfield parent;
    public ActionMissionField(int amount, Playfield parent)
    {
        this.parent = parent;
        this.amount = amount;
    }
    @Override
    public boolean execute(Player p) {
        double scale = p.calcMoneyScale();
        int money =  (int)(this.amount*scale);
        double winrate = Math.random();
        boolean won = winrate < (0.5 + (0.05*p.getGroupSize()));
        String questName = randomQuestName();
        this.parent.getParent().getParent().addOutput(
                p.getName() + " accepted the Quest '" + questName + "'"
        );
        if(won)
        {
            p.addMoney(money);
            this.parent.getParent().getParent().addOutput(
                    /*TODO: auftragsgeber in englisch*/
                    p.getName() + " got handed " + money + "g by the Auftragsgeber"
            );
            return false;
        }
        //money/=p.getMoneyScale();
        //p.addMoney(-money);
        this.parent.getParent().getParent().addOutput(
                p.getName() + " couldn't finish the quest in time"
        );
        return false;
    }
    private static String randomQuestName()
    {
        // TODO: random quest name
        return "Mission";
    }
    @Override
    public boolean giveInput(int input, Player p) {return false;}
}
