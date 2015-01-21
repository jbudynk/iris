///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package cfa.vo.iris.test.sedstacker;
//
//import cfa.vo.interop.SAMPController;
//import cfa.vo.iris.interop.SedSAMPController;
//import cfa.vo.iris.sed.ExtSed;
//import cfa.vo.iris.sed.SedlibSedManager;
//import cfa.vo.iris.test.sedstacker.samp.SedStackerNormalizer;
//import cfa.vo.iris.test.sedstacker.samp.SedStackerRedshifter;
//import cfa.vo.sed.builder.SedBuilder;
//import cfa.vo.sedlib.io.SedFormat;
//import cfa.vo.sherpa.SherpaClient;
//import java.net.URL;;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.After;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import spv.controller.StartSherpa;
//
///**
// *
// * @author jbudynk
// */
//public class StackTest {
//    
//    SAMPController controller;
//    SedStackerManager manager;
//    SherpaClient client;
//    SedlibSedManager sedManager;
//    StartSherpa sherpa;
//    
//    @Before
//    public void setUp() throws Exception {
//	SedBuilder builder = new SedBuilder();
//        builder.init(new App(), new Ws());
//
//        sedManager = (SedlibSedManager) SedBuilder.getWorkspace().getSedManager();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//	
//    }
//    public StackTest() {
//    }
//
//    /**
//     * Test of remove method, of class Stack.
//     */
//    @Test
//    public void testRemove() throws Exception {
//	
//	Stack stack = new Stack("Stack");
//	
//	URL fileURL = Stack.class.getResource("/multiple_segment_sed.vot");
//	ExtSed sed = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	stack.add(sed);
//	stack.add(sed);
//	
//	assertFalse(stack.getSegment(0).getTarget().getName() == stack.getSegment(1).getTarget().getName());
//	
//	stack.removeSegment(1);
//	assertEquals(stack.getNumberOfSegments(), 1);
//    }
//
//    /**
//     * Test of add method, of class Stack.
//     */
//    @Test
//    public void testAdd() throws Exception {
//	
//	// create a Sed with multiple Segments
//	URL fileURL = Stack.class.getResource("/multiple_segment_sed.vot");
//	ExtSed sed = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	
////	manager = new SedStackerManager();
//	
//	// add the Sed to a Stack
//	Stack stack = new Stack("Stack");
//	stack.add(sed);
//	manager.add(stack);
//	
//	assertEquals(stack.getSegment(0).getFluxAxisUnits(), sed.getSegment(0).getFluxAxisUnits());
//	assertEquals(stack.getNumberOfSegments(), 1);
//	
//	// check that the Sed the Stack references is updated with new Segments
//	assertTrue(sedManager.existsSed("Stack"));
//	
//	ExtSed sed1 = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	ExtSed sed2 = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	ExtSed sed3 = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	
//	List<ExtSed> seds = Arrays.asList(sed1,sed2,sed3);
//	stack.add(seds);
//	
//	assertEquals(4, sedManager.getSelected().getNumberOfSegments());
//	assertEquals(stack.getSegment(0).getFluxAxisUnits(), sed.getSegment(0).getFluxAxisUnits());
//	assertEquals(stack.getSegment(0).getSpectralAxisUnits(), sed.getSegment(0).getSpectralAxisUnits());
//	assertEquals(stack.getNumberOfSegments(), 4);
//    }
//
//    /**
//     * Test of shift method, of class Stack.
//     */
//    @Test
//    public void testShift() throws Exception {
//	
//	controller = new SedSAMPController("SEDStacker","SEDStacker",null);
//	controller.setAutoRunHub(false);
//	controller.start(false);
//
//        Thread.sleep(2000);
//	System.out.println();
//
//        while(!controller.isConnected()) {
//            System.out.println("waiting connection");
//            Thread.sleep(1000);
//        }
//	
//	// Redshift a stack
//	
//	URL fileURL = Stack.class.getResource("/id41.fits");
//	ExtSed sed1 = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	ExtSed sed2 = ExtSed.read(fileURL.getPath(), SedFormat.VOT);
//	
//	// create a stack
//	Stack stack = new Stack("Stack");
////	manager.add(stack);
//	stack.add(sed1);
//	stack.add(sed2);
//	double[] zs = new double[] {0.5, 1.0};
//	stack.setRedshifts(zs);
//	System.out.println(stack.getRedshift(stack.getSegment(0)));
//	// shift the stack
//	ZConfig zconf = new ZConfig();
//	zconf.setCorrectFlux(false);
//	zconf.setNewz(0.0);
//	
//	SedStackerRedshifter redshifter = new SedStackerRedshifter(controller, manager);
//	redshifter.shift(stack, stack.getRedshifts(), zconf);
//	
//	// check that the original redshift of the stack is the same, but that
//	// the wavelengths and values have changed.
//	
//	//URL fileURL1 = Stack.class.getResource("/multiple_segment_sed_z0.5.vot");
//	//URL fileURL2 = Stack.class.getResource("/multiple_segment_sed_z1.0.vot");
//	//ExtSed shifted_sed1 = ExtSed.read(fileURL1.getPath(), SedFormat.VOT);
//	//ExtSed shifted_sed2 = ExtSed.read(fileURL2.getPath(), SedFormat.VOT);
//	//double[] expectedX1 = shifted_sed1.getSegment(0).getSpectralAxisValues();
//	//double[] expectedX2 = shifted_sed2.getSegment(0).getSpectralAxisValues();
//	//for (int i=0; i< expectedX1.length; i++) {
//	//    assertEquals(expectedX1[i], stack.getSegment(0).getSpectralAxisValues()[i]);
//	//}
//	//assertArraysEqual(expectedX2, stack.getSegment(1).getSpectralAxisValues());
//	assertEquals(zs[0], stack.getRedshift(stack.getSegment(0)));
//	assertEquals(zs[1], stack.getRedshift(stack.getSegment(1)));
//	//assertArraysEqual(expectedX1, sedManager.getSeds().get(1).getSegment(0).getSpectralAxisValues());
//	controller.stop();
//    }
//
//    /**
//     * Test of normalize method, of class Stack.
//     */
//    @Test
//    public void testNormalize() throws Exception {
//	controller = new SedSAMPController("SEDStacker","SEDStacker",null);
//	controller.setAutoRunHub(false);
//	controller.start(false);
//
//        Thread.sleep(2000);
//	System.out.println();
//
//        while(!controller.isConnected()) {
//            System.out.println("waiting connection");
//            Thread.sleep(1000);
//        }
//	
//	URL fileURL1 = Stack.class.getResource("/id41.fits");
//	URL fileURL2 = Stack.class.getResource("/id41_z0.5.vot");
//	ExtSed sed1 = ExtSed.read(fileURL1.getPath(), SedFormat.VOT);
//	ExtSed sed2 = ExtSed.read(fileURL2.getPath(), SedFormat.VOT);
//	
//	// create a stack
//	Stack stack = new Stack("Stack");
//	stack.add(sed1);
//	stack.add(sed2);
//	double[] zs = new double[] {0.5, 1.0};
//	stack.setRedshifts(zs);
//	
//	// normalize the stack
//	NormConfig normconf = new NormConfig();
//	normconf.setAtPointXUnits("Angstrom");
//	normconf.setAtPointXValue(5000.0);
//	normconf.setAtPointYType("Value");
//	normconf.setAtPointYUnits("erg/s/cm2");
//	normconf.setAtPointYValue(10e-14);
//	normconf.setIntegrate(false);
//	normconf.setIntegrateBoundsXMax("max");
//	normconf.setIntegrateBoundsXMin("min");
//	normconf.setIntegrateXUnits("Angstrom");
//	normconf.setIntegrateYType("Value");
//	normconf.setIntegrateYValue(1.0);
//	normconf.setIntegrateYUnits("erg/s/cm2/Angstrom");
//	
//	SedStackerNormalizer normalizer = new SedStackerNormalizer(controller, manager);
//	normalizer.normalize(stack, normconf);
//    }
//
//    /**
//     * Test of stack method, of class Stack.
//     */
//    @Test
//    public void testStack() {
//    }
//    
//    private boolean assertArraysEqual(double[] arr1, double[] arr2) {
//	
//	int j = 0;
//	
//	if (arr1.length != arr2.length) {
//	    return false;
//	}
//	for (int i=0; i<arr1.length; i++) {
//	    try {
//		if (arr1[i] == arr2[i]) {
//		    j++;
//		}
//	    } catch (ArrayIndexOutOfBoundsException ex) {
//		return false;
//	    }
//	}
//	
//	return j==arr2.length;
//    }
//    
//}