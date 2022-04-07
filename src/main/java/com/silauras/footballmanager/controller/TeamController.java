package com.silauras.footballmanager.controller;

import com.silauras.footballmanager.dto.request.TeamDTO;
import com.silauras.footballmanager.entity.Team;
import com.silauras.footballmanager.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/team")
public class TeamController {

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public ResponseEntity<Team> findTeam(Integer teamId) {
        if (!teamRepository.existsById(teamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Team team = teamRepository.getByIdAndFetchPlayersEagerly(teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);

    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Team>> getAll() {
        List<Team> teams = teamRepository.findAllAndFetchPlayers();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody TeamDTO teamDTO) {
        if (teamDTO.getId() != null && !teamRepository.existsById(teamDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        Team team = new Team();
        if (teamDTO.getId() != null && !teamRepository.existsById(teamDTO.getId())) {
            team.setId(teamDTO.getId());
        }
        team.setName(teamDTO.getName());
        team.setMoney(teamDTO.getMoney());
        team.setCommission(teamDTO.getCommission());
        teamRepository.saveAndFlush(team);
        return new ResponseEntity<>(team.getId().toString(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> changeTeam(TeamDTO teamDTO) {
        if (teamDTO.getId() == null || !teamRepository.existsById(teamDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Team team = teamRepository.getById(teamDTO.getId());
        if (teamDTO.getName() != null) {
            team.setName(teamDTO.getName());
        }
        if (teamDTO.getMoney() != null) {
            team.setMoney(teamDTO.getMoney());
        }
        if (teamDTO.getCommission() != null) {
            team.setCommission(teamDTO.getCommission());
        }
        teamRepository.saveAndFlush(team);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTeam(Integer teamId) {
        if (!teamRepository.existsById(teamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        teamRepository.deleteById(teamId);
        teamRepository.flush();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
