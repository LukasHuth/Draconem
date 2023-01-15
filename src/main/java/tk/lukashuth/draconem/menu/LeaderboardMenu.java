package tk.lukashuth.draconem.menu;

import tk.lukashuth.draconem.utils.Controller;
import tk.lukashuth.draconem.utils.Leaderboard;
import tk.lukashuth.draconem.utils.Player;
import tk.lukashuth.draconem.utils.Screen;

public class LeaderboardMenu implements GUI {
    private final Controller parent;
    private final Screen screen;
    private Leaderboard leaderboard;
    private int offset;
    public LeaderboardMenu(Screen screen, Controller parent)
    {
        this.parent = parent;
        this.screen = screen;
        this.leaderboard = new Leaderboard();
    }
    @Override
    public void render() {
        this.screen.resetBuffer();
        int i = 0;
        int length = (int)Math.min(this.screen.getHeight() - 2 - 2 - 2, this.leaderboard.getPlayers().size());
        int offset = (this.screen.getHeight()/2)-((length+2)/2);
        this.screen.drawStringToMiddle("Leaderboard", offset);
        offset+=2;
        for(; i < length;i++)
        {
            Player p = this.leaderboard.getPlayers().get(this.offset+i);
            this.screen.drawStringToMiddle(p.getName() + " : " + p.getMoney(), offset+i);
        }
        this.screen.drawStringToMiddle("> back <", offset+i+2);
    }

    @Override
    public void input(int input) {
        switch (input)
        {
            case 13:
                this.executeSelection();
                break;
            default:
                break;
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void setState(int s) {

    }

    @Override
    public void executeSelection() {
        this.parent.selectMainMenu();
    }

    @Override
    public void resume() {

    }
}
