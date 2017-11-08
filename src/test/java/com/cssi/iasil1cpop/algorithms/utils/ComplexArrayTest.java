package com.cssi.iasil1cpop.algorithms.utils;

import static org.junit.Assert.*;

import org.hipparchus.complex.Complex;
import org.hipparchus.util.Pair;
import org.junit.Test;

public class ComplexArrayTest {

	@Test
	public void testComplexArrayInt() {
		int size = 10;
		ComplexArray ca = new ComplexArray(size);
		assertEquals(ca.getData().length, 2*size);
		for (int i = 0; i<size; i++){
			assertEquals(new Complex(0d, 0d), ca.get(i));
		}
	}

	@Test
	public void testComplexArrayIntComplex() {
		int size = 10;
		Complex c = new Complex(4d, 6d);
		ComplexArray ca = new ComplexArray(size, c);
		assertEquals(ca.getData().length, 2*size);
		for (int i = 0; i<size; i++){
			assertEquals(c, ca.get(i));
		}
	}

	@Test
	public void testComplexArrayDoubleArray() {
		
		int size = 10;
		double [] data = new double[size*2];
		for(int i=0, j=1; i<data.length; i+=2, j+=2){
			data[i] = i;
			data[j] = j;
		}
		
		ComplexArray ca = new ComplexArray(data);
		assertEquals(ca.getData().length, 2*size);
		for (int i = 0; i<size; i++){
			Complex c = new Complex(2*i, 2*i+1);
			assertEquals(c, ca.get(i));
		}
	}

	@Test
	public void testFromRealValues() {
		int size = 10;
		double [] reals = new double[size];
		for(int i=0; i<reals.length; i++){
			reals[i] = i;
		}
		ComplexArray ca = ComplexArray.fromRealValues(reals);
		for (int i = 0; i<size; i++){
			Complex c = new Complex(i, 0);
			assertEquals(c, ca.get(i));
		}
	}

	@Test
	public void testFromImaginaryValues() {
		int size = 10;
		double [] ims = new double[size];
		for(int i=0; i<ims.length; i++){
			ims[i] = i;
		}
		ComplexArray ca = ComplexArray.fromImaginaryValues(ims);
		for (int i = 0; i<size; i++){
			Complex c = new Complex(0, i);
			assertEquals(c, ca.get(i));
		}
	}

	
	@Test
	public void testGetRealValues() {
		int size = 10;
		double [] data = new double[size*2];
		for(int i=0, j=1; i<data.length; i+=2, j+=2){
			data[i] = i;
			data[j] = j;
		}
		
		ComplexArray ca = new ComplexArray(data);
		assertEquals(ca.getData().length, 2*size);
		double[] reals = ca.getRealValues();
		for (int i = 0; i<size; i++){
			assertEquals(2*i, reals[i], 10E-10);
		}
	}

	@Test
	public void testGetImaginaryValues() {
		int size = 10;
		double [] data = new double[size*2];
		for(int i=0, j=1; i<data.length; i+=2, j+=2){
			data[i] = i;
			data[j] = j;
		}
		
		ComplexArray ca = new ComplexArray(data);
		assertEquals(ca.getData().length, 2*size);
		double[] ims = ca.getImaginaryValues();
		for (int i = 0; i<size; i++){
			assertEquals(2*i+1, ims[i], 10E-10);
		}
	}

