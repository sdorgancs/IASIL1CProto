package com.cssi.iasil1cpop.plugins;

import com.cssi.pdap.dpi.plugins.ITupleApi;

public class TupleReader {
	private ITupleApi tuple;

	public TupleReader(ITupleApi tuple) {
		super();
		this.tuple = tuple;
	}

	public ITupleApi getTuple() {
		return tuple;
	}

	public void setTuple(ITupleApi tuple) {
		this.tuple = tuple;
	}
	
	@SuppressWarnings("unchecked")
	public <Obj> Obj read(String fieldname, Obj defaultValue){
		try{
			return (Obj) tuple.getValueByField(fieldname);
		}catch(final Throwable t){
			return defaultValue;
		}
		
		
	}
}
