/**
 */
package org.eclipse.viatra.cep.core.metamodels.automaton;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.viatra.cep.core.metamodels.events.Event;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.viatra.cep.core.metamodels.automaton.InternalModel#getAutomata <em>Automata</em>}</li>
 *   <li>{@link org.eclipse.viatra.cep.core.metamodels.automaton.InternalModel#getLatestEvent <em>Latest Event</em>}</li>
 *   <li>{@link org.eclipse.viatra.cep.core.metamodels.automaton.InternalModel#getEnabledForTheLatestEvent <em>Enabled For The Latest Event</em>}</li>
 *   <li>{@link org.eclipse.viatra.cep.core.metamodels.automaton.InternalModel#getEventTokensInModel <em>Event Tokens In Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.viatra.cep.core.metamodels.automaton.AutomatonPackage#getInternalModel()
 * @model
 * @generated
 */
public interface InternalModel extends EObject {
    /**
     * Returns the value of the '<em><b>Automata</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.viatra.cep.core.metamodels.automaton.Automaton}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Automata</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Automata</em>' containment reference list.
     * @see org.eclipse.viatra.cep.core.metamodels.automaton.AutomatonPackage#getInternalModel_Automata()
     * @model containment="true"
     * @generated
     */
    EList<Automaton> getAutomata();

    /**
     * Returns the value of the '<em><b>Latest Event</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Latest Event</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Latest Event</em>' containment reference.
     * @see #setLatestEvent(Event)
     * @see org.eclipse.viatra.cep.core.metamodels.automaton.AutomatonPackage#getInternalModel_LatestEvent()
     * @model containment="true"
     * @generated
     */
    Event getLatestEvent();

    /**
     * Sets the value of the '{@link org.eclipse.viatra.cep.core.metamodels.automaton.InternalModel#getLatestEvent <em>Latest Event</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Latest Event</em>' containment reference.
     * @see #getLatestEvent()
     * @generated
     */
    void setLatestEvent(Event value);

    /**
     * Returns the value of the '<em><b>Enabled For The Latest Event</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.viatra.cep.core.metamodels.automaton.Automaton}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enabled For The Latest Event</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enabled For The Latest Event</em>' reference list.
     * @see org.eclipse.viatra.cep.core.metamodels.automaton.AutomatonPackage#getInternalModel_EnabledForTheLatestEvent()
     * @model
     * @generated
     */
    EList<Automaton> getEnabledForTheLatestEvent();

    /**
     * Returns the value of the '<em><b>Event Tokens In Model</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.viatra.cep.core.metamodels.automaton.EventToken}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Tokens In Model</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Event Tokens In Model</em>' reference list.
     * @see org.eclipse.viatra.cep.core.metamodels.automaton.AutomatonPackage#getInternalModel_EventTokensInModel()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     *        annotation="org.eclipse.incquery.querybasedfeature patternFQN='org.eclipse.viatra.cep.core.metamodels.derived.eventTokensInModel'"
     * @generated
     */
    EList<EventToken> getEventTokensInModel();

} // InternalModel
