/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.algorithms.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.hipparchus.complex.Complex;
import org.hipparchus.util.Pair;

/**
 * ComplexArray defines an array of double complex It is a utility class to
 * simplify in place computation and keeping compatibility with Hipparchus
 * library complex format ComplexArray data as stored in a double array such as
 * Real = data[i], Imaginary = data[i+1]
 * 
 * @author sdorgan
 */
public final class ComplexArray implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Complex array stored as double array: Real = data[i], Imaginary =
	// data[i+1]
	private double[] data;

	/**
	 * Constructs a complexe array of length <code>size</code>
	 * 
	 * @param size
	 *            length of the complex array
	 */
	public ComplexArray(int size) {
		data = new double[size * 2];
		Arrays.fill(data, 0d);
	}

	/**
	 * Constructs a complexe array of length <code>size</code>
	 * 
	 * @param size
	 *            length of the complex array
	 */
	public ComplexArray(int size, Complex value) {
		data = new double[size * 2];
		map(c -> value);
	}

	/**
	 * Wraps a complex array stored as a double array: Real = data[i], Imaginary
	 * = data[i+1]
	 * 
	 * @param data
	 *            data to wrap
	 */
	public ComplexArray(double[] data) {
		this.data = data;
	}

	/**
	 * Creates a ComplexArray from real values
	 * 
	 * @param reals
	 *            an array of real values
	 * @return a ComplexArray where c[i] = new Complex(reals[i])
	 */
	public static ComplexArray fromRealValues(double[] reals) {
		ComplexArray a = new ComplexArray(reals.length);
		for (int i = 0, j = 0; i < a.data.length; i += 2, j++) {
			a.data[i] = reals[j];
		}
		return a;
	}

	/**
	 * Creates a ComplexArray from imaginary values
	 * 
	 * @param reals
	 *            an array of imaginary values
	 * @return a ComplexArray where c[i] = new Complex(0d , imaginaries[i])
	 */
	public static ComplexArray fromImaginaryValues(double[] imaginaries) {
		ComplexArray a = new ComplexArray(imaginaries.length);
		for (int i = 1, j = 0; i < a.data.length; i += 2, j++) {
			a.data[i] = imaginaries[j];
		}
		return a;
	}

	/**
	 * Length of the array
	 * 
	 * @return the length of the array
	 */
	public int length() {
		return data.length / 2;
	}

	/**
	 * 
	 * @return reals values
	 */
	public double[] getRealValues() {
		int len = length();
		double[] reals = new double[len];
		for (int i = 0, j = 0; i < data.length; i += 2, j++) {
			reals[j] = data[i];
		}
		return reals;
	}

	/**
	 * 
	 * @return imaginary values
	 */
	public double[] getImaginaryValues() {
		int len = length();
		double[] ims = new double[len];
		for (int i = 1, j = 0; i < data.length; i += 2, j++) {
			ims[j] = data[i];
		}
		return ims;
	}

	/**
	 * Swaps 2 elements of the array
	 * 
	 * @param i
	 *            index of the first element
	 * @param j
	 *            index of the second element
	 */
	public void swap(int i, int j) {
		int ci = i * 2;
		int cj = j * 2;
		double tmp = data[ci];
		data[ci] = data[cj];
		data[cj] = tmp;
		tmp = data[ci + 1];
		data[ci + 1] = data[cj + 1];
		data[cj + 1] = tmp;

	}

	/**
	 * Puts <code>c</code> at indew <code>i</code>
	 * 
	 * @param i
	 *            index of the first element
	 * @param c
	 *            a double complex
	 */
	public void put(int i, Complex c) {
		int ci = i * 2;
		data[ci] = c.getReal();
		data[ci + 1] = c.getImaginary();
	}

	/**
	 * Returns the element <code>i</code>
	 * 
	 * @param i
	 *            an index
	 * @return the element <code>i</code> of the array
	 */
	public Complex get(int i) {
		int ci = i * 2;
		return new Complex(data[ci], data[ci + 1]);
	}

	/**
	 * Combines this array with an another array
	 * 
	 * @param f
	 *            the combiner (ex: a.combine((c, c2) -> c.multiply(c2)) returns
	 *            this = this * a2)
	 * @param a2
	 *            the array to be combined with
	 */
	public void combine(BiFunction<Complex, Complex, Complex> f, ComplexArray a2) {
		int size = Math.min(this.data.length, a2.data.length);
		for (int i = 0, j = 1; i < size; i += 2, j += 2) {
			Complex c1 = new Complex(data[i], data[j]);
			Complex c2 = new Complex(a2.data[i], a2.data[j]);
			Complex c3 = f.apply(c1, c2);
			data[i] = c3.getReal();
			data[i + 1] = c3.getImaginary();
		}
	}

	/**
	 * Map elements between <code>firstIndex</code> and <code>lastIndex</code>
	 * with <code>function</code>
	 * 
	 * @param function
	 *            a unary operator
	 * @param firstIndex
	 *            first index is included
	 * @param lastIndex
	 *            last index is excluded
	 */
	public void map(Function<Complex, Complex> function, int firstIndex, int lastIndex) {
		int first = firstIndex * 2;
		int last = lastIndex * 2;
		for (int i = first, j = first + 1; i < last; i += 2, j += 2) {
			Complex c = new Complex(data[i], data[j]);
			c = function.apply(c);
			data[i] = c.getReal();
			data[j] = c.getImaginary();
		}
	}

	/**
	 * Map all elements with <code>function</code>
	 * 
	 * @param function
	 *            a unary operator
	 */
	public void map(Function<Complex, Complex> function) {
		map(function, 0, length());
	}

	/**
	 * The reduce() method applies a function against an accumulator and each
	 * element in the array (from left to right) to reduce it to a single value.
	 * 
	 * @param f
	 *            function to apply
	 * @param initValue
	 *            initial value of the accumulator
	 * @param firstIndex
	 *            index of the first element to reduce
	 * @param lastIndex
	 *            index of the last element to reduce (excluded)
	 * @return the accumulator
	 */
	public <Acc> Acc reduce(BiFunction<Complex, Acc, Acc> f, Acc initValue, int firstIndex, int lastIndex) {

		int first = firstIndex * 2;
		int last = lastIndex * 2;
		Acc acc = initValue;
		for (int i = first, j = first + 1; i < last; i += 2, j += 2) {
			Complex c = new Complex(data[i], data[j]);
			acc = f.apply(c, acc);
		}
		return acc;
	}

	/**
	 * The reduce() method applies a function against an accumulator and each
	 * element in the array (from left to right) to reduce it to a single value.
	 * 
	 * @param f
	 *            function to apply
	 * @param initValue
	 *            initial value of the accumulator
	 * @return the accumulator
	 */
	public <Acc> Acc reduce(BiFunction<Complex, Acc, Acc> f, Acc initValue) {
		return reduce(f, initValue, 0, length());
	}

	/**
	 * Prepends (newsize - length) zero to this array
	 * 
	 * @param newsize
	 *            new size of the array
	 */
	public void padleft(int newsize) {
		int newdatasize = 2 * newsize;
		int padsize = newdatasize - data.length;
		double[] newdata = new double[newdatasize];
		Arrays.fill(newdata, 0, padsize, 0d);
		System.arraycopy(data, 0, newdata, padsize, newdatasize - padsize);
		data = newdata;
	}

	/**
	 * Appends (newsize - length) zero to this array
	 * 
	 * @param newsize
	 *            new size of the array
	 */
	public void padright(int newsize) {
		data = Arrays.copyOf(data, 2 * newsize);
	}

	/**
	 * Creates a deep copy of this array
	 * 
	 * @return a new complex array equal to this array
	 */
	public ComplexArray deepCopy() {
		return new ComplexArray(Arrays.copyOf(data, data.length));
	}

	/**
	 * Prepends array to this object
	 * 
	 * @param array
	 *            a ComplexArray
	 */
	public void prepend(ComplexArray array) {
		double[] array_data = array.getData();
		int array_length = array_data.length;
		double[] new_data = Arrays.copyOf(array_data, array_length + data.length);
		System.arraycopy(data, 0, new_data, array_length, new_data.length - array_length);
		data = new_data;
	}

	/**
	 * Appends array to this object
	 * 
	 * @param array
	 *            a ComplexArray
	 */
	public void append(ComplexArray array) {
		double[] array_data = array.getData();
		int array_length = array_data.length;
		double[] new_data = Arrays.copyOf(data, array_length + data.length);
		System.arraycopy(array_data, 0, new_data, data.length, new_data.length - data.length);
		data = new_data;
	}

	/**
	 * Returns the index of the maximum value and the maximum value of this
	 * array.
	 * 
	 * @param f
	 *            function used to compared the elements (ex
	 *            a.max(Complex::Abs))
	 * @return the index of the maximum value and the maximum value of this
	 *         array.
	 */
	public Pair<Integer, Double> max(Function<Complex, Double> f) {
		double m = Double.MIN_VALUE;
		int mi = -1;
		for (int i = 0; i < length(); i++) {
			double v = f.apply(get(i));
			if (v > m) {
				mi = i;
				m = v;
			}
		}
		return Pair.create(mi, m);
	}

	/**
	 * Returns the index of the minimum value and the minimum value of this
	 * array.
	 * 
	 * @param f
	 *            function used to compared the elements (ex
	 *            a.min(Complex::Abs))
	 * @return the index of the minimum value and the minimum value of this
	 *         array.
	 */
	public Pair<Integer, Double> min(Function<Complex, Double> f) {
		double m = Double.MAX_VALUE;
		int mi = -1;
		for (int i = 0; i < length(); i++) {
			double v = f.apply(get(i));
			if (v < m) {
				mi = i;
				m = v;
			}
		}
		return Pair.create(mi, m);
	}

	/**
	 * Retrieves the internal representation of the complex array
	 * 
	 * @return a double array with Real = data[i], Imaginary = data[i+1]
	 */
	public double[] getData() {
		return data;
	}

}
