import javax.swing.*;

public class ConverterRunner {

    private enum Radix {
        BINARY("Binary", 2), OCTAL("Octal", 8), DECIMAL("Decimal", 10), HEXADECIMAL("Hexadecimal", 16);
        public String value;
        public int num;

        Radix(String value, int num) {
            this.value = value;
            this.num = num;
        }
    }

    private JButton convertButton;
    private JTextField input;
    private JComboBox inputList;
    private JTextField output;
    private JComboBox outputList;
    private JPanel converterRunner;
    private Object option1;
    private Object option2;
    private Radix radix1;
    private Radix radix2;


    private ConverterRunner() {

        convertButton.addActionListener(e -> {
            option1 = inputList.getSelectedItem();
            option2 = outputList.getSelectedItem();
            radix1 = Radix.valueOf(option1.toString().toUpperCase());
            radix2 = Radix.valueOf(option2.toString().toUpperCase());
            output.setText(Converter.convert(input.getText(), radix1.num, radix2.num));
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Base Converter");
        frame.setContentPane(new ConverterRunner().converterRunner);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
