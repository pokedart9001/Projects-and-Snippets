import java.util.Stack;
import java.util.Arrays;
import java.util.Scanner;

public class ExpressionSolver
{
	private Stack<Integer> nums;
	private Stack<Character> operators;
	private String[] expression;

	public ExpressionSolver()
	{
		nums = new Stack<Integer>();
		operators = new Stack<Character>();
	}

	public ExpressionSolver(String expression)
	{
		this();
		setExpression(expression);
	}

	private void setExpression(String expression)
	{
		expression = expression.replaceAll("\\s", "");
		int check = "0123456789".contains(expression.substring(0, 1)) ? 1 : 0;
		for (int i = 1; i < expression.length(); i++)
		{
			if ("0123456789".contains(expression.substring(i, i+1)))
			{
				if (check != 1)
				{
					expression = expression.substring(0, i) + " " + (expression.substring(i));
					check = 1;
				}
			}
			if ("(+-*/^)".contains(expression.substring(i, i+1)))
			{
				expression = expression.substring(0, i) + " " + (expression.substring(i));
				check = 2;
				i++;
			}
		}
		this.expression = expression.split(" ");
	}

	public int solve(boolean debug)
	{
		if (debug)
			System.out.println(Arrays.toString(expression));
		for (String chr : expression)
		{
			if ("0123456789".contains(chr.substring(0, 1)))
			{
				nums.push(Integer.parseInt(chr));
			}
			if ("(+-*/^)".contains(chr))
			{
				if (chr.charAt(0) == ')')
				{
					while (nums.size() >= 2 && operators.peek() != '(')
					{
						nums.push(apply(operators.pop(), nums.pop(), nums.pop()));
					}
					operators.pop();
				}
				else
				{
					while (nums.size() >= 2 && !operators.isEmpty() && !precedent(chr.charAt(0), operators.peek()))
					{
						nums.push(apply(operators.pop(), nums.pop(), nums.pop()));
					}
					operators.push(chr.charAt(0));
				}
			}
			if (debug)
			{
				System.out.println("Term: " + chr);
				System.out.println("Numbers: " + nums);
				System.out.println("Operators: " + operators);
				System.out.println();
			}
		}
		while (nums.size() >= 2 && !operators.isEmpty())
		{
			nums.push(apply(operators.pop(), nums.pop(), nums.pop()));
		}
		if (debug)
			System.out.print("Answer: ");
		return nums.pop();
	}

	public int solve()
	{
		return solve(false);
	}

	private int apply(char op, int b, int a)
	{
		switch(op)
		{
			case '+': return a + b;
			case '-': return a - b;
			case '*': return a * b;
			case '/': return a / b;
			case '^': return (int)Math.pow(a, b);
			default: return 0;
		}
	}

	private boolean precedent(char op1, char op2)
	{
		String ops = "+-*/^)";
		return ops.indexOf(op1) > ops.indexOf(op2);
	}

	private boolean hasOperator(String s)
	{
		for (char c : s.toCharArray())
			if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') 
				return true;
		return false;
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String expression;

		while (true)
		{
			System.out.print("Enter an expression: ");
			expression = input.nextLine();

			ExpressionSolver solver = new ExpressionSolver(expression);
			System.out.println(solver.solve(args.length > 0 && args[0].equals("debug")));
			System.out.println();
		}
	}
}