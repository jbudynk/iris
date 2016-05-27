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

//import com.fathzer.soft.javaluator.AbstractEvaluator;
//import com.fathzer.soft.javaluator.BracketPair;
//import com.fathzer.soft.javaluator.Constant;
//import com.fathzer.soft.javaluator.Operator;
//import com.fathzer.soft.javaluator.Parameters;
import cfa.vo.iris.visualizer.metadata.javaluator.AbstractEvaluator;
import cfa.vo.iris.visualizer.metadata.javaluator.BracketPair;
import cfa.vo.iris.visualizer.metadata.javaluator.Constant;
import cfa.vo.iris.visualizer.metadata.javaluator.Operator;
import cfa.vo.iris.visualizer.metadata.javaluator.Parameters;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 * An evaluator for string expressions
 */
public class StringEvaluator extends AbstractEvaluator<String> {
    
    // string flags for the evaluation condition is met (YES) or not met (NO)
    public final static String YES = "_YES_";
    public final static String NO = "_NO_";
    
    // basic string operators
    public final static Operator NOT = new Operator("!", 1, Operator.Associativity.RIGHT, 1);
    public final static Operator CONTAINS = new Operator("contains", 2, Operator.Associativity.LEFT, 0);
    public final static Operator IS_EMPTY = new Operator("isEmpty", 1, Operator.Associativity.RIGHT, 0);
    public final static Operator EQUALS = new Operator("isEqual", 2, Operator.Associativity.LEFT, 0);
    public final static Operator BEGINS_WITH = new Operator("beginsWith", 2, Operator.Associativity.LEFT, 0);
    public final static Operator ENDS_WITH = new Operator("endsWith", 2, Operator.Associativity.LEFT, 0);
    
    // string identifiers
    public final static BracketPair DOUBLE_QUOTES = new BracketPair('\'', '\'');
    public final static BracketPair SINGLE_QUOTES = new BracketPair('\"', '\"');
    
    private static final Parameters PARAMETERS;
    
    static {
        PARAMETERS = new Parameters();
        PARAMETERS.add(CONTAINS); // contains
        PARAMETERS.add(IS_EMPTY); // is empty
        PARAMETERS.add(BEGINS_WITH); // begins with
        PARAMETERS.add(ENDS_WITH); // ends with
        PARAMETERS.addExpressionBracket(DOUBLE_QUOTES); // " "
        PARAMETERS.addExpressionBracket(SINGLE_QUOTES); // ' '
    }
    
    public StringEvaluator() {
        super(PARAMETERS);
    }
    
    /**
     * Evaluates the filter expression.
     * @param expression the filter expression to evaluate
     * @return A hash set of the table rows which fulfill the filter expression
     */
    @Override
    protected String toValue(String expression, Object o) {
        return expression;
    }
    
    protected String evaluate(Constant constant, Object evaluationContext) {
        return constant.toString();
    }
    
    /**
     * Evaluate a logical expression.
     * met.
     * @param operator
     * @param operands
     * @param evaluationContext
     * @return a string flag saying whether the condition is met ("_YES_") or 
     * not met ("_NO_")
     */
    @Override
    protected String evaluate(Operator operator, Iterator<String> operands, Object evaluationContext) {
        String left;
        String right;
        
        if (operator == CONTAINS) {
            left = operands.next();
            right = operands.next();
            if (left.contains(right))
                return YES;
        } else if (operator == EQUALS) {
            left = operands.next();
            right = operands.next();
            if (left.equals(right))
                return YES;
        } else if (operator == IS_EMPTY) {
            right = operands.next();
            if (right.isEmpty())
                return YES;
        } else if (operator == BEGINS_WITH) {
            left = operands.next();
            right = operands.next();
            if (left.startsWith(right))
                return YES;
        } else if (operator == ENDS_WITH) {
            left = operands.next();
            right = operands.next();
            if (left.endsWith(right))
                return YES;
        } else if (operator == NOT) {
            right = operands.next();
            if (right.equals(YES))
                return NO;
            return YES;
        } else {
            return super.evaluate(operator, operands, evaluationContext);
        }
        return null;
    }
    
    @Override
    public String evaluate(String expression, Object evaluationContext) {
        
        return super.evaluate(expression, evaluationContext);
    }
}
