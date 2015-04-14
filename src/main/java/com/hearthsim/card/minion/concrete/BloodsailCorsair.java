package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.effect.EffectCharacter;
import com.hearthsim.event.effect.EffectHero;
import com.hearthsim.event.effect.EffectHeroWeaponDestroy;

public class BloodsailCorsair extends Minion implements MinionBattlecryInterface {

    private static final EffectHero effect = new EffectHeroWeaponDestroy(1);

    /**
     * Battlecry: Remove 1 Durability from your opponent's weapon.
     */
    public BloodsailCorsair() {
        super();
    }

    @Override
    public FilterCharacter getBattlecryFilter() {
        return FilterCharacter.OPPONENT;
    }

    @Override
    public EffectCharacter getBattlecryEffect() {
        return BloodsailCorsair.effect;
    }
}
