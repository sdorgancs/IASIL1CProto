package com.cssi.iasil1cpop.algorithms.utils;

import org.hipparchus.complex.Complex;
import org.junit.Assert;
import org.junit.Test;

public class BaseAlgorithmsTest {

	@Test
	public void TestMakeHermitianEvenComplex() {
		{
			BaseAlgorithms algo = new BaseAlgorithms();
			int nfft = 10;
			ComplexArray spk = new ComplexArray(nfft);
			spk.put(0, new Complex(0d, 0d));
			spk.put(1, new Complex(0d, 0d));
			spk.put(2, new Complex(0d, 0d));
			spk.put(3, new Complex(0d, 0d));
			spk.put(4, new Complex(0d, 0d));
			spk.put(5, new Complex(0d, 0d));
			spk.put(6, new Complex(0d, 2d));
			spk.put(7, new Complex(0d, 3d));
			spk.put(8, new Complex(0d, 0d));
			spk.put(9, new Complex(0d, 0d));

			ComplexArray spkShifted = algo.make_hermitian(spk, false);

			ComplexArray expectedSym = new ComplexArray(nfft);
			
			expectedSym.put(0, new Complex(0d, 0d));
			expectedSym.put(1, new Complex(0d, -0d));
			expectedSym.put(2, new Complex(0d, -0d));
			expectedSym.put(3, new Complex(0d, -3d));
			expectedSym.put(4, new Complex(0d, -2d));
			expectedSym.put(5, new Complex(0d, -0d));
			expectedSym.put(6, new Complex(0d, 2d));
			expectedSym.put(7, new Complex(0d, 3d));
			expectedSym.put(8, new Complex(0d, 0d));
			expectedSym.put(9, new Complex(0d, 0d));

			Assert.assertArrayEquals(expectedSym.getData(), spkShifted.getData(), 10E-10);
		}
	}

	@Test
	public void TestMakeHermitianOddComplex() {
		{
			BaseAlgorithms algo = new BaseAlgorithms();
			int nfft = 11;
			ComplexArray spk = new ComplexArray(nfft);
			spk.put(0, new Complex(0d, 0d));

			spk.put(1, new Complex(0d, 0d));
			spk.put(2, new Complex(0d, 0d));
			spk.put(3, new Complex(0d, 0d));
			spk.put(4, new Complex(0d, 0d));
			spk.put(5, new Complex(0d, 0d));

			spk.put(6, new Complex(0d, 2d));
			spk.put(7, new Complex(0d, 3d));
			spk.put(8, new Complex(0d, 0d));
			spk.put(9, new Complex(0d, 0d));
			spk.put(10, new Complex(0d, 0d));

			ComplexArray spkShifted = algo.make_hermitian(spk, false);

			ComplexArray expectedSym = new ComplexArray(nfft);
			expectedSym.put(0, new Complex(0d, 0d));

			expectedSym.put(1, new Complex(0d, 0d));
			expectedSym.put(2, new Complex(0d, 0d));
			expectedSym.put(3, new Complex(0d, 0d));
			expectedSym.put(4, new Complex(0d, -3d));
			expectedSym.put(5, new Complex(0d, -2d));

			expectedSym.put(6, new Complex(0d, 2d));
			expectedSym.put(7, new Complex(0d, 3d));
			expectedSym.put(8, new Complex(0d, 0d));
			expectedSym.put(9, new Complex(0d, 0d));
			expectedSym.put(10, new Complex(0d, 0d));
			

			Assert.assertArrayEquals(expectedSym.getData(), spkShifted.getData(), 10E-10);
		}
	}

	@Test
	public void TestMakeHermitianReal() {
		BaseAlgorithms algo = new BaseAlgorithms();
		int nfft = 9;
		ComplexArray spk = new ComplexArray(nfft);
		for (int i = 0; i < nfft; i++) {
			spk.put(i, new Complex(0.0d, i + 1));
		}
		ComplexArray spkShifted = algo.make_hermitian(spk, true);

		ComplexArray expectedSym = new ComplexArray(nfft);
		expectedSym.put(0, new Complex(0.0d, 1.0d));

		expectedSym.put(1, new Complex(0.0d, 9.0d));
		expectedSym.put(2, new Complex(0.0d, 8.0d));
		expectedSym.put(3, new Complex(0.0d, 7.0d));
		expectedSym.put(4, new Complex(0.0d, 6.0d));

		expectedSym.put(5, new Complex(0.0d, 6.0d));
		expectedSym.put(6, new Complex(0.0d, 7.0d));
		expectedSym.put(7, new Complex(0.0d, 8.0d));
		expectedSym.put(8, new Complex(0.0d, 9.0d));

		Assert.assertArrayEquals(expectedSym.getData(), spkShifted.getData(), 10E-10);
	}

