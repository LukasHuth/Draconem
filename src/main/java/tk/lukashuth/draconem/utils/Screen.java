package tk.lukashuth.draconem.utils;

import tk.lukashuth.draconem.Main;

public class Screen {
    private char[] screenBuffer;
    private char[] lastScreen;
    private int width;
    private int height;
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public Screen(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.screenBuffer = new char[this.width*this.height];
        this.lastScreen = new char[this.width*this.height];
        this.init();
    }
    private void init()
    {
        for(int i = 0; i < this.width*this.height; i++) this.lastScreen[i] = 'a';
        this.render();
    }
    public void render()
    {
        char c;
        for(int i = 0; i < this.width*this.height; i++)
        {
            c = this.screenBuffer[i];
            if(c == 0) c = ' ';
            if(this.lastScreen[i] == c) continue;
            int x = i % this.width;
            int y = i / this.width;
            Main.setCursorTo(x, y);
            System.out.print(c);
            this.lastScreen[i] = c;
        }
        Main.setCursorTo(this.width-1, this.height-1);
    }
    public void draw(char c, int x, int y)
    {
        this.screenBuffer[y * this.width + x] = c;
    }
    public void drawStringToMiddle(String str, int column)
    {
        int offset = (this.width / 2) - (str.length() / 2);
        for(int i = 0; i < str.length(); i++)
            draw(str.charAt(i), offset + i, column);
    }
    public void drawString(String str, int start, int column)
    {
        for(int i = 0; i < str.length(); i++)
            draw(str.charAt(i), start + i, column);
    }
    public void resetBuffer()
    {
        for(int i = 0; i < this.width*this.height; i++) this.screenBuffer[i] = 0;
    }
}
