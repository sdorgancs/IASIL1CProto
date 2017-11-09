/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.plugins;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.hipparchus.util.Pair;

import com.cssi.iasil1cpop.algorithms.Algorithm;
import com.cssi.iasil1cpop.plugins.errors.ErrorReport;
import com.cssi.iasil1cpop.plugins.errors.ErrorSeverity;
import com.cssi.pdap.dpi.plugins.Event;
import com.cssi.pdap.dpi.plugins.IAuxiliaryDataManager;
import com.cssi.pdap.dpi.plugins.IBreakpointHelper;
import com.cssi.pdap.dpi.plugins.IDataFlowEvent;
import com.cssi.pdap.dpi.plugins.IFunctionPlugin;
import com.cssi.pdap.dpi.plugins.IHistoricalDataManager;
import com.cssi.pdap.dpi.plugins.ILoggerHelper;
import com.cssi.pdap.dpi.plugins.IPluginConfig;
import com.cssi.pdap.dpi.plugins.ITracerHelper;
import com.cssi.pdap.dpi.plugins.ITupleApi;
import com.cssi.pdap.dpi.plugins.TupleApiImpl;

/**
 * Helper class to simplify scientific algorithms integration as a DPI plugin
 * 
 * @author sdorgan
 * @param <Input>
 * @param <Output>
 */
public abstract class FunctionPlugin<Input, Output, Algo extends Algorithm<Input, Output>> implements IFunctionPlugin {
	// DPI services entry point
	private IPluginConfig dpiContext;
	private TupleAdaptor tupleAdaptor;

	/**
	 * 
	 * @param input
	 * @return
	 */
	public abstract CheckedValue<Input> readAndCheckInputs(ITupleApi input);

	public abstract Algo getAlgorithm();

	public abstract List<CheckedValue<Object>> checkOutputs(Output ouput, ErrorReport error);

	/**
	 * Generic implementation of the IFunctionPlugin.execute method
	 * 
	 * @param input
	 *            a tuple transmitted by the DPI
	 * @param a
	 *            list of output tuples created by <code>Algo</code> from the
	 *            input tuple associated with their error report
	 */
	@Override
	public LinkedList<Pair<IDataFlowEvent, ITupleApi>> execute(ITupleApi input) {
		// initialize output data
		LinkedList<Pair<IDataFlowEvent, ITupleApi>> list = new LinkedList<>();

		// Read input and error report
		CheckedValue<Input> p = readAndCheckInputs(input);
		Input in = p.getValue();
		ErrorSeverity severity = p.getReport().severity();
		final IDataFlowEvent event = p.getReport().createEvent(in);

		// If the input parameter cannot be created or severity is critical the
		// tuple is skipped
		if (in == null || severity == ErrorSeverity.CRITICAL) {
			list.add(new Pair<>(event, new TupleApiImpl()));
			return list;
		}
		try {
			// Call algorithm
			Output out = getAlgorithm().call(p.getValue());

			// Convert output into tuples
			List<CheckedValue<Object>> outputs = checkOutputs(out, p.getReport().duplicate());

			// associate the error report to each tuple
			outputs.forEach(cv -> {
				IDataFlowEvent e = cv.getReport().createEvent(cv.getValue());
				Pair<String, ITupleApi> t;

				try {

					t = tupleAdaptor.createTuple(out, cv.getValue());
					list.add(new Pair<>(e, t.getValue()));
					getBreakpointHelper().emit(t.getKey(), t.getValue());

				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
					// If an unforeseen exception occurs
					// A log message is sent to the DPI
					getILoggerHelper().emit(
							new Event("exception", new AbstractMap.SimpleEntry<>("exception", ex.getClass().getName()),
									new AbstractMap.SimpleEntry<>("message", ex.getMessage())));
					// A report is associated to an empty tuple
					list.add(new Pair<>(event, new TupleApiImpl()));
				}

			});
		} catch (Exception ex) {
			// If an unforeseen exception occurs
			// A log message is sent to the DPI
			getILoggerHelper()
					.emit(new Event("exception", new AbstractMap.SimpleEntry<>("exception", ex.getClass().getName()),
							new AbstractMap.SimpleEntry<>("message", ex.getMessage())));
			// A report is associated to an empty tuple
			list.add(new Pair<>(event, new TupleApiImpl()));
		}
		return list;
	}

	/**
	 * Store <code>init</code> parameter
	 * 
	 * @param init
	 *            DPI interfaces entry point
	 */
	@Override
	public void init(IPluginConfig init) {
		dpiContext = init;
		tupleAdaptor = new TupleAdaptor();
	}

	@Override
	public void close() {
		// Nothing to do
	}

	/**
	 * Delegates call to <code>dpiContext</code>
	 * 
	 * @return the DPI auxiliary data manager service interface
	 */
	public IAuxiliaryDataManager getAuxiliaryDataManager() {
		return dpiContext.getAuxiliaryDataManager();
	}

	/**
	 * Delegates call to <code>dpiContext</code>
	 * 
	 * @return the DPI auxiliary data manager service interface
	 */
	public IHistoricalDataManager getHistoricalDataManager() {
		return dpiContext.getHistoricalDataManager();
	}

	/**
	 * Delegates call to <code>dpiContext</code>
	 * 
	 * @return the DPI auxiliary data manager service interface
	 */
	public ILoggerHelper getILoggerHelper() {
		return dpiContext.getILoggerHelper();
	}

	/**
	 * Delegates call to <code>dpiContext</code>
	 * 
	 * @return the DPI auxiliary data manager service interface
	 */
	public ITracerHelper getTracerHelper() {
		return dpiContext.getTracerHelper();
	}

	/**
	 * Delegates call to <code>dpiContext</code>
	 * 
	 * @return the DPI auxiliary data manager service interface
	 */
	public IBreakpointHelper getBreakpointHelper() {
		return dpiContext.getBreakpointHelper();
	}

	/**
	 * Delegates call to <code>dpiContext</code>
	 * 
	 * @return the DPI auxiliary data manager service interface
	 */
	public Properties getProperties() {
		return dpiContext.getProperties();
	}
}
