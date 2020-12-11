package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class PostfixEvaluator extends Evaluator {

	private LinkedStack<Integer> stack = new LinkedStack<Integer>();

	/** return stack object (for testing purpose) */
	public LinkedStack<Integer> getStack() {
		return stack;
	}

	/**
	 * This method performs one step of evaluation of a postfix expression. The
	 * input is a token. Follow the postfix evaluation algorithm to implement this
	 * method. If the expression is invalid, throw an exception with the
	 * corresponding exception message.
	 */
	public void evaluate_step(String token) throws Exception {
		if (isOperand(token)) {
			stack.push(Integer.parseInt(token));
		} else {
			Integer right = stack.pop();
			if (right == null)
				throw new Exception("too few operands");
			if (token.equals("!"))
			{
				stack.push(-right);
			}
			else
			{
				Integer left = stack.pop();
				if (left == null)
					throw new Exception("too few operands");
				stack.push(operate(left, right, token));
			}
		}
	}

	/**
	 * This method evaluates a postfix expression (defined by expr) and returns the
	 * evaluation result. It throws an Exception if the postfix expression is
	 * invalid.
	 */
	public Integer evaluate(String expr) throws Exception {

		for (String token : ArithParser.parse(expr)) {
			evaluate_step(token);
		}
		// The stack should have exactly one operand after evaluation is done
		if (stack.size() > 1) {
			throw new Exception("too many operands");
		} else if (stack.size() < 1) {
			throw new Exception("too few operands");
		}
		return stack.pop();
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
		System.out.println(new PostfixEvaluator().evaluate("50 25 ! / 3 +"));
	}
}