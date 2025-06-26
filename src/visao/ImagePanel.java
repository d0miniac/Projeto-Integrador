package visao;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private int w, h;

    public ImagePanel(URL imageUrl) {
        try {
            if (imageUrl != null) {
                image = ImageIO.read(imageUrl);
                w = image.getWidth();
                h = image.getHeight();
            } else {
                System.err.println("URL da imagem é nula.");
                w = 200;
                h = 200;
            }
        } catch (IOException ioe) {
            System.err.println("Erro ao carregar imagem: " + ioe.getMessage());
            w = 200;
            h = 200;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}