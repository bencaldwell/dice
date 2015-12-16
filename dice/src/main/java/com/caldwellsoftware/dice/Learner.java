/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caldwellsoftware.dice;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.caldwellsoftware.dice.Gene;
import com.caldwellsoftware.dice.Genotype;
import java.util.Random;
import java.util.function.Supplier;


/**
 *
 * @author bencaldwell
 */
public class Learner {
    
    private final List<Gene> inputs;
    private final List<Gene> genes;
    private final List<Gene> outputs;
    private final List<Genotype> population;
    private final int popSize;
    private final int maxDepth;
    private static final Random rand = new Random();
    
    static Logger log = Logger.getLogger(Learner.class.getName());
    
    public static class Builder {
            
        private List<Gene> genes = new ArrayList<>();
        private List<Gene> inputs = new ArrayList<>();
        private List<Gene> outputs = new ArrayList<>();
        private List<Genotype> population = new ArrayList<>();
        
        private int popSize = 2;
        private int maxDepth = 5;
        
        public Builder() {

        }
        
        public Builder addInputs(List<? extends Gene> inputs) {
            this.inputs.addAll(inputs);
            return this;
        }
        
        public Builder addGenes(List<? extends Gene> genes) {
            this.genes.addAll(genes);
            return this;
        }
        
        public Builder addOutputs(List<? extends Gene> outputs) {
            this.outputs.addAll(outputs);
            return this;
        }

        public Builder setPopSize(int popSize) {
            this.popSize = popSize;
            return this;
        }
        
        public Learner build() {
            return new Learner(this);
        }
    }
    
    private Learner(Builder builder) {
        
        this.inputs = builder.inputs;
        this.genes = builder.genes;
        this.outputs = builder.outputs;
        this.population = builder.population;
        this.popSize = builder.popSize;
        this.maxDepth = builder.maxDepth;
    }
    
    public void create() {
        // use a lambda function to supply random copies of genes where required
        Supplier<Gene> geneSupplier = () -> {
            int selector = rand.nextInt(genes.size());
                Gene gene = genes.get(selector);
                return new Gene(gene); // return a deep copy of the gene for use
        };
        
        // create the members of the population - provide the gene supplier
        for (int i=0; i<popSize; i++) {
            population.add(new Genotype(inputs, outputs, geneSupplier, rand));
        }
    }

    
    public void start() throws Exception {
        //TEST: create a population
        this.create();
        //TODO: evaluate a population
        //TODO: killoff
        //TODO: mutate
        //TODO: crossover
        //TODO: evaluate again...
    }
    
    
    private void evaluate() {
        //TODO: write all individuals to uppaal system
        //TODO: evaluate uppaal system for ioco using TRON
        //TODO: provide fitness results 
    }
}
