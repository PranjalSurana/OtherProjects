package com.fidelity.model;

public enum PerformanceReviewResult {
    BELOW(1),
    AVERAGE(2),
    ABOVE(3);

    private int code;

    private PerformanceReviewResult(int code) {
        this.code = code;
    }

    public static PerformanceReviewResult of(int code) {
        for (PerformanceReviewResult revRes : PerformanceReviewResult.values()) {
            if (revRes.getCode() == code) {
                return revRes;
            }
        }
        throw new IllegalArgumentException("bad code: " + code);
    }

    public int getCode() {
        return code;
    }
}