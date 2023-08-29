package com.jaskarth.ecotones.world.entity.ai.system;

import java.util.List;

public interface ActionableEntity {
    List<? extends Action> getActions();
}
