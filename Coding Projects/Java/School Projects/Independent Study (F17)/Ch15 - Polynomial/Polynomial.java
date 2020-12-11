import java.util.LinkedList;
import java.util.Collections;

public class Polynomial
{
	private class Term implements Comparable<Term>
	{
		private int[] term = new int[2];

		private Term(int coefficient, int power)
		{
			term[0] = coefficient;
			term[1] = power;
		}

		private int coefficient()
		{
			return term[0];
		}

		private int power()
		{
			return term[1];
		}

		public int compareTo(Term other)
		{
			return other.term[1] - term[1];
		}
	}

	private LinkedList<Term> terms;

	public Polynomial()
	{
		terms = new LinkedList<Term>();
	}

	public Polynomial(int coefficient, int power)
	{
		this();
		addTerm(coefficient, power);
	}

	public LinkedList<Term> terms()
	{
		return terms;
	}

	public void addTerm(int coefficient, int power)
	{
		for (Term term : terms)
		{
			if (term.power() == power)
			{
				coefficient += term.coefficient();
				terms.remove(term);
				break;
			}
		}
		terms.add(new Term(coefficient, power));
		Collections.sort(terms);
	}

	public void addTerm(Term term)
	{
		addTerm(term.coefficient(), term.power());
	}

	public Polynomial add(Polynomial other)
	{
		Polynomial result = new Polynomial();
		for (Term term : terms)
			result.addTerm(term);

		for (Term term : other.terms)
			result.addTerm(term);

		return result;
	}

	public Polynomial multiply(Polynomial other)
	{
		Polynomial result = new Polynomial();
		for (Term term1 : terms)
			for (Term term2 : other.terms)
				result.addTerm(term1.coefficient()*term2.coefficient(), term1.power()+term2.power());
		return result;
	}

	public Polynomial derivative()
	{
		Polynomial result = new Polynomial();
		LinkedList<Term> temp = new LinkedList<Term>(terms);
		int size = temp.size();

		for (int i = 0; i < size; i++)
		{
			Term term = temp.pop();
			if (term.power() != 0)
				result.addTerm(term.coefficient()*term.power(), term.power()-1);
		}
		return result;
	}

	public String toString()
	{
		String result = "";
		for (Term term : terms)
			result += String.format("%dx^%d + ", term.coefficient(), term.power());
		result = result.replace("+ -", "- ").replaceAll("x\\^0|\\^1", "").replaceAll("(\\D)1x|(^)1x", "$1x");
		return result.substring(0, result.length()-3);
	}

	public static void main(String[] args)
	{
		Polynomial poly1 = new Polynomial();
		poly1.addTerm(11, 1);
		poly1.addTerm(6, 2);

		Polynomial poly2 = new Polynomial();
		poly2.addTerm(1, 3);
		poly2.addTerm(10, 0);
		poly2.addTerm(3, 2);

		System.out.println("Poly1: " + poly1);
		System.out.println("Poly2: " + poly2);
		System.out.println("Addition: " + poly1.add(poly2));
		System.out.println("Multiplication: " + poly1.multiply(poly2));
		System.out.println("Derivative of Poly1: " + poly1.derivative());
		System.out.println("Derivative of Poly2: " + poly2.derivative());
	}
}