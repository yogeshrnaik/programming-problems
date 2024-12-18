package com.programming.agoda;

import java.util.*;

public class ExcelRow {

// Design a row class, with following functionality
// []

// SET A = 5
// [A : 5]

// SET B = &A
// [A : 5, B : 5]

// SET A = 4
// [A : 4, B : 4]

// SET C = &A+&B
// [A : 4, B : 4, C : 8]

    public static void main(String[] args) {
        Row row = new Row();
        row.set("A", 5);
        row.print();
        row.setByReference("B", "A");
        row.print();
        row.set("A", 4);
        row.print();
        row.setSum("C", "A", "B");
        row.print();
    }
}

abstract class Cell {
    public abstract Integer getValue();
}

class ValueCell extends Cell {
    Integer value;

    ValueCell(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}

class ReferenceCell extends Cell {
    Cell cell;

    ReferenceCell(Cell cell) {
        this.cell = cell;
    }

    public Integer getValue() {
        return cell.getValue();
    }

    public void setValue(Cell cell) {
        this.cell = cell;
    }

    public String toString() {
        return Integer.toString(cell.getValue());
    }
}

class SumCell extends Cell {
    Cell cell1;
    Cell cell2;

    SumCell(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Integer getValue() {
        return cell1.getValue() + cell2.getValue();
    }

    public String toString() {
        return Integer.toString(this.getValue());
    }
}

class Row {

    private Map<String, ReferenceCell> cells = new HashMap<String, ReferenceCell>();

    public void set(String cellId, Integer value) {
        if (cells.containsKey(cellId)) {
            cells.get(cellId).setValue(new ValueCell(value));
        } else {
            cells.put(cellId, new ReferenceCell(new ValueCell(value)));
        }
    }

    // Set B = &A
    public void setByReference(String cellId, String valueCellId) {
        cells.put(cellId, new ReferenceCell(cells.get(valueCellId)));
    }

    // SET C = &A+&B
    // [A : 4, B : 4, C : 8]
    public void setSum(String cellId, String cell1, String cell2) {
        cells.put(cellId, new ReferenceCell(new SumCell(cells.get(cell1), cells.get(cell2))));
    }

    public void print() {
        for (Map.Entry<String, ReferenceCell> cell : cells.entrySet()) {
            System.out.print(cell.getKey() + "=" + cell.getValue() + ", ");
        }
        System.out.println();
    }
}



