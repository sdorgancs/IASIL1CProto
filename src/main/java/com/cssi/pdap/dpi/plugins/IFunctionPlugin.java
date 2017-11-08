/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.pdap.dpi.plugins;

import java.util.LinkedList;
import org.hipparchus.util.Pair;

/**
 *
 * @author sdorgan
 */
public interface IFunctionPlugin extends IFunctionnalBlockPlugin{
    LinkedList<Pair<IDataFlowEvent, ITupleApi>> execute(ITupleApi input);
    
}
