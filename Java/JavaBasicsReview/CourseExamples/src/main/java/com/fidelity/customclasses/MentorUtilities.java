package com.fidelity.customclasses;

public class MentorUtilities {

	private MentorUtilities() {
		// prevent this class from being instantiated
	}

	// Other methods
	@SuppressWarnings("unused")
	public static void exampleDowncastMethod(Mentor mentor) {
		if (mentor instanceof FullTimeMentor) {
			FullTimeMentor ftm = (FullTimeMentor) mentor;
			// now call subclass specific methods

		}
		// work with generic Mentor methods here
		
	}
	
	@SuppressWarnings("unused")
	public static void processPay(Mentor mentor) {
		double pay = mentor.calculatePay();
		// process pay as required
	}
}
