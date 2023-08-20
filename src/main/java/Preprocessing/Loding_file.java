package Preprocessing;

import Gui.Main_Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Loding_file {
    public JButton button;
    public JTextField path;
    public String path_location;
    public JButton Loding_fil() {

        button = new JButton("Select CSV File");
        button.setSize(30,30);
        button.setLocation(650,180);
        button.setLayout(null);

        return button;
    }
    public JTextField text_path(){
        path = new JTextField();
        path.setText("Location to input file");
        path.setSize(250,30);
        path.setLocation(400,180);
        path.setLayout(null);
        path.setEditable(false);
        return path;
    }

}
