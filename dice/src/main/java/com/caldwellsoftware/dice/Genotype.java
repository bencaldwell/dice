/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caldwellsoftware.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author bencaldwell
 */
public class Genotype {
    private final List<? extends Gene> outputs;
    private final List<? extends Gene> inputs;
    private int maxDepth = 5;
    private final Supplier<? extends Gene> geneSupplier;
    private final Random rand;
    static final Logger logger = Logger.getLogger(Genotype.class.getName());
    
    public Genotype(    final List<Gene> inputs, 
                        final List<Gene> outputs, 
                        Supplier<? extends Gene> geneSupplier,
                        Random rand) {
        
        this.geneSupplier = geneSupplier;
        this.rand = rand;
        
        // copy outputs for use by this genotype
        List<Gene> tempOutputs = new ArrayList<>();
        outputs.stream().forEach((output) -> {
            tempOutputs.add(new Gene(output));
        });
        this.outputs = tempOutputs;
        
        // copy inputs for use by this genotype
        List<Gene> tempInputs = new ArrayList<>();
        inputs.stream().forEach((input) -> {
            tempInputs.add(new Gene(input));
        });
        this.inputs = tempInputs;
        
        // generate a random genotype with only inputs and outputs fixed
        this.generate();
    }
    
    /**
     * Generate a genotype
     */
    private void generate() {

        /** create the tree by connecting a random
         * gene or input to the inputs of each gene
         **/
        logger.debug("=====Genotype=====");
        outputs.stream().forEach((output) -> {
            output.initInputs(this::inputSupplier);
            if (logger.getLevel() == Level.DEBUG) {
                output.printTree();
            }
        });
    }

    /**
     * supply random inputs/genes based on the current depth of the tree.
     * Inputs are more likely as the tree gets deeper.
     * @param depth
     * @return 
     */
    private Gene inputSupplier(int depth) {
        
        // shallow depth means more likely to choose a gene
        // deep depth means more likely to choose an input
        // max depth means force choosing an input
        int selection = this.rand.nextInt(this.maxDepth - depth);

        // randomly choose a gene or input
        Gene gene = null;
        if (selection == 0) {
            // selection == 0 then choose an input
            int index = this.rand.nextInt(this.inputs.size());
            gene = this.inputs.get(index);
        } else {
            // otherwise choose a gene
            gene = this.geneSupplier.get();
        }

        return gene;
    };
}
