package tk.lukashuth.draconem.fields;

import tk.lukashuth.draconem.utils.Field;
import tk.lukashuth.draconem.utils.Player;
import tk.lukashuth.draconem.utils.PlayerClass;
import tk.lukashuth.draconem.utils.Playfield;

public class StartField implements Field {
    private int selection;
    private Playfield parent;
    public StartField(Playfield parent)
    {
        this.parent = parent;
    }
    @Override
    public boolean execute(Player p) {
        this.parent.getParent().getParent().addOutput(
                p.getName() + " can choose his profession"
        );
        this.selection = 0;
        this.printFirstSelect(p);
        return true;
    }
    private void givePlayerClass(Player p)
    {
        p.setPlayerClass(PlayerClass.values()[(this.selection+1)*3-2]);
    }
    private void adjustPlayerClass(Player p)
    {
        if (this.selection == 1) {
            this.parent.getParent().getParent().addOutput(p.getName() + " is now a trained " + p.getPlayerClass().name());
            p.setPlayerClass(PlayerClass.valueOf("Advanced_" + p.getPlayerClass().name()));
            return;
        }
        this.parent.getParent().getParent().addOutput(p.getName() + " is now a training " + p.getPlayerClass().name());
    }
    private boolean executeEnter(Player p)
    {
        if(p.getPlayerClass() == PlayerClass.NONE)
        {
            this.givePlayerClass(p);
            this.selection = 0;
            for(int i = 0; i < PlayerClass.values().length; i++)
            {
                String out = PlayerClass.values()[i].name();
                if(out.equalsIgnoreCase("none")) continue;
                if(out.startsWith("Advanced")) continue;
                if(out.startsWith("Apprentice")) continue;
                this.parent.getParent().getParent().removeOutput(0);
            }
            this.parent.getParent().getParent().addOutput("Do you study or are you already trained");
            this.printSelection(p);
            return true;
        }
        for(int i = 0; i < 2; i++) this.parent.getParent().getParent().removeOutput(0); // remove selection
        this.adjustPlayerClass(p);
        return false;
    }
    private boolean awaitArrow;
    @Override
    public boolean giveInput(int input, Player p) {
        boolean arrow = false;
        switch(input)
        {
            case -32:
                this.awaitArrow = true;
                arrow = true;
                break;
            case 72:
                if(!this.awaitArrow) break;
            case 'w':
                this.selectionUp(p);
                break;
            case 80:
                if(!this.awaitArrow) break;
            case 's':
                this.selectionDown(p);
                break;
            case 13:
                return this.executeEnter(p);
            default:
                break;
        }
        if(!arrow) this.awaitArrow = false;
        this.printSelection(p);
        return true;
    }
    private String formatSelect(String str, int pos)
    {
        if(this.selection != pos) return str;
        return "> " + str + " <";
    }
    private void printSelection(Player p)
    {
        if(p.getPlayerClass() != PlayerClass.NONE)
        {
            for(int i = 0; i < 2; i++)
                this.parent.getParent().getParent().removeOutput(0);
            this.parent.getParent().getParent().addOutput(formatSelect("Studying", 0));
            this.parent.getParent().getParent().addOutput(formatSelect("Already Trained", 1));
            return;
        }
        int pos = 0;
        for(int i = 0; i < PlayerClass.values().length; i++)
        {
            String out = PlayerClass.values()[i].name();
            if(out.equalsIgnoreCase("none")) continue;
            if(out.startsWith("Advanced")) continue;
            if(out.startsWith("Apprentice")) continue;
            if(this.selection == pos) out = "> " + out + " <";
            this.parent.getParent().getParent().removeOutput(pos);
            this.parent.getParent().getParent().addOutput(out);
            pos++;
        }
    }
    private void printFirstSelect(Player p)
    {
        int pos = 0;
        for(int i = 0; i < PlayerClass.values().length; i++)
        {
            String out = PlayerClass.values()[i].name();
            if(out.equalsIgnoreCase("none")) continue;
            if(out.startsWith("Advanced")) continue;
            if(out.startsWith("Apprentice")) continue;
            if(this.selection == pos++) out = "> " + out + " <";
            this.parent.getParent().getParent().addOutput(out);
        }
    }
    private void selectionDown(Player p) {
        int val=2;
        if(p.getPlayerClass() == PlayerClass.NONE) val=(PlayerClass.values().length-1)/3;
        this.selection++;
        this.selection+=val;
        this.selection%=val;
    }

    private void selectionUp(Player p) {
        int val=2;
        if(p.getPlayerClass() == PlayerClass.NONE) val=(PlayerClass.values().length-1)/3;
        this.selection--;
        this.selection+=val;
        this.selection%=val;
    }

}
