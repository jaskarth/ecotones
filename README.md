# Ecotones

Ecotones is an overhaul mod for Minecraft that adds over 70 unique biomes and over 200 in total in a custom world type, as well as many new things to do.
Ecotones aims to improve the natural feeling of the world by taking inspiration from the real world, so no matter where you go in a world it'll always feel different,
even if it's within the same biome.

# Features

## Biomes

Each biome generally has varying topography as applicable (foothills, hills, mountainous, etc.)

* Hot
  * Desert
  * Lush Forest
  * Lush Savanna
  * Scrubland
  * Steppe
  * Tropical Grassland
  * Tropical Rainforest
  * Tropical Woodland
* Warm
  * Cool Desert
  * Cool Scrubland
  * Lichen Woodland
  * Prarie
  * Rocky Steppe
  * Spruce Forest
  * Temperate Forest
  * Temperate Rainforest
* Cave
  * Limestone Cave
* Climatic
  * | Aspen Foothills    | Dried Forest    | Lowlands       | Pine Forest          | Sparse Forest       |
    |--------------------|-----------------|----------------|----------------------|---------------------|
    | Birch Forest       | Dry Savanna     | Lush Desert    | Pine Peaks           | Sparse Tundra       |
    | Birch Grove        | Dry Steppe      | Lush Foothills | Poplar Forest        | Spruce Fields       |
    | Birch Lakes        | Fertile Valley  | Lush Shrubland | Pumpkin Patch        | Spruce Glen         |
    | Bluebell Wood      | Flooded Savanna | Mangrove Swamp | Red Rock Ridge       | Spruce Marsh        |
    | Clay Basin         | Flower Prairie  | Maple Forest   | Rocky Mountain       | Spruce Shrubland    |
    | Clover Fields      | Granite Springs | Montane Fields | Rocky Slopes         | Sunflower Plains    |
    | Dandelion Field    | Hazel Grove     | Moor           | Rose Field           | Temperate Grassland |
    | Dark Oak Thicket   | Hot Pine Forest | Mountain Peaks | Savanna Mesa         | Thorn Brush         |
    | Dead Spruce Forest | Larch Forest    | Palm Forest    | Shield Taiga         | White Mesa          |
    | Desert Shrubland   | Lavender Field  | Pine Barrens   | Sparse Alpine Forest | Woodland Thicket    |
* Other
  * Chasm
  * Green Spires
  * Oasis
  * Uluru

Biomes include new features like:

* Overhauled river dynamics and generation
* Bushes
* Cat tails
* Duckweed
* Patches of moist farmland growing wheat
* Pumpkin farms
* Rosemary thickets
* New ore vein configurations
* Phosphate domes
* Sulfurous lakes
* Structures like outposts, cottages, and campfires
* New tree types and features like roots, leaf piles, lichen, and pinecones
* Rock structures like boulders, springs, and spires
* Drainage areas, which moisten the ground into other soils and allow thickets to become more dense

## Mobs

* Ducks!
  * Ducks wade, roost, forage, and explore each biome.

## Mechanics

* New mechanics like:
  * Sap, tapping, distillation, jars, jams, and syrup
    * Turpentine, which can strip the color out of dyed blocksit's ann
  * Dandelion poofing!
  * Asbestos, asbestos excursion, and funneling
  * Material grinding
  * Fertilizers and fertilizer distribution


# Discord Server

https://discord.gg/EwQmvQtshV

Join to talk about Ecotones and get information about the next updates!

## Building

Ecotones builds using the Gradle build system. We recommend you use IntelliJ as an IDE. Simply import the Gradle project,
set up your development workspace, and build.

* Set up your development workspace by importing the Gradle project
* Decompile Minecraft sources: `gradlew genSourcesWithQuiltflower`
  * Recommended: if you have Vineflower available (e.g. through [loom-vineflower](https://github.com/Juuxel/loom-vineflower)), instead run: `gradlew genSourcesWithVineflower`
* All dependencies are met with Maven and are subsequently compiled into the resulting jarfile.
* Build a jarfile with `gradlew build` to produce `ecotones-<version>.jar` in `./build/libs`.

## Warning

Ecotones is still in development, and large parts of it is unfinished. The mod is in a playable state, but things like the biome layout will change a lot!
