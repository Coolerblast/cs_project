package com.kevinzhong.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private static Map<String, BufferedImage> loaded = new HashMap<String, BufferedImage>();

    public static BufferedImage loadImage(String path) {
        try{
            if(!loaded.containsKey(path))
                loaded.put(path,ImageIO.read(new FileInputStream(path)));
            return loaded.get(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred when loading Image path: " + path);
            System.exit(1);
        }
        return null;
    }
}
