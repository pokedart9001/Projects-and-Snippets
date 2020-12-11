package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class InfixEvaluator extends Evaluator {

	private LinkedStack<String> operators = new LinkedStack<String>();
	private LinkedStack<Integer> operands = new LinkedStack<Integer>();

	/** return stack object (for testing purpose) */
	public LinkedStack<String> getOperatorStack() {
		return operators;
	}

	public LinkedStack<Integer> getOperandStack() {
		return operands;
	}

	/**
	 * This method performs one step of evaluation of a infix expression. The input
	 * is a token. Follow the infix evaluation algorithm to implement this method.
	 * If the expression is invalid, throw an exception with the corresponding
	 * exception message.
	 */
	public void evaluate_step(String token) throws Exception {
		if (isOperand(token)) {
			operands.push(Integer.parseInt(token));
		} else if (token.equals("(")) {
			operators.push(token);
		} else if (isOperator(token) && (operators.isEmpty() || precedence(token) > precedence(operators.top()))) {
			operators.push(token);
		} else if (token.equals(")")) {
			while (!operators.top().equals("(")) {
				processOperator();
				if (operators.isEmpty())
					throw new Exception("missing (");
			}
			operators.pop();
		} else {
			while (operators.top() != null && precedence(token) <= precedence(operators.top())) {
				processOperator();
			}
			operators.push(token);
		}
	}

	public void processOperator() throws Exception {
		Integer right = operands.pop();
		if (right == null)
			throw new Exception("too few operands");
		String op = operators.pop();
		if (op.equals("!")) {
			operands.push(-right);
		} else {
			Integer left = operands.pop();
			if (left == null)
				throw new Exception("too few operands");
			operands.push(operate(left, right, op));
		}
	}

	/**
	 * This method evaluates an infix expression (defined by expr) and returns the
	 * evaluation result. It throws an Exception object if the infix expression is
	 * invalid.
	 */
	public Integer evaluate(String expr) throws Exception {

		for (String token : ArithParser.parse(expr)) {
			evaluate_step(token);
		}

		while (!operators.isEmpty())
			processOperator();

		// The operand stack should have exactly one operand after the evaluation is
		// done
		if (operands.size() > 1)
			throw new Exception("too many operands");
		else if (operands.size() < 1)
			throw new Exception("too few operands");

		return operands.pop();
	}
	
	protected static int operate(int a, int b, String op) throws Exception {
		switch (op) {
		case "+":
			return a + b;
		case "-":
			return a - b;
		case "*":
			return a * b;
		case "/":
			if (b == 0)
				throw new Exception("division by zero");
			else
				return a / b;
		default:
			throw new Exception("invalid operator");
		}
	}

	protected static boolean isOperator(String token) {
		return "+-*/!".contains(token);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(new InfixEvaluator().evaluate("5+(5+2*(5+9))/!8"));
	}
}
