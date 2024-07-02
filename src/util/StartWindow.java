package util;

import logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame {
    private Game game;
    public StartWindow(Game game){
        this.game = game;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int width = 500;
        int height = 400;
        setBounds(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2, width, height);
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        panel.setBackground(Color.black);
        panel.setLayout(new GridBagLayout());

        // Создание стартовой кнопки
        JButton button = new JButton("<html><h1><font color=\"white\">Start");;
                button.setOpaque(true);
                button.setBackground(Color.magenta);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                game.startCharacters();
                game.setWindowVisible();
            }
        });

        setVisible(true);
    }
}
