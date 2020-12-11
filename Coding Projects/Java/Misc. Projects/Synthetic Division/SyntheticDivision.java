import java.util.Arrays;

public class SyntheticDivision
{
	public static void main(String[] args)
	{
		System.out.println(Arrays.toString(division(3, 1, -2, 2)));
	}

	public static int[] division(int root, int... coefficients)
	{
		int[] result = new int[coefficients.length];
		result[0] = coefficients[0];

		for (int i = 1; i < coefficients.length; i++)
			result[i] = coefficients[i] + root * result[i-1];

		return result;
	}
}