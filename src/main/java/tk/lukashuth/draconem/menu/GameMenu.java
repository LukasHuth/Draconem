package tk.lukashuth.draconem.menu;

import tk.lukashuth.draconem.utils.*;

import java.util.ArrayList;

public class GameMenu implements GUI {
    private final Controller parent;
    private final Screen screen;
    private Game game;
    private int state;
    private final ArrayList<String> output;
    private boolean paused;
    public void addOutput(String out) { this.output.add(out); }
    public GameMenu(Screen screen, Controller parent)
    {
        this.state = 0;
        this.parent = parent;
        this.screen = screen;
        this.game = new Game(this);
        this.newGame = false;
        this.needsinput = false;
        this.output = new ArrayList<>();
        // TODO: welcome message
        this.output.add("Welcome to Draconem, you are one of a few who found their way to this world, have fun exploring it!");
        this.paused = false;
    }
    @Override
    public void render() {
        if(this.paused)
        {
            this.renderPlayerBox();
            return;
        }
        this.screen.resetBuffer();
        if(this.newGame)
        {
            this.renderCreatePlayers();
            return;
        }
        int neededSpace = this.renderPlayerBox();
        this.renderGame(neededSpace);
    }
    private void renderGame(int notUseableSpace)
    {
        int space = this.screen.getHeight()-notUseableSpace;
        for(int i = 0; i < Math.min(this.output.size(), space-2); i++)
        {
            this.screen.drawString(this.output.get(this.output.size()-i-1), 5, notUseableSpace + space - 2 - i);
        }
    }
    private StringBuilder activeCreatingPlayer;
    private String error;
    private void renderCreatePlayers()
    {
        int offset = this.screen.getHeight()/2;
        if(this.game.getPlayerCount() == 0)
        {
            this.screen.drawStringToMiddle("With how many players do you want to play?", offset-1);
            this.screen.drawStringToMiddle(activeCreatingPlayer.toString(), offset+1);
            return;
        }
        this.screen.drawStringToMiddle("Please enter your name Player " + (this.game.getPlayers().size()+1), offset-2);
        this.screen.drawStringToMiddle(this.activeCreatingPlayer.toString(), offset);
        this.screen.drawStringToMiddle(this.error, offset+2);
    }
    private int renderPlayerBox()
    {
        int off = 0;
        for(int i = 0; i < this.game.getPlayers().size(); i+=2)
        {
            off += 2;
            int playersLeft = this.game.getPlayers().size() - (i*2);
            if(playersLeft < 2)
            {
                Player p = this.game.getPlayer(i);
                screen.drawStringToMiddle(p.getName() + " "+ p.getMoney() + "g [" + (int)p.getPosition() + "]", off);
            }
            else
            {
                Player p1 = this.game.getPlayer(i);
                Player p2 = this.game.getPlayer(i+1);
                String str1 = p1.getName() + " "+ p1.getMoney() + "g [" + (int)p1.getPosition() + "]";
                String str2 = p2.getName() + " "+ p2.getMoney() + "g [" + (int)p2.getPosition() + "]";
                int w = this.screen.getWidth()/3;
                int s1 = w - (str1.length()/2);
                int s2 = w*2 - (str2.length()/2);
                for(int j = 0; j < str1.length(); j++)
                {
                    this.screen.draw(str1.charAt(j), s1 + j, off);
                }
                for(int j = 0; j < str2.length(); j++)
                {
                    this.screen.draw(str2.charAt(j), s2 + j, off);
                }
                //i++;
            }
        }
        this.drawBox(off+2);
        return off+2;
    }
    private void drawBox(int lowerStart)
    {
        for(int i = 0; i < this.screen.getWidth(); i++)
        {
            this.screen.draw('-', i, lowerStart);
            this.screen.draw('-', i, 0);
        }
        for(;lowerStart>1;lowerStart--)
        {
            this.screen.draw('|',0, lowerStart-1);
            this.screen.draw('|', this.screen.getWidth()-1, lowerStart-1);
        }
    }
    private boolean needsinput;
    @Override
    public void input(int input) {
        if(input == -1) return;
        // TODO: select Pause on escape
        if(input == 27)
        {
            this.paused = true;
            this.parent.selectPauseGame();
        }
        if(this.newGame)
        {
            if(this.game.getPlayerCount() == 0)
            {
                if(input==13)
                {
                    this.game.setPlayerCount(Integer.parseInt(this.activeCreatingPlayer.toString()));
                    this.resetActiveCreatingPlayer();
                    return;
                }
                if(input-'2'>4 || input-'2'<0) return;
                this.resetActiveCreatingPlayer();
                this.activeCreatingPlayer.append(input-'0');
                return;
            }
            if(input==13)
            {
                if(this.game.hasPlayer(this.activeCreatingPlayer.toString()))
                {
                    this.error = "This name is already taken";
                    this.resetActiveCreatingPlayer();
                    return;
                }
                this.game.addPlayer(this.activeCreatingPlayer.toString());
                this.resetActiveCreatingPlayer();
                if(this.game.getPlayers().size() == this.game.getPlayerCount()) this.newGame = false;
                return;
            }
            if(input == 8)
            {
                if(this.activeCreatingPlayer.length() == 0) return;
                this.activeCreatingPlayer.deleteCharAt(this.activeCreatingPlayer.length()-1);
            }
            if(input == 127) this.resetActiveCreatingPlayer();
            if(!(input >= 'a' && input <= 'z') && !(input >= 'A' && input <= 'Z')) return;
            this.activeCreatingPlayer.append((char)input);
            return;
        }
        if(this.needsinput)
        {
            if(input == 13)
            {
                this.needsinput = this.game.giveInput(this.input);
                return;
            }
            if(input-'0' < 0 || input-'0'>9) return;
            this.input = input-'0';
            return;
        }
        if(this.game.isFinished()) {
            //TODO:  go to finish screen with player infos
            this.paused = true;
            this.parent.selectEndedGame();
            return;
        }
        this.needsinput = this.game.execute();
        if(this.needsinput){
            this.input = 0;
            return;
        }
        this.spaceOutput(2);
        this.cleanOutput();
    }
    private void spaceOutput(int i)
    {
        for(;i > 0; i--) this.output.add("");
    }
    private void cleanOutput()
    {
        int s = (int)(screen.getHeight()*1.5);
        while(output.size() > s)
        {
            output.remove(0);
        }
    }
    private int input;
    private void resetActiveCreatingPlayer()
    {
        this.activeCreatingPlayer.delete(0, this.activeCreatingPlayer.length());
    }
    @Override
    public void reset() {
        this.game = new Game(this);
        if(state==1){
            //TODO: load game
        } else if(state == 2)
        {
            this.newGame = true;
            this.activeCreatingPlayer = new StringBuilder();
            this.activeCreatingPlayer.append(2);
            this.game.getPlayers().clear();
            this.game.setPlayerCount(0);
            this.error = "";
        }
    }
    private boolean newGame;
    public void setState(int state) { this.state = state; }
    @Override
    public void executeSelection() {

    }

    @Override
    public void resume() {
        this.paused = false;
    }
}
