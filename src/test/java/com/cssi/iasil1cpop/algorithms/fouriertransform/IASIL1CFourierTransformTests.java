/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.algorithms.fouriertransform;

import org.hipparchus.complex.Complex;
import org.junit.Assert;
import org.junit.Test;

import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

/**
 * Tests for IASIL1CFourierTransform class
 * 
 * @author sdorgan
 */
public class IASIL1CFourierTransformTests {


	@Test
	public void TestExtractScienceSignal() {
		IASIL1CFourierTransform algo = new IASIL1CFourierTransform();
		int nfft = 10;
		ComplexArray spk = new ComplexArray(nfft);
		for (int i = 0; i < nfft; i++) {
			spk.put(i, new Complex(0.0d, i + 1));
		}
		ComplexArray science = algo.extractScienceSignal(spk, 1, 3);

		ComplexArray expectedSym = new ComplexArray(nfft);
		expectedSym.put(0, new Complex(0d, 0d));
		expectedSym.put(1, new Complex(0d, 0d));
		expectedSym.put(2, new Complex(0d, 0d));
		expectedSym.put(3, new Complex(0d, 0d));
		expectedSym.put(4, new Complex(0d, 0d));

		expectedSym.put(5, new Complex(0d, 0d));
		expectedSym.put(6, new Complex(0d, 2d));
		expectedSym.put(7, new Complex(0d, 3d));
		expectedSym.put(8, new Complex(0d, 0d));
		expectedSym.put(9, new Complex(0d, 0d));

		Assert.assertArrayEquals(expectedSym.getData(), science.getData(), 10E-10);
	}



//	@Test
//	public void TestOpd() {
//		IASIL1CFourierTransform algo = new IASIL1CFourierTransform();
//		int nfft = 8;
//		ComplexArray spk = new ComplexArray(nfft);
//		for (int i = 0; i < nfft; i++) {
//			spk.put(i, new Complex(0.0d, i + 1));
//		}
//		double[] basis = algo.opd(nfft, 2d);
//
//		double[] expected = { -8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0 };
//
//		Assert.assertArrayEquals(expected, basis, 10E-10);
//	}

	@Test
	public void TestCall() {
		IASIL1CFourierTransform algo = new IASIL1CFourierTransform();
		int nfft = 10;
		ComplexArray spk = new ComplexArray(nfft);
		for (int i = 0; i < nfft; i++) {
			spk.put(i, new Complex(0.0d, i + 1));
		}
		IASIL1CFourierInput input = new IASIL1CFourierInput();
		input.setSpk(spk);
		input.setFirstScience(1);
		input.setLastScience(3);
		input.setDopd(6.5d);
		input.setNfft(spk.length());
		ComplexArray ife = algo.call(input).getInterferogram();

		ComplexArray expectedSym = new ComplexArray(nfft);
		expectedSym.put(0, new Complex(0.0, 0.0));

		expectedSym.put(1, new Complex(8.057480106940814, 0d));
		expectedSym.put(2, new Complex(-7.330937578935453, 0.d));
		expectedSym.put(3, new Complex(0.27751455142577597, 0d));
		expectedSym.put(4, new Complex(3.355198088601028, 0.d));
		expectedSym.put(5, new Complex(0d, 0d));
		expectedSym.put(6, new Complex(3.355198088601028, 0d));
		expectedSym.put(7, new Complex(0.27751455142577597, 0d));
		expectedSym.put(8, new Complex(-7.330937578935453, 0d));
		expectedSym.put(9, new Complex(8.057480106940814, 0d));

		Assert.assertArrayEquals(expectedSym.getData(), ife.getData(), 10E-10);
	}
}
