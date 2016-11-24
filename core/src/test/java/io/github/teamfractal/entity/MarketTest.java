package io.github.teamfractal.entity;

import io.github.teamfractal.entity.resource.ResourceType;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    @org.junit.jupiter.api.Test
    void buyRoboticon() {
        Market market = new Market();
        Player player = new Player();
        player.addMoney(100000);

        int roboticBefore = player.getResource(ResourceType.Robotic);
        market.buyRoboticon(player,3);
        int roboticAfter = player.getResource(ResourceType.Robotic);

        assertEquals(3, roboticAfter - roboticBefore);
    }

    @org.junit.jupiter.api.Test
    void buyFood() {

    }

    @org.junit.jupiter.api.Test
    void buyEnergy() {

    }

    @org.junit.jupiter.api.Test
    void buyOre() {

    }

}