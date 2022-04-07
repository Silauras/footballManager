package com.silauras.footballmanager.service;

import com.silauras.footballmanager.entity.Player;
import com.silauras.footballmanager.entity.Team;
import com.silauras.footballmanager.exception.NoEnoughMoneyException;
import com.silauras.footballmanager.repository.PlayerRepository;
import com.silauras.footballmanager.repository.TeamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
public class TransferService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TransferService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public void movePlayer(Player player, Team newOwner) throws NullPointerException, NoEnoughMoneyException {
        if (player == null || newOwner == null) {
            throw new NullPointerException();
        }
        Team oldOwner = player.getTeam();

        if (oldOwner == null){
            player.setTeam(newOwner);
            playerRepository.saveAndFlush(player);
            return;
        }
        BigDecimal costOfTransfer = BigDecimal.valueOf(player.getExperience() * 100000 / player.getAge());
        costOfTransfer = costOfTransfer.add(
                costOfTransfer.multiply(
                        oldOwner.getCommission().divide(BigDecimal.valueOf(100), new MathContext(10))));

        if (costOfTransfer.compareTo(newOwner.getMoney()) <= 0) {
            newOwner.setMoney(newOwner.getMoney().subtract(costOfTransfer));
            oldOwner.setMoney(oldOwner.getMoney().add(costOfTransfer));
        } else {
            throw new NoEnoughMoneyException();
        }
        player.setTeam(newOwner);
        playerRepository.saveAndFlush(player);
        teamRepository.saveAllAndFlush(List.of(oldOwner, newOwner));
    }
}
