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
        return null;
    }

    @Override
    public String getMaterial() {
        return null;
    }

    @Override
    public List getBlocks() {
        return blocks;
    }
}

class BlockImpl implements Block {

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public String getMaterial() {
        return null;
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
        return null;
    }

    @Override
    public List findBlocksByMaterial(String material) {
        return null;
    }

    @Override
    public int count() {
        return (int)getStream().count();
    }

    public static void main(String[] args) {
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(new BlockImpl());
        blocks.add(new BlockImpl());

        List<Block> blocks2 = new ArrayList<Block>();
        blocks2.add(new BlockImpl());
        blocks2.add(new BlockImpl());
        blocks2.add(new BlockImpl());

        CompositeBlock comp = new CompositeBlockImpl(blocks2);
        blocks.add(comp);

        Wall w = new Wall(blocks);
        System.out.println(w.count());
    }
}