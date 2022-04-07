package com.silauras.footballmanager.controller;

import com.silauras.footballmanager.entity.Player;
import com.silauras.footballmanager.entity.Team;
import com.silauras.footballmanager.repository.PlayerRepository;
import com.silauras.footballmanager.repository.TeamRepository;
import com.silauras.footballmanager.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/transfer")
public class TransferController {

    private final TransferService transferService;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public TransferController(TransferService transferService, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.transferService = transferService;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @PutMapping
    public ResponseEntity<String> movePlayer(Integer playerId, Integer teamId){
        Optional<Player> player = playerRepository.findByIdAndFetchTeamEagerly(playerId);
        Optional<Team> newOwner = teamRepository.findById(teamId);
        if (player.isPresent() && newOwner.isPresent()){
            transferService.movePlayer(player.get(), newOwner.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
