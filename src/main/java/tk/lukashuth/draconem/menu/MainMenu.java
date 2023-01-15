package tk.lukashuth.draconem.menu;

import tk.lukashuth.draconem.utils.Controller;
import tk.lukashuth.draconem.utils.Screen;

public class MainMenu implements GUI {
    private int selected;
    private static final String[] options = {"Rules", "Leaderboard", "Start", "Load Game", "Exit"};
    private static final String title = "Main Menu";
    private final Screen screen;
    private final Controller parent;
    public MainMenu(Screen screen, Controller parent)
    {
        this.parent = parent;
        this.screen = screen;
        this.selected = 0;
    }
    @Override
    public void render() {
        this.screen.resetBuffer();
        int offset = (this.screen.getHeight()/2)-(options.length+1);
        this.screen.drawStringToMiddle(title, offset);
        offset+=4;
        String opt;
        for(int i = 0; i < options.length; i++)
        {
            opt = options[i];
            if(i == this.selected) opt = "> "+opt+" <";
            this.screen.drawStringToMiddle(opt, offset);
            offset+=2;
        }
    }
    private boolean awaitArrow;
    @Override
    public void input(int input) {
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
                this.selectionUp();
                break;
            case 80:
                if(!this.awaitArrow) break;
            case 's':
                this.selectionDown();
                break;
            case 13:
                this.executeSelection();
            default:
                break;
        }
        if(!arrow) this.awaitArrow = false;
    }

    @Override
    public void reset() {
        this.selected = 0;
    }

    @Override
    public void setState(int s) {

    }

    private void selectionDown() {
        this.selected+=options.length+1;
        this.selected%=options.length;
    }

    private void selectionUp() {
        this.selected+= options.length;
        this.selected--;
        this.selected%=options.length;
    }

    @Override
    public void executeSelection() {
        switch(this.selected)
        {
            case 0:
                this.parent.selectRules();
                this.parent.resetActiveGUI();
                break;
            case 1:
                this.parent.selectLeaderboard();
                this.parent.resetActiveGUI();
                break;
            case 2:
                this.parent.selectNewGame();
                this.parent.resetActiveGUI();
                break;
            case 3:
                this.parent.selectLoadGame();
                this.parent.resetActiveGUI();
            case 4:
                this.parent.exitGame();
                break;
        }
    }

    @Override
    public void resume() {

    }

}
