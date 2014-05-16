/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa.vo.iris.test.vizier;

import cfa.vo.sedlib.Segment;
import java.util.HashMap;
import cfa.vo.sedlib.DoubleParam;
import cfa.vo.sedlib.Point;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jamie
 */
public class VizierImporterTest {
    
    public VizierImporterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getSedFromName method, of class VizierImporter.
     */
    @Test
    public void testGetSedFromName_String() throws Exception {
        System.out.println("getSedFromName");
        String targetName = "";
        Collection expResult = null;
        Collection result = VizierImporter.getSedFromName(targetName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSedFromName method, of class VizierImporter.
     */
    @Test
    public void testGetSedFromName_String_String() throws Exception {
        System.out.println("getSedFromName");
        String targetName = "";
        String searchRadius = "";
        Collection expResult = null;
        Collection result = VizierImporter.getSedFromName(targetName, searchRadius);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSedFromName method, of class VizierImporter.
     */
    @Test
    public void testGetSedFromName_3args_removeTrue() throws Exception {
        System.out.println("getSedFromName_3args_1");
        String targetName = "3c273";
        String searchRadius = "1.5";
        boolean remove = true;
        
        Collection<Segment> expResult = null;
        Collection<Segment> result = VizierImporter.getSedFromName(targetName, searchRadius, remove);
        System.out.println(result.size());
        
        //check that result has this point in it
        Point p = new Point();
        p.createSpectralAxis().setValue(new DoubleParam(138.55e+3));
        p.createFluxAxis().setValue(new DoubleParam(68.0e-3));
        p.createFluxAxis().createAccuracy().setStatError(new DoubleParam(1.3e-3));
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testGetSedFromName_3args_removeFalse() throws Exception {
        System.out.println("getSedFromName_3args_1");
        String targetName = "3c273";
        String searchRadius = "1.5";
        boolean remove = false;
        
        Collection expResult = null;
        Collection result = VizierImporter.getSedFromName(targetName, searchRadius, remove);
        System.out.println(result.size());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSedFromName method, of class VizierImporter.
     */
    @Test
    public void testGetSedFromName_3args_2() throws Exception {
        System.out.println("getSedFromName");
        String targetName = "";
        String searchRadius = "";
        String endpoint = "";
        Collection expResult = null;
        Collection result = VizierImporter.getSedFromName(targetName, searchRadius, endpoint);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSedFromName method, of class VizierImporter.
     */
    @Test
    public void testGetSedFromName_4args() throws Exception {
        System.out.println("gettSedFromName_4args");
        String targetName = "";
        String searchRadius = "";
        String endpoint = "";
        boolean remove = false;
        Collection expResult = null;
        Collection result = VizierImporter.getSedFromName(targetName, searchRadius, endpoint, remove);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
