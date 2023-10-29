package xyz.yellowstrawberry;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final String[] versions = new String[]{
            "1.7.10",
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12",
            "1.12.2",
            "1.13",
            "1.13",
            "1.14",
            "1.15",
            "1.15.2",
            "1.16",
            "1.16.5",
            "1.17",
            "1.17.1",
            "1.18",
            "1.18.1",
            "1.19",
            "1.19.1",
            "1.19.2",
            "1.19.3",
            "1.20",
            "1.20.1",
            "1.20.2",
    };
    private static final BufferedImage[] panes;

    static {
        try {
            panes = new BufferedImage[] {
                    ImageIO.read(Main.class.getResourceAsStream("/panes/white.png")),
                    ImageIO.read(Main.class.getResourceAsStream("/panes/dark.png"))
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String[] apis = new String[]{
            "Bukkit-API",
            "Spigot-API",
            "Folia-API",
            "Paper-API"
    };

    public static void main(String[] args) throws IOException {
        boolean isDark = false;
        for(BufferedImage pane : panes) {
            for(String api : apis) {
                String f = api.substring(0, api.length()-4).toLowerCase();
                BadgeMaker badgeMaker = new BadgeMaker(
                        new File("C:/Users/eunso/IdeaProjects/badge-maker/src/main/resources/exports/"+(isDark?"dark":"white")+"/"+f+"/"),
                        pane,
                        ImageIO.read(Main.class.getResourceAsStream("/logos/"+api+".png")),
                        api,
                        versions,
                        isDark?Color.WHITE:Color.BLACK
                );
                badgeMaker.startExport();
            }
            isDark = true;
        }
    }
}