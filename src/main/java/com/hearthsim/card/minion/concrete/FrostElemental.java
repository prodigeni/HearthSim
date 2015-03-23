package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionTargetableBattlecry;
import com.hearthsim.event.battlecry.BattlecryTargetableAction;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class FrostElemental extends Minion implements MinionTargetableBattlecry {

    /**
     * Battlecry: Freeze a character
     */
    private final static BattlecryTargetableAction battlecryAction = new BattlecryTargetableAction() {
        protected boolean canTargetEnemyHero() { return true; }
        protected boolean canTargetEnemyMinions() { return true; }
//        protected boolean canTargetOwnHero() { return true; }
//        protected boolean canTargetOwnMinions() { return true; }

        @Override
        public HearthTreeNode useTargetableBattlecry_core(PlayerSide originSide, Minion origin, PlayerSide targetSide, Minion targetMinion, HearthTreeNode boardState) throws HSException {
            targetMinion.setFrozen(true);
            return boardState;
        }
    };

    public FrostElemental() {
        super();
    }

    /**
     * Let's assume that it is never a good idea to freeze your own character
     */
    @Override
    public boolean canTargetWithBattlecry(PlayerSide originSide, Card origin, PlayerSide targetSide, int targetCharacterIndex, BoardModel board) {
        return FrostElemental.battlecryAction.canTargetWithBattlecry(originSide, origin, targetSide, targetCharacterIndex, board);
    }

    @Override
    public HearthTreeNode useTargetableBattlecry_core(PlayerSide originSide, Minion origin, PlayerSide targetSide, Minion targetMinion, HearthTreeNode boardState) throws HSException {
        return FrostElemental.battlecryAction.useTargetableBattlecry_core(originSide, origin, targetSide, targetMinion, boardState);
    }
}
