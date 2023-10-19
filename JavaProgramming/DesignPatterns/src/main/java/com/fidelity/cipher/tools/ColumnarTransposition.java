package com.fidelity.cipher.tools;

public class ColumnarTransposition {
	private Disruption disruption;

	private int[] keyOrder;

	public ColumnarTransposition(String key) {
		this(key, false, Disruption.NONE);
	}

	public ColumnarTransposition(String key, Disruption disruption) {
		this(key, false, disruption);
	}

	public ColumnarTransposition(String key, boolean allowDuplicates) {
		this(key, allowDuplicates, Disruption.NONE);
	}

	public ColumnarTransposition(String key, boolean allowDuplicates, Disruption disruption) {
		this.disruption = disruption;
		keyOrder = ColumnKey.keyOrderFromText(key, allowDuplicates);
	}

	public String encode(String message) {
		int keyLen = keyOrder.length;
		int gridDepth = message.length() / keyLen;
		if (message.length() % keyLen != 0) {
			gridDepth++;
		}
		Character[][] grid = populateGridForEncoding(message, keyOrder, gridDepth);

		return readGridByColumns(keyLen, gridDepth, grid).toString();
	}

	public Character[][] populateGridForEncoding(String message, int[] keyOrder, int gridDepth) {
		return disruption.populateGridForEncoding(message, keyOrder, gridDepth);
	}

	public String decode(String ciphertext) {
		int keyLen = keyOrder.length;
		int overlap = ciphertext.length() % keyLen;
		int fullRows = ciphertext.length() / keyLen;
		int gridDepth = fullRows + ((overlap == 0) ? 0 : 1);

		Character[][] grid = new Character[gridDepth][keyLen];

		int currentPos = 0;
		for (int col = 0; col < keyLen; col++) {
			int colDepth = fullRows;
			// If it is a long column rather than a short one
			if (keyOrder[col] < overlap) {
				colDepth++;
			}
			for (int row = 0; row < colDepth; row++) {
				grid[row][keyOrder[col]] = ciphertext.charAt(currentPos++);
			}
		}

		return readGridByRows(keyOrder, gridDepth, grid).toString();
	}

	private StringBuilder readGridByRows(int[] keyOrder, int gridDepth, Character[][] grid) {
		return disruption.readGridByRows(keyOrder, gridDepth, grid);
	}

	private StringBuilder readGridByColumns(int keyLen, int gridDepth, Character[][] grid) {
		StringBuilder sb = new StringBuilder();
		for (int col = 0; col < keyLen; col++) {
			for (int row = 0; row < gridDepth; row++) {
				if (grid[row][keyOrder[col]] != null) {
					sb.append(grid[row][keyOrder[col]]);
				}
			}
		}
		return sb;
	}

	public static enum Disruption {
		NONE {
			@Override
			protected Character[][] populateGridForEncoding(String message, int[] keyOrder, int gridDepth) {
				int keyLen = keyOrder.length;
				int messagePosition = 0;							// character position in message
				Character[][] grid = new Character[gridDepth][keyLen];

				for (int row = 0; row < gridDepth; row++) {
					for (int col = 0; col < keyLen; col++) {
						grid[row][col] = message.charAt(messagePosition++);
						if (messagePosition >= message.length()) {
							break;
						}
					}
				}
				return grid;
			}

			@Override
			protected StringBuilder readGridByRows(int[] keyOrder, int gridDepth, Character[][] grid) {
				StringBuilder sb = new StringBuilder();
				int keyLen = keyOrder.length;
				for (int row = 0; row < gridDepth; row++) {
					for (int col = 0; col < keyLen; col++) {
						if (grid[row][col] != null) {
							sb.append(grid[row][col]);
						}
					}
				}
				return sb;
			}

		},

