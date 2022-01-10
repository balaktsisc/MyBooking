package Misc;

import javax.swing.*;

public class Utils {
    public static void dateSanitize(JTextField day, JTextField month, JTextField year) {
        if (day.getText().length()==1){
            day.setText("0" + day.getText());
        }
        if (month.getText().length()==1){
            month.setText("0" + month.getText());
        }
        if (year.getText().length()<=2){
            year.setText("20" + year.getText());
        }
    }
}
