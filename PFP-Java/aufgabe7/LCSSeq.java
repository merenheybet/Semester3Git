public class LCSSeq extends LCS{
    @Override
    public char[] longestCommonSubsequence(char[] x, char[] y) {
        // Calculate the matrix sequentially
        int[][] matrix = new int[y.length + 1][x.length + 1];

        for(int i = 0; i < y.length + 1; i++){
            for(int j = 0; j < x.length + 1; j++){
                this.computeValue(x, y, matrix, i, j);
            }
        }

        return readMatrix(matrix, x, y);
    }
}
