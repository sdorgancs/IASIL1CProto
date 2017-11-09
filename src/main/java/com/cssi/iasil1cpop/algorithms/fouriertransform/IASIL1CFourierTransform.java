/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.algorithms.fouriertransform;

import javax.print.Doc;

import com.cssi.iasil1cpop.algorithms.Algorithm;
import com.cssi.iasil1cpop.algorithms.utils.BaseAlgorithms;
import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

/**
 * Implementation of the IASI L1C POP scientific algorithm Frourier Transform
 *
 * @see Doc IASING‐DD‐4200‐0487‐CNES chapter 4.3
 * @author sdorgan
 */
public final class IASIL1CFourierTransform implements Algorithm<IASIL1CFourierInput, IASL1CFourierOutput> {

	/**
	 * Implementation of the IASI L1C POP fourier transform algorithm
	 * 
	 *
	 * @param input
	 *            IASI L1C Fourier transform input parameter that contains:
	 *            <ul>
	 *            <li>spk: a complex spectrum (the length of this spectrum must
	 *            be even)</li>
	 *            <li>first: index of the first element included in the
	 *            rectangular window</li>
	 *            <li>last: index of the first element excluded from the
	 *            rectangular window</li>
	 *            <li>dopd: on board ODP step value</li>
	 *            </ul>
	 * @return a IASL1CFourierOutput that contains:
	 *         <ul>
	 *         <li>ife: the computed interferogram</li>
	 *         <li>odpBasis: an array of double</li>
	 *         </ul>
	 */
	@Override
	public IASL1CFourierOutput call(IASIL1CFourierInput input) {
		ComplexArray spkSym = extractScienceSignal(input.getSpk(), input.getFirstScience(), input.getLastScience());
		algo.make_hermitian(spkSym, false);
		ComplexArray ife = algo.fft(spkSym);
		ComplexArray ifeSym = algo.make_hermitian(ife, true);
		IASL1CFourierOutput output = new IASL1CFourierOutput();
		output.setInterferogram(ifeSym);
		double[] odp = new double[input.getSpk().length()];
		output.setOdpdBasis(algo.opd(odp, input.getDopd()));
		return output;
	}

	/**
	 * Extracts science signal
	 * 
	 * @param spk
	 *            input signal
	 * @param first
	 *            index of the first science sample
	 * @param last
	 *            index of the last science sample (excluded)
	 * @return the science spectrum
	 */
	public ComplexArray extractScienceSignal(ComplexArray spk, int first, int last) {
		ComplexArray spike = new ComplexArray(spk.length());
		int middle = spk.length() / 2;
		for (int i = first; i < last; i++) {
			spike.put(middle + i, spk.get(i));
		}
		return spike;

	}

	private BaseAlgorithms algo = new BaseAlgorithms();

}
