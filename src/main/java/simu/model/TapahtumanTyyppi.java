package simu.model;

import simu.framework.ITapahtumanTyyppi;

/**
 *The TapahtumanTyyppi enum defines the types of events used in the simulation model.
 * These event types are based on the requirements of the simulation model.
 */
// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	/**
	 * Arrival event type.
	 */
	ARR1,
	/**
	 * Package machine (pakettiautomaatti) event type.
	 */
 PAKETTIAUTOMAATTI,
	/**
	 * Service selection (palvelunvalinta) event type.
	 */
 PALVELUNVALINTA,
	/**
	 * Pickup/drop-off (nouto/lähetä) event type.
	 */
 NOUTOLAHETA,
	/**
	 * Special cases (erityistapaukset) event type.
	 */
 ERITYISTAPAUKSET

}
