package tk.lukashuth.draconem.menu;

import tk.lukashuth.draconem.utils.Controller;
import tk.lukashuth.draconem.utils.Screen;

public class RulesMenu implements GUI {
    private final Controller parent;
    private final Screen screen;
    private boolean offsetable;
    private static final String rules = """
            The Rules are simple:
            The game is playable with 2 to 8 players.
            After you start the game, you create the players, then the game tells you whose turn it is.
            
            
            The game board consists of many adjoining squares, which are triggered. You roll a ten-sided die, and the
            game moves your piece along the board by the number you rolled. The object of the game is to move your
            figure to the end of all the squares and reach Mount Celestia and have more money than all the other
            players with your adventure party.
            
            
            There are several types of squares:
            
            Stop:
            This square stops any piece when it comes over it during its move, forcing the respective piece to trigger
            the event. Most of the time, you have to choose between two different paths on these squares.
            
            Gold:
            These are gold fields, on them you get the gold that the field holds for you.
            
            Action:
            this field is the action field, good or bad things can happen here.
            
            Group Expansion:
            The group field, on this field you will get a member added to your adventure group.
            
            
            The adventure group
            At the beginning of the game you are on your own, at some point a red box will allow you to add another
            person to your party, this party member is just part of your party and can't do anything to it during the
            game. Once you have reached Mount Celestia, they will give you a small gold bonus.
            
            
            Saving or loading a score
            At the end of a round, the game will ask if you want to pause it at this point. Type b in the command line.
            Now you are in the pause menu. There you can save the game, continue or quit it. When quitting a game you
            will also be asked if you want to save the active game state. It is only possible to save one savegame at a time.
            If you want to load the previous game state you have to choose the option "Load Game" in the main menu, after
            that the game takes over again.
            
            
            The main menu
            1 shows the game rules
            2 shows the best players of the past games (is empty if never played)
            3 creates a new game
            4 starts the saved game if it exists
            5 Ends the game
            
            
            The pause menu
            1 shows the game rules
            2 shows the best players of the past games (is empty if never played)
            3 continue game
            4 Save game
            5 Cancel game and start new game
            6 end game
            
            
            General controls
            If the game gives you a choice, enter the number of your choice.
            If the game does not present you with a choice at the end of an output, just confirm with Enter to continue.
            """;
    private int offset = 0;
    public RulesMenu(Screen screen, Controller parent)
    {
        this.parent = parent;
        this.screen = screen;
        this.offsetable = (this.screen.getHeight()-2-2) < rules.lines().count();
        this.length = (int)Math.min(this.screen.getHeight() - 2 - 2, rules.lines().count());
    }
    @Override
    public void render() {
        this.screen.resetBuffer();
        int i = 0;
        int length = (int)Math.min(this.screen.getHeight() - 2 - 2, rules.lines().count());
        int offset = (this.screen.getHeight()/2)-((length+2)/2);
        for(; i < length;i++)
        {
            this.screen.drawStringToMiddle((String)rules.lines().toArray()[this.offset+i], offset+i);
        }
        this.screen.drawStringToMiddle("> back <", offset+i+1);
    }

    private int length;
    private boolean awaitArrow;
    @Override
    public void input(int input) {
        if(input == 13) this.executeSelection();
        boolean arrow = false;
        switch (input) {
            case -32: {
                this.awaitArrow = true;
                arrow = true;
            } break;
            case 13:
                this.executeSelection();
                break;
            case 72:
                if(!awaitArrow) break;
            case 'w': {
                if(offsetable && offset > 0) offset--;
                offset += rules.lines().count();
                offset %= rules.lines().count();
            } break;
            case 80:
                if(!awaitArrow) break;
            case 's': {
                if(offsetable && ((offset+length+1) < rules.lines().count())) offset++;
                offset += rules.lines().count();
                offset %= rules.lines().count();
            } break;
            default: {} break;
        }
        if(!arrow) this.awaitArrow = false;
    }

    @Override
    public void reset() {}

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
