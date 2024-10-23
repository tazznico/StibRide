package esi.atl.G57618.stibride.model.repository;

import esi.atl.G57618.stibride.model.dao.FavoriteDao;
import esi.atl.G57618.stibride.model.dto.FavoriteDto;
import esi.atl.G57618.stibride.model.utils.RepositoryException;

import java.util.List;

public class FavoriteRepository implements Repository<String, FavoriteDto> {

    private final FavoriteDao dao;

    public FavoriteRepository() throws RepositoryException {
        dao = FavoriteDao.getInstance();
    }

    public FavoriteRepository(FavoriteDao dao) {
        this.dao = dao;
    }

    @Override
    public String add(FavoriteDto item) throws RepositoryException {
        if (item.getKey().equals("")) {
            throw new RepositoryException("The name cannot be empty");
        }
        return dao.insert(item);
    }

    @Override
    public void update(FavoriteDto item) throws RepositoryException {
        if (item.getKey().equals("")) {
            throw new RepositoryException("The name cannot be empty");
        }
        dao.update(item);
    }

    @Override
    public void remove(String key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<FavoriteDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriteDto get(String key) throws RepositoryException {
        return dao.select(key);
    }
}
