package xyz.yellowstrawberry;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;

public class BadgeMaker {
    private final File exportFolder;
    private final BufferedImage pane;
    private final BufferedImage logo;
    private final String name;
    private final String[] versions;
    private final Color color;

    public BadgeMaker(File exportFolder, BufferedImage pane, BufferedImage logo, String name, String[] versions, Color color) {
        this.exportFolder = exportFolder;
        exportFolder.mkdirs();
        this.pane = pane;
        this.logo = logo;
        this.name = name;
        this.versions = versions;
        this.color = color;
    }

    public void startExport() {
        for(String version : versions) {
            try {
                ImageIO.write(draw(name, version, true), "PNG", new File(exportFolder+"/"+version+"-PLUS.png"));
                ImageIO.write(draw(name, version, false), "PNG", new File(exportFolder+"/"+version+".png"));
            } catch (IOException | FontFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public BufferedImage draw(String name, String version, boolean plus) throws IOException, FontFormatException {
        BufferedImage img = deepCopy(pane);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.drawImage(logo, 51, 47, 108, 108, null);
        Font f= Font.createFont(Font.TRUETYPE_FONT, BadgeMaker.class.getResourceAsStream("/fonts/MinecraftRegular.otf"));
        g.setColor(color);
        g.setFont(f.deriveFont(38f));
        g.drawString("Requires", 186, 43+30);
        String s = name+" "+version;
        AttributedString as = new AttributedString(s+(plus?"+":""));
        as.addAttribute(TextAttribute.FONT, f.deriveFont(58f));
        as.addAttribute(TextAttribute.SIZE, 58f);
        if(plus) as.addAttribute(TextAttribute.FOREGROUND, new Color(0x2E9F0C), s.length(), s.length()+1);
        g.drawString(as.getIterator(), 189, 89+47);
        return img;
    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
