package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.exception.HSException;
import com.hearthsim.util.tree.HearthTreeNode;

public class AncestralHealing extends SpellCard {

	/**
	 * Constructor
	 * 
	 * @param hasBeenUsed Whether the card has already been used or not
	 */
	public AncestralHealing(boolean hasBeenUsed) {
		super("Ancestral Healing", (byte)0, hasBeenUsed);
	}

	/**
	 * Constructor
	 * 
	 * Defaults to hasBeenUsed = false
	 */
	public AncestralHealing() {
		this(false);
	}

	@Override
	public Object deepCopy() {
		return new AncestralHealing(this.hasBeenUsed_);
	}
	
	/**
	 * 
	 * Use the card on the given target
	 * 
	 * This card heals the target minion up to full health and gives it taunt.  Cannot be used on heroes.
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
		if (targetMinion instanceof Hero) {
			//cant't use it on the heroes
			return null;
		}
		HearthTreeNode toRet = super.use_core(targetPlayerIndex, targetMinion, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
		if (toRet != null) {
			if (targetMinion.getHealth() < targetMinion.getMaxHealth()) 
				toRet = targetMinion.takeHeal((byte)(targetMinion.getMaxHealth() - targetMinion.getHealth()), targetPlayerIndex, boardState, deckPlayer0, deckPlayer1);
			targetMinion.setTaunt(true);
		}
		return toRet;
	}
}
