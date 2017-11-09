package com.cssi.iasil1cpop.algorithms.utils;

import org.hipparchus.complex.Complex;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class BaseAlgorithms {
	/**
	 * Implements in place fftshift on a complex spectrum (place zero frequency
	 * in the center)
	 *
	 * @param spk
	 *            a spectrum
	 * @param conjugate
	 *            if true conjugate negative frequency spectrum values
	 * @return transformed spk to facilitate algorithms pipelining
	 */
	public ComplexArray fftshift(ComplexArray spk) {
		int middle = spk.length() / 2;

		if ((spk.length() % 2) == 0) {// even
			for (int i = 0; i < middle; i++) {
				spk.swap(i, middle + i);
			}
		} else {// odd

			Complex cm = spk.get(middle);
			for (int i = 0; i < middle; i++) {
				Complex ci = spk.get(i);
				Complex cm1i = spk.get(middle + 1 + i);
				spk.put(i, cm1i);
				spk.put(middle + i, ci);
			}
			spk.put(spk.length() - 1, cm);
		}

		return spk;
	}

	/**
	 * Implements in place ifftshift on a complex spectrum (place zero frequency
	 * in the center)
	 *
	 * @param spk
	 *            a spectrum
	 * @param conjugate
	 *            if true conjugate negative frequency spectrum values
	 * @return transformed spk to facilitate algorithms pipelining
	 */
	public ComplexArray ifftshift(ComplexArray spk) {
		int middle = spk.length() / 2;

		if ((spk.length() % 2) == 0) {// even
			for (int i = 0; i < middle; i++) {
				spk.swap(i, middle + i);
			}
		} else {// odd
			Complex cmi = spk.get(middle);
			for (int i = 0; i < middle; i++) {
				Complex ci = spk.get(i);
				spk.put(i, cmi);
				cmi = spk.get(middle + i + 1);
				spk.put(middle + i + 1, ci);

			}
			spk.put(middle, cmi);
		}

		return spk;
	}

	/**
	 * Creates in place the Hermitian spectrum of spk
	 *
	 * @param spk
	 *            a spectrum
	 * @return transformed spk to facilitate algorithms pipelining
	 */
	public ComplexArray make_hermitian(ComplexArray spk, boolean realSpectrum) {
		int middle = spk.length() / 2 + 1;
		for (int i = 1; i < middle; i++) {
			spk.put(i, spk.get(spk.length() - i));
		}
		if (!realSpectrum)
			spk.map(Complex::conjugate, 0, middle);
		return spk;
	}

	/**
	 * Apply a rectanguler window
	 *
	 * @param spk
	 *            spectrum to windowed
	 * @param first
	 *            index of the first element (included)
	 * @param last
	 *            index of the last element (excluded)
	 * @return transformed spk to facilitate algorithms pipelining
	 */
	public ComplexArray rectangularWindowing(ComplexArray spk, int first, int last) {
		if (first < 0 || first >= spk.length()) {
			throw new IllegalArgumentException(
					String.format("Parameter first should be between 0 and %d", spk.length()));
		}
		if (last < 0 || last >= spk.length()) {
			throw new IllegalArgumentException(
					String.format("Parameter last should be between 0 and %d", spk.length()));
		}
		if (first >= last) {
			throw new IllegalArgumentException("Parameter first should be inferior to parameter last");
		}

		spk.map(c -> new Complex(0.0d, 0.0d), 0, first);
		spk.map(c -> new Complex(0.0d, 0.0d), last, spk.length());
		return spk;
	}

	public ComplexArray multiply(ComplexArray a1, ComplexArray a2) {
		a1.combine((c, c2) -> c.multiply(c2), a2);
		return a1;
	}

	public double meanAbs(ComplexArray a1, int first, int last) {
		int N = last - first;
		return a1.reduce((c, mean) -> ((c.abs() / N) + mean), 0d, first, last);
	}

	public ComplexArray convolve(ComplexArray array, ComplexArray filter) {
		return ifft(multiply(fft(array), fft(filter)));
	}

	/**
	 * Computes an in place fft on a complex spectrum
	 *
	 * @param spk
	 *            a complex spectrum
	 * @return transformed spk to facilitate algorithms pipelining
	 */
	public ComplexArray ifft(ComplexArray spk) {
		// Memoized the FFT plan
		if (transform == null || planSize != spk.length()) {
			planSize = spk.length();
			transform = new DoubleFFT_1D(planSize);
		}
		transform.complexInverse(spk.getData(), true);
		return spk;
	}

	/**
	 * Computes an in place fft on a complex spectrum
	 *
	 * @param spk
	 *            a complex spectrum
	 * @return transformed spk to facilitate algorithms pipelining
	 */
	public ComplexArray fft(ComplexArray spk) {
		// Memoized the FFT plan
		if (transform == null || planSize != spk.length()) {
			planSize = spk.length();
			transform = new DoubleFFT_1D(planSize);
		}
		transform.complexForward(spk.getData());
		return spk;
	}

	/**
	 * Computes opd basis
	 * 
	 * @param nfft
	 *            size of the basis
	 * @param dopd
	 * @return
	 */
	public double[] opd(double[] data, double dopd) {
		double middle = ((double) data.length) / 2d;
		for (int i = 0; i < data.length; i++) {
			data[i] = dopd * (i - middle);
		}
		return data;
	}

	// Keep to memoized the FFT plan
	private DoubleFFT_1D transform;
	// sized of the FFT plan
	private int planSize = -1;
}
