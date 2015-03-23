package com.hearthsim.card.minion;

import com.hearthsim.card.Card;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public interface MinionTargetableBattlecry {
    /**
     * Derived classes should implement this function for targtable battlecries.
     *
     * @param originSide
     * @param origin
     * @param targetMinion
     * @param boardState
     * @return
     * @throws HSException
     */
    public HearthTreeNode useTargetableBattlecry_core(PlayerSide originSide, Minion origin, PlayerSide targetSide, Minion targetMinion, HearthTreeNode boardState) throws HSException;

    public boolean canTargetWithBattlecry(PlayerSide originSide, Card origin, PlayerSide targetSide, int targetCharacterIndex, BoardModel board);
}
