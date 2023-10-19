package com.fidelity.packages;

import com.fidelity.objects.Mentor;

public class UseMentor {

	public com.fidelity.business.Mentor copyMentor(Mentor mentor) {
		return new com.fidelity.business.Mentor(mentor.getFullName());
	}
}
