package Gui;

import javax.swing.*;
import java.awt.*;

public class display_screen {
public JTextArea display;

public  JTextArea display()
{
    display= new JTextArea();
    display.grabFocus();
    display.setLocation(5,5);
    display.setSize(720,580);
    display.setEditable(false);
    return display;
}
}
