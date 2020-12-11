import com.fathzer.soft.javaluator.DoubleEvaluator;
import javax.swing.*;
import java.awt.event.ActionListener;

public class Calculator {
    private JPanel Calculator;
    private JTextField display;
    private JScrollPane scrollPane;
    private JButton plus;
    private JButton minus;
    private JButton multiply;
    private JButton divide;
    private JButton equals;
    private JButton num0;
    private JButton num1;
    private JButton num2;
    private JButton num3;
    private JButton num4;
    private JButton num5;
    private JButton num6;
    private JButton num7;
    private JButton num8;
    private JButton num9;
    private JButton memPlus;
    private JButton memMinus;
    private JButton memGet;
    private JButton decimalPoint;
    private JButton clear;
    private DoubleEvaluator evaluator = new DoubleEvaluator();
    private String expression = "";
    private double result;
    private double mem = 0;
    private boolean pressed = false;

    private Calculator() {
        JButton[] buttons = {num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, decimalPoint, plus, minus, multiply, divide};
        for (JButton b : buttons) {
            ActionListener listener = e -> {
                display.setText(pressed ? b.getText() : (display.getText() + b.getText()).replace("x", "*"));
                expression += b.getText();
                pressed = b.equals(plus) || b.equals(minus) || b.equals(multiply) || b.equals(divide);
            };
            b.addActionListener(listener);
        }

        equals.addActionListener(e -> {
            result = evaluator.evaluate(expression.replace("x", "*").replace("÷", "/"));
            display.setText(result == Math.round(result) ? Long.toString(Math.round(result)) : Double.toString(result));
            expression = display.getText();
        });

        memPlus.addActionListener(e -> {});
        memMinus.addActionListener(e -> {});
        memGet.addActionListener(e -> display.setText(mem == Math.round(mem) ? Long.toString(Math.round(mem)) : Double.toString(mem)));
        clear.addActionListener(e -> {
            display.setText("");
            expression = "";
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().Calculator);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
