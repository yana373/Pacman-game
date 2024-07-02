package util;


import javax.swing.*;
import java.awt.*;

public class EndWindow extends JFrame {
    public EndWindow(String text){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int width = 500;
        int height = 400;
        setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2, width, height);
        setTitle("Pacman");

        Container container = getContentPane();

        // Создание надписи
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        Font font = new Font("Verdana", Font.BOLD, 40);
        label.setFont(font);
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        container.add(label);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
