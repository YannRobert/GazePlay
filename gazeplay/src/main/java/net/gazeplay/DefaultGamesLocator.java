package net.gazeplay;

import lombok.extern.slf4j.Slf4j;
import net.gazeplay.games.blocs.Blocs;
import net.gazeplay.games.blocs.BlocsGamesStats;
import net.gazeplay.games.bubbles.Bubble;
import net.gazeplay.games.bubbles.BubblesGamesStats;
import net.gazeplay.games.creampie.CreamPie;
import net.gazeplay.games.creampie.CreampieStats;
import net.gazeplay.games.magiccards.Card;
import net.gazeplay.games.magiccards.MagicCardsGamesStats;
import net.gazeplay.games.ninja.Ninja;
import net.gazeplay.games.ninja.NinjaStats;
import net.gazeplay.games.scratchcard.ScratchcardGamesStats;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DefaultGamesLocator implements GamesLocator {


    @Override
    public List<GameSpec> listGames() {

        List<GameSpec> result = new ArrayList<>();

        result.add(new GameSpec("Creampie", CreampieStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            CreamPie.launch(root, scene, stats);
            return stats;
        }));

        result.add(new GameSpec("Ninja Portraits", NinjaStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Ninja.launch(root, scene, stats);
            return stats;
        }));

        result.add(new GameSpec("Magic Cards (2x2)", MagicCardsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Card.addCards(root, scene, cbxGames, 2, 2, stats);
            return stats;
        }));

        result.add(new GameSpec("Magic Cards (2x3)", MagicCardsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Card.addCards(root, scene, cbxGames, 2, 3, stats);
            return stats;
        }));

        result.add(new GameSpec("Magic Cards (3x2)", MagicCardsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Card.addCards(root, scene, cbxGames, 3, 2, stats);
            return stats;
        }));

        result.add(new GameSpec("Magic Cards (3x3)", MagicCardsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Card.addCards(root, scene, cbxGames, 3, 3, stats);
            return stats;
        }));

        result.add(new GameSpec("blocks (2x2)", BlocsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Blocs.makeBlocks(scene, root, cbxGames, 2, 2, true, 1, false, stats);
            return stats;
        }));

        result.add(new GameSpec("blocks (2x3)", BlocsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Blocs.makeBlocks(scene, root, cbxGames, 2, 3, true, 1, false, stats);
            return stats;
        }));

        result.add(new GameSpec("blocks (3x3)", BlocsGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Blocs.makeBlocks(scene, root, cbxGames, 3, 3, true, 1, false, stats);
            return stats;
        }));

        result.add(new GameSpec("Carte à gratter", ScratchcardGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Blocs.makeBlocks(scene, root, cbxGames, 100, 100, false, 0.6f, true, stats);
            return stats;
        }));

        result.add(new GameSpec("Colored Bubbles", BubblesGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Bubble bubble = new Bubble(scene, root, Bubble.COLOR, stats);
            return stats;
        }));

        result.add(new GameSpec("Portrait Bubbles", BubblesGamesStats::new, (gameSpec, stats, scene, root, cbxGames) -> {
            Bubble bubble = new Bubble(scene, root, Bubble.PORTRAIT, stats);
            return stats;
        }));


        log.info("Games found : " + result.size());

        return result;
    }

}
