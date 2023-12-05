package com.zhykh.imgtoascii;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class imageProcessing {
    public static Deque<String> getChars (BufferedImage img, int comprLvl) {
        Deque<String> symbolsQueue = new ArrayDeque<>();

        if (comprLvl == 1) {
            for (int y = 1; y <= img.getHeight(); y++) {
                for (int x = 1; x <= img.getWidth(); x++) {
                    symbolsQueue.add(getCharFromBrightness(getBrightness(img, x, y)));
                }
            }
        }

        else {
            for (int y = 1; y <= img.getHeight(); y = y + comprLvl) {
                for (int x = 1; x <= img.getWidth(); x = x + comprLvl) {
                    symbolsQueue.add(getCharFromSquareCords(img, getCordsFromSquare(comprLvl, y, x)));
                }
            }
        }

        return symbolsQueue;
    }
    private static String getCharFromBrightness (int brightness) {
        String[] chars = new String[]{"$","@","B","%","8","&","W","M","#","*","o","a","h","k","b","d","p","q","w",
                "m","Z","O","0", "Q","L","C","J","U","Y","X","z","c","v","u","n","x","r","j","f","t","/","|","(",")",
                "1","{","}","[","]","?","-","_","+","~","<",">","i","!","l","I",";",":","'","^","`","."};
        int charNum =(int) Math.round(brightness / 3.863) - 1;

        return chars[charNum];
    }

    private static int getBrightness (BufferedImage img, int x, int y) {
        int color = img.getRGB(x, y);

        int red   = (color >>> 16) & 0xFF;
        int green = (color >>>  8) & 0xFF;
        int blue  = (color) & 0xFF;

        return Math.round(red * 0.2126f + green * 0.7152f + blue * 0.0722f);
    }

    private static String getCharFromSquareCords (BufferedImage img, List<Integer> cords) {
        List<Integer> lums = new ArrayList<>();
        for (int i = 0; i <= cords.toArray().length; i = i + 2) {
            lums.add(getBrightness(img, cords.get(i), cords.get(i+1)));
        }

        double lumSumm = 0;
        for (int i = 0; i < lums.toArray().length; i++) {
            lumSumm += lums.get(i);
        }

        return getCharFromBrightness((int)Math.floor(lumSumm / lums.toArray().length));
    }

    private static List<Integer> getCordsFromSquare (int comprLvl, int y, int x) {
        List<Integer> cords = new ArrayList<>();
        for (int i = y; i < i + comprLvl; i++) {
            for (int j = x; j < j + comprLvl; j++) {
                cords.add(x);
                cords.add(y);
            }
        }

        return cords;
    }
    public static Deque<Integer> getColors (BufferedImage img, int comprLvl) {
        Deque<Integer> colorsQueue = new ArrayDeque<>();
        List<Integer> color;

        if (comprLvl == 1) {
            for (int y = 1; y <= img.getHeight(); y++) {
                for (int x = 1; x <= img.getWidth(); x++) {
                    color = getOnePixColor(img, x, y);
                    colorsQueue.add(color.get(0));
                    colorsQueue.add(color.get(1));
                    colorsQueue.add(color.get(2));
                }
            }
        }
        else {
            for (int y = 1; y <= img.getHeight(); y = y + comprLvl) {
                for (int x = 1; x <= img.getWidth(); x = x + comprLvl) {
                    List<Integer> cords = getCordsFromSquare(comprLvl, y, x);
                    color = getSquareColor(cords, img);
                    colorsQueue.add(color.get(0));
                    colorsQueue.add(color.get(1));
                    colorsQueue.add(color.get(2));
                }
            }
        }

        return colorsQueue;
    }
    private static List<Integer> getOnePixColor (BufferedImage img, int x, int y) {
        List<Integer> colors = new ArrayList<>();
        int color = img.getRGB(x, y);

        int red   = (color >>> 16) & 0xFF;
        int green = (color >>>  8) & 0xFF;
        int blue  = (color) & 0xFF;

        colors.add(red);
        colors.add(green);
        colors.add(blue);

        return colors;
    }
    private static List<Integer> getSquareColor (List<Integer> cords, BufferedImage img) {
        List<Integer> colors = new ArrayList<>();

        List<Integer> reds = new ArrayList<>();
        List<Integer> greens = new ArrayList<>();
        List<Integer> blues = new ArrayList<>();
        for (int i = 0; i <= cords.toArray().length; i = i + 2) {
            int color = img.getRGB(cords.get(i), cords.get(i+1));

            int red   = (color >>> 16) & 0xFF;
            int green = (color >>>  8) & 0xFF;
            int blue  = (color) & 0xFF;

            reds.add(red);
            greens.add(green);
            blues.add(blue);
        }

        double redSumm = 0;
        double greenSumm = 0;
        double blueSumm = 0;
        for (int i = 0; i <= reds.toArray().length; i++) {
            redSumm += reds.get(i);
            greenSumm += greens.get(i);
            blueSumm += blues.get(i);
        }

        colors.add((int)Math.floor(redSumm / reds.toArray().length));
        colors.add((int)Math.floor(greenSumm / greens.toArray().length));
        colors.add((int)Math.floor(blueSumm / blues.toArray().length));

        return colors;
    }
}
