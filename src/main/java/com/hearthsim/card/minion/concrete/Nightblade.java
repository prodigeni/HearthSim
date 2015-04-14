package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.event.effect.EffectCharacter;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class Nightblade extends Minion implements MinionBattlecryInterface {

    public Nightblade() {
        super();
    }

    /**
     * Battlecry: Deal 4 damage to himself
     */
    @Override
    public EffectCharacter<Minion> getBattlecryEffect() {
        return (PlayerSide originSide, Minion origin, PlayerSide targetSide, int minionPlacementIndex, HearthTreeNode boardState) -> {
            HearthTreeNode toRet = boardState;
            toRet = toRet.data_.getWaitingPlayer().getHero().takeDamageAndNotify((byte) 3, PlayerSide.CURRENT_PLAYER, PlayerSide.WAITING_PLAYER, boardState, false, false);
            return toRet;
        };
    }

}
