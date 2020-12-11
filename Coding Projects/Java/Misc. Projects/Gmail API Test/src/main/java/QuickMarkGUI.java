import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuickMarkGUI extends JFrame
{
	private JPanel centerPanel;
	private JButton applyButton;
	private JPanel[] labelLists;
	
	private Gmail service;
	
	private QuickMarkGUI(Gmail service)
	{
		this.service = service;
		centerPanel = new JPanel();
		
		labelLists = new JPanel[3];
		for (int i = 0; i < labelLists.length; i++)
		{
			labelLists[i] = new JPanel();
			labelLists[i].setLayout(new GridLayout(0, 1));
		}
		labelLists[0].setBorder(new TitledBorder(new EtchedBorder(), "Apply To:"));
		labelLists[1].setBorder(new TitledBorder(new EtchedBorder(), "Keep:"));
		labelLists[2].setBorder(new TitledBorder(new EtchedBorder(), "Remove:"));
		
		List<String> labelNames = getLabelNames();
		JCheckBox[][] labelOptions = new JCheckBox[3][labelNames.size()];
		
		for (int r = 0; r < labelOptions.length; r++)
			for (int c = 0; c < labelOptions[r].length; c++)
			{
				labelOptions[r][c] = new JCheckBox(labelNames.get(c));
				labelLists[r].add(labelOptions[r][c]);
			}
		
		applyButton = new JButton("Apply");
		applyButton.addActionListener(e ->
		{
			List<String> labelsToList = getLabelNames();
			List<String> labelsToKeep = getLabelNames();
			List<String> labelsToRemove = getLabelNames();
			
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < labelOptions[i].length; j++)
					if (!labelOptions[i][j].isSelected())
						(i == 0 ? labelsToList : i == 1 ? labelsToKeep : labelsToRemove).remove(labelOptions[i][j].getText());
			
			try
			{
				QuickMark.quickMark(service, labelsToList, labelsToKeep, labelsToRemove);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Done!");
			System.exit(0);
		});
		
		addElements();
	}
	
	private void addElements()
	{
		for (JPanel panel : labelLists)
			centerPanel.add(panel);
		
		add(centerPanel, BorderLayout.CENTER);
		add(applyButton, BorderLayout.SOUTH);
		
		pack();
		setMinimumSize(getSize());
		setMaximumSize(getSize());
	}
	
	private List<String> getLabelNames()
	{
		ListLabelsResponse response = null;
		try
		{
			response = service.users().labels().list("me").execute();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return Objects.requireNonNull(response).getLabels().stream().map(Label::getName).collect(Collectors.toList());
	}
	
	public static void main(String args[]) throws IOException, GeneralSecurityException
	{
		Gmail service = Quickstart.getGmailService();
		QuickMarkGUI frame = new QuickMarkGUI(service);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}