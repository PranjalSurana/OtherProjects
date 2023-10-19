package com.fidelity.enums;

public enum PerformanceReviewResult {
    BELOW("does not meet expectations") {
    	@Override
    	public boolean isApplicable(int grade) {
    		return grade < 75;
    	}
    }, 
    AVERAGE("meets expectations") {
    	@Override
    	public boolean isApplicable(int grade) {
    		return grade >= 75 && grade < 95;
    	}
    },             
    ABOVE("exceeds expectations") {
    	@Override
    	public boolean isApplicable(int grade) {
    		return grade >= 95;
    	}
    };

	// Fields
    private String message;

    // Constructors
    private PerformanceReviewResult(String msg) {
        this.message = msg;
    }

    // Other methods
    public String getMessage() {
        return message;
    }
    
    public boolean isApplicable(int grade) {
    	return false;
    }
}