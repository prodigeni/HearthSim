package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;

public class ThrallmarFarseer extends Minion {

	private static final boolean HERO_TARGETABLE = true;
	private static final boolean TRANSFORMED = false;
	private static final byte SPELL_DAMAGE = 0;
	
	public ThrallmarFarseer() {
        super();
        spellDamage_ = SPELL_DAMAGE;
        heroTargetable_ = HERO_TARGETABLE;
        transformed_ = TRANSFORMED;
	}
}
