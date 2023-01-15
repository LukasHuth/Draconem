package tk.lukashuth.draconem.utils;

import tk.lukashuth.draconem.Main;
import tk.lukashuth.draconem.menu.*;

public class Controller {
    private final Screen screen;
    private final GUI mainMenu;
    private final GUI rulesMenu;
    private final GUI gameMenu;
    private final GUI leaderboardMenu;
    private final GUI pauseMenu;
    private final GUI finishedMenu;
    private GUI last;
    private GUI active;
    private boolean run;
    public GUI getGameMenu() { return this.gameMenu; }
    public Controller()
    {
        this.screen = new Screen(220,60);
        this.mainMenu = new MainMenu(this.screen, this);
        this.rulesMenu = new RulesMenu(this.screen, this);
        this.gameMenu = new GameMenu(this.screen, this);
        this.leaderboardMenu = new LeaderboardMenu(this.screen, this);
        this.pauseMenu = new PauseMenu(this.screen, this);
        this.finishedMenu = new FinishedMenu(this.screen, this);
        this.active = this.mainMenu;
        this.last = this.mainMenu;
        this.run = true;
    }
    private static final double framerate = 60;
    private static final double frameConst = 1_000_000_000/framerate;
    public void start()
    {
        long now;
        long last = System.nanoTime();
        while(run)
        {
            now = System.nanoTime();
            if(now-last < frameConst) continue;
            last+=frameConst;
            this.active.input(Main.getKeyPress());
            this.active.render();
            this.screen.render();
        }
    }
    public void selectMainMenu()
    {
        this.active = this.mainMenu;
        this.last = this.mainMenu;
    }
    public void selectRules()
    {
        this.active = this.rulesMenu;
    }
    public void selectLeaderboard()
    {
        this.active = this.leaderboardMenu;
    }
    public void selectNewGame()
    {
        this.selectGame();
        this.active.setState(2);
    }
    public void selectLoadGame()
    {
        this.selectGame();
        this.active.setState(1);
    }
    public void selectPauseGame()
    {
        //this.exitGame(); // only for debug
        //TODO: select pause game
        this.active = this.pauseMenu;
        this.last = this.pauseMenu;
    }
    public void openParent()
    {
        this.active = this.last;
    }
    public void selectEndedGame()
    {
        // TODO: select ended game screen
        this.active = this.finishedMenu;
        this.last = this.finishedMenu;
    }
    public void selectGame()
    {
        this.active = this.gameMenu;
    }
    public void exitGame()
    {
        this.run = false;
    }
    public void resetActiveGUI()
    {
        this.active.reset();
    }
    public void resumeActive() { this.active.resume(); }
}
