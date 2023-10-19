package com.fidelity.mixed1;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/*
 * Evaluate expressions programmatically using the Spring Expression Evaluator
 * 
 * This is surprisingly difficult to do. The reason is that the expression evaluator needs a reference
 * to the bean factory in order to evaluate expressions against bean properties
 */
@Configuration
public class AppConfigByExpressionEvaluation implements BeanFactoryAware {
	BeanFactory beanFactory;
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Bean("rand1x")
	public RandomSimulator getRand1x() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("new java.util.Random().nextDouble() * 100.0");
		double seed = exp.getValue(Double.class);

		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}

	@Bean("rand2x")
	public RandomSimulator getRand2x() {
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setBeanResolver(new BeanFactoryResolver(this.beanFactory));
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("@rand1x.seed");
		double seed = exp.getValue(context, Double.class);

		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}
}
