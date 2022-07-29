package map;

import java.awt.Color;

import javax.swing.JFrame;

public class PixelMap {
    private final int pixelSize;
    private final PixelCanvas canvas;
    private final JFrame jFrame;

    public PixelMap(int pixelSize, PixelCanvas canvas, JFrame jFrame) {
        this.pixelSize = pixelSize;
        this.canvas = canvas;
        this.jFrame = jFrame;
    }

    public static PixelMap withParams(int width, int height, int pixelSize) {
        PixelCanvas pixelCanvas = new PixelCanvas(width, height);

        JFrame jFrame = new JFrame("MAP");
        pixelCanvas.fillCanvas(Color.WHITE);
        jFrame.add(pixelCanvas);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return new PixelMap(pixelSize, pixelCanvas, jFrame);
    }

    // Drawing

    public void drawObjectMap(ObjectMap objectMap) {
        canvas.fillCanvas(Color.WHITE);
        objectMap.getSenseContainers().forEach((coordinates, senseContainer) -> drawObject(coordinates.first(), coordinates.second(), senseContainer.getColor()));
        objectMap.getMapObjects().forEach(mapObject -> drawObject(mapObject.getXCoordinate(), mapObject.getYCoordinate(), mapObject.getColor()));
    }

    public void drawObject(int x, int y, Color color) {
        int pixelStartX = pixelSize * x;
        int pixelStartY = pixelSize * y;
        canvas.drawRect(color, pixelStartX, pixelStartY, pixelSize, pixelSize);
    }

    public void show() {
        jFrame.setVisible(true);
    }

    public void hide() {
        jFrame.setVisible(false);
    }
}
