package tk.lukashuth.draconem.menu;

public interface GUI {
    void render();
    void input(int input);
    void reset();
    void setState(int s);
    void executeSelection();
    void resume();
}
