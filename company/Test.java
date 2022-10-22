package com.company;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        String file = "accounts.json";
        System.out.println("Choose how you'd like to run the game");
        System.out.println("Terminal\nHardcoded\nGraphic Interface");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        if (s.equals("Terminal")) {
            Game.run(file);
        }
        if (s.equals("Hardcoded")) {
            Game.runHardcoded(file);
        }
        if (s.equals("Graphic Interface")) {
            Game.runGraphicInterface(file);
        }
    }
}