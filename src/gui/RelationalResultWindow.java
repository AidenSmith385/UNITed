package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Window that pops up for relational results.
 * 
 * @author S24Team3F
 * @version 5/8/24
 */
public class RelationalResultWindow extends JDialog {
	private static final long serialVersionUID = 1L;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 100;
    private boolean isExtended;
    private JLabel resultArea;

    /**
     * Constructor.
     * 
     * @param owner the frame.
     * @param title the title.
     * @param modal whether or not it is modal.
     * @param result the result.
     */
    public RelationalResultWindow(Frame owner, String title, boolean modal, String result) {
        super(owner, title, modal);
        initialize(result);
    }

    /**
     * initializing the window.
     * 
     * @param result the result.
     */
    private void initialize(String result) {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        resultArea = new JLabel(result);
        add(resultArea, BorderLayout.NORTH);
        setVisible(true);
    }

    /**
     * Adjusts the position of the window.
     */
    private void adjustPosition() {
        if (getOwner() != null) {
            Point ownerLoc = getOwner().getLocation();
            int ownerWidth = getOwner().getWidth();
            int newX = isExtended ? ownerLoc.x + ownerWidth : ownerLoc.x + ownerWidth - WIDTH;
            setLocation(newX, ownerLoc.y);
            setVisible(isExtended);
        }
    }
}
