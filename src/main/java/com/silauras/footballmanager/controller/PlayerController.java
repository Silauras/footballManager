package com.silauras.footballmanager.controller;

import com.silauras.footballmanager.dto.response.PlayerDTO;
import com.silauras.footballmanager.entity.Player;
import com.silauras.footballmanager.entity.Team;
import com.silauras.footballmanager.repository.PlayerRepository;
import com.silauras.footballmanager.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/player")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerController(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public ResponseEntity<PlayerDTO> findPlayer(Integer playerId) {
        Optional<Player> playerFromDB = playerRepository.findByIdAndFetchTeamEagerly(playerId);
        if (playerFromDB.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PlayerDTO playerDTO;
        Player player = playerFromDB.get();
        try{
             playerDTO = new PlayerDTO(
                    player.getId(),
                    player.getName(),
                    player.getAge(),
                    player.getExperience(),
                    player.getTeam().getId()
            );
        }catch (NullPointerException ignored){
            playerDTO = new PlayerDTO(
                    player.getId(),
                    player.getName(),
                    player.getAge(),
                    player.getExperience()
            );
        }

        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<PlayerDTO>> getAll() {
        List<Player> players = playerRepository.findAllAndFetchTeamEagerly();
        List<PlayerDTO> playerDTOS = new ArrayList<>();
        for (Player player : players) {
            if (player.getTeam() != null) {
                playerDTOS.add(new PlayerDTO(
                        player.getId(),
                        player.getName(),
                        player.getAge(),
                        player.getExperience(),
                        player.getTeam().getId())
                );
            } else {
                if (player.getTeam() != null) {
                    playerDTOS.add(new PlayerDTO(
                            player.getId(),
                            player.getName(),
                            player.getAge(),
                            player.getExperience())
                    );
                }

            }
        }
        return new ResponseEntity<>(playerDTOS, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createPlayer(@RequestBody @Valid PlayerDTO playerDTO) {
        if (playerDTO.getId() != null && playerRepository.existsById(playerDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Player player = new Player();
        if (playerDTO.getId() != null && !playerRepository.existsById(playerDTO.getId())) {
            player.setId(playerDTO.getId());
        }
        player.setName(playerDTO.getName());
        player.setAge(playerDTO.getAge());
        player.setExperience(playerDTO.getExperience());
        if (playerDTO.getTeamId() != null) {
            Optional<Team> team = teamRepository.findById(playerDTO.getTeamId());
            team.ifPresent(player::setTeam);
        }
        playerRepository.saveAndFlush(player);
        return new ResponseEntity<>(player.getId().toString(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> changePlayer(PlayerDTO playerDTO) {
        if (playerDTO.getId() == null || !playerRepository.existsById(playerDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Player player = playerRepository.getById(playerDTO.getId());
        if (playerDTO.getName() != null) {
        }
        player.setName(playerDTO.getName());
        player.setAge(playerDTO.getAge());
        player.setExperience(playerDTO.getExperience());
        Optional<Team> team = teamRepository.findById(playerDTO.getTeamId());
        team.ifPresent(player::setTeam);
        playerRepository.saveAndFlush(player);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePlayer(Integer playerId) {
        playerRepository.deleteById(playerId);
        playerRepository.flush();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
