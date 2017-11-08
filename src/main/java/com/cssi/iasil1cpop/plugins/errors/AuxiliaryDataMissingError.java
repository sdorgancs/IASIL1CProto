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
public class AuxiliaryDataMissingError extends Error{
    String dataIdentifier;
    public AuxiliaryDataMissingError(ErrorSeverity criticity, String description, String dataIdentifier) {
        super(criticity, description);
        this.dataIdentifier = dataIdentifier;
    }
    
}
