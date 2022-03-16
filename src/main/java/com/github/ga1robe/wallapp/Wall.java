package com.github.ga1robe.wallapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

public class Wall implements Structure {
    private ComposeBlock composeBlock = new ComposeBlock();

    public void addStruckBlock(String color, String material) {
        composeBlock.addBlock(new StruckBlock(color,material));
    }

    public class ComposeBlock implements CompositeBlock {
        private List<StruckBlock> struckBlockList = new ArrayList<>();

        public void addBlock(StruckBlock struckBlock){
            struckBlockList.add(struckBlock);
        }

        @Override
        public String getColor() {
            return struckBlockList.stream().map(x -> x.getColor()).collect(Collectors.joining());
        }

        @Override
        public String getMaterial() {
            return struckBlockList.stream().map(x -> x.getMaterial()).collect(Collectors.joining());
        }

        @Override
        public List getBlocks() {
            return struckBlockList;
        }
    }

    public class StruckBlock implements Block {

        private String color;
        private String material;

        public StruckBlock(String color, String material) {
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

    @Override
    public Optional findBlockByColor(String color) {
        List blocks = composeBlock.getBlocks();
        if(blocks.isEmpty())
            return Optional.empty();
        else
            return blocks.stream().map(b -> ((StruckBlock) b).getColor()).filter(b -> b.equals(color)).findAny();
    }

    @Override
    public List findBlocksByMaterial(String material) {
        List blocks = composeBlock.getBlocks();
        return (List) blocks.stream().map(b -> ((StruckBlock) b).getMaterial()).filter(b -> b.equals(material)).collect(Collectors.toList());
    }

    @Override
    public int count() {
        List blocks = composeBlock.getBlocks();
        return blocks.size();
    }
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List getBlocks();
}