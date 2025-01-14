package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import units.*;

public class PlaybackWindow extends JDialog implements ActionListener, ChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton playButton, pauseButton, stopButton;
	private JSlider speedSlider;
	private JLabel titleLabel;
	private String fileName;
	private InputArea inputArea;
	private static boolean isPlaying;
	private Timer timer;
	private int currentLineIndex = 0;

	public PlaybackWindow(String fileName, InputArea inputArea, NumberPad numberPad, Frame owner) {
		super(owner, "Playback from: " + fileName, false);
		this.fileName = fileName;
		this.inputArea = inputArea;
		isPlaying = false;

		titleLabel = new JLabel("Playback from: " + fileName);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new FlowLayout());

		playButton = new JButton("Play");
		playButton.addActionListener(this);
		buttonPanel.add(playButton);

		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(this);
		pauseButton.setEnabled(false);
		buttonPanel.add(pauseButton);

		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		buttonPanel.add(stopButton);

		speedSlider = new JSlider(500, 5000);
		speedSlider.addChangeListener(this);
		buttonPanel.add(speedSlider);

		add(buttonPanel, BorderLayout.CENTER);

		setSize(450, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		//readActionEventsFromFile();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			playButton.setEnabled(false);
			pauseButton.setEnabled(true);
			stopButton.setEnabled(true);
			isPlaying = true;
			startPlayback();
		} else if (e.getSource() == pauseButton) {
			playButton.setEnabled(true);
			pauseButton.setEnabled(false);
			stopButton.setEnabled(true);
			isPlaying = false;
			pausePlayback();
		} else if (e.getSource() == stopButton) {
			isPlaying = false;
			pausePlayback();
			dispose();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == speedSlider) {
			if (timer != null) {
				timer.setDelay(5500 - speedSlider.getValue());
			}
		}
	}

	private void startPlayback() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			List<String> lines = reader.lines().toList();
			timer = new Timer(5500 - speedSlider.getValue(), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e	) {
						if (currentLineIndex < lines.size() && isPlaying) {
							System.out.println("Pass 1st if");
							if (lines.get(currentLineIndex).startsWith("1")) {
								System.out.println("Pass 2nd if");

								String event = lines.get(currentLineIndex).substring(1);
								inputArea.actionPerformed(
								new ActionEvent(inputArea, ActionEvent.ACTION_PERFORMED, event));
								currentLineIndex++;
							} else {
								String operandString = lines.get(currentLineIndex).substring(1);
								String[] operandParts = operandString.split(" ");
								for (char ch : operandParts[0].toCharArray()) {
									String digit = String.valueOf(ch);
									inputArea.actionPerformed(
									new ActionEvent(inputArea, ActionEvent.ACTION_PERFORMED, digit));
								}
								if (operandParts.length > 1) {
									Unit unit = CompoundUnit.getUnitByName(operandParts[1]);
									inputArea.setUnit(unit);
								} else {
									inputArea.setUnit(Unit.UNITLESS);
								}
								currentLineIndex++;
							}
						} else {
							pausePlayback();
						}					
				}
			});
			timer.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void pausePlayback() {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		playButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(true);
	}

	public String getFileName() {
		return fileName;
	}

	public static boolean getPlaying() {
		return isPlaying;
	}
}
