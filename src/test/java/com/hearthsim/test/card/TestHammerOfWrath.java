package com.hearthsim.test.card;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.concrete.HammerOfWrath;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.CardDrawNode;
import com.hearthsim.util.tree.HearthTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestHammerOfWrath {

    private HearthTreeNode board;
    private PlayerModel currentPlayer;
    private PlayerModel waitingPlayer;

    private Deck deck;
    private static final byte mana = 2;
    private static final byte attack0 = 2;
    private static final byte health0 = 3;
    private static final byte health1 = 7;

    @Before
    public void setup() throws HSException {
        board = new HearthTreeNode(new BoardModel());
        currentPlayer = board.data_.getCurrentPlayer();
        waitingPlayer = board.data_.getWaitingPlayer();

        Minion minion0_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
        Minion minion0_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
        Minion minion1_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
        Minion minion1_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);

        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_0);
        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_1);

        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_0);
        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_1);

        Card cards[] = new Card[10];
        for (int index = 0; index < 10; ++index) {
            cards[index] = new TheCoin();
        }

        deck = new Deck(cards);

        currentPlayer.setMana((byte) 10);
        currentPlayer.setMaxMana((byte) 10);
    }

    @Test
    public void test0() throws HSException {
        HammerOfWrath fb = new HammerOfWrath();
        currentPlayer.placeCardHand(fb);

        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 0, board, deck, null);
        assertFalse(ret == null);
        assertEquals(currentPlayer.getHand().size(), 0);
        assertTrue(ret instanceof CardDrawNode);
        PlayerModel currentPlayer = ret.data_.modelForSide(PlayerSide.CURRENT_PLAYER);

        assertEquals(((CardDrawNode)ret).getNumCardsToDraw(), 1);
        assertEquals(currentPlayer.getNumMinions(), 2);
        assertEquals(waitingPlayer.getNumMinions(), 2);
        assertEquals(currentPlayer.getHero().getHealth(), 27);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), health0);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), health1 - 1);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), health0);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), health1 - 1);
        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), attack0);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), attack0);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), attack0);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), attack0);
        assertFalse(currentPlayer.getMinions().get(0).getCharge());
        assertFalse(currentPlayer.getMinions().get(1).getCharge());
        assertFalse(currentPlayer.getMinions().get(0).getFrozen());
        assertFalse(waitingPlayer.getMinions().get(0).getFrozen());
    }

    @Test
    public void test1() throws HSException {
        HammerOfWrath fb = new HammerOfWrath();
        currentPlayer.placeCardHand(fb);

        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.WAITING_PLAYER, 1, board, deck, null);
        assertFalse(ret == null);

        assertEquals(currentPlayer.getHand().size(), 0);
        assertTrue(ret instanceof CardDrawNode);
        assertEquals(((CardDrawNode)ret).getNumCardsToDraw(), 1);

        assertEquals(currentPlayer.getNumMinions(), 2);
        assertEquals(waitingPlayer.getNumMinions(), 1);
        assertEquals(currentPlayer.getHero().getHealth(), 30);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), health0);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), health1 - 1);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), health1 - 1);
        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), attack0);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), attack0);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), attack0);
    }
}
