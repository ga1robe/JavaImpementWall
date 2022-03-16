package com.github.ga1robe.wallapp;

import com.github.ga1robe.wallapp.Wall.*;

public class RunApp {



    public static void main(String args[]){
        Wall wall = new Wall();

        System.out.println(wall.findBlockByColor("red"));
        System.out.println(wall.findBlocksByMaterial("wood"));
        System.out.println(wall.count());
    }
}
