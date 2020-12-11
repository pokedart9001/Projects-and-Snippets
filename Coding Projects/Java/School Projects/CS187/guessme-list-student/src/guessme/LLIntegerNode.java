package guessme;

/**
 * This class defines a linked list node storing an integer.
 * Use primitive type int (do not use wrapper class Integer)
 * You must provide the following methods:
 * - a constructor
 * - a setInfo method and a getInfo method
 * - a setLink method and a getLink method
 */
public class LLIntegerNode {
	private int data;
	private LLIntegerNode link;
	
	public LLIntegerNode(int value)
	{
		setInfo(value);
		setLink(null);
	}
	
	public int getInfo()
	{
		return data;
	}
	
	public void setInfo(int value)
	{
		data = value;
	}
	
	public LLIntegerNode getLink()
	{
		return link;
	}
	
	public void setLink(LLIntegerNode node)
	{
		link = node;
	}
}

