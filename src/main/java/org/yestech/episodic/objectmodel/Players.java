package org.yestech.episodic.objectmodel;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author A.J. Wright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "players")
public class Players {

    @XmlAttribute
    private int width;
    @XmlAttribute
    protected int height;
    @XmlElements({
        @XmlElement(name = "player", type = Player.class)        
    })
    protected List<Player> players;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Players players1 = (Players) o;

        if (height != players1.height) return false;
        if (width != players1.width) return false;
        //noinspection RedundantIfStatement
        if (players != null ? !players.equals(players1.players) : players1.players != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + (players != null ? players.hashCode() : 0);
        return result;
    }
}
