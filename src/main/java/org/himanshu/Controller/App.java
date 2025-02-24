package org.himanshu.Controller;

import org.himanshu.view.Welcome;

public class App
{
    public static void main( String[] args )
    {
        Welcome welcome = new Welcome();

        do {
            welcome.wellcomeScreen();
        }
        while (true);
    }
}
