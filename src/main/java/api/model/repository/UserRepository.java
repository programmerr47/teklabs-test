package api.model.repository;

import api.model.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Cacheable(value = "username", unless="#result == null")
    User findByUsername(String username);

    @Override
    @CacheEvict("username")
    <S extends User> List<S> save(Iterable<S> users);

    @Override
    @CacheEvict("username")
    <S extends User> S save(S users);

    @Override
    @CacheEvict("username")
    void delete(Long userId);

    @Override
    @CacheEvict("username")
    void delete(User user);

    @Override
    @CacheEvict("username")
    void delete(Iterable<? extends User> users);

    @Override
    @CacheEvict("username")
    void deleteAll();

}
