package com.github.ga1robe.wallapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List getBlocks();
}

class CompositeBlockImpl implements CompositeBlock {
    private List<Block> blocks;

    CompositeBlockImpl(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public String getColor() {
        return blocks.stream().map(b -> b.getColor()).findFirst().toString();
    }

    @Override
    public String getMaterial() {
        return blocks.stream().map(b -> b.getMaterial()).findFirst().toString();
    }

    @Override
    public List getBlocks() {
        return blocks;
    }
}

class BlockImpl implements Block {
    private String color;
    private String material;

    public BlockImpl(String color, String material) {
        this.color = color;
        this.material = material;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public String getMaterial() {
        return this.material;
    }
}

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    private Stream<Block> getBlocksStream(Block block) {
        if (block instanceof CompositeBlock) {
            return Stream.concat(Stream.of(block),((CompositeBlock)block).getBlocks().stream().flatMap(b -> getBlocksStream((Block) b)));
        }
        return Stream.of(block);
    }

    private Stream<Block> getStream() {
        return this.blocks.stream().flatMap(b -> this.getBlocksStream(b));
    }

    @Override
    public Optional findBlockByColor(String color) {
        return getStream().map(b -> b.getColor()).filter(b -> b == color).findFirst();
    }

    @Override
    public List findBlocksByMaterial(String material) {
        return getStream().map(b -> b.getMaterial()).filter(b -> b == material).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int)getStream().count();
    }

    public static void main(String[] args) {
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(new BlockImpl("color1","material1"));
        blocks.add(new BlockImpl("color1","material1"));

        List<Block> blocks2 = new ArrayList<Block>();
        blocks2.add(new BlockImpl("color2","material2"));
        blocks2.add(new BlockImpl("color2","material2"));
        blocks2.add(new BlockImpl("color2","material2"));

        CompositeBlock comp = new CompositeBlockImpl(blocks2);
        blocks.add(comp);

        Wall w = new Wall(blocks);
        System.out.println(w.count());
        System.out.println(w.findBlockByColor("color1"));
        System.out.println(w.findBlocksByMaterial("material1"));
    }
}