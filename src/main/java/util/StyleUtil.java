package util;

import javafx.scene.paint.Color;

public class StyleUtil {

    /**
     * Get the corresponding color for each digit value
     * Following the classic Windows Version
     * 
     * 1       Blue
     * 2       Green  
     * 3       Red    
     * 4       Purple 
     * 5       Maroon 
     * 6       Turquoise
     * 7       Black  
     * 8       Gray   
     * @param value
     * @return
     */
    public static Color getColorForValue(int value) {
        switch(value) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return Color.PURPLE;
            case 5:
                return Color.MAROON;
            case 6:
                return Color.TURQUOISE;
            case 7:
                return Color.BLACK;
            case 8:
                return Color.GRAY;

            default:
                return Color.BLACK;
        }
    }

}