	@Test
	public void testSwap() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i));
		}
		ComplexArray ca2 = ca.deepCopy();
		ca.swap(3, 6);
		ca.swap(0, 5);
		ca.swap(9, 2);
		
		assertEquals(ca2.get(3), ca.get(6));
		assertEquals(ca2.get(6), ca.get(3));
		
		assertEquals(ca2.get(0), ca.get(5));
		assertEquals(ca2.get(5), ca.get(0));
		
		assertEquals(ca2.get(9), ca.get(2));
		assertEquals(ca2.get(2), ca.get(9));
	}

	@Test
	public void testCombine() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ComplexArray ca2 = new ComplexArray(10);
		for (int i = 0;i<ca2.length();i++){
			ca2.put(i, new Complex(2*(i+1)));
		}
		ca.combine((c1, c2) -> c1.divide(c2), ca2);
		for (int i = 0;i<ca2.length();i++){
			assertEquals(new Complex(0.5), ca.get(0));
		}
		
	}

	@Test
	public void testMapFunctionOfComplexComplexIntInt() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ca.map(c->c.multiply(2), 1, 5);
		assertEquals(new Complex(1), ca.get(0));
		for (int i = 1; i<5; i++){
			assertEquals(new Complex(2*(i+1)), ca.get(i));
		}
		for (int i = 5; i<ca.length(); i++){
			assertEquals(new Complex(i+1), ca.get(i));
		}
	}

	@Test
	public void testMapFunctionOfComplexComplex() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ca.map(c->c.multiply(2));
		
		for (int i = 0; i<ca.length(); i++){
			assertEquals(new Complex(2*(i+1)), ca.get(i));
		}
	}

	@Test
	public void testReduceBiFunctionOfComplexAccAccAccIntInt() {
		int first = 1;
		int last = 5;
		int N = last - first;
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		Double m = ca.reduce((c, mean) -> ((c.abs()/N) + mean), 0d, first , last);
		assertEquals(3.5, m, 10E-10);
	}

	@Test
	public void testReduceBiFunctionOfComplexAccAccAcc() {

		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		Double m = ca.reduce((c, mean) -> ((c.abs()/10d) + mean), 0d);
		assertEquals(5.5, m, 10E-10);
	}

	@Test
	public void testPadleft() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ca.padleft(15);
		for (int i = 0;i<5;i++){
			assertEquals(new Complex(0d), ca.get(i));
		}
		for (int i = 5;i<ca.length();i++){
			assertEquals(new Complex((i-5)+1), ca.get(i));
		}
	}

	@Test
	public void testPadright() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ca.padright(15);
		for (int i = 0;i<10;i++){
			assertEquals(new Complex(i+1), ca.get(i));
		}
		for (int i = 10;i<15;i++){
			assertEquals(new Complex(0d), ca.get(i));
		}
	}

	@Test
	public void testDeepCopy() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ComplexArray ca2 = ca.deepCopy();
		assertArrayEquals(ca.getData(), ca2.getData(), 10E-10);
		ca.put(3, new Complex(0d));
		assertNotEquals(ca2.get(3), ca.get(3));
	}

	@Test
	public void testPrepend() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ca.prepend(new ComplexArray(5, new Complex(0d)));
		for (int i = 0;i<5;i++){
			assertEquals(new Complex(0d), ca.get(i));
		}
		for (int i = 5;i<ca.length();i++){
			assertEquals(new Complex((i-5)+1), ca.get(i));
		}
	}

	@Test
	public void testAppend() {
		ComplexArray ca = new ComplexArray(10);
		for (int i = 0;i<ca.length();i++){
			ca.put(i, new Complex(i+1));
		}
		ca.append(new ComplexArray(5, new Complex(0d)));
		for (int i = 0;i<10;i++){
			assertEquals(new Complex(i+1), ca.get(i));
		}
		for (int i = 10;i<15;i++){
			assertEquals(new Complex(0d), ca.get(i));
		}
	}

	@Test
	public void testMax() {
		ComplexArray ca = new ComplexArray(10);
		
		ca.put(0, new Complex(1d));
		Pair<Integer, Double> p = ca.max(Complex::abs);
		assertEquals(0, (int)p.getKey());
		assertEquals(1d, p.getValue(), 10E-10);
		
		ca.put(4, new Complex(2d));
		p = ca.max(Complex::abs);
		assertEquals(4, (int)p.getKey());
		assertEquals(2d, p.getValue(), 10E-10);
		
		ca.put(9, new Complex(3d));
		p = ca.max(Complex::abs);
		assertEquals(9, (int)p.getKey());
		assertEquals(3d, p.getValue(), 10E-10);
	}

	@Test
	public void testMin() {
		ComplexArray ca = new ComplexArray(10, new Complex(100d));
		
		ca.put(0, new Complex(99d));
		Pair<Integer, Double> p = ca.min(Complex::abs);
		assertEquals(0, (int)p.getKey());
		assertEquals(99d, p.getValue(), 10E-10);
		
		ca.put(4, new Complex(98d));
		p = ca.min(Complex::abs);
		assertEquals(4, (int)p.getKey());
		assertEquals(98d, p.getValue(), 10E-10);
		
		ca.put(9, new Complex(97d));
		p = ca.min(Complex::abs);
		assertEquals(9, (int)p.getKey());
		assertEquals(97d, p.getValue(), 10E-10);
	}

}
