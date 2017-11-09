/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.plugins.errors;

/**
 *
 * @author sdorgan
 */
public class TimelinessError extends Error {
	String plugin;
	long expectedDuration;
	long effectiveDuration;

	public TimelinessError(ErrorSeverity criticity, String description, String plugin, long expectedDuration,
			long effectiveDuration) {
		super(criticity, description);
		this.plugin = plugin;
		this.expectedDuration = expectedDuration;
		this.effectiveDuration = effectiveDuration;
	}

}
