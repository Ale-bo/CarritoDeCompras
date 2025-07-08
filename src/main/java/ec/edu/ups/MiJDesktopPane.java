package ec.edu.ups;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int W = getWidth();
        int H = getHeight();
        int cx = W / 2;

        // ====== CIELO ======
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, W, H);

        // ====== PAISAJE DE MONTAÑAS DETRÁS ======
        g2.setColor(new Color(100, 100, 120));
        Polygon m1 = new Polygon(
                new int[]{0, W/4, W/2},
                new int[]{H - 150, H - 300, H - 150},
                3
        );
        Polygon m2 = new Polygon(
                new int[]{W/3, W/2, 2*W/3},
                new int[]{H - 150, H - 280, H - 150},
                3
        );
        Polygon m3 = new Polygon(
                new int[]{W/2, 3*W/4, W},
                new int[]{H - 150, H - 320, H - 150},
                3
        );
        g2.fillPolygon(m1);
        g2.fillPolygon(m2);
        g2.fillPolygon(m3);

        // ====== SUELO DE CÉSPED ======
        g2.setColor(new Color(60, 179, 113));  // verde césped
        g2.fillRect(0, H - 100, W, 100);

        // ====== SUPERMERCADO (centrado) ======
        int marketW = 400, marketH = 200;
        int marketX = cx - marketW/2, marketY = H - 100 - marketH;
        // pared
        g2.setColor(new Color(200, 200, 250));
        g2.fillRect(marketX, marketY, marketW, marketH);
        // techo
        Polygon roof = new Polygon();
        roof.addPoint(marketX, marketY);
        roof.addPoint(marketX + marketW/2, marketY - 50);
        roof.addPoint(marketX + marketW, marketY);
        g2.setColor(new Color(150, 150, 200));
        g2.fillPolygon(roof);
        // puerta
        g2.setColor(new Color(180, 180, 180));
        int doorW = 80, doorH = 120;
        g2.fillRect(marketX + marketW/2 - doorW/2, marketY + marketH - doorH, doorW, doorH);
        // rótulo
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.drawString("SUPERMERCADO", marketX + 40, marketY + 40);

        // ====== ESTANTERÍA Y PRODUCTOS ======
        int shelfX = marketX + 30, shelfY = marketY + 80, shelfW = marketW - 60, shelfH = 20;
        g2.setColor(new Color(139, 69, 19));
        for (int i = 0; i < 3; i++) {
            int y = shelfY + i * 60;
            g2.fillRect(shelfX, y, shelfW, shelfH);

            int prodW = 30, prodH = 40;
            int cols = shelfW / (prodW + 10);
            for (int j = 0; j < cols; j++) {
                int x = shelfX + 10 + j * (prodW + 10);
                int red   = Math.min(255, Math.max(0, 100 + j * 10));
                int green = Math.min(255, Math.max(0, 150 - i * 20));
                int blue  = Math.min(255, Math.max(0, 100 + i * 30));
                g2.setColor(new Color(red, green, blue));
                g2.fillRect(x, y - prodH, prodW, prodH);
            }
        }

        // ====== PERSONA AGARRANDO PRODUCTO (totalmente negra) ======
        int px = marketX + 100, py = marketY + marketH;
        g2.setColor(Color.BLACK);
        // cabeza
        g2.fillOval(px - 15, py - 140, 30, 30);
        // cuerpo
        g2.fillRect(px - 10, py - 110, 20, 60);
        // piernas
        g2.fillRect(px - 10, py - 50, 8, 40);
        g2.fillRect(px + 2,  py - 50, 8, 40);
        // brazos
        g2.setStroke(new BasicStroke(6));
        g2.drawLine(px - 5, py - 90, shelfX + 40, shelfY + 20);
        g2.drawLine(px + 5, py - 90, px + 60, py - 70);

        // ====== CARRITO DE COMPRAS ======
        int cartW = 100, cartH = 60;
        int cartX = px + 80, cartY = py - 80;
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cartX, cartY, cartW, cartH, 15, 15);
        g2.drawLine(cartX, cartY, cartX - 20, cartY - 20);
        g2.fillOval(cartX + 10, cartY + cartH, 16, 16);
        g2.fillOval(cartX + cartW - 26, cartY + cartH, 16, 16);
        g2.setStroke(new BasicStroke(1));
        for (int i = 1; i < 4; i++) {
            int x = cartX + i * cartW / 4;
            g2.drawLine(x, cartY, x, cartY + cartH);
        }
        for (int i = 1; i < 3; i++) {
            int y = cartY + i * cartH / 3;
            g2.drawLine(cartX, y, cartX + cartW, y);
        }
    }
}
