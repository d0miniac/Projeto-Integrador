package visao;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class ImageButton extends JButton {
    private BufferedImage image;

    public ImageButton(URL imageUrl) {
        try {
            if (imageUrl != null) {
                image = ImageIO.read(imageUrl);
            } else {
                System.err.println("URL da imagem é nulo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int borderRadius = 20;

        Shape roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, borderRadius, borderRadius);
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        if (image != null) {
            Image resizedImage = image.getScaledInstance(width - 3, height - 3, Image.SCALE_SMOOTH);
            g2d.drawImage(resizedImage, 0, 0, this);
        }

        g2d.setColor(Color.BLACK);
        g2d.draw(roundedRectangle);
    }
}