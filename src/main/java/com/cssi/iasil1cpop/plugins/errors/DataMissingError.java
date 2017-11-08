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
public class DataMissingError extends Error{
    String productIdentifier;
    public DataMissingError(ErrorSeverity criticity, String description, String productIdentifier) {
        super(criticity, description);
        this.productIdentifier = productIdentifier;
    }
    
}
