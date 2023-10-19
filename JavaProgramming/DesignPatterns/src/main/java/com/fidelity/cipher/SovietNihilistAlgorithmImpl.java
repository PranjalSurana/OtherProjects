package com.fidelity.cipher;

import java.util.Objects;

import com.fidelity.cipher.tools.ColumnarTransposition;
import com.fidelity.cipher.tools.ModularArithmetic;
import com.fidelity.cipher.tools.PolybiusSquare;
import com.fidelity.cipher.tools.StraddlingCheckerboard;

public class SovietNihilistAlgorithmImpl implements CipherAlgorithm {
	private ModularArithmetic ma;
	private ColumnarTransposition ct;
	private StraddlingCheckerboard sc;

	private static final int PERIOD = 5;

	private static final String PLAINTEXT = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	@Override
	public String encrypt(String message) {
		checkKey();
		StringBuilder sb = new StringBuilder(ct.encode(ma.add(sc.encrypt(message))));
		int positionInOutputString = PERIOD;
		while (positionInOutputString < sb.length()) {
			sb.insert(positionInOutputString++, ' '); // add extra position because we inserted a space
			positionInOutputString += PERIOD;
		}
		return sb.toString();
	}

	@Override
	public String decrypt(String message) {
		checkKey();
		if (!message.matches("[\\d ]*")) {
			throw new IllegalArgumentException("Nihilist messages to be decrypted must contain only digits and space");
		}
		StringBuilder sb = new StringBuilder(message);
		while (sb.indexOf(" ") >= 0) {
			sb.deleteCharAt(sb.indexOf(" "));
		}
		return sc.decrypt(ma.subtract(ct.decode(sb.toString())));
	}

	private void checkKey() {
		if (Objects.isNull(ct)) {
			throw new IllegalStateException("Keys not set");
		}
	}

	@Override
	public void setKey(String key) {
		PolybiusSquare psPlaintext = new PolybiusSquare("");

		// In this case we will split the key on a comma
		String[] keys = key.split(",");
		if (keys.length != 2) {
			throw new IllegalArgumentException("Two keys required, separated by comma");
		}
		sc = new StraddlingCheckerboard(keys[0]);

		StringBuilder sb = new StringBuilder();
		for (char ch : keys[1].toCharArray()) {
			if (PLAINTEXT.indexOf(ch) < 0) {
				throw new IllegalArgumentException("Key may only contain upper & lower roman alphabet, numbers and space");
			}
			PolybiusSquare.RowCol rc = psPlaintext.findChar(ch);
			sb.append(Integer.toString(rc.getRow()));
			sb.append(Integer.toString(rc.getCol()));
		}

		ma = new ModularArithmetic(sb.toString());
		ct = new ColumnarTransposition(keys[1], ColumnarTransposition.Disruption.TRIANGULAR);
	}

	/*
	 * This cipher is influenced by the Nihilist family of ciphers used (mainly) by
	 * Russian spies from WW2 through the 1950s. The pinnacle of Nihilist development
	 * was the VIC cipher, which remained unbroken until a defector revealed how it
	 * worked.
	 * 
	 * Nihilist ciphers share a number of common features: use of Polybius Square
	 * (later replaced by a straddling checkerboard) to generate ciphertext or keytext
	 * and a key addition (later modular addition). The VIC cipher also included two
	 * columnar transpositions (one disrupted) and chain addition to generate pseudo-
	 * random numbers from a small key.
	 * 
	 * In this derivation, we first encode using a straddling checkerboard, then
	 * perform a modular addition, then use a disrupted columnar transposition.
	 * 
	 * With checkerboard key 3517269, Hello_World becomes 57033334312529493332
	 * 
	 * Take the key "Nihilist" and generate 3055545560556770 from a plain Polybius Square
	 * 
	 * And, by modular addition:
	 * 
	 * 57033334312529493332
	 * 30555455605567703055
	 * --------------------
	 * 87588789917086196387
	 * 
	 * Again, using Nihilist (without repeated letters), do a disrupted columnar transposition
	 * 
	 * Nihlst
	 * 021345
	 * 
	 * 789917
	 * 808619
	 * 756387
	 * 88
	 * 
	 * 7878 / 986 / 8058 / 963 / 118 / 797
	 * 
	 * Which we represent as: 78789 86805 89631 18797
	 * 
	 * Now_is_the_time_for_all_good_men_to_come_to_the_aid_of_the_party becomes
	 * 5343763126831213003121637303123744931223333312377443231237307312143123543730312143121300312263231243743121300312378291375
	 * 3055545560556770305554556055677030555455605567703055545560556770305554556055677030555455605567703055545560556770305554556
	 * -------------------------------------------------------------------------------------------------------------------------
	 * 8398208686387983308675183358790774486678938879070498776797853082448677099785989173676755917720934298288681856082673745821
	 * 
	 * 121 / 6 = 20 + 1
	 * 
	 * Nihlst
	 * 021345
	 * 
	 * -
 	 * 8
 	 * 39
 	 * 820
 	 * 8686
 	 * 38798
 	 * 330867
 	 * 51
 	 * 833
 	 * 5879
 	 * 07744
 	 * 866789
 	 * 3
 	 * 88
 	 * 790
 	 * 7049
 	 * 87767
 	 * 978530
 	 * 824
 	 * 4867
 	 * 7
 	 * 
 	 * Nihlst
	 * 021345
	 * 
	 * 099785
 	 * 898917
 	 * 393676
 	 * 820755
 	 * 868691
 	 * 387987
 	 * 330867
 	 * 517209
 	 * 833342
 	 * 587998
 	 * 077442
 	 * 866789
 	 * 388681
 	 * 888560
 	 * 790826
 	 * 704973
 	 * 877677
 	 * 978530
 	 * 824458
 	 * 486721
 	 * 7
 	 * 
 	 * 083883358508387789847 / 98308707377688047846 / 99926831387688907728 /
 	 * 79676982394765896547 / 81759860494886277352 / 57651779282910637081
 	 * 
 	 * 08388 33585 08387 78984 79830 87073 77688 04784 69992 68313 87688 90772
 	 * 87967 69823 94765 89654 78175 98604 94886 27735 25765 17792 82910 63708 1
	 * 
	 */
}
