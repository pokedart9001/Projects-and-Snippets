import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class BaseConverter extends JFrame
{
	JPanel panel;

	JPanel inputPanel;
	ButtonGroup inputGroup;
	JRadioButton[] inputButtons;
	JPanel outputPanel;
	ButtonGroup outputGroup;
	JRadioButton[] outputButtons;

	JTextField inputText;
	JTextField outputText;

	public BaseConverter()
	{
		inputGroup = new ButtonGroup();

		inputButtons = new JRadioButton[4];
		inputButtons[0] = new JRadioButton("Binary");
		inputButtons[1] = new JRadioButton("Octal");
		inputButtons[2] = new JRadioButton("Decimal");
		inputButtons[3] = new JRadioButton("Hexadecimal");

		inputButtons[2].setSelected(true);

		for (JRadioButton button : inputButtons)
		{
			button.addActionListener(e -> outputText.setText(convert()));
			inputGroup.add(button);
		}

		outputGroup = new ButtonGroup();

		outputButtons = new JRadioButton[4];
		outputButtons[0] = new JRadioButton("Binary");
		outputButtons[1] = new JRadioButton("Octal");
		outputButtons[2] = new JRadioButton("Decimal");
		outputButtons[3] = new JRadioButton("Hexadecimal");

		outputButtons[0].setSelected(true);

		for (JRadioButton button : outputButtons)
		{
			button.addActionListener(e -> outputText.setText(convert()));
			outputGroup.add(button);
		}
		
		inputText = new JTextField(15);
		outputText = new JTextField(15);
		outputText.setEditable(false);

		inputText.addCaretListener(e -> outputText.setText(convert()));

		addElements();
	}

	public void addElements()
	{
		inputPanel = new JPanel();
		inputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Input"));
		inputPanel.add(inputText);
		for (JRadioButton button : inputButtons)
			inputPanel.add(button);

		outputPanel = new JPanel();
		outputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Output"));
		outputPanel.add(outputText);
		for (JRadioButton button : outputButtons)
			outputPanel.add(button);

		add(inputPanel, BorderLayout.NORTH);
		add(outputPanel, BorderLayout.SOUTH);
		pack();
		setMinimumSize(getBounds().getSize());
		setMaximumSize(getBounds().getSize());
	}

	private String convert()
	{
		String result = "";
		int radix1 =
			inputButtons[0].isSelected() ? 2 :
			inputButtons[1].isSelected() ? 8 :
			inputButtons[2].isSelected() ? 10 : 16;
		int radix2 =
			outputButtons[0].isSelected() ? 2 :
			outputButtons[1].isSelected() ? 8 :
			outputButtons[2].isSelected() ? 10 : 16;
		try
		{
			result = Long.toString(Long.parseLong(inputText.getText(), radix1), radix2).toUpperCase();
		}
		catch (java.lang.NumberFormatException e)
		{
			return inputText.getText().isEmpty() ? "" : "Invalid Input";
		}
		if (radix2 == 2)
		{
			while (result.length() % 4 != 0)
				result = 0 + result;
			result = result.replaceAll("(.{4})(?!$)", "$1 ");
		}
		return result;
	}

	public static void main(String[] args)
	{
		BaseConverter converter = new BaseConverter();
		converter.setTitle("Base Converter");
		converter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		converter.setLocationRelativeTo(null);
		converter.setVisible(true);
	}
}