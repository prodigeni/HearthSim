package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.event.effect.EffectCharacter;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class MurlocTidehunter extends Minion implements MinionBattlecryInterface {

    public MurlocTidehunter() {
        super();
    }

    /**
     * Battlecry: Summon a Murloc Scout
     */
    @Override
    public EffectCharacter<Minion> getBattlecryEffect() {
        return (PlayerSide originSide, Minion origin, PlayerSide targetSide, int minionPlacementIndex, HearthTreeNode boardState) -> {
            HearthTreeNode toRet = boardState;
            if (toRet != null) {
                PlayerModel currentPlayer = boardState.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
                if (!currentPlayer.isBoardFull()) {
                    Minion mdragon = new MurlocScout();
                    toRet = mdragon.summonMinion(PlayerSide.CURRENT_PLAYER, this, boardState, false, false);
                }
            }
            return toRet;
        };
    }

}
