package com.cssi.iasil1cpop.algorithms.spikecorrection;

import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

public class IASIL1CSpikeCorrectionInput {
	private ComplexArray spk;
	private Integer firstScience;
	private Integer lastScience;
	private Double dsigma;
	private Integer nfft;
	private Integer firstSpk;
	private Integer lastSpk;
	private Double dopd;
	private Integer kOS;
	private ComplexArray aff;
	private Double spkCoCutoff;

	public ComplexArray getSpk() {
		return spk;
	}

	public void setSpk(ComplexArray spk) {
		this.spk = spk;
	}

	public Integer getFirstScience() {
		return firstScience;
	}

	public void setFirstScience(Integer firstScience) {
		this.firstScience = firstScience;
	}

	public Integer getLastScience() {
		return lastScience;
	}

	public void setLastScience(Integer lastScience) {
		this.lastScience = lastScience;
	}

	public Double getDsigma() {
		return dsigma;
	}

	public void setDsigma(Double dsigma) {
		this.dsigma = dsigma;
	}

	public Integer getNfft() {
		return nfft;
	}

	public void setNfft(Integer nfft) {
		this.nfft = nfft;
	}

	public Integer getFirstSpk() {
		return firstSpk;
	}

	public void setFirstSpk(Integer firstSpk) {
		this.firstSpk = firstSpk;
	}

	public Integer getLastSpk() {
		return lastSpk;
	}

	public void setLastSpk(Integer lastSpk) {
		this.lastSpk = lastSpk;
	}

	public Double getDopd() {
		return dopd;
	}

	public void setDopd(Double dopd) {
		this.dopd = dopd;
	}

	public Integer getkOS() {
		return kOS;
	}

	public void setkOS(Integer kOS) {
		this.kOS = kOS;
	}

	public ComplexArray getAff() {
		return aff;
	}

	public void setAff(ComplexArray aff) {
		this.aff = aff;
	}

	public Double getSpkCoCutoff() {
		return spkCoCutoff;
	}

	public void setSpkCoCutoff(Double spkCoCutoff) {
		this.spkCoCutoff = spkCoCutoff;
	}

}
