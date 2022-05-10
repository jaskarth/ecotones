package supercoder79.ecotones.entity.ai.system;

import java.util.List;

public interface ActionableEntity {
    List<? extends Action> getActions();
}
