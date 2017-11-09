/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.plugins;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.hipparchus.util.Pair;

import com.cssi.pdap.dpi.plugins.ITupleApi;
import com.cssi.pdap.dpi.plugins.TupleApiImpl;

/**
 *
 * @author sdorgan
 */
public class TupleAdaptor {

	public TupleAdaptor() {
	}

	public static class BeanOutput {
		private Object object;

		public BeanOutput(Object object) {
			this.object = object;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
	}

	public Pair<String, ITupleApi> createTuple(Object outpout, Object fieldValue)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyUtilsBean bean = new PropertyUtilsBean();
		Map<String, Object> descr = bean.describe(outpout);
		ITupleApi tuple = new TupleApiImpl();
		for (Entry<String, Object> e : descr.entrySet()) {
			String name = String.format("%s_%s", outpout.getClass().getSimpleName(), e.getKey());
			tuple.addField(name, e.getValue());

		}
		String name = outpout.getClass().getSimpleName();
		return Pair.create(name, tuple);
	}

}
