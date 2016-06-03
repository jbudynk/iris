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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.starlink.table.StarTable;

/**
 *
 * A class that validates an input filter expression for strings.
 */
public class FilterStringExpressionValidator {
    StarTable starTable; // the stacked startable to filter
    StringEvaluator stringEvaluator; // evaluator for string expressions
    String expression; // filter expression
    
    public FilterStringExpressionValidator(StarTable table) {
        this.starTable = table;
        this.expression = "";
        this.stringEvaluator = new StringEvaluator();
    }
    
    public FilterStringExpressionValidator(StarTable table, String expression) {
        this.starTable = table;
        this.expression = expression;
        this.stringEvaluator = new StringEvaluator();
    }
    
    /**
     * Find all rows the in the StarTable that comply with the filter
     * expression.
     * 
     * @return the array of row indices that comply with the filter expression. 
     * 
     * @throws cfa.vo.iris.visualizer.metadata.FilterExpressionException if the 
     *         expression is invalid.
     */
    public List<Integer> process() throws FilterExpressionException {
        return process(expression);
    }
    
    /**
     * Find all rows the in the StarTable that comply with the given filter
     * expression.
     * 
     * @param expression
     * @return the array of row indices that comply with the filter expression. 
     * 
     * @throws IllegalArgumentException if the expression is invalid.
     */
    public List<Integer> process(String expression) throws IllegalArgumentException {
        
        // initial check for bad expressions
        if (expression.isEmpty()) {
            throw new IllegalArgumentException(FilterExpressionException.EMPTY_EXPRESSION_MSG);
        }
        if (!expression.contains("$")) {
            throw new IllegalArgumentException(FilterExpressionException.DEFAULT_MSG);
        }
        
        // get column specifiers
        ColumnMapper<String> mapper = new ColumnMapper<>(starTable, expression);
        
        // list to hold evaluated expressions
        List<String> evaluatedExpression = new ArrayList<>();
        
        // Evaluate the expression for each row in the StarTable.
        for (int i=0; i<this.starTable.getRowCount(); i++) {
            
            // evaluate the expression
            try {
                evaluatedExpression.add((String) mapper.evaluateRow(starTable.getRow(i), String.class));
            } catch (IOException ex) {
                Logger.getLogger(FilterDoubleExpressionValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // return array of indices to select
        return getFilteredIndices(evaluatedExpression);
    }
    
    private List<Integer> getFilteredIndices(List<String> evaluatedExpression) {
        // TODO: come up with more efficient way of getting indices.
        
        // get non-null indices from evaluatedExpression list
        List<Integer> indices = new ArrayList<>();
        for (int i=0; i<evaluatedExpression.size(); i++) {
            String val = evaluatedExpression.get(i);
            if (val.equals(StringEvaluator.YES))
                indices.add(i);
        }
        return indices;
    }
    
    public void setStarTable(StarTable table) {
        this.starTable = table;
    }
    
    public StarTable getStarTable() {
        return this.starTable;
    }
}
