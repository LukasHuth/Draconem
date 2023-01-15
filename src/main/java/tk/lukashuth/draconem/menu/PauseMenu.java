package tk.lukashuth.draconem.menu;

import tk.lukashuth.draconem.utils.Controller;
import tk.lukashuth.draconem.utils.Screen;

public class PauseMenu implements GUI {
    private final Controller parent;
    private final Screen screen;
    private static final String[] options = {"Rules", "Leaderboard", "Continue", "New Game", "Exit"};
    private static final String title = "Paused";
    private int selected;
    public PauseMenu(Screen screen, Controller parent)
    {
        this.screen = screen;
        this.parent = parent;
        this.selected = 0;
    }
    @Override
    public void render() {
        this.screen.resetBuffer();
        this.parent.getGameMenu().render();
        int unusable = (int) Math.round(((GameMenu)this.parent.getGameMenu()).getGame().getPlayerCount()/2.0+0.1);
        unusable*=2;
        unusable+=3;
        int offset = ((this.screen.getHeight()-unusable)/2)-(options.length+1);
        offset+=unusable;
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

    private void selectionDown() {
        this.selected++;
        this.selected+=options.length;
        this.selected%=options.length;
    }

    private void selectionUp() {
        this.selected--;
        this.selected+=options.length;
        this.selected%=options.length;
    }

    @Override
    public void setState(int s) {

    }

    @Override
    public void executeSelection() {
        switch(this.selected)
        {
            case 0:
                this.parent.selectRules();
                return;
            case 1:
                this.parent.selectLeaderboard();
                return;
            case 2:
                this.parent.selectGame();
                this.parent.resumeActive();
                return;
            case 3:
                this.parent.selectNewGame();
                this.parent.resetActiveGUI();
                return;
            case 4:
                this.parent.exitGame();
                return;
            default:
                break;
        }
    }

    @Override
    public void resume() {

    }
}
