package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The window used for recording.
 * 
 * @author S24Team3F
 * @version 5/8/24
 */
public class RecordingWindow extends JFrame implements ActionListener {
  private JButton recordButton, pauseButton, stopButton;
  private JLabel titleLabel;
  private static String fileName;
  private static boolean isRecording;

  /**
   * Constructor.
   * 
   * @param fileName the file name.
   */
  public RecordingWindow(String fileName) {
      super("Record to: " + fileName);
      this.fileName = fileName;
      isRecording = false;
      
      titleLabel = new JLabel("Record to: " + fileName);
      titleLabel.setHorizontalAlignment(JLabel.CENTER);
      add(titleLabel, BorderLayout.NORTH);

      JPanel buttonPanel = new JPanel(new FlowLayout());

      recordButton = new JButton("Record");
      recordButton.addActionListener(this);
      buttonPanel.add(recordButton);

      pauseButton = new JButton("Pause");
      pauseButton.addActionListener(this);
      pauseButton.setEnabled(false);
      buttonPanel.add(pauseButton);

      stopButton = new JButton("Stop");
      stopButton.addActionListener(this);
      stopButton.setEnabled(false);
      buttonPanel.add(stopButton);

      add(buttonPanel, BorderLayout.CENTER);

      setSize(300, 150);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
      if (e.getSource() == recordButton) {
          recordButton.setEnabled(false);
          pauseButton.setEnabled(true);
          stopButton.setEnabled(true);
          isRecording = true;
      } else if (e.getSource() == pauseButton) {
          recordButton.setEnabled(true);
          pauseButton.setEnabled(false);
          stopButton.setEnabled(true);
          isRecording = false;
      } else if (e.getSource() == stopButton) {
          stopRecording();
          dispose();
      }
  }

  /**
   * Stops the recording.
   */
  private void stopRecording() {
          try {
              File file = new File(fileName);
              FileWriter writer = new FileWriter(file);
              for (String item: InputArea.getRecording()) {
                writer.write(item + "\n");
              }
              writer.close();
              JOptionPane.showMessageDialog(this, "Recording saved to: " + fileName);
          } catch (IOException e) {
              e.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error occurred while saving recording!");
          }
  }
  
  /**
   * Returns the file name.
   * 
   * @return the file name.
   */
  public static String getFileName() {
    return fileName;
  }
  
  /**
   * Returns the recording.
   * 
   * @return the recording.
   */
  public static boolean getRecording() {
    return isRecording;
  }
}