package com.cssi.iasil1cpop.algorithms.spikecorrection;


import org.hipparchus.analysis.interpolation.AkimaSplineInterpolator;
import org.hipparchus.analysis.polynomials.PolynomialSplineFunction;
import org.hipparchus.complex.Complex;
import org.hipparchus.util.Pair;

import com.cssi.iasil1cpop.algorithms.Algorithm;
import com.cssi.iasil1cpop.algorithms.utils.BaseAlgorithms;
import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

public class IASIL1CSpikeCorrection implements Algorithm<IASIL1CSpikeCorrectionInput, IASIL1CSpikeCorrectionOutput> {
	private BaseAlgorithms algo = new BaseAlgorithms();

	@Override
	public IASIL1CSpikeCorrectionOutput call(IASIL1CSpikeCorrectionInput in) {
		//Step 1b
		ComplexArray spk = in.getSpk();
		ComplexArray spkSym = extractSpikeSignal(spk, in.getFirstSpk(), in.getLastSpk());
		spkSym = algo.make_hermitian(spkSym, false);
		int paddingSize = (in.getkOS() - 1)*spk.length()/2;
		spkSym.padleft(spkSym.length() + paddingSize);
		spkSym.padright(spkSym.length() + paddingSize);

		//Step 1c
		ComplexArray IspkOs = algo.fft(spkSym);
		
		//Step 1d
		Pair<Integer, Double> pair = IspkOs.max(Complex::getReal);
		
		//Step 1e
	    double aSpk = pair.getValue()*spk.length()/(2* in.getLastSpk() - in.getFirstSpk());
		
	    //Step 1f
	    ComplexArray dirac = new ComplexArray(IspkOs.length());
	    dirac.put(pair.getKey(), new Complex(aSpk, 0d));
	    
	    //Step 1g
	    ComplexArray IspkOsSyn = algo.convolve(IspkOs, dirac);
	    
	    //Step 1h
	    double[] opd = new double[spk.length()];
	    algo.opd(opd, in.getDopd());
	    double[] odpOs = new double[spk.length()*in.getkOS()];
	    algo.opd(odpOs, in.getDopd());
	    
	    double[] IspkSyn = new double[spk.length()];
	    AkimaSplineInterpolator interpolator = new AkimaSplineInterpolator();
	    PolynomialSplineFunction splines = interpolator.interpolate(odpOs, IspkOsSyn.getRealValues());
	    for (int i = 0; i<opd.length; i++){
	    	IspkSyn[0] = splines.value(opd[i]);
	    }
	    
	    //Step 2a
	    ComplexArray spkSciCoor = ComplexArray.fromRealValues(IspkSyn);
	    algo.ifft(spkSciCoor);
	    
	    //Step 2b
	    spkSciCoor.combine((css,cs) -> cs.add(css.negate()), in.getSpk());
	    
	    //Step 3a
	    double spkCoQualityIndex = algo.meanAbs(spkSciCoor, in.getFirstSpk(), in.getLastSpk());
	    
	    //Step 3b
	    boolean spkCoQualityFlag = (spkCoQualityIndex > in.getSpkCoCutoff())?false:true;
		
		IASIL1CSpikeCorrectionOutput output = new IASIL1CSpikeCorrectionOutput();
		output.setSpkCorrectedScience(spkSciCoor);
		output.setSpkCoQualityIndex(spkCoQualityIndex);
		output.setSpkCoQualityFlag(spkCoQualityFlag);
		return output;
	}
	
	public ComplexArray extractSpikeSignal(ComplexArray spk, int first, int last){
		ComplexArray spike = new ComplexArray(spk.length());
		int middle = spk.length()/2;
		for (int i = first; i<last; i++){
			spike.put(middle+i, spk.get(i - first));
		}
		return spike;
		
	}
}


