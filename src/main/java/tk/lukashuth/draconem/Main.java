package tk.lukashuth.draconem;

import tk.lukashuth.draconem.utils.Controller;

public class Main {
    public static native int getKeyPress();
    public static native void setCursorTo(int x, int y);
    static {
        System.loadLibrary("addon");
    }
    public static void main(String[] args)
    {
        // Console size 220 x 61
        /*
        while(true)
        {
            int key = getKeyPress();
            if(key == -1) continue;
            System.out.println(key);
        }
        //*/
        //*
        Controller controller = new Controller();
        controller.start();
        //*/
    }
}
