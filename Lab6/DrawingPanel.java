package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    protected static DrawingPanel instance;
    protected static JLabel[] items;
    protected static BufferedImage image; //the offscreen image
    Graphics2D graphics; //the tools needed to draw in the image

    final static int W = 800, H = 600;
    protected static int numVertices = 6;
    protected static int numEdges = 0;


    private int centerX = 400;
    private int centerY = 300;
    private int radius = 35 * (numVertices % 10);
    private int vertexDimension = 30;


    protected static Map<Integer, Map.Entry<Integer, Integer>> nodes;
    protected static List<AbstractEdge> edges;

    private Random random = new Random();


    public DrawingPanel(MainFrame frame) {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.frame = frame;
        instance = this;
        createOffscreenImage();
        initPanel();
        createBoard();
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);
    }

    private void createBoard() {
        createOffscreenImage();
        drawVertices();
        drawEdges();
        JGraphTAlgorithm alg = new JGraphTAlgorithm();
        repaint();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
    }

    public static void refresh() {
        instance.clearBuffer();
        instance.clearPanel();
        instance.createBoard();
        instance.repaint();
    }

    public void clearBuffer() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, W, H);
    }

    public void clearPanel() {
        removeAll();
        repaint();
        nodes.clear();
        edges.clear();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }


    private void drawVertices() {
        for (int i = 0; i < numVertices; i++) {
            Color randomColor = new Color(random.nextInt(175) + 80, random.nextInt(175) + 80, random.nextInt(175) + 80);
            graphics.setColor(randomColor);
            double angle = 2 * Math.PI * i / numVertices;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            graphics.fillOval(x - vertexDimension / 2, y - vertexDimension / 2, vertexDimension, vertexDimension);
            Map.Entry<Integer, Integer> entry = new AbstractMap.SimpleEntry<>(x, y);
            nodes.put(i, entry);
        }
    }


    private void drawEdges() {
        this.items = new JLabel[numEdges];
        int i = 0;
        while (i < numEdges) {
            int vertex1 = (int) (Math.random() * numVertices);
            int vertex2 = (int) (Math.random() * numVertices);
            if (vertex1 != vertex2 && !existsLine(vertex1, vertex2)) {

                AbstractEdge edge = new AbstractEdge();
                edge.setRightNode(vertex1);
                edge.setLeftNode(vertex2);

                int x1 = getXCoord(vertex1);
                int y1 = getYCoord(vertex1);
                int x2 = getXCoord(vertex2);
                int y2 = getYCoord(vertex2);

                graphics.setColor(Color.BLACK);
                graphics.drawLine(x1, y1, x2, y2);

                JLabel label = new JLabel();
                items[i] = label;
                items[i].setBounds((x1 + x2) / 2 - 5, (y1 + y2) / 2 - 5, 10, 10);
                items[i].addMouseListener(new MyMouseListener(items[i]));
                items[i].setBorder(BorderFactory.createDashedBorder(Color.RED));
                items[i].setOpaque(true);

                edge.setLabel(label);
                edge.setIndex(i);
                edges.add(edge);
                add(items[i]);

                i++;
            }
        }
    }

    protected static boolean existsLine(Integer vertex1, Integer vertex2) {
        for (AbstractEdge index : edges) {
            if ((index.getLeftNode() == vertex1 && index.getRightNode() == vertex2) || (index.getLeftNode() == vertex2 && index.getRightNode() == vertex1)) {
                return true;
            }
        }
        return false;
    }

    private int getXCoord(int vertex) {
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> index : nodes.entrySet()) {
            if (index.getKey().equals(vertex)) {
                return index.getValue().getKey();
            }
        }
        return 0;
    }

    private int getYCoord(int vertex) {
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> index : nodes.entrySet()) {
            if (index.getKey().equals(vertex)) {
                return index.getValue().getValue();
            }
        }
        return 0;
    }
}