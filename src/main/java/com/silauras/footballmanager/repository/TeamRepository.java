package com.silauras.footballmanager.repository;

import com.silauras.footballmanager.entity.Player;
import com.silauras.footballmanager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.id = (:id)")
    Team getByIdAndFetchPlayersEagerly(@Param("id") Integer id);

    @Query("select t from Team t LEFT join fetch t.players")
    List<Team> findAllAndFetchPlayers();
}