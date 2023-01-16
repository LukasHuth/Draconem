#include <jni.h>
#include <stdio.h>
#include <conio.h>
#include <windows.h>

JNIEXPORT void JNICALL
Java_tk_lukashuth_draconem_Main_setCursorTo(JNIEnv *env, jobject obj, jint _x, jint _y)
{
    int x = (int)_x;
    int y = (int)_y;
    HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
    COORD pos = {x, y};
    SetConsoleCursorPosition(hConsole, pos);
    WriteConsole(hConsole, "", 0, NULL, NULL);
    return;
}

JNIEXPORT jint JNICALL
Java_tk_lukashuth_draconem_Main_getKeyPress(JNIEnv *env, jobject obj)
{
    int lastkey = -1;
    char a;
    if(kbhit()) {
        a = getch();
        //printf("%c\n", a);
        lastkey = a;
    }
    return (jint) lastkey;
}
