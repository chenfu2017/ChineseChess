package com.chenfu;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class Utils {

    public static ImageIcon getImageIcon(String src) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChessFrame.class.getResource("/images/" + src)));
    }

    public static Image getImage(String src) {
        return Toolkit.getDefaultToolkit().getImage(ChessFrame.class.getResource("/images/" + src));
    }

    public static String getMusicUrl(String filename) {
        return Utils.class.getResource("/music/"+filename).getPath();
    }

}
