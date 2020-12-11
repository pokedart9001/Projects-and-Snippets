import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Pizza extends JFrame
{
	JRadioButton[] sizes;
	JCheckBox[] toppings;
	JTextField priceText;

	ActionListener listener = e -> setPrice();

	public Pizza()
	{
		add(createButtons(), BorderLayout.CENTER);
		add(createPriceField(), BorderLayout.SOUTH);
		createMenuBar();
		pack();
		setMinimumSize(getBounds().getSize());
		setMaximumSize(getBounds().getSize());
	}

	public void createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		JMenuItem exit = new JMenuItem("Exit");

		exit.addActionListener(e -> System.exit(0));
		fileMenu.add(exit);
		menuBar.add(fileMenu);
	}

	public JPanel createButtons()
	{
		JPanel panel = new JPanel();
		panel.add(createSizeButtons());
		panel.add(createToppingButtons());

		return panel;
	}

	public JPanel createSizeButtons()
	{
		sizes = new JRadioButton[]{ new JRadioButton("Small"), new JRadioButton("Medium"), new JRadioButton("Large") };
		sizes[0].setSelected(true);
		
		JPanel panel = new JPanel(new GridLayout(sizes.length, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Size"));
		ButtonGroup sizeGroup = new ButtonGroup();

		for (JRadioButton button : sizes)
		{
			button.addActionListener(listener);
			sizeGroup.add(button);
			panel.add(button);
		}
		return panel;
	}

	public JPanel createToppingButtons()
	{
		toppings = new JCheckBox[]{ new JCheckBox("Pepperoni"), new JCheckBox("Bacon") };
		JPanel panel = new JPanel(new GridLayout(toppings.length, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Toppings"));

		for (JCheckBox button : toppings)
		{
			button.addActionListener(listener);
			panel.add(button);
		}
		return panel;
	}

	public JPanel createPriceField()
	{
		JLabel priceLabel = new JLabel("Your Price:");
		priceText = new JTextField(5);
		priceText.setEditable(false);
		priceText.setText("$12.00");

		JPanel panel = new JPanel();
		panel.add(priceLabel);
		panel.add(priceText);

		return panel;
	}

	public void setPrice()
	{
		double price = 12.0;
		for (int i = 0; i < sizes.length; i++)
			if (sizes[i].isSelected())
				price += i*2.0;
		for (int i = 0; i < toppings.length; i++)
			if (toppings[i].isSelected())
				price += 0.5;

		priceText.setText(String.format("$%.2f", price));
	}

	public static void main(String[] args)
	{
		Pizza pizza = new Pizza();
		pizza.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pizza.setLocationRelativeTo(null);
		pizza.setVisible(true);
	}
}