	@Test
	public void TestFft() {
		BaseAlgorithms algo = new BaseAlgorithms();
		{
			
			int nfft = 8;
			ComplexArray spk = new ComplexArray(nfft);
			spk.put(0, new Complex(0d, 0d));
			spk.put(1, new Complex(0d, -3d));
			spk.put(2, new Complex(0d, -2d));
			spk.put(3, new Complex(0d, 0d));

			spk.put(4, new Complex(0d, 0d));
			spk.put(5, new Complex(0d, 2d));
			spk.put(6, new Complex(0d, 3d));
			spk.put(7, new Complex(0d, 0d));
			
			ComplexArray ife = algo.fft(spk.deepCopy());

			ComplexArray expectedSym = algo.ifft(ife.deepCopy());

			Assert.assertArrayEquals(expectedSym.getData(), spk.getData(), 10E-10);
		}
		{
			
			int nfft = 9;
			ComplexArray spk = new ComplexArray(nfft);

			ComplexArray ife = algo.fft(spk.deepCopy());

			ComplexArray expectedSym = algo.ifft(ife.deepCopy());

			Assert.assertArrayEquals(expectedSym.getData(), spk.getData(), 10E-10);
		}
		{
			
			int nfft = 9;
			ComplexArray spk = new ComplexArray(nfft);
			spk.put(0, new Complex(15, 0));

			spk.put(1, new Complex(0d, 0d));
			spk.put(2, new Complex(0d, -3d));
			spk.put(3, new Complex(0d, -2d));
			spk.put(4, new Complex(0d, 0d));

			spk.put(5, new Complex(0d, 0d));
			spk.put(6, new Complex(0d, 2d));
			spk.put(7, new Complex(0d, 3d));
			spk.put(8, new Complex(0d, 0d));

			ComplexArray ife = algo.fft(spk.deepCopy());


			ComplexArray expectedSym = algo.ifft(ife.deepCopy());

			Assert.assertArrayEquals(expectedSym.getData(), spk.getData(), 10E-10);
		}
	}
	
	@Test
	public void TestFftshiftEvent(){
		
		int nfft = 6;
		ComplexArray spk = new ComplexArray(nfft);
		
		for(int i = 0; i<spk.length(); i++){
			spk.put(i, new Complex(i+1));
		}
		BaseAlgorithms algo = new BaseAlgorithms();
		ComplexArray spkRef = spk.deepCopy();
		algo.fftshift(spk);
		
		ComplexArray expected = new ComplexArray(nfft);
		expected.put(0, new Complex(4));
		expected.put(1, new Complex(5));
		expected.put(2, new Complex(6));
		expected.put(3, new Complex(1));
		expected.put(4, new Complex(2));
		expected.put(5, new Complex(3));
		
		Assert.assertArrayEquals(expected.getData(), spk.getData(), 10E-10);
		
		algo.ifftshift(spk);
		Assert.assertArrayEquals(spkRef.getData(), spk.getData(), 10E-10);

	}
	
	@Test
	public void TestFftshiftOdd() {
		{
			int nfft = 7;
			ComplexArray spk = new ComplexArray(nfft);

			for (int i = 0; i < spk.length(); i++) {
				spk.put(i, new Complex(i + 1));
			}
			BaseAlgorithms algo = new BaseAlgorithms();
			ComplexArray spkRef = spk.deepCopy();
			algo.fftshift(spk);

			ComplexArray expected = new ComplexArray(nfft);
			expected.put(0, new Complex(5));
			expected.put(1, new Complex(6));
			expected.put(2, new Complex(7));
			expected.put(3, new Complex(1));
			expected.put(4, new Complex(2));
			expected.put(5, new Complex(3));
			expected.put(6, new Complex(4));


			Assert.assertArrayEquals(expected.getData(), spk.getData(), 10E-10);
			algo.ifftshift(spk);
			
			Assert.assertArrayEquals(spkRef.getData(), spk.getData(), 10E-10);
			
		}
		{
			int nfft = 9;
			ComplexArray spk = new ComplexArray(nfft);

			for (int i = 0; i < spk.length(); i++) {
				spk.put(i, new Complex(i + 1));
			}
			BaseAlgorithms algo = new BaseAlgorithms();
			algo.fftshift(spk);

			ComplexArray expected = new ComplexArray(nfft);
			expected.put(0, new Complex(6));
			expected.put(1, new Complex(7));
			expected.put(2, new Complex(8));
			expected.put(3, new Complex(9));
			expected.put(4, new Complex(1));
			expected.put(5, new Complex(2));
			expected.put(6, new Complex(3));
			expected.put(7, new Complex(4));
			expected.put(8, new Complex(5));


			Assert.assertArrayEquals(expected.getData(), spk.getData(), 10E-10);
		}

	}

}
