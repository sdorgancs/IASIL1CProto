/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.algorithms.fouriertransform;

import java.io.Serializable;

import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

/**
 *
 * @author sdorgan
 */
public class IASL1CFourierOutput implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComplexArray ife;
	private double[] odpdBasis;

	/**
	 * @return the odpdBasis
	 */
	public double[] getOdpdBasis() {
		return odpdBasis;
	}

	/**
	 * @param odpdBasis
	 *            the odpdBasis to set
	 */
	public void setOdpdBasis(double[] odpdBasis) {
		this.odpdBasis = odpdBasis;
	}

	/**
	 * @return the ife
	 */
	public ComplexArray getInterferogram() {
		return ife;
	}

	/**
	 * @param ife
	 *            the ife to set
	 */
	public void setInterferogram(ComplexArray ife) {
		this.ife = ife;
	}

}
