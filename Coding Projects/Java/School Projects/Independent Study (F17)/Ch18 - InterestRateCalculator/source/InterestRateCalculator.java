import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class InterestRateCalculator extends JFrame
{
	JTextArea output;

	public InterestRateCalculator()
	{
		setLayout(new GridLayout(1, 3));
		output = new JTextArea();
		output.setEditable(false);

		JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(new TitledBorder(new EtchedBorder(), "Output"));
		borderPanel.add(new JScrollPane(output));
		borderPanel.add(Box.createHorizontalStrut(2), BorderLayout.EAST);
		borderPanel.add(Box.createHorizontalStrut(2), BorderLayout.WEST);
		borderPanel.add(Box.createVerticalStrut(2), BorderLayout.NORTH);
		borderPanel.add(Box.createVerticalStrut(2), BorderLayout.SOUTH);

		add(createLeftPanel());
		add(borderPanel);

		pack();
		setMinimumSize(new Dimension(getBounds().width + 10, getBounds().height + 5));
		setMaximumSize(getBounds().getSize());
	}

	private JPanel createLeftPanel()
	{
		JPanel panel = new JPanel(new GridLayout(2, 1));

		JPanel inputPanel = new JPanel(new GridLayout(4, 1));
		inputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Input"));
		JLabel[] labels = {
			new JLabel("Initial Amount ($):"),
			new JLabel("Interest Rate (%):"),
			new JLabel("Number of Years:")
		};
		JComponent[] fields = {
			new JSpinner(new SpinnerNumberModel(15.0, 0.0, 999999.99, 1.0)),
			new JSpinner(new SpinnerNumberModel(5.0, 1.0, 100.0, 1.0)),
			new JSpinner(new SpinnerNumberModel(10, 1, 999, 1))
		};

		for (int i = 0; i < 3; i++)
		{
			JPanel flow = new JPanel();
			flow.add(labels[i]);
			flow.add(fields[i]);
			inputPanel.add(flow);
		}

		JPanel radioPanel = new JPanel();
		radioPanel.setBorder(new TitledBorder(new EtchedBorder(), "Compounding"));
		JRadioButton[] compounds = {
			new JRadioButton("Annual"),
			new JRadioButton("Monthly"),
			new JRadioButton("Continuous")
		};
		ButtonGroup group = new ButtonGroup();

		compounds[0].setSelected(true);
		for (JRadioButton button : compounds)
		{
			group.add(button);
			radioPanel.add(button);
		}
		inputPanel.add(radioPanel);

		JButton button = new JButton("Calculate");
		button.addActionListener(e -> 
		{
			double initial = (Double)(((JSpinner)fields[0]).getValue());
			double rate = (Double)(((JSpinner)fields[1]).getValue()) / 100;
			int years = (Integer)(((JSpinner)fields[2]).getValue());
			int mode = selectedCompound(compounds);

			output.setText("");
			for (int i = 1; i <= years; i++)
				output.append(String.format("After year %d: $%.2f\n", i, calculateInterest(initial, rate, i, mode)));
		});

		panel.add(inputPanel);
		panel.add(button);
		return panel;
	}

	private double calculateInterest(double initial, double rate, int years, int mode)
	{
		switch(mode)
		{
			case 0: return initial * Math.pow(1 + rate, years);
			case 1: return initial * Math.pow(1 + rate/12, years*12);
			case 2: return initial * Math.pow(Math.E, rate * years);
			default: return initial;
		}
	}

	private int selectedCompound(JRadioButton[] compounds)
	{
		for (int i = 0; i < compounds.length; i++)
			if (compounds[i].isSelected())
				return i;
		return -1;
	}

	public static void main(String[] args)
	{
		InterestRateCalculator calculator = new InterestRateCalculator();
		calculator.setTitle("Interest Rate Calculator");
		calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calculator.setLocationRelativeTo(null);
		calculator.setVisible(true);
	}
}