/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.codehaus.groovy.ast.stmt;

import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.BooleanExpression;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents an if (condition) { ... } else { ... } statement in Groovy.
 */
public class IfStatement extends Statement {

    private BooleanExpression booleanExpression;
    private Statement ifBlock;
    private Statement elseBlock;

    public IfStatement(final BooleanExpression booleanExpression, final Statement ifBlock, final Statement elseBlock) {
        setBooleanExpression(booleanExpression);
        setIfBlock(ifBlock);
        setElseBlock(elseBlock);
    }

    public void setBooleanExpression(final BooleanExpression booleanExpression) {
        this.booleanExpression = Objects.requireNonNull(booleanExpression);
    }

    public void setIfBlock(final Statement statement) {
        ifBlock = Objects.requireNonNull(statement);
    }

    public void setElseBlock(final Statement statement) {
        elseBlock = Optional.ofNullable(statement).orElse(EmptyStatement.INSTANCE);
    }

    //--------------------------------------------------------------------------

    @Override
    public void visit(final GroovyCodeVisitor visitor) {
        visitor.visitIfElse(this);
    }

    public BooleanExpression getBooleanExpression() {
        return booleanExpression;
    }

    public Statement getIfBlock() {
        return ifBlock;
    }

    public Statement getElseBlock() {
        return elseBlock;
    }

    @Override
    public String getText() {
        Statement thenStmt = getIfBlock(), elseStmt = getElseBlock();

        StringBuilder text = new StringBuilder(64);
        text.append("if (");
        text.append(getBooleanExpression().getText());
        text.append(") ");
        text.append(thenStmt.getText());
        if (!elseStmt.isEmpty()) {
            if (!(thenStmt instanceof BlockStatement)) {
                text.append(';');
            }
            text.append(" else ");
            text.append(elseStmt.getText());
        }
        return text.toString();
    }
}
