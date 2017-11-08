/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.plugins.functions.fouriertransform;

import java.util.ArrayList;
import java.util.List;

import com.cssi.iasil1cpop.algorithms.fouriertransform.IASIL1CFourierInput;
import com.cssi.iasil1cpop.algorithms.fouriertransform.IASIL1CFourierTransform;
import com.cssi.iasil1cpop.algorithms.fouriertransform.IASL1CFourierOutput;
import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;
import com.cssi.iasil1cpop.plugins.CheckedValue;
import com.cssi.iasil1cpop.plugins.FunctionPlugin;
import com.cssi.iasil1cpop.plugins.TupleReader;
import com.cssi.iasil1cpop.plugins.errors.AuxiliaryDataIntegrityError;
import com.cssi.iasil1cpop.plugins.errors.AuxiliaryDataMissingError;
import com.cssi.iasil1cpop.plugins.errors.DataIntegrityError;
import com.cssi.iasil1cpop.plugins.errors.DataMissingError;
import com.cssi.iasil1cpop.plugins.errors.ErrorReport;
import com.cssi.iasil1cpop.plugins.errors.ErrorSeverity;
import com.cssi.pdap.dpi.plugins.ITupleApi;
import com.cssi.pdap.dpi.plugins.TupleApiImpl;

/**
 * Implements a DPI function plugin for the algorithm IASI L1C Fourier Transform 
 * @author sdorgan
 */
public class IASIL1CFourierTransformPlugin
		extends FunctionPlugin<IASIL1CFourierInput, IASL1CFourierOutput, IASIL1CFourierTransform> {
	/**
	 * @return the IASI L1C Fourier Transform algorithm implementation
	 */
	@Override
	public IASIL1CFourierTransform getAlgorithm() {
		return new IASIL1CFourierTransform();
	}

	/**
	 * Grabs and validates inputs necessary to call the IASI L1C Fourier Transform algorithm
	 * @param input the tuple of data transfered by the DPI to the plugin
	 * @return a CheckedInput containing the IASI L1C Fourier Transform inputs and an error report 
	 */
	@Override
    public CheckedValue<IASIL1CFourierInput> readAndCheckInputs(ITupleApi input){
        ErrorReport errorReport = new ErrorReport();
        // Get SGA1_IAS_1C_AUX_CNF_ from DPI Auxiliary data manager
        ITupleApi query = new TupleApiImpl();
        query.addField("internalName", "SGA1_IAS_1C_AUX_CNF_");
        query.addField("version", "1.0");
        query.addField("commandName", "GetFourierInput");
        List<ITupleApi> aux_data = getAuxiliaryDataManager().queryParams(input);
        
        //Complete the error report with data completeness check
        checkDataCompletness(input, aux_data, errorReport);
        
        //Read data from DPI tuple
        TupleReader input_reader = new TupleReader(input);
        ComplexArray spk = input_reader.read("OGP_SPKCO", new ComplexArray(0));
        
       // Read SGA1_IAS_1C_AUX_CNF_ data
        TupleReader aux_reader = new TupleReader(aux_data.get(0));
        Integer first = aux_reader.read("FIRST_SCIENCE", -1);
        Integer last = aux_reader.read( "LAST_SCIENCE", -1);
        Double dopd = aux_reader.read("DOPD", 0d);
        Integer nfft = aux_reader.read("NFFT", -1);
        
       //Complete the error report with data integrity check
        checkDataIntegriy(spk, first, last, nfft, errorReport);
        
        //Create IASI L1C Fourier Transform input
        IASIL1CFourierInput algoInput = new IASIL1CFourierInput();
        algoInput.setSpk(spk);
        algoInput.setFirstScience(first);
        algoInput.setLastScience(last);
        algoInput.setNfft(nfft);
        algoInput.setDopd(dopd);
        
        return new CheckedValue<>(algoInput, errorReport);
    }
	
	/**
	 * Checks Ouput and enriches error report before transfer to the DPI
	 * @param the algorithm output object
	 * @return the objects to transform into tuple and the associated error report
	 */
	@Override
	public List<CheckedValue<Object>> checkOutputs(IASL1CFourierOutput ouput, ErrorReport errorReport) {
		ArrayList<CheckedValue<Object>> l = new ArrayList<>();
		if (ouput.getInterferogram() == null || ouput.getInterferogram().length() == 0){
			errorReport.addError(new DataIntegrityError(
                    ErrorSeverity.WARNING,
                    "Null Interferogram produced by Fourier transform",
                    "Interferogram",
                    ""
            ));
		}
		l.add(new CheckedValue<Object>(ouput, errorReport));
		return l;
	}

	private void checkDataIntegriy(ComplexArray spk, Integer first, Integer last, Integer nfft,
			ErrorReport errorReport) {
		if (nfft != spk.length()) {
             errorReport.addError(new DataIntegrityError(
                    ErrorSeverity.WARNING,
                    "SGA1_IAS_1C_AUX_CNF_ NFT field mismatches OGP_SPKCO size",
                    "OGP_SPKCO",
                    ""
            ));
        }
         if (first < 0 || first >= nfft) {
            errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.CRITICAL,
                    String.format("Parameter first should be between 0 and %d", spk.length()),
                    "SGA1_IAS_1C_AUX_CNF_",
                    "FIRST_SCIENCE"
            ));
        }
        if (last < 0 || last >= nfft) {
            errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.CRITICAL,
                    String.format("Parameter last should be between 0 and %d", spk.length()),
                    "SGA1_IAS_1C_AUX_CNF_",
                    "FIRST_SCIENCE"
            ));
        }
        if (first >= last) {
             errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.CRITICAL,
                    "Parameter first should be inferior to parameter last",
                    "SGA1_IAS_1C_AUX_CNF_",
                    "FIRST_SCIENCE"
            ));
        }
	}

	private void checkDataCompletness(ITupleApi input, List<ITupleApi> aux_data, ErrorReport errorReport) {
		if (!input.contains("OGP_SPKCO")) {
            errorReport.addError(new DataMissingError(
                    ErrorSeverity.CRITICAL,
                    "Missing OGP_SPKCO tuple",
                    "OGP_SPKCO"
            ));
        }

       
        
        if (aux_data.isEmpty() || aux_data.size() > 1) {
            errorReport.addError(new AuxiliaryDataMissingError(
                    ErrorSeverity.CRITICAL,
                    "Auxiliary SGA1_IAS_1C_AUX_CNF_ not found",
                    "SGA1_IAS_1C_AUX_CNF_"
            ));
        }
        ITupleApi aux_cnf = aux_data.get(0);
        if (aux_cnf.contains("FIRST_SCIENCE")) {
            errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.CRITICAL,
                    "Field FIRST_SCIENCE Missing",
                    "SGA1_IAS_1C_AUX_CNF_",
                    "FIRST_SCIENCE"
            ));
        }
        if (aux_cnf.contains("LAST_SCIENCE")) {
            errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.CRITICAL,
                    "Field LAST_SCIENCE Missing",
                    "SGA1_IAS_1C_AUX_CNF_",
                    "LAST_SCIENCE"
            ));
        }
        if (aux_cnf.contains("DOPD")) {
            errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.WARNING,
                    "Field DOPD Missing",
                    "SGA1_IAS_1C_AUX_CNF_",
                    "DOPD"
            ));
        }
        if (aux_cnf.contains("NFFT")) {
            errorReport.addError(new AuxiliaryDataIntegrityError(
                    ErrorSeverity.ERROR,
                    "Field NFFT Missing",
                    "SGA1_IAS_1C_AUX_CNF_",
                    "NFFT"
            ));
        }
	}
	

}