		/*
		 * 24301
		 * 
		 * AAA    <- void starts in the column that contains 0, keyPosition = 0, rowIV = 0, startOVICR = 3
		 * AAAA   <-                                            keyPosition = 0, rowIV = 1, startOVICR = 4
		 * AAAAA  <- full row between voids                     keyPosition = 0, rowIV = 2, startOVICR = 5
		 * AAAA   <- void starts in the column that contains 1  keyPosition = 1, rowIV = 0, startOVICR = 4
		 * AAAAA  <- full row between voids                     keyPosition = 1, rowIV = 1, startOVICR = 5
		 *        <- void starts in the column that contains 2  keyPosition = 2, rowIV = 0, startOVICR = 0
		 * A      
		 * AA
		 * AAA
		 * AAAA
		 * AAAAA
		 * A      <- don't forget partial lines at the end!
		 * 
		 */
		TRIANGULAR {
			@Override
			protected Character[][] populateGridForEncoding(String message, int[] keyOrder, int gridDepth) {
				int keyLen = keyOrder.length;
				int charsOnLastLine = message.length() % keyLen;	// number of characters on last line
				if (charsOnLastLine == 0) {
					charsOnLastLine = keyLen;
				}
				int messagePosition = 0;							// character position in message
				int keyPosition = 0;								// position in key (current void start position)
				int rowInVoid = 0;									// row in void (void size decreases by 1 on each line)
				Character[][] grid = new Character[gridDepth][keyLen];

				for (int row = 0; row < gridDepth; row++) {
					int startOfVoidInCurrentRow = keyOrder[keyPosition] + rowInVoid;
					// Handle last line differently
					if (row == gridDepth - 1) {
						startOfVoidInCurrentRow = Math.min(charsOnLastLine, startOfVoidInCurrentRow);
					}
					// Populate this part row
					for (int col = 0; col < startOfVoidInCurrentRow; col++) {
						grid[row][col] = message.charAt(messagePosition++);
						if (messagePosition >= message.length()) {
							break;
						}
					}
					// There is a full row between each void, so if that was a full row, start a new void
					if (startOfVoidInCurrentRow == keyLen) {
						keyPosition++;
						rowInVoid = 0;
					} else {
						rowInVoid++;
					}
				}

				// Now fill in any blanks
				if (messagePosition < message.length()) {
					for (int row = 0; row < gridDepth; row++) {
						for (int col = 0; col < keyLen; col++) {
							if (grid[row][col] == null) {
								grid[row][col] = message.charAt(messagePosition++);
								if (messagePosition >= message.length()) {
									break;
								}
							}
						}
						if (messagePosition >= message.length()) {
							break;
						}
					}
				}

				return grid;
			}

			@Override
			protected StringBuilder readGridByRows(int[] keyOrder, int gridDepth, Character[][] grid) {
				StringBuilder sb = new StringBuilder();
				int keyLen = keyOrder.length;
				int keyPosition = 0;								// position in key (current void start position)
				int rowInVoid = 0;									// row in void (void size decreases by 1 on each line)

				for (int row = 0; row < gridDepth; row++) {
					int startOfVoidInCurrentRow = keyOrder[keyPosition] + rowInVoid;
					// Extract this part row
					for (int col = 0; col < startOfVoidInCurrentRow; col++) {
						if (grid[row][col] == null) {
							break;
						}
						sb.append(grid[row][col]);
					}
					// There is a full row between each void, so if that was a full row, start a new void
					if (startOfVoidInCurrentRow == keyLen) {
						keyPosition++;
						rowInVoid = 0;
					} else {
						rowInVoid++;
					}
				}

				// Reset voids
				keyPosition = 0;
				rowInVoid = 0;

				for (int row = 0; row < gridDepth; row++) {
					int startOfVoidInCurrentRow = keyOrder[keyPosition] + rowInVoid;
					// Extract the void
					for (int col = startOfVoidInCurrentRow; col < keyLen; col++) {
						if (grid[row][col] == null) {
							break;
						}
						sb.append(grid[row][col]);
					}
					// There is a full row between each void, so if that was a full row, start a new void
					if (startOfVoidInCurrentRow == keyLen) {
						keyPosition++;
						rowInVoid = 0;
					} else {
						rowInVoid++;
					}
				}
				return sb;
			}
		};

		protected abstract Character[][] populateGridForEncoding(String message, int[] keyOrder, int gridDepth);
		protected abstract StringBuilder readGridByRows(int keyOrder[], int gridDepth, Character[][] grid);
	}


