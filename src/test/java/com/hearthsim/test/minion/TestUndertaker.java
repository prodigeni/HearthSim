package com.hearthsim.test.minion;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.BoulderfistOgre;
import com.hearthsim.card.minion.concrete.LootHoarder;
import com.hearthsim.card.minion.concrete.RaidLeader;
import com.hearthsim.card.minion.concrete.Undertaker;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUndertaker {

    private HearthTreeNode board;
    private PlayerModel currentPlayer;
    private PlayerModel waitingPlayer;

    private Deck deck;

    @Before
    public void setup() throws HSException {
        board = new HearthTreeNode(new BoardModel());
        currentPlayer = board.data_.getCurrentPlayer();
        waitingPlayer = board.data_.getWaitingPlayer();

        Minion minion0_0 = new BoulderfistOgre();
        Minion minion0_1 = new RaidLeader();
        Minion minion1_0 = new BoulderfistOgre();
        Minion minion1_1 = new RaidLeader();
        Minion minion1_2 = new Undertaker();

        currentPlayer.placeCardHand(minion0_0);
        currentPlayer.placeCardHand(minion0_1);

        waitingPlayer.placeCardHand(minion1_0);
        waitingPlayer.placeCardHand(minion1_1);
        waitingPlayer.placeCardHand(minion1_2);

        Card cards[] = new Card[10];
        for (int index = 0; index < 10; ++index) {
            cards[index] = new TheCoin();
        }

        deck = new Deck(cards);

        Card fb = new Undertaker();
        currentPlayer.placeCardHand(fb);

        currentPlayer.setMana((byte) 18);
        waitingPlayer.setMana((byte) 18);

        currentPlayer.setMaxMana((byte) 8);
        waitingPlayer.setMaxMana((byte) 8);

        HearthTreeNode tmpBoard = new HearthTreeNode(board.data_.flipPlayers());
        tmpBoard.data_.getCurrentPlayer().getHand().get(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayer().getHero(), tmpBoard, deck, null);
        tmpBoard.data_.getCurrentPlayer().getHand().get(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayer().getHero(), tmpBoard, deck, null);
        tmpBoard.data_.getCurrentPlayer().getHand().get(0).useOn(PlayerSide.CURRENT_PLAYER, tmpBoard.data_.getCurrentPlayer().getHero(), tmpBoard, deck, null);

        board = new HearthTreeNode(tmpBoard.data_.flipPlayers());
        currentPlayer = board.data_.getCurrentPlayer();
        waitingPlayer = board.data_.getWaitingPlayer();

        currentPlayer.getHand().get(0).useOn(PlayerSide.CURRENT_PLAYER, currentPlayer.getHero(), board, deck, null);
        currentPlayer.getHand().get(0).useOn(PlayerSide.CURRENT_PLAYER, currentPlayer.getHero(), board, deck, null);

        board.data_.resetMana();
        board.data_.resetMinions();
    }

    @Test
    public void test0() throws HSException {
        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.WAITING_PLAYER, 0, board, deck, null);

        assertNull(ret);

        assertEquals(currentPlayer.getHand().size(), 1);
        assertEquals(currentPlayer.getNumMinions(), 2);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(currentPlayer.getMana(), 8);
        assertEquals(waitingPlayer.getMana(), 8);
        assertEquals(currentPlayer.getHero().getHealth(), 30);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 7);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);
    }

    @Test
    public void test1() throws HSException {
        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 2, board, deck, null);

        assertFalse(ret == null);

        assertEquals(currentPlayer.getHand().size(), 0);
        assertEquals(currentPlayer.getNumMinions(), 3);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(currentPlayer.getMana(), 7);
        assertEquals(waitingPlayer.getMana(), 8);
        assertEquals(currentPlayer.getHero().getHealth(), 30);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 7);
        assertEquals(currentPlayer.getMinions().get(2).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 7);
        assertEquals(currentPlayer.getMinions().get(2).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getTotalAttack(), 7);
    }

    public void test2() throws HSException {
        currentPlayer.placeCardHand(new LootHoarder());

        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 3, board, deck, null);
        assertFalse(ret == null);

        assertEquals(currentPlayer.getHand().size(), 0);
        assertEquals(currentPlayer.getNumMinions(), 4);
        assertEquals(waitingPlayer.getNumMinions(), 3);
        assertEquals(currentPlayer.getMana(), 5);
        assertEquals(waitingPlayer.getMana(), 8);
        assertEquals(currentPlayer.getHero().getHealth(), 30);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), 7);
        assertEquals(currentPlayer.getMinions().get(2).getHealth(), 3);
        assertEquals(currentPlayer.getMinions().get(3).getHealth(), 1);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), 2);
        assertEquals(waitingPlayer.getMinions().get(2).getHealth(), 7);

        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), 7);
        assertEquals(currentPlayer.getMinions().get(2).getTotalAttack(), 3);
        assertEquals(currentPlayer.getMinions().get(3).getTotalAttack(), 3);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), 2);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), 2);
    }
}
