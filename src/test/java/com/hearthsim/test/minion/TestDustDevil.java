package com.hearthsim.test.minion;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.AngryChicken;
import com.hearthsim.card.minion.concrete.BloodfenRaptor;
import com.hearthsim.card.minion.concrete.DustDevil;
import com.hearthsim.card.minion.concrete.GoldshireFootman;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDustDevil {

    private HearthTreeNode board;
    private PlayerModel currentPlayer;
    private PlayerModel waitingPlayer;

    private Deck deck;

    @Before
    public void setup() throws HSException {
        board = new HearthTreeNode(new BoardModel());
        currentPlayer = board.data_.getCurrentPlayer();
        waitingPlayer = board.data_.getWaitingPlayer();

        Minion minion0_0 = new GoldshireFootman();
        Minion minion0_1 = new GoldshireFootman();
        Minion minion1_0 = new AngryChicken();
        Minion minion1_1 = new AngryChicken();

        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_0);
        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_1);

        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_0);
        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_1);

        Card cards[] = new Card[10];
        for (int index = 0; index < 10; ++index) {
            cards[index] = new BloodfenRaptor();
        }

        deck = new Deck(cards);

        Minion fb = new DustDevil();
        currentPlayer.placeCardHand(fb);

        currentPlayer.setMana((byte) 7);
        waitingPlayer.setMana((byte) 7);

        currentPlayer.setMaxMana((byte) 7);
        waitingPlayer.setMaxMana((byte) 7);
    }

    @Test
    public void testOverload() throws HSException {
        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 2, board, deck, null);

        assertFalse(ret == null);

        assertEquals(currentPlayer.getHand().size(), 0);
        assertEquals(currentPlayer.getNumMinions(), 3);
        assertEquals(waitingPlayer.getNumMinions(), 2);

        assertEquals(currentPlayer.getMana(), 6);
        assertEquals(waitingPlayer.getMana(), 7);

        assertEquals(currentPlayer.getHero().getHealth(), 30);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getTotalHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getTotalHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(2).getTotalHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalHealth(), 1);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 1);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 1);
        assertEquals(currentPlayer.getMinions().get(2).getTotalAttack(), 3);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 1);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 1);

        //overloaded for 2, so when resetMana is called, it should set the mana to 5
        board.data_.resetMana();
        assertEquals(currentPlayer.getMana(), 5);
        assertEquals(waitingPlayer.getMana(), 7);
    }
}
