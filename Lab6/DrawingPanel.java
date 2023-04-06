package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class DrawingPanel extends JPanel implements Serializable {
    final MainFrame frame;
    protected static DrawingPanel instance;
    protected static JLabel[] items;

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
        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
    }

    public static void refresh() {
        instance.clearPanel();
        instance.repaint();
    }

    public void clearPanel() {
        Graphics g = getGraphics();
        g.setColor(getBackground());
        for (JLabel label : items) {
            remove(label);
        }
        g.fillRect(0, 0, W, H);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawVertices(g);
        drawEdges(g);
    }

    private void drawVertices(Graphics g) {
        for (int i = 0; i < numVertices; i++) {
            Color randomColor = new Color(random.nextInt(175) + 80, random.nextInt(175) + 80, random.nextInt(175) + 80);
            g.setColor(randomColor);
            double angle = 2 * Math.PI * i / numVertices;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            g.fillOval(x - vertexDimension / 2, y - vertexDimension / 2, vertexDimension, vertexDimension);
            Map.Entry<Integer, Integer> entry = new AbstractMap.SimpleEntry<>(x, y);
            nodes.put(i, entry);
        }
    }

    private void drawEdges(Graphics g) {
        items = new JLabel[numEdges];
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

                g.setColor(Color.BLACK);
                g.drawLine(x1, y1, x2, y2);

                final JLabel label = new JLabel();
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
