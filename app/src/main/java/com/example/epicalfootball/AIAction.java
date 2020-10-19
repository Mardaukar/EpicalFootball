package com.example.epicalfootball;

import com.example.epicalfootball.math.Position;

public class AIAction {
    private String action;
    private Position targetPosition;

    public AIAction() {
        this.action = "move";
        this.targetPosition = new Position();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
    }
}
