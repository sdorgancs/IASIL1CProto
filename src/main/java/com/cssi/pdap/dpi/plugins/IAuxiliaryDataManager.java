/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.pdap.dpi.plugins;

import java.util.List;

/**
 *
 * @author sdorgan
 */
public interface IAuxiliaryDataManager {
	void ingestParams(ITupleApi params);

	List<ITupleApi> queryParams(ITupleApi query);
}
