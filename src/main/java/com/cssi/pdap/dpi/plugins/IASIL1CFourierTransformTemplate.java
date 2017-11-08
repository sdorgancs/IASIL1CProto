package com.cssi.pdap.dpi.plugins;

import java.util.LinkedList;

import org.hipparchus.util.Pair;

import com.cssi.iasil1cpop.plugins.functions.fouriertransform.IASIL1CFourierTransformPlugin;

public class IASIL1CFourierTransformTemplate implements IFunctionPlugin {
	final IASIL1CFourierTransformPlugin delegate = new IASIL1CFourierTransformPlugin();

	@Override
	public void init(IPluginConfig init) {
		delegate.init(init);
	}

	@Override
	public void close() {
		delegate.close();
	}

	@Override
	public LinkedList<Pair<IDataFlowEvent, ITupleApi>> execute(ITupleApi input) {
		return delegate.execute(input);
	}

}
