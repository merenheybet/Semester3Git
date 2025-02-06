
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LCSTest {

	public static final int MAX_THREADS = 10;
	public static final int THREADS = 8;

	/**
	 * Test computeValue with equal characters.
	 */
	@Test
	public void testComputeValueWithEqualCharacters() {
		final LCS lcs = new LCSSeq();
		char[] x = { 'a' };
		char[] y = { 'a' };
		int[][] matrix = new int[2][2];
		lcs.computeValue(x, y, matrix, 1, 1);
		assertEquals(1, matrix[1][1]);
	}

	/**
	 * Test computeValue with distinct characters.
	 */
	@Test
	public void testComputeValueWithDistinctCharacters() {
		final LCS lcs = new LCSSeq();
		char[] x = { 'a' };
		char[] y = { 'b' };
		int[][] matrix = new int[2][2];
		lcs.computeValue(x, y, matrix, 1, 1);
		assertEquals(0, matrix[1][1]);
	}

	/**
	 * Test computeValue with distinct characters for maximum.
	 */
	@Test
	public void testComputeValueWithDistinctCharactersForMaximum() {
		final LCS lcs = new LCSSeq();
		char[] x = { 'c', 'a' };
		char[] y = { 'a', 'b' };
		int[][] matrix = new int[3][3];
		matrix[1][2] = 1;
		lcs.computeValue(x, y, matrix, 2, 2);
		assertEquals(1, matrix[2][2]);
	}

	/**
	 * Sequence 1 sequential
	 */
	@Test
	public void testSeq1() throws InterruptedException {
		final LCS lcs = new LCSSeq();
		char[] x = "XMJYAUZ".toCharArray();
		char[] y = "MZJAWXU".toCharArray();
		String expected = "MJAU";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 2 sequential
	 */
	@Test
	public void testSeq2() throws InterruptedException {
		final LCS lcs = new LCSSeq();
		char[] x = "CHIMPANZEE".toCharArray();
		char[] y = "HUMAN".toCharArray();
		String expected = "HMAN";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 3 sequential
	 */
	@Test
	public void testSeq3() throws InterruptedException {
		final LCS lcs = new LCSSeq();
		char[] x = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA".toCharArray();
		char[] y = "GTCGTTCGGAATGCCGTTGCTCTGTAAA".toCharArray();
		String expected = "GTCGTCGGAAGCCGGCCGAA";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 4 sequential
	 */
	@Test
	public void testSeq4() throws InterruptedException {
		final LCS lcs = new LCSSeq();
		char[] x = "HALLO".toCharArray();
		char[] y = "HOLLA".toCharArray();
		String expected = "HLL";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 5 sequential
	 */
	@Test
	public void testSeq5() throws InterruptedException{
		final LCS lcs = new LCSSeq();
		char[] x =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
				"gtcacagctg gcaataacaa aagagaagaa agaagagctc catgattgta aaattgctcc"+
				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggaaattgt"+
				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
				"gtgtcgaatt gtt").toCharArray();
		char[] y =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
				"gtcacaactg gcaataacaa aagagaagaa agaagagctc caggattgta aaattgctcc"+
				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggagattgt"+
				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
				"gtgtcgaatt gtt").toCharArray();
		 String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		 assertEquals(2481, result.length());
	}

	/**
	 * Sequence 1 version with blocking queue
	 */
	@Test
	public void testQueued1() throws InterruptedException {
		final LCS lcs = new LCSQueue(THREADS);
		char[] x = "XMJYAUZ".toCharArray();
		char[] y = "MZJAWXU".toCharArray();
		String expected = "MJAU";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 2 version with blocking queue
	 */
	@Test
	public void testQueued2() throws InterruptedException {
		final LCS lcs = new LCSQueue(THREADS);
		char[] x = "CHIMPANZEE".toCharArray();
		char[] y = "HUMAN".toCharArray();
		String expected = "HMAN";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 3 version with blocking queue
	 */
	@Test
	public void testQueued3() throws InterruptedException {
		final LCS lcs = new LCSQueue(THREADS);
		char[] x = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA".toCharArray();
		char[] y = "GTCGTTCGGAATGCCGTTGCTCTGTAAA".toCharArray();
		String expected = "GTCGTCGGAAGCCGGCCGAA";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 4 version with blocking queue
	 */
	@Test
	public void testQueued4() throws InterruptedException {
		final LCS lcs = new LCSQueue(THREADS);
		char[] x = "HALLO".toCharArray();
		char[] y = "HOLLA".toCharArray();
		String expected = "HLL";
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(expected, result);
	}

	/**
	 * Sequence 5 version with blocking queue
	 */
	@Test
	public void testQueue5() throws InterruptedException{
		final LCS lcs = new LCSQueue(THREADS);
		char[] x =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
				"gtcacagctg gcaataacaa aagagaagaa agaagagctc catgattgta aaattgctcc"+
				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggaaattgt"+
				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
				"gtgtcgaatt gtt").toCharArray();
		char[] y =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
				"gtcacaactg gcaataacaa aagagaagaa agaagagctc caggattgta aaattgctcc"+
				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggagattgt"+
				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
				"gtgtcgaatt gtt").toCharArray();
		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
		assertEquals(2481, result.length());
	}

	/**
	 * Sequence 5 version with blocking queue. Test with different thread counts.
	 */
	@Test
	public void testQueue5Stress() throws InterruptedException{
		char[] x =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
				"gtcacagctg gcaataacaa aagagaagaa agaagagctc catgattgta aaattgctcc"+
				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggaaattgt"+
				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
				"gtgtcgaatt gtt").toCharArray();
		char[] y =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
				"gtcacaactg gcaataacaa aagagaagaa agaagagctc caggattgta aaattgctcc"+
				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggagattgt"+
				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
				"gtgtcgaatt gtt").toCharArray();
		for (int i = 1; i < MAX_THREADS; i++) {
			final LCS lcs = new LCSQueue(i);
			String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
			assertEquals(2481, result.length());
		}
	}

//	/**
//	 * Sequence 1 version with exchanger
//	 */
//	@Test
//	public void testEx1() throws InterruptedException {
//		final LCS lcs = new LCSExchanger(THREADS);
//		char[] x = "XMJYAUZ".toCharArray();
//		char[] y = "MZJAWXU".toCharArray();
//		String expected = "MJAU";
//		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
//		assertEquals(expected, result);
//	}
//
//	/**
//	 * Sequence 2 version with exchanger
//	 */
//	@Test
//	public void testEx2() throws InterruptedException {
//		final LCS lcs = new LCSExchanger(THREADS);
//		char[] x = "CHIMPANZEE".toCharArray();
//		char[] y = "HUMAN".toCharArray();
//		String expected = "HMAN";
//		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
//		assertEquals(expected, result);
//	}
//
//	/**
//	 * Sequence 3 version with exchanger
//	 */
//	@Test
//	public void testEx3() throws InterruptedException {
//		final LCS lcs = new LCSExchanger(THREADS);
//		char[] x = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA".toCharArray();
//		char[] y = "GTCGTTCGGAATGCCGTTGCTCTGTAAA".toCharArray();
//		String expected = "GTCGTCGGAAGCCGGCCGAA";
//		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
//		assertEquals(expected, result);
//	}
//
//	/**
//	 * Sequence 4 version with exchanger
//	 */
//	@Test
//	public void testEx4() throws InterruptedException {
//		final LCS lcs = new LCSExchanger(THREADS);
//		char[] x = "HALLO".toCharArray();
//		char[] y = "HOLLA".toCharArray();
//		String expected = "HLL";
//		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
//		assertEquals(expected, result);
//	}
//
//	/**
//	 * Sequence 5 version with exchanger
//	 */
//	@Test
//	public void testEx5() throws InterruptedException{
//		final LCS lcs = new LCSExchanger(THREADS);
//		char[] x =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
//				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
//				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
//				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
//				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
//				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
//				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
//				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
//				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
//				"gtcacagctg gcaataacaa aagagaagaa agaagagctc catgattgta aaattgctcc"+
//				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
//				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
//				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
//				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
//				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
//				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
//				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
//				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
//				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
//				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
//				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
//				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
//				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
//				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
//				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
//				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
//				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
//				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggaaattgt"+
//				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
//				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
//				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
//				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
//				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
//				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
//				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
//				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
//				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
//				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
//				"gtgtcgaatt gtt").toCharArray();
//		char[] y =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
//				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
//				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
//				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
//				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
//				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
//				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
//				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
//				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
//				"gtcacaactg gcaataacaa aagagaagaa agaagagctc caggattgta aaattgctcc"+
//				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
//				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
//				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
//				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
//				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
//				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
//				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
//				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
//				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
//				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
//				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
//				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
//				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
//				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
//				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
//				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
//				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
//				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggagattgt"+
//				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
//				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
//				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
//				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
//				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
//				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
//				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
//				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
//				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
//				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
//				"gtgtcgaatt gtt").toCharArray();
//		String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
//		assertEquals(2481, result.length());
//	}
//
//	/**
//	 * Sequence 5 version with exchanger. Test with different thread counts.
//	 */
//	@Test
//	public void testEx5Stress() throws InterruptedException{
//		char[] x =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
//				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
//				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
//				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
//				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
//				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
//				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
//				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
//				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
//				"gtcacagctg gcaataacaa aagagaagaa agaagagctc catgattgta aaattgctcc"+
//				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
//				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
//				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
//				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
//				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
//				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
//				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
//				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
//				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
//				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
//				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
//				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
//				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
//				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
//				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
//				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
//				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
//				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggaaattgt"+
//				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
//				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
//				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
//				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
//				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
//				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
//				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
//				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
//				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
//				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
//				"gtgtcgaatt gtt").toCharArray();
//		char[] y =("tatggagaga ataaaagaac tgagagatct aatgtcgcag tcccgcactc gcgagatact"+
//				"cactaagacc actgtggacc atatggccat aatcaaaaag tacacatcag gaaggcaaga"+
//				"gaagaacccc gcactcagaa tgaagtggat gatggcaatg agatacccaa ttacagcaga"+
//				"caagagaata atggacatga ttccagagag gaatgaacaa ggacaaaccc tctggagcaa"+
//				"aacaaacgat gctggatcag accgagtgat ggtatcacct ctggccgtaa catggtggaa"+
//				"taggaatggc ccaacaacaa gtacagttca ttaccctaag gtatataaaa cttatttcga"+
//				"aaaggtcgaa aggttgaaac atggtacctt cggccctgtc cacttcagaa atcaagttaa"+
//				"aataaggagg agagttgata caaaccctgg ccatgcagat ctcagtgcca aggaggcaca"+
//				"ggatgtgatt atggaagttg ttttcccaaa tgaagtgggg gcaagaatac tgacatcaga"+
//				"gtcacaactg gcaataacaa aagagaagaa agaagagctc caggattgta aaattgctcc"+
//				"cttgatggtg gcgtacatgc tagaaagaga attggtccgt aaaacaaggt ttctcccagt"+
//				"agccggcgga acaggcagtg tttatattga agtgttgcac ttaacccaag ggacgtgctg"+
//				"ggagcagatg tacactccag gaggagaagt gagaaatgat gatgttgacc aaagtttgat"+
//				"tatcgctgct agaaacatag taagaagagc agcagtgtca gcagacccat tagcatctct"+
//				"cttggaaatg tgccacagca cacagattgg aggagtaagg atggtggaca tccttagaca"+
//				"gaatccaact gaggaacaag ccgtagacat atgcaaggca gcaatagggt tgaggattag"+
//				"ctcatctttc agttttggtg ggttcacttt caaaaggaca agcggatcat cagtcaagaa"+
//				"agaagaagaa gtgctaacgg gcaacctcca aacactgaaa ataagagtac atgaagggta"+
//				"tgaagaattc acaatggttg ggagaagagc aacagctatt ctcagaaagg caaccaggag"+
//				"attgatccag ttgatagtaa gcgggagaga cgagcagtca attgctgagg caataattgt"+
//				"ggccatggta ttctcacaag aggattgcat gatcaaggca gttaggggcg atctgaactt"+
//				"tgtcaatagg gcaaaccagc gactgaaccc catgcaccaa ctcttgaggc atttccaaaa"+
//				"agatgcaaaa gtgcttttcc agaactgggg aattgaatcc atcgacaatg tgatgggaat"+
//				"ggtcggaata ctgcccgaca tgaccccaag cacggagatg tcgctgagag ggataagagt"+
//				"cagcaaaatg ggagtagatg aatactccag cacggagaga gtggtagtga gtattgaccg"+
//				"atttttaagg gttagagatc aaagagggaa cgtactattg tctcccgaag aagtcagtga"+
//				"aacgcaagga actgagaagt tgacaataac ttattcgtca tcaatgatgt gggagatcaa"+
//				"tggccctgag tcagtgctag tcaacactta tcaatggata atcaggaact gggagattgt"+
//				"gaaaattcaa tggtcacaag atcccacaat gttatacaac aaaatggaat ttgaaccatt"+
//				"tcagtctctt gtccctaagg caaccagaag ccggtacagt ggattcgtaa ggacactgtt"+
//				"ccagcaaatg cgggatgtgc ttgggacatt tgacactgtc caaataataa aacttctccc"+
//				"ctttgctgct gctccaccag aacagagtag gatgcaattt tcctcattga ctgtgaatgt"+
//				"gagaggatca gggttgagga tactggtaag aggcaattct ccagtattca attacaacaa"+
//				"ggcaaccaaa cgacttacag ttcttggaaa ggatgcaggt gcattgactg aagatccaga"+
//				"tgaaggcaca tctggggtgg agtctgctgt cctgagagga tttctcattt tgggcaaaga"+
//				"agacaagaga tatggcccag cattaagcat caatgaactg agcaatcttg caaaaggaga"+
//				"gaaagctaat gtgctaattg ggcaagggga cgtagtgttg gtaatgaaac gaaaacggga"+
//				"ctctagcata cttactgaca gccagacagc gaccaaaaga attcggatgg ccatcaatta"+
//				"gtgtcgaatt gtt").toCharArray();
//		for (int i = 1; i < MAX_THREADS; i++) {
//			final LCS lcs = new LCSExchanger(i);
//			String result = String.valueOf(lcs.longestCommonSubsequence(x, y));
//			assertEquals(2481, result.length());
//		}
//	}
}
