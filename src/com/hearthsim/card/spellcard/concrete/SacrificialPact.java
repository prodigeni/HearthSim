package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Demon;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.exception.HSException;
import com.hearthsim.util.tree.HearthTreeNode;

public class SacrificialPact extends SpellCard {

	/**
	 * Constructor
	 * 
	 * @param hasBeenUsed Whether the card has already been used or not
	 */
	public SacrificialPact(boolean hasBeenUsed) {
		super("Sacrificial Pact", (byte)0, hasBeenUsed);
	}

	/**
	 * Constructor
	 * 
	 * Defaults to hasBeenUsed = false
	 */
	public SacrificialPact() {
		this(false);
	}

	@Override
	public Object deepCopy() {
		return new SacrificialPact(this.hasBeenUsed_);
	}
	
	/**
	 * 
	 * Use the card on the given target
	 * 
	 * Gives a destroy a demon
	 * 
	 * @param thisCardIndex The index (position) of the card in the hand
	 * @param playerIndex The index of the target player.  0 if targeting yourself or your own minions, 1 if targeting the enemy
	 * @param minionIndex The index of the target minion.
	 * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
	 * 
	 * @return The boardState is manipulated and returned
	 */
	@Override
	protected HearthTreeNode use_core(
			int targetPlayerIndex,
			Minion targetMinion,
			HearthTreeNode boardState,
			Deck deckPlayer0,
			Deck deckPlayer1,
			boolean singleRealizationOnly)
		throws HSException
	{
		if (!(targetMinion instanceof Demon)) {
			return null;
		}
		
		HearthTreeNode toRet = super.use_core(targetPlayerIndex, targetMinion, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
		if (toRet != null) {
			toRet = toRet.data_.getHero_p0().takeHeal((byte)5, 0, toRet, deckPlayer0, deckPlayer1);
			targetMinion.setHealth((byte)-99);
//			toRet = targetMinion.destroyed(targetPlayerIndex, toRet, deckPlayer0, deckPlayer1);
//			toRet.data_.removeMinion(targetPlayerIndex, targetMinion);
		}
		return toRet;
	}
}
