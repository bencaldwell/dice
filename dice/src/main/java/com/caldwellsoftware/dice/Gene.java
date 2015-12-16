/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caldwellsoftware.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.log4j.Logger;

/**
 *
 * @author bencaldwell
 */
public class Gene {

    private final String name;    
    private final int instanceNum;
    private Gene output;
    private List<Gene> inputs = new ArrayList<>();
    private int arity;
    static final Logger logger = Logger.getLogger(Gene.class.getName());
    private static int instanceCounter = 0;
    
    public Gene(String name, int arity) {
        this.name = name;
        this.instanceNum = instanceCounter++;
        this.arity = arity;
    }
        
    /**
     * Copy constructor.
     * @param src 
     */
    public Gene(Gene src) {
        // new Gene with the same name
        this(src.name, src.arity);
    }
            
    public Gene getOutput() {
        return this.output;
    }
    
    public void setOutput(Gene output) {
        this.output = output;
    }
    
    public List<? super Gene> getInputs() {
        return this.inputs;
    }
    
    public void initInputs(Function<Integer, ? extends Gene> inputSupplier) {
        for (int i=0; i<arity; i++) {
            Gene input = inputSupplier.apply(this.getDepth()); // get a random input
            this.inputs.add(input); // store the input
            input.setOutput(this); // set the inputs output to point back to this gene
            input.initInputs(inputSupplier); // init the input's inputs (recursive operation)
        }
    }
    
    public int getDepth() {
        if (this.output == null) {
            return 0;
        } else {
            return this.getOutput().getDepth() + 1;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    public void printTree() {
        printTree("", true);
    }
    
    private void printTree(String prefix, boolean isTail) {
        logger.debug(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < this.inputs.size() - 1; i++) {
            this.inputs.get(i).printTree(prefix + (isTail ? "    " : "│   "), false);
        }
        if (this.inputs.size() > 0) {
            this.inputs.get(this.inputs.size() - 1).printTree(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}
