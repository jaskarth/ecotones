package com.jaskarth.ecotones.client;

public final class ClientRegistrySyncState {
    public static State state = State.NONE;

    public enum State {
        NONE,
        WAITING,
        SYNCED
    }
}
