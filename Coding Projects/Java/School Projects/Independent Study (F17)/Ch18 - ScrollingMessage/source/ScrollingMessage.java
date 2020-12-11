import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ScrollingMessage extends JFrame
{
	Canvas canvas;
	Timer timer;

	public ScrollingMessage()
	{
		canvas = new Canvas();
		timer = new Timer(17, e ->
		{
			canvas.offset += canvas.increment * canvas.speed;
			canvas.update(canvas.getGraphics());
		});
		reset();

		setJMenuBar(createMenuBar());
		add(canvas);
		pack();
		setMinimumSize(new Dimension(400, 300));
		setMaximumSize(getBounds().getSize());
	}

	private JPanel createOutputPanel()
	{
		JPanel panel = new JPanel();
		panel.add(canvas);
		return panel;
	}

	private JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();

		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		return menuBar;
	}

	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		JMenuItem reset = new JMenuItem("Reset");
		JMenuItem exit = new JMenuItem("Exit");

		reset.addActionListener(e -> reset());
		exit.addActionListener(e -> System.exit(0));

		menu.add(reset);
		menu.add(exit);
		return menu;
	}

	private JMenu createEditMenu()
	{
		JMenu menu = new JMenu("Edit");
		JMenuItem text = new JMenuItem("Text");
		JMenuItem font = new JMenuItem("Font");
		JMenu color = new JMenu("Color...");
		JMenuItem foreColor = new JMenuItem("Foreground");
		JMenuItem backColor = new JMenuItem("Background");
		JMenuItem scrolling = new JMenuItem("Scrolling");

		text.addActionListener(e ->
		{
			String message = JOptionPane.showInputDialog(this, "New Message:", "Change Text", JOptionPane.PLAIN_MESSAGE);
			canvas.message = message == null ? canvas.message : message;
		});

		font.addActionListener(e ->
		{
			JFrame frame = createJFrame("Change Font");
			JPanel comboPanel = new JPanel();
			JPanel modPanel = new JPanel(new GridLayout(2, 1));
			JPanel typePanel = new JPanel();
			JPanel optionPanel = new JPanel();
			comboPanel.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
			modPanel.setBorder(new TitledBorder(new EtchedBorder(), "Modifiers"));

			JComboBox<String> fontCombo = new JComboBox<String>
			(
				GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()
			);
			fontCombo.setSelectedItem(canvas.font.getName());
			comboPanel.add(fontCombo);

			JCheckBox[] types = {new JCheckBox("Bold"), new JCheckBox("Italic")};

			types[0].setSelected(canvas.font.getStyle() == 1 || canvas.font.getStyle() == 3);
			types[1].setSelected(canvas.font.getStyle() >= 2);

			for (JCheckBox box : types)
				typePanel.add(box);
			modPanel.add(typePanel);

			JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(canvas.font.getSize(), 10, 100, 1));
			modPanel.add(sizeSpinner);

			JButton[] options = createOptionButtons(frame, e1 -> canvas.setMessage(canvas.message, new Font((String)fontCombo.getSelectedItem(), typeValue(types), (Integer)sizeSpinner.getValue())));

			for (JButton button : options)
				optionPanel.add(button);

			frame.add(comboPanel, BorderLayout.NORTH);
			frame.add(modPanel, BorderLayout.CENTER);
			frame.add(optionPanel, BorderLayout.SOUTH);
			
			frame.pack();
			frame.setMinimumSize(frame.getBounds().getSize());
			frame.setMaximumSize(frame.getBounds().getSize());
			frame.setVisible(true);
		});

		foreColor.addActionListener(e ->
		{
			JColorChooser foreColorChooser = new JColorChooser(canvas.foreground);
			Color choice = JColorChooser.showDialog(this, "Change Color", canvas.foreground);
			canvas.foreground = choice == null ? canvas.foreground : choice;
		});

		backColor.addActionListener(e ->
		{
			JColorChooser backColorChooser = new JColorChooser(canvas.background);
			Color choice = JColorChooser.showDialog(this, "Change Color", canvas.background);
			canvas.background = choice == null ? canvas.background : choice;
		});

		scrolling.addActionListener(e ->
		{
			JFrame frame = createJFrame("Change Scrolling");
			JPanel panel = new JPanel(new GridLayout(2, 1));
			JPanel speedPanel = new JPanel();
			JPanel optionPanel = new JPanel();
			JSpinner speedSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 50, 1));
			JCheckBox reverse = new JCheckBox("Reverse");

			JButton[] options = createOptionButtons(frame, e1 -> canvas.setTimerVals(canvas.offset, (Integer)speedSpinner.getValue(), reverse.isSelected() ? -1 : 1));
			for (JButton button : options)
				optionPanel.add(button);

			speedPanel.add(new JLabel("Speed:"));
			speedPanel.add(speedSpinner);
			speedPanel.add(reverse);
			panel.add(speedPanel);
			panel.add(optionPanel);

			frame.add(panel);
			frame.pack();
			frame.setMinimumSize(frame.getBounds().getSize());
			frame.setMaximumSize(frame.getBounds().getSize());
			frame.setVisible(true);
		});

		color.add(foreColor);
		color.add(backColor);
		menu.add(text);
		menu.add(font);
		menu.add(color);
		menu.add(scrolling);
		return menu;
	}

	private JFrame createJFrame(String title)
	{
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setLocationRelativeTo(this);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return frame;
	}

	private JButton[] createOptionButtons(JFrame frame, ActionListener action)
	{
		JButton[] options = {new JButton("OK"), new JButton("Cancel"), new JButton("Apply")};
		options[0].addActionListener(action);
		options[0].addActionListener(e -> frame.dispose());
		options[1].addActionListener(e -> frame.dispose());
		options[2].addActionListener(action);
		return options;
	}

	public int typeValue(JCheckBox[] types)
	{
		int type = 0;
		int inc = 1;
		for (JCheckBox box : types)
		{
			type += box.isSelected() ? inc : 0;
			inc++;
		}
		return type;
	}

	private void reset()
	{
		timer.restart();
		canvas.setVals();
	}

	public static void main(String[] args)
	{
		ScrollingMessage frame = new ScrollingMessage();
		frame.setTitle("Scrolling Message");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}