package supercoder79.ecotones.config;

@Comment("Configuration file for Ecotones. This file uses the JSON format, with comments delimited by two slashes (//).\nComments are only supported on newlines.")
public class ConfigSpec {
    @Comment("Controls the size of biomes. 0 = tiny, 1 = small, 2 = medium (default), 3 = large, 4 = humungous.")
    public int biomeSize = 2;

    @Comment("Client settings.")
    public Client client = new Client();

    public static class Client {
        @Comment("Ecotones uses an enhanced skybox when in an ecotones world. You can disable that here.")
        public boolean useEcotonesSky = true;
    }
}
