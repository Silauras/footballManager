package com.silauras.footballmanager.repository;

import com.silauras.footballmanager.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.team WHERE p.id = (:id)")
    Optional<Player> findByIdAndFetchTeamEagerly(@Param("id") Integer id);

    // ha-ha... doesn't work
    @Query("select p from Player p  LEFT OUTER JOIN FETCH p.team")
    List<Player> findAllAndFetchTeamEagerly();
}