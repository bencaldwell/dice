/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.caldwellsoftware.dice;

import com.caldwellsoftware.dice.Learner.Builder;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author bencaldwell
 */
public class TestDigLatch {
    
    public TestDigLatch() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void digLatch() throws FileNotFoundException {
        URL configURL = this.getClass().getResource("diglatch/config.xml");
        File configFile = new File(configURL.toString());
        
        Builder builder = new Learner.Builder();
        builder.addGenes(Arrays.asList(new Gene("TON1",1), new Gene("TOFF1",1), new Gene("AND",2)));
        builder.addInputs(Arrays.asList(new Gene("IN1",0), new Gene("IN2",0)));
        builder.addOutputs(Arrays.asList(new Gene("OUT1",1), new Gene("OUT2",1)));
        
        Learner learner = builder.build();
        try {
            learner.start();
        } catch (Exception ex) {
            Logger.getLogger(TestDigLatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