	/*
	 * A columnar transposition is one of the simplest encodings used. The message
	 * is written below a key word. Each character in the keyword is assigned a
	 * number based on the usual sorting sequence. Then columns are read off in 
	 * the order suggested by the keyword numbers.
	 * 
	 * Very long messages are usually chunked by some pre-determined size, but this
	 * implementation doesn't support that.
	 * 
	 * Columnar transpositions are not very effective on their own, but a double
	 * transposition or a disrupted transposition can form part of a very effective
	 * hand cipher.
	 * 
	 * _0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.
	 * 
	 * RoYgBiV <- keyword
	 * 1634052 <- keyword numbers from plaintext above
	 * 
	 * Hello_W <- message written under the keyword
	 * orld
	 * 
	 * BRVYgio <- keyword columns re-ordered in numeric order
	 * oHWll_e
	 *  o ld r
	 * 
	 * Columns read off in numeric order:
	 * o Ho W ll ld _ er  ->  oHoWllld_er
	 * 
	 * 
	 * RoYgBiV
	 * 1634052
	 * 
	 * Now_is_
	 * the_tim
	 * e_for_a
	 * ll_good
	 * _men_to
	 * _come_t
	 * o_the_a
	 * id_of_t
	 * he_part
	 * y
	 * 
	 * 
	 * BRVYgio
	 * iN_w_so
	 * ttme_ih
	 * reafo__
	 * old_gol
	 * __oentm
	 * e_tom_c
	 * eoath__
	 * fit_o_d
	 * aht_pre
	 *  y
	 * 
	 * itro_eefaNtel__oihy_madotattwef_eot____ognmhopsi_ot___roh_lmc_de
	 * 
	 * 
	 * ThE_aNsWeRiS42
	 *  1    1 1 1   
	 * 71309438052621
	 * 
	 * Now_is_the_tim
	 * e_for_all_good
	 * _men_to_come_t
	 * o_the_aid_of_t
	 * he_party
	 * 
	 * 
	 * _24ENRSTWaehis
	 * 
	 * _miwsetNtiho__
	 * odof__oelrl_ga
	 * nt_etoe___cmmo
	 * ht_t__foied_oa
	 * p  _r  hya e t
	 * 
	 * _onhpmdttio__wfet_s_t_re_o_toefNe_ohtl_iyir_eahlcdo_m_e_gmo_aoat
	 * 
	 * 
	 * The key can also be constructed with repeated letters. Some transpositions
	 * treat the repeated letters differently (perhaps reading them backwards) and
	 * others just number them from left to right.
	 * 
	 * ThE_aNsWeR_iS_42
	 *  1  1 111  1
	 * 9350165027148243
	 * 
	 * Now_is_the_time_
	 * for_all_good_men
	 * _to_come_to_the_
	 * aid_of_the_party
	 * 
	 * 
	 * ___24ENRSTWaehis
	 *           111111
	 * 0123456789012345
	 * 
	 * __m_ewseiNtihot_
	 * _omnerlo_f_agodl
	 * _oh_eoott_ec_t_m
	 * __rytdfeaatohip_
	 * 
	 * _____oo_mmhr_n_yeeetwrodslofeotei_taNf_at_etiacohg_hootitd_p_lm_
	 * 
	 * 
	 * 
	 * A disrupted transposition leaves out certain cells in the grid, or
	 * delays filling them. Here a triangular disruption starts with each of
	 * the key columns in turn. This type of disruption was part of the VIC
	 * cipher, one of the most complex and effective hand ciphers ever used.
	 * 
	 * If the key evaluates to 24301, then the voids look like this:
	 * 
	 * 24301
	 * 
	 * AAA    <- void starts in the column that contains 0
	 * AAAA
	 * AAAAA  <- full row between voids
	 * AAAA   <- void starts in the column that contains 1
	 * AAAAA  <- full row between voids
	 *        <- void starts in the column that contains 2
	 * A
	 * AA
	 * AAA
	 * AAAA
	 * AAAAA
	 * A      <- don't forget partial row at end
	 * 
	 * Once the right number of rows have been filled, go back and
	 * fill in the voids:
	 * 
	 * 42301
	 * 
	 * AAAOO  <- void starts in the column that contains 0
	 * AAAAO
	 * AAAAA  <- full row between voids
	 * AAAAO  <- void starts in the column that contains 1
	 * AAAAA  <- full row between voids
	 * OOOOO  <- void starts in the column that contains 2
	 * AOOOO
	 * AAOOO
	 * AAAOO
	 * AAAAO
	 * AAAAA
	 * A      <- don't forget partial row at end
	 * 
	 * Here's an example:
	 * 
	 * First work out how many rows are needed:
	 * Now_is_the_tim/e_for_all_good/_men_to_come_t/o_the_aid_of_t/
	 * he_party_and_t/he_quick_brown/_fox_jumps_ove/r_the_lazy_dog/
	 * 
	 * Number of rows = 8
	 * 
	 * Then fill the grid, leaving out the triangular voids
	 * 
	 * ThE_aNsWeRiS42
	 *  1    1 1 1   
	 * 71309438052621
	 * 
	 * Now
	 * _is_
	 * the_t
	 * ime_fo
	 * r_all_g
	 * ood_men_
	 * to_come_t
	 * o_the_aid_
	 * 
	 * ThE_aNsWeRiS42
	 *  1    1 1 1   
	 * 71309438052621
	 * 
	 * Nowof_the_part
	 * _is_y_and_the_
	 * the_tquick_bro
	 * ime_fown_fox_j
	 * r_all_gumps_ov
	 * ood_men_er_the
	 * to_come_t_lazy
	 * o_the_aid__dog
	 * 
	 * o___l_cht_ojveygrer_ohzowseead_t__qo_em___kfpr__ahbx_tadN_tirotohninu__ifytflmoeedc_metdoihm_oo_pt_os_l_tauwgnea
	 * 
	 * Another example:
	 * 
	 * Now_is_/the_tim/e_for_a/ll_good/_men_to/_come_t/o_the_a/id_of_t/
	 * he_part/y
	 * 
	 * 9 full rows, 1 character on row 10
	 * 
	 * RoYgBiV
	 * 1634052
	 * 
	 * Now_
	 * is_th
	 * e_time
	 * _for_al
	 * 
	 * l
	 * _g
	 * ood
	 * _men
	 * _
	 * 
	 * 
	 * RoYgBiV
	 * 1634052
	 * 
	 * Now_to_
	 * is_thco
	 * e_timem
	 * _for_al
	 * e_to_th
	 * le_aid_
	 * _gof_th
	 * oode_pa
	 * _menrty
	 * _
	 * 
	 * thm__i__rNie_el_o___omlh_hayw_tot_ode_tiroafenoceatdtptos_f_egom
	 * 
	 * 
	 * RoYgBiV
	 * 1634052
	 * 
	 * Hellrld
	 * o_Wo
	 * 
	 * rHodlWlole_
	 * 
	 * 
	 */
}
