package com.fidelity.cipher.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ColumnarTranspositionTest {

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          'oHoWllld er', 'Hello World'",
		"'RoYgBiV',          'itro eefaNtel  oihy madotattwef eot    ognmhopsi ot   roh lmc de', "
									+ "'Now is the time for all good men to come to the aid of the party'",
		"'ThE aNsWeR iS 42', ' onhpmdttio  wfet s t re o toefNe ohtl iyir eahlcdo m e gmo aoat', "
									+ "'Now is the time for all good men to come to the aid of the party'"
	})
	void testEncode(String key, String expected, String message) {
		ColumnarTransposition ct = new ColumnarTransposition(key);
		assertEquals(expected, ct.encode(message));
	}

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          'oHoWllld er', 'Hello World'",
		"'RoYgBiV',          'itro eefaNtel  oihy madotattwef eot    ognmhopsi ot   roh lmc de', "
									+ "'Now is the time for all good men to come to the aid of the party'",
		"'ThE aNsWeR iS 42', ' onhpmdttio  wfet s t re o toefNe ohtl iyir eahlcdo m e gmo aoat', "
									+ "'Now is the time for all good men to come to the aid of the party'"
	})
	void testDecode(String key, String ciphertext, String expected) {
		ColumnarTransposition ct = new ColumnarTransposition(key);
		assertEquals(expected, ct.decode(ciphertext));
	}

	@Test
	void testEncodeWithRepeats() {
		ColumnarTransposition ct = new ColumnarTransposition("ThE aNsWeR iS 42", true);
		assertEquals("     oo mmhr n yeeetwrodslofeotei taNf at etiacohg hootitd p lm ", 
				ct.encode("Now is the time for all good men to come to the aid of the party"));
	}

	@Test
	void testDecodeWithRepeats() {
		ColumnarTransposition ct = new ColumnarTransposition("ThE aNsWeR iS 42", true);
		assertEquals("Now is the time for all good men to come to the aid of the party", 
				ct.decode("     oo mmhr n yeeetwrodslofeotei taNf at etiacohg hootitd p lm "));
	}

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          'rHodlWlole ', 'Hello World'",
		"'RoYgBiV',          'thm  i  rNie el o   omlh hayw tot ode tiroafenoceatdtptos f egom', "
			+ "'Now is the time for all good men to come to the aid of the party'",
		"'ThE aNsWeR iS 42', 'o   l cht ojveygrer ohzowseead t  qo em   kfpr  ahbx tadN tirotohninu  ifytflmoeedc metdoihm oo pt os l tauwgnea', "
			+ "'Now is the time for all good men to come to the aid of the party and the quick brown fox jumps over the lazy dog'"
	})
	void testEncodeWithDisruption(String key, String expected, String message) {
		ColumnarTransposition ct = new ColumnarTransposition(key, ColumnarTransposition.Disruption.TRIANGULAR);
		assertEquals(expected, ct.encode(message));
	}

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          'rHodlWlole ', 'Hello World'",
		"'RoYgBiV',          'thm  i  rNie el o   omlh hayw tot ode tiroafenoceatdtptos f egom', "
			+ "'Now is the time for all good men to come to the aid of the party'",
		"'ThE aNsWeR iS 42', 'o   l cht ojveygrer ohzowseead t  qo em   kfpr  ahbx tadN tirotohninu  ifytflmoeedc metdoihm oo pt os l tauwgnea', "
			+ "'Now is the time for all good men to come to the aid of the party and the quick brown fox jumps over the lazy dog'"
	})
	void testDecodeWithDisruption(String key, String ciphertext, String expected) {
		ColumnarTransposition ct = new ColumnarTransposition(key, ColumnarTransposition.Disruption.TRIANGULAR);
		assertEquals(expected, ct.decode(ciphertext));
	}


}
