import static java.lang.Math.*;

public class TernarySearchImpl implements TernarySearch {

	
	/**
	 * Finds the minimum of a given function in the interval [left,right]
	 * 
	 * @param f
	 *            the function that is analyzed
	 * @param left
	 *            the left interval value
	 * @param right
	 *            the right interval value
	 * @return the minimum or <code>null</code> with illegal interval positions
	 *         
	 */
	@Override
	public Double findMinimum(Function f, double left, double right) {
		if (left > right){
			return null;
		}

		// Precision copied from the test file, could be bigger in order to have better accuracy.
		while (Math.abs(right - left) >= 0.000001){
			double right_third = right - (right - left) / 3;
			double left_third = left + (right - left) / 3;

			if(f.compute(left_third) > f.compute(right_third)){
				left = left_third;
			}
			else{
				right = right_third;
			}
		}

		return (left+right) / 2;



	}

}
