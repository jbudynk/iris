/*
 * Copyright 2016 Chandra X-Ray Observatory.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cfa.vo.iris.visualizer.metadata;

import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.visualizer.stil.tables.IrisStarTable;
import cfa.vo.iris.visualizer.stil.tables.IrisStarTableAdapter;
import cfa.vo.sedlib.io.SedFormat;
import cfa.vo.testdata.TestData;
import java.util.concurrent.Executors;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.ac.starlink.table.StarTable;

/**
 *
 * Test that expressions with string-valued columns are filtered correctly
 */
public class StringExpressionTest {
    private IrisStarTableAdapter adapter = new IrisStarTableAdapter(Executors.newSingleThreadExecutor());
    private FilterStringExpressionValidator validator;
    StarTable table;
    String expression;
    
    @Rule public ExpectedException exception = ExpectedException.none();
    
    public StringExpressionTest() {
        
    }
    
    @Before
    public void setUp() throws Exception {
        // setup table to filter
        ExtSed sed = ExtSed.read(TestData.class.getResource("3c273.vot").openStream(), SedFormat.VOT);
        IrisStarTable table = adapter.convertSegment(sed.getSegment(0));
        
        // filter validator
        validator = new FilterStringExpressionValidator(table.getSegmentMetadataTable());
    }
    
    @Test
    public void testSimpleExpressions() throws Exception {
        
        Integer[] expected;
        Integer[] results;
        
        expected = new Integer[]{0, 75, 76, 87, 88, 103, 104, 118, 
            119, 134, 135, 147, 148, 149, 150, 151, 161, 162, 171, 172, 177, 
            179, 181, 183, 200, 201, 202, 203, 204, 208, 222, 224, 242, 246, 
            249, 251, 253, 268, 269, 273, 277, 279, 281, 289, 294, 296, 297, 
            299, 301, 303, 318,329, 335, 354, 358, 364, 371, 401, 421};
        
        expression = "$0 contains \"1 sigma\"";
        results = (Integer[]) validator.process(expression)
                .toArray(new Integer[expected.length]);
        assertArrayEquals(expected, results);
        
        expected = new Integer[]{0, 75, 76, 87, 88, 103, 104, 118, 
            119, 134, 135, 147, 161, 162, 171, 172, 204, 208, 222, 224, 242, 
            246, 249, 251, 253, 268, 269, 273, 277, 279, 281, 289, 296, 318, 
            329, 335, 354, 358, 364, 371, 401, 421};
        
        expression = "$0 equals 1 sigma";
        results = (Integer[]) validator.process(expression)
                .toArray(new Integer[expected.length]);
        assertArrayEquals(expected, results);
        
//        expected = (Integer[]) new IntRange(1, 455).toArray();
//        expected = new Integer[]{0, 75, 76, 87, 88, 103, 104, 118, 
//            119, 134, 135, 147, 148, 149, 150, 151, 161, 162, 171, 172, 177, 
//            179, 181, 183, 200, 201, 202, 203, 204, 208, 222, 224, 242, 246, 
//            249, 251, 253, 268, 269, 273, 277, 279, 281, 289, 294, 296, 297, 
//            299, 301, 303, 318,329, 335, 354, 358, 364, 371, 401, 421};
//        
//        expression = "$0 !equals \"1 sigma\"";
//        results = (Integer[]) validator.process(expression)
//                .toArray(new Integer[expected.length]);
//        assertArrayEquals(expected, results);
        
    }

    @Test
    public void testBadExpressions() throws Exception {
        
        // setup table to filter
        ExtSed sed = ExtSed.read(TestData.class.getResource("3c273.vot").openStream(), SedFormat.VOT);
        IrisStarTable table = adapter.convertSegment(sed.getSegment(0));
        
        // an exception should be thrown if a comparison expression has a 
        // column with string values
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(FilterExpressionException.STRING_COLUMN_MSG);
        
        expression = "$1 + 2 < 6";
        validator.process(expression);
    }
    
}
