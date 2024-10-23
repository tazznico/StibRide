package esi.atl.G57618.stibride;

import esi.atl.G57618.stibride.model.dao.FavoriteDao;
import esi.atl.G57618.stibride.model.dto.FavoriteDto;
import esi.atl.G57618.stibride.model.dto.StationDto;
import esi.atl.G57618.stibride.model.utils.RepositoryException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FavoriteDaoTest {

    private final FavoriteDto home;
    private final FavoriteDto notExist;

    private final List<FavoriteDto> all;

    private FavoriteDao instance;

    public FavoriteDaoTest() {
        notExist = new FavoriteDto("notExist", new StationDto(8292, "DE BROUCKERE"), new StationDto(8382, "GARE DE L'OUEST"));
        all = new ArrayList<>();
        all.add(new FavoriteDto("other", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM")));
        home = new FavoriteDto("home", new StationDto(8641, "ERASME"), new StationDto(8742, "BEEKKANT"));
        all.add(home);

        try {
            instance = FavoriteDao.getInstance();
        } catch (RepositoryException ex) {
            org.junit.jupiter.api.Assertions.fail("Error connection to database", ex);
        }
    }

    @Test
    public void testSelectAll() throws Exception {
        List<FavoriteDto> result = instance.selectAll();
        boolean found = false;
        for (FavoriteDto item : all) {
            found = false;
            for (var itemBdd : result) {
                if (itemBdd.equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSelect() throws Exception {
        FavoriteDto result = instance.select("home");
        assertEquals(home, result);
    }

    @Test
    public void testSelectEmptyGivenValue() throws Exception {
        assertThrows(RepositoryException.class, () -> {
            instance.select(null);
        });
    }

    @Test
    public void testSelectNotExist() throws Exception {
        FavoriteDto result = instance.select(notExist.getKey());
        assertNull(result);
    }

    @Test
    public void testInsert() throws Exception {
        var newFavorite = new FavoriteDto("added0", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        List<FavoriteDto> resultBefore = instance.selectAll();
        instance.insert(newFavorite);
        List<FavoriteDto> resultAfter = instance.selectAll();
        assertEquals(resultBefore.size(), resultAfter.size() - 1);
        Assertions.assertEquals(newFavorite, instance.select("added0"));
        instance.delete("added0");
    }

    @Test
    public void testInsertEmptyGivenValue() throws Exception {
        assertThrows(RepositoryException.class, () -> {
            instance.insert(null);
        });
    }

    @Test
    public void testDelete() throws Exception {
        var newFavorite = new FavoriteDto("added2", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        instance.insert(newFavorite);
        List<FavoriteDto> resultBefore = instance.selectAll();
        instance.delete("added2");
        List<FavoriteDto> resultAfter = instance.selectAll();
        assertEquals(resultBefore.size() - 1, resultAfter.size());
        assertNull(instance.select("added2"));
    }

    @Test
    public void testDeleteEmptyGivenValue() throws Exception {
        assertThrows(RepositoryException.class, () -> {
            instance.delete(null);
        });
    }

    @Test
    public void testUpdate() throws Exception {
        var newFavorite = new FavoriteDto("added3", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        instance.insert(newFavorite);
        FavoriteDto resultBefore = instance.select("added3");

        var editedFavorite = new FavoriteDto("added3", new StationDto(8292, "DE BROUCKERE"), new StationDto(8382, "GARE DE L'OUEST"));
        instance.update(editedFavorite);
        FavoriteDto resultAfter = instance.select("added3");

        assertEquals(resultBefore.getDestination(), newFavorite.getDestination());
        assertEquals(resultBefore.getSource(), newFavorite.getSource());
        assertEquals(resultAfter.getDestination(), editedFavorite.getDestination());
        assertEquals(resultAfter.getSource(), editedFavorite.getSource());
        instance.delete("added3");
    }

    @Test
    public void testUpdateEmptyGivenValue() throws Exception {
        assertThrows(RepositoryException.class, () -> {
            instance.update(null);
        });
    }
}
