package gui;

import javax.swing.*;
import java.awt.*;

public class ExtendableWindow extends JDialog {
    private static final int WIDTH = 250;
    private static final int HEIGHT = 500;
    private boolean isExtended;
    private static JTextArea historyArea;

    public ExtendableWindow(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initialize();
    }

    private void initialize() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.CENTER);

        isExtended = false; // Start as collapsed
    }

    public void toggle() {
        isExtended = !isExtended;
        adjustPosition();
    }

    private void adjustPosition() {
        if (getOwner() != null) {
            Point ownerLoc = getOwner().getLocation();
            int ownerWidth = getOwner().getWidth();
            int newX = isExtended ? ownerLoc.x + ownerWidth : ownerLoc.x + ownerWidth - WIDTH;
            setLocation(newX, ownerLoc.y);
            setVisible(isExtended);
        }
    }

    public static void addHistoryEntry(String entry) {
        historyArea.append(entry + "\n");
    }
    
    public String getHistoryContent() {
      return historyArea.getText();
    }
}
