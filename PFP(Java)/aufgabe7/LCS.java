/**
 * 
 * Base class of classes that determine the longest common subsequence with
 * dynamic programming.
 * 
 * The sequences are represented by char[] arrays.
 * 
 * See:
 * http://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 * 
 */
public abstract class LCS {

	/**
	 * Computes the value of the LCS matrix at the position specified by row and
	 * column.
	 *
	 * @param x
	 *            first sequence
	 * @param y
	 *            second sequence
	 * @param matrix
	 *            DP-Matrix
	 * @param row
	 *            the row of the value to compute
	 * @param column
	 *            the column of the value to compute
	 */

	/*
	* if row or col=0 then it's epsilon with epsilon, which is trivially 0
	* for any other row/col combination take the charAt these Indexes and check if they're the same
	* char: if yes get the left diagonal value++; if no get the maximum from upper or left neighbour.
	*
	* y -> row, x-> col?
	*
	*/
	public final void computeValue(char[] x, char[] y, int[][] matrix, int row, int column) {
		if(row == 0 || column == 0){
			matrix[row][column] = 0;
			return;
		}
		// if characters match increment left diagonal value by one
		if(x[column-1] == y[row-1]){
			matrix[row][column] = matrix[row-1][column-1] + 1;
		}
		// if the characters don't match
		else{
			matrix[row][column] = Math.max(matrix[row-1][column], matrix[row][column-1]);
		}
	}

	/**
	 * 
	 * Reads the longest common subsequence from a dynamic programming
	 * matrix
	 * 
	 * @param matrix
	 *            DP-Matrix
	 * @param x
	 *            first sequence
	 * @param y
	 *            second sequence
	 * @return the longest common subsequence as char[]
	 */
	public final char[] readMatrix(int[][] matrix, char[] x, char[] y) {
		char[] result = new char[matrix[y.length][x.length]];
		int i = y.length;
		int j = x.length;
		while (i > 0 && j > 0) {
			if (y[i - 1] == x[j - 1]) {
				assert i > 0;
				assert matrix[i][j] > 0;
				assert matrix[i][j] <= result.length;
				result[matrix[i][j] - 1] = y[i - 1];
				j--;
				i--;
			} else if (matrix[i][j - 1] > matrix[i - 1][j]) {
				j--;
			} else {
				i--;
			}
		}
		return result;
	}

	/**
	 * Creates and fills a dynamic programming matrix then reads the result
	 * with the readMatrix method.
	 * 
	 * @param x
	 *            first sequence
	 * @param y
	 *            second sequence
	 * @return the longest common subsequence as char[]
	 */
	public abstract char[] longestCommonSubsequence(final char[] x, final char[] y);
}
