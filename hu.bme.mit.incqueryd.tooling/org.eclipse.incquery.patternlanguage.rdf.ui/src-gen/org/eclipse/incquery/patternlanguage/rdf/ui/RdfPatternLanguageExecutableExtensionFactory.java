/*
 * generated by Xtext
 */
package org.eclipse.incquery.patternlanguage.rdf.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.eclipse.incquery.patternlanguage.rdf.ui.internal.RdfPatternLanguageActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class RdfPatternLanguageExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return RdfPatternLanguageActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return RdfPatternLanguageActivator.getInstance().getInjector(RdfPatternLanguageActivator.ORG_ECLIPSE_INCQUERY_PATTERNLANGUAGE_RDF_RDFPATTERNLANGUAGE);
	}
	
}
