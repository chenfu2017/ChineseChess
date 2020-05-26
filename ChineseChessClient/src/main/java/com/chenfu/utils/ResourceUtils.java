package com.chenfu.utils;

import com.chenfu.view.GameView;

import javax.swing.*;
import java.awt.*;

public class ResourceUtils {

    public static ImageIcon getImageIcon(String src) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(GameView.class.getResource("/images/" + src)));
    }

    public static Image getImage(String src) {
        return Toolkit.getDefaultToolkit().getImage(GameView.class.getResource("/images/" + src));
    }

    public static String getMusicUrl(String filename) {
        return ResourceUtils.class.getResource("/music/"+filename).getPath();
    }

}
