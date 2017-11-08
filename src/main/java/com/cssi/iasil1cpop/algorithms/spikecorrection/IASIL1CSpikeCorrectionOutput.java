package com.cssi.iasil1cpop.algorithms.spikecorrection;

import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

public class IASIL1CSpikeCorrectionOutput {
	private ComplexArray spkCorrectedScience;
	private Double spkCoQualityIndex;
	private boolean spkCoQualityFlag;
	public ComplexArray getSpkCorrectedScience() {
		return spkCorrectedScience;
	}
	public void setSpkCorrectedScience(ComplexArray spkCorrectedScience) {
		this.spkCorrectedScience = spkCorrectedScience;
	}
	public Double getSpkCoQualityIndex() {
		return spkCoQualityIndex;
	}
	public void setSpkCoQualityIndex(Double spkCoQualityIndex) {
		this.spkCoQualityIndex = spkCoQualityIndex;
	}
	public boolean isSpkCoQualityFlag() {
		return spkCoQualityFlag;
	}
	public void setSpkCoQualityFlag(boolean spkCoQualityFlag) {
		this.spkCoQualityFlag = spkCoQualityFlag;
	}
